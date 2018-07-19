import ServoClass
import StepTest
import UltrasonicClass
import threading
import time
import RPi.GPIO as GPIO
import os
from multiprocessing import Process

def Func1():
    fr = open("count.txt", 'r')
    readstr = fr.readline()
    cnt = int(readstr)
    fr.close()
    se = ServoClass.ServoExp(cnt)
    st = StepTest.StepMotor()
    while True:
        try:
            if(se.isAlarmTime("15:19")):
                print("TRUE")
                st.step()
                #os.system("java -classpath .:classes:/opt/pi4j/lib/'*' StepMotor")
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

if __name__ == '__main__':
    p1 = Process(target=Func1,args=())
    p2 = Process(target=Func2,args=())
    p1.start()
    p2.start()
    p1.join