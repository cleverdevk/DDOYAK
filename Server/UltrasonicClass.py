import RPi.GPIO as GPIO
import time

class Ultrasonic:
    def __init__(self):
        self.GPIO = GPIO
        self.GPIO.setmode(GPIO.BCM)
        self.trig = 13
        self.echo = 19
        self.GPIO.setup(self.trig, GPIO.OUT)
        self.GPIO.setup(self.echo, GPIO.IN)
        self.pulse_start=0
        self.pulse_end=0
        self.pulse_duration=0
        self.distance=0
    
    def getDistance(self):
        self.GPIO.output(self.trig, False)
        time.sleep(0.5)
        
        self.GPIO.output(self.trig, True)
        time.sleep(0.00001)
        self.GPIO.output(self.trig, False)
        
        while self.GPIO.input(self.echo) == 0:
            self.pulse_start = time.time()
            
        while self.GPIO.input(self.echo) ==1 :
            self.pulse_end = time.time()
            
        self.pulse_duration = self.pulse_end - self.pulse_start
        self.distance = self.pulse_duration * 17000
        self.distance = round(self.distance, 2)
        
        return self.distance
    
    def Cleanup(self):
        self.GPIO.cleanup()