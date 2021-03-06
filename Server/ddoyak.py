import StepClass
import UltrasonicClass
import time
import RPi.GPIO as GPIO
import os
from multiprocessing import Process
from multiprocessing import Queue
import main_user

# compare time parameter with current time and return boolean value, time format = "YY:MM:DD:hh:mm"
def CheckAlarmTime(input_time):
	year = int(input_time[0:2])
	month = int(input_time[3:5])
	hour = int(input_time[6:8])
	min = int(input_time[9:])
	c_year = int(time.strftime('%y'))
	c_month = int(time.strftime('%m'))
	c_hour = int(time.strftime('%H'))
	c_min = int(time.strftime('%M'))
	time.sleep(5)
	if(hour == c_hour and min == c_min and year == c_year and month == c_month):
		return True
	else:
		return False	

# Function of Ultrasonic for Threading
def runUltrasonic(distanceQueue):
	us = UltrasonicClass.Ultrasonic()
	while True:
		try:
			distanceQueue.put_nowait(us.getDistance())
			#print(us.getDistance())
			time.sleep(2)
		except KeyboardInterrupt:
			break
			us.Cleanup()

#Function of StepMotor for Threading, using Func <CheckAlarmTime> and Operating StepMotor
def runMotor(alarmTime):
	sm = StepClass.StepMotor()
	distanceQueue = Queue()
	proc_ultra = Process(target=runUltrasonic,args=(distanceQueue,))
	proc_ultra.start()
	while True:
		try:
			if(CheckAlarmTime(alarmTime)):
				print("TRUE")
				sm.step()
				time.sleep(5)
				print(distanceQueue.get())
				break
		except KeyboardInterrupt:
			break
	proc_ultra.join()

#Function of User Server for Threading
def runUserServer(queue):
        server_user = main_user.SocketServer(35357,queue)
        server_user.run()
#Function of Guardian Server for Threading
def runGuardianServer(queue):
        server_guardian = main_user.SocketServer(35358,queue)
        server_guardian.run()

#main function
if __name__ == '__main__':
	tempQueue = Queue()
	alarmQueue = Queue()
	proc_ultra = Process(target=runUltrasonic,args=(5,))
	proc_userver = Process(target=runUserServer,args=(alarmQueue,))
	proc_gserver = Process(target=runGuardianServer,args=(tempQueue,))
	
	alarmQueue.put("18:07:14:32")
	alarmQueue.put("18:07:14:32")
	alarmQueue.put("18:07:14:32")
	proc_ultra.start()
	proc_userver.start()
	proc_gserver.start()
	
	
	
	while True:
		nextAlarmTime = alarmQueue.get()
		
		print('[ NEXT ALARM TIME : ',nextAlarmTime,']')
		proc_motor = Process(target=runMotor,args=(nextAlarmTime,))
		proc_motor.start()
		proc_motor.join()

			
	proc_ultra.join()
	proc_userver.join()
	proc_gserver.join()
			