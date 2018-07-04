import RPi.GPIO as GPIO
import time

class ServoExp:
    def __init__(self,cnt):
        self.GPIO = GPIO
        self.GPIO.setmode(GPIO.BCM)
        self.GPIO.setup(18, GPIO.OUT)
        self.p = GPIO.PWM(18,40)
        self.p.start(0)
        self.cnt = cnt
    def rotate(self,cnt):
        self.cnt += 1
        self.p.ChangeDutyCycle(cnt%8 +1)
        time.sleep(1)
    def Cleanup(self):
        self.p.stop()
        self.GPIO.cleanup()
    def getCnt(self):
        return self.cnt
    def isAlarmTime(self,input_time):
        hour = int(input_time[0:2])
        min = int(input_time[3:])
        currenthour = int(time.strftime('%H'))
        currentmin = int(time.strftime('%M'))
        print(hour,min,currenthour,currentmin)
        time.sleep(2)
        if(hour == currenthour and currentmin == min):
            return True
        else:
            return False