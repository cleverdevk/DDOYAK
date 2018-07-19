import StepClass
import UltrasonicClass
import time
import RPi.GPIO as GPIO
import os
from multiprocessing import Process
from multiprocessing import Queue
import main_user

def CheckAlarmTime(input_time):
	year = int(input_time[0:2])
	month = int(input_time[3:5])
	hour = int(input_time[6:8])
	min = int(input_time[9:])
	c_year = int(time.strftime('%Y'))
	c_month = int(time.strftime('%M'))
	c_hour = int(time.strftime('%H'))
	c_min = int(time.strftime('%M'))
	time.sleep(5)
	if(hour == c_hour and min == c_min and year == c_year and month == c_month):
		return True
	else:
		return False	

def runMotor(alarmTime):
	sm = StepClass.StepMotor()
	while True:
		try:
			if(CheckAlarmTime(alarmTime)):
				print("TRUE")
				sm.step()
				time.sleep(3)
				break
		except KeyboardInterrupt:
			break

def runUltrasonic():
	us = UltrasonicClass.Ultrasonic()
	while True:
		try:
			print(us.getDistance())
			time.sleep(5)
		except KeyboardInterrupt:
			break
			us.Cleanup()
			
def runUserServer(queue):
        server_user = main_user.SocketServer(35357,queue)
        server_user.run()
def runGuardianServer():
        server_guardian = main_user.SocketServer(35358,queue)
        server_guardian.run()
if __name__ == '__main__':
	
	alarmQueue = Queue()
	proc_ultra = Process(target=runUltrasonic,args=())
	proc_userver = Process(target=runUserServer,args=(alarmQueue,))
	proc_gserver = Process(target=runGuardianServer,args=())
	
	#alarmQueue.put("19:34")
	#alarmQueue.put("19:36")
	#alarmQueue.put("19:38")
	proc_ultra.start()
	proc_userver.start()
	proc_gserver.start()
	while True:
		nextAlarmTime = alarmQueue.get()
		
		print('[',nextAlarmTime,']')
		proc_motor = Process(target=runMotor,args=(nextAlarmTime,))
		proc_motor.start()
		proc_motor.join()

			
	proc_ultra.join()
	proc_userver.join()
	proc_gserver.join()
			