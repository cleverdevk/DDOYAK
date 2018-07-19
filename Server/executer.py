import os
import sys

class Executer:
    def __init__(self, tcpServer, timeQueue):
        self.andRaspTCP = tcpServer
        self.timeQueue = timeQueue
        self.AlarmTime = ""
    def startCommand(self, command):
        self.AlarmTime = command
        print(" [USER SERVER] Received Time : ",self.AlarmTime)
    def getAlarmTime(self):
        return self.AlarmTime
	#	if command[0:1] == "1":
        #                print("Received Time : ", )
	#		self.andRaspTCP.sendAll("1\n")
	#		self.Alarmtime = 
	#	elif command == "2\n":
	#		print("COM 2 is Executed")
	#		self.andRaspTCP.sendAll("2\n")
	#	else:
	#		print("Default Command is Executed\nReceived Messsage : %s" % command)
	#		self.andRaspTCP.sendAll("Default is executed\n")
