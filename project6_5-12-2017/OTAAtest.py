from network import LoRa
import socket
import time
import binascii
import machine

# Initialize LoRa in LORAWAN mode.
lora = LoRa(mode=LoRa.LORAWAN)


print (binascii.hexlify(machine.unique_id()))

# create an OTAA authentication parameters
app_eui = binascii.unhexlify('70B3D57ED0008902'.replace(' ',''))
app_key = binascii.unhexlify('7A 9D B9 B2 5F 32 F1 25 7D 8D B7 5F 2D 32 8D 58'.replace(' ',''))

# join a network using OTAA (Over the Air Activation)
lora.join(activation=LoRa.OTAA, auth=(app_eui, app_key), timeout=0)

# wait until the module has joined the network
while not lora.has_joined():
    time.sleep(2.5)
    print('Not yet joined...')

# create a LoRa socket
s = socket.socket(socket.AF_LORA, socket.SOCK_RAW)

# set the LoRaWAN data rate
s.setsockopt(socket.SOL_LORA, socket.SO_DR, 5)

# make the socket blocking
# (waits for the data to be sent and for the 2 receive windows to expire)
s.setblocking(True)

# send some data
s.send(bytes([0x01, 0x02, 0x03]))

# make the socket non-blocking
# (because if there's no data received it will block forever...)
s.setblocking(False)

# get any data received (if any...)
data = s.recv(64)
print(data)
