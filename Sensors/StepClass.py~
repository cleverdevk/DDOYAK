import time
import sys
import RPi.GPIO as GPIO


class StepMotor:
    def __init__(self):
        self.GPIO = GPIO
        self.GPIO.setmode(GPIO.BCM)
        self.GPIO.setwarnings(False)
        self.GPIO.setup(17, GPIO.OUT)
        self.GPIO.setup(18, GPIO.OUT)
        self.GPIO.setup(27, GPIO.OUT)
        self.GPIO.setup(22, GPIO.OUT)

    def step_4(self,p):
        if p==0:
            self.GPIO.output(17,0)
            self.GPIO.output(18,0)
            self.GPIO.output(27,0)
            self.GPIO.output(22,0)
        if p==1:
            self.GPIO.output(17,1)
            self.GPIO.output(18,1)
            self.GPIO.output(27,0)
            self.GPIO.output(22,0)
        if p==2:
            self.GPIO.output(17,0)
            self.GPIO.output(18,1)
            self.GPIO.output(27,1)
            self.GPIO.output(22,0)
        if p==3:
            self.GPIO.output(17,0)
            self.GPIO.output(18,0)
            self.GPIO.output(27,1)
            self.GPIO.output(22,1)
        if p==4:
            self.GPIO.output(17,1)
            self.GPIO.output(18,0)
            self.GPIO.output(27,0)
            self.GPIO.output(22,1)
            
    def steps_4(self,value):
        print(value)
        pas=0
        if(value<0):
            for i in range(0,abs(value)):
                self.step_4(pas)
                time.sleep(0.005)
                pas+=1
                if(pas>=4):
                    pas=0
        else:
            for i in range(0,abs(value)):
                self.step_4(pas)
                time.sleep(0.005)
                if(pas==0):
                    pas=4
                pas-=1
            self.step_4(0)
    def rotate(self,count):
        pas=0
        self.step_4(0)
        self.steps_4(count*2100)
    def step(self):
        pas=0
        self.step_4(0)
        self.steps_4(370)
    def rstep(self):
        pas = 0
        self.step_4(0)
        self.steps_4(-370)
        

