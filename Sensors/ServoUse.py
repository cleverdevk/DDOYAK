import ServoClass
import UltrasonicClass
import threading
import time
import RPi.GPIO as GPIO

def Func1():
    fr = open("count.txt", 'r')
    readstr = fr.readline()
    cnt = int(readstr)
    fr.close()
    se = ServoClass.ServoExp(cnt)
    while True:
        try:
            if(se.isAlarmTime("16:28")):
                print("TRUE")
                se.rotate(cnt)
                cnt+=1
        except KeyboardInterrupt:
            break
            
    fw = open("count.txt",'w')
    fw.write(str(se.getCnt()))
    se.Cleanup()
def Func2():
    us = UltrasonicClass.Ultrasonic()
    while True:
        try:
            print(us.getDistance())
            time.sleep(2)
        except KeyboardInterrupt:
            break
    us.Cleanup()

threading._start_new_thread(Func1,())
threading._start_new_thread(Func2,())

while True:
    pass