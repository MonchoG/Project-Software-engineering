

from SI7006A20 import SI7006A20
from LTR329ALS01 import LTR329ALS01
from MPL3115A2 import MPL3115A2,ALTITUDE,PRESSURE

from network import LoRa
import time
import binascii

si = SI7006A20()
lt = LTR329ALS01()
mp = MPL3115A2()

lora = LoRa(mode=LoRa.LORAWAN)
app_eui = binascii.unhexlify('70B3D57ED0008A37')
app_key = binascii.unhexlify('A174D4D145F7ADB02A530F795E367CB7')
lora.join(activation=LoRa.OTAA, auth=(app_eui, app_key), timeout=0)


while True:
    print("\n")
    temp=(round(si.temperature())+50)

    light=round(lt.light())
    light1= (int) ((light & 0xff0000)>>16)
    light2= (int) ((light & 0x00ff00)>>8)
    light3= (int) (light & 0x0000ff)

    press=round(mp.pressure())
    press1= (int) ((press & 0xff0000)>>16)
    press2= (int) ((press & 0x00ff00)>>8)
    press3= (int) (press & 0x0000ff)

    hum= round(si.humidity())

    datas = bytes([temp, hum , light1,light2,light3, press1, press2, press3])

    print("temperature", (temp))
    print("hum",hum)
    print("light",light)
    print("presure",press)
    time.sleep(0.5)



#wacht voor conectie
    while not lora.has_joined():
        time.sleep(2.5)
        print('Not joined yet...')

    print('Network joined!')

#send to ttn
    import socket
    s = socket.socket(socket.AF_LORA, socket.SOCK_RAW)
    s.setsockopt(socket.SOL_LORA, socket.SO_DR, 5)
    s.setblocking(False)
    s.send(datas)

    time.sleep(50)
