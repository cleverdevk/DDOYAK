import sys
import threading
import tcpServer
import executer
from multiprocessing import Queue
import time
 
# make public queue
commandQueue = Queue()
 
# init module
andRaspTCP = tcpServer.TCPServer(commandQueue, "", 35357)
andRaspTCP.start()
 
 
# set module to executer
commandExecuter = executer.Executer(andRaspTCP)
 
def gettingMsg():
    while True:
        try:
            command = commandQueue.get()
            commandExecuter.startCommand(command)
        except:
            pass
def sendingMsg(): 
    while True:
        sys.stdout.write(">>")
        data = input()
        data = data.encode("utf-8") 
        data = str(data).split("b'",1)[1].rsplit("'",1)[0]
        data = str(data)+ '\n' 
        andRaspTCP.sendAll(data)
threading._start_new_thread(sendingMsg,())
threading._start_new_thread(gettingMsg,())

while True:
    pass
