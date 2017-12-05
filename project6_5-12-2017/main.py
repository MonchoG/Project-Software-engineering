# See https://docs.pycom.io for more information regarding library specifics

#from pysense import Pysense
from LIS2HH12 import LIS2HH12
from SI7006A20 import SI7006A20
from LTR329ALS01 import LTR329ALS01
from MPL3115A2 import MPL3115A2,ALTITUDE,PRESSURE

#py = Pysense()
#mp = MPL3115A2(py,mode=ALTITUDE) # Returns height in meters. Mode may also be set to PRESSURE, returning a value in Pascals
si = SI7006A20()
lt = LTR329ALS01()
li = LIS2HH12()

#print(mp.temperature())
#print(mp.altitude())
#mpp = MPL3115A2(py,mode=PRESSURE) # Returns pressure in Pa. Mode may also be set to ALTITUDE, returning a value in meters
#print(mpp.pressure())
while True:
    #print("temperature", si.temperature())
    #print("hoi",si.humidity())
    #print("light",lt.light())
    print(li.acceleration())
    #print(li.roll())
    #print(li.pitch())
