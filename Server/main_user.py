import sys
import threading
import tcpServer
import executer
from multiprocessing import Queue
from multiprocessing import Process
import time
import queue

class SocketServer:
    def __init__(self,port,timeQueue):
        # make public queue
        self.commandQueue = Queue()
        self.timeQueue = timeQueue
        self.port = port
        # init module
        self.andRaspTCP = tcpServer.TCPServer(self.commandQueue, "", port, self.timeQueue)
        
     
     
        # set module to executer
        self.commandExecuter = executer.Executer(self.andRaspTCP, self.timeQueue)
     
    def gettingMsg(self):
        if(self.port == 35357):
            print("[USER SERVER] Getting Thread is Executing..")
        elif(self.port == 35358):
            print("[GUARDIAN SERVER] Getting Thread is Executing..")
        while True:
            try:
                self.command = self.commandQueue.get()
                #self.timeQueue.put(self.command)
                self.commandExecuter.startCommand(self.command)
            except:
                pass
    def sendingMsg(self):
        if(self.port == 35357):
            print("[USER SERVER] Sending Thread is Executing..")
        elif(self.port == 35358):
            print("[GUARDIAN SERVER] Sending Thread is Executing..")
        while True:
            pass
    #        sys.stdout.write(">>")
    #        data = input()
    #        data = data.encode("utf-8") 
    #        data = str(data).split("b'",1)[1].rsplit("'",1)[0]
    #        data = str(data)+ '\n' 
    #        andRaspTCP.sendAll(data)
        
    def run(self):
        self.andRaspTCP.start()
        proc_sending = Process(target=self.sendingMsg,args=())
        proc_getting = Process(target=self.gettingMsg,args=())
        proc_getting.start()
        proc_sending.start()
        
        

