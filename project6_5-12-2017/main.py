# See https://docs.pycom.io for more information regarding library specifics

#from pysense import Pysense
#from LIS2HH12 import LIS2HH12
from SI7006A20 import SI7006A20
from LTR329ALS01 import LTR329ALS01
from MPL3115A2 import MPL3115A2,ALTITUDE,PRESSURE


from network import LoRa
import time
import binascii

lora = LoRa(mode=LoRa.LORAWAN)

app_eui = binascii.unhexlify('70B3D57ED0008A37')
app_key = binascii.unhexlify('A174D4D145F7ADB02A530F795E367CB7')

lora.join(activation=LoRa.OTAA, auth=(app_eui, app_key), timeout=0)

# wait until the module has joined the network



#py = Pysense()
#mp = MPL3115A2(py,mode=ALTITUDE) # Returns height in meters. Mode may also be set to PRESSURE, returning a value in Pascals
si = SI7006A20()
lt = LTR329ALS01()
#li = LIS2HH12()
mp = MPL3115A2()

#print(mp.temperature())
#print(mp.altitude())
#mpp = MPL3115A2(py,mode=PRESSURE) # Returns pressure in Pa. Mode may also be set to ALTITUDE, returning a value in meters
#print(mpp.pressure())
while True:
    print("\n")
    a=round(si.temperature())
    b=round(si.light())
    c=round(mp.pressure()/10000)
    print("temperature", a)


    print("hum",si.humidity())
    print("light",lt.light())
    print("pressure",mp.pressure())
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
    s.send(bytes([a,b,c]))
    time.sleep(2.5)
