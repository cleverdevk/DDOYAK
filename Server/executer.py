import os
import sys

class Executer:
	def __init__(self, tcpServer):
		self.andRaspTCP = tcpServer
	def startCommand(self, command):
		if command == "1\n":
        	#	print("COM 1 is Executed")
			self.andRaspTCP.sendAll("1\n")
			os.system("python3 ~/ServerTest/hello.py")
		elif command == "2\n":
			print("COM 2 is Executed")
			self.andRaspTCP.sendAll("2\n")
		else:
			print("Default Command is Executed")
			self.andRaspTCP.sendAll("Default is executed\n")
