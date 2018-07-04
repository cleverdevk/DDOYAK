import RPi.GPIO as GPIO
import time
 
pin = 18 # PWM pin num 18
 
GPIO.setmode(GPIO.BCM)
GPIO.setup(pin, GPIO.OUT)
p = GPIO.PWM(pin, 40)
p.start(0)
cnt = 0
try:
    while True:
        p.ChangeDutyCycle(cnt % 8+1)
        print("angle : %d" % (cnt%8 + 1))
        cnt += 1
        time.sleep(1)
except KeyboardInterrupt:
    p.stop()
    GPIO.cleanup()
GPIO.cleanup()
