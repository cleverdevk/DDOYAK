import sys
import os

os.system("kill -9 $(ps | grep 'python3' | awk '{print $1}')")
