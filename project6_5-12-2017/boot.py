# boot.py -- run on boot-up
# See https://docs.pycom.io for more information regarding library specifics

#from pysense import Pysense
#from LIS2HH12 import LIS2HH12
from SI7006A20 import SI7006A20
from LTR329ALS01 import LTR329ALS01
from MPL3115A2 import MPL3115A2,ALTITUDE,PRESSURE
import time

from network import LoRa
import time
import binascii

lora = LoRa(mode=LoRa.LORAWAN)
#credentials to connect to the lora network
app_eui = binascii.unhexlify('70B3D57ED0008A37')
app_key = binascii.unhexlify('A174D4D145F7ADB02A530F795E367CB7')

lora.join(activation=LoRa.OTAA, auth=(app_eui, app_key), timeout=0)





#py = Pysense()
#mp = MPL3115A2(py,mode=ALTITUDE) # Returns height in meters. Mode may also be set to PRESSURE, returning a value in Pascals
si = SI7006A20() #humidity 
lt = LTR329ALS01() 
#li = LIS2HH12()
mp = MPL3115A2()

#print(mp.temperature())
#print(mp.altitude())
#mpp = MPL3115A2(py,mode=PRESSURE) # Returns pressure in Pa. Mode may also be set to ALTITUDE, returning a value in meters
#print(mpp.pressure())
while True:
    print("\n")
    temp=(round(si.temperature())+273)
    temp1= (temp & 0xFF00) >> 8
    temp2= (temp & 0x00FF)

    light=round(si.light()) # split the data in multiple packages. 1 package can only contain 255 bits of data.
    light1= (int) ((light & 0xff0000)>>16)
    light2= (int) ((light & 0x00ff00)>>8)
    light3= (int) (light & 0x0000ff)



    press=round(mp.pressure()) #split the data in multiple packages. bar will be measured with decimal points
    press1= (int) ((press & 0xff0000)>>16)
    press2= (int) ((press & 0x00ff00)>>8)
    press3= (int) (press & 0x0000ff)

    hum= round(si.humidity())




    print("temperature", (temp-273)) #temp is internally measured in celcius but convert to kelvin to make minus 0 measurement available
    print("hum",hum)
    print("light",light)
    print("presure",press)
    time.sleep(0.5)
    #print(li.acceleration())
    #print(li.roll())
    #print(li.pitch())


    while not lora.has_joined():
        time.sleep(2.5)
        print('Not joined yet...')

    print('Network joined!')
    import socket
    s = socket.socket(socket.AF_LORA, socket.SOCK_RAW)
    s.setsockopt(socket.SOL_LORA, socket.SO_DR, 5)
    s.setblocking(False)
    #a=round(si.temperature())
    s.send(bytes([temp1,temp2, hum , light1,light2,light3, press1, press2, press3]))
    time.sleep(50)
