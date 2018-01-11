

from SI7006A20 import SI7006A20
from LTR329ALS01 import LTR329ALS01
from MPL3115A2 import MPL3115A2,ALTITUDE,PRESSURE

from network import LoRa
import time
import binascii

si = SI7006A20()
lt = LTR329ALS01()
mp = MPL3115A2()

#sla de gegevens op van ttn 
lora = LoRa(mode=LoRa.LORAWAN)
app_eui = binascii.unhexlify('70B3D57ED0008A37')
app_key = binascii.unhexlify('A174D4D145F7ADB02A530F795E367CB7')
lora.join(activation=LoRa.OTAA, auth=(app_eui, app_key), timeout=0)


while True:
    #de temperatuur is gecapt tot minimaal -50 graden celcius
    temp=(round(si.temperature())+50)
    # het licht varieert het meeste en hier heeft die bereik van 0 tot 64000 ongeveer
    # wordt met lux gemeten
    light=round(lt.light())
    light2= (int) ((light & 0x00ff00)>>8)
    light3= (int) (light & 0x0000ff)
    #het presure varieert meestal tussen 0.8... en 1.2.... het wordt met Pa gemeten
    press=round(mp.pressure()-80000)
    press2= (int) ((press & 0x00ff00)>>8)
    press3= (int) (press & 0x0000ff)

    hum= round(si.humidity())

    datas = bytes([temp, hum ,light2,light3, press2, press3])
      
    print("\n")
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
