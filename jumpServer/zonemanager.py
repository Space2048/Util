#!/usr/bin/python3
# -*- coding: utf-8 -*-

import os,re,sys,subprocess,platform

osName = platform.system()

zoneListFile = "./zonelist.txt"
releaseFileZoneID = "11"
SSH_COMMAND = "ssh -oStrictHostKeyChecking=no -i ~/.ssh/id_rsa "

zoneOptionHead = """
--------------------------------------
q	退出
"""

zoneOptionTail = """
--------------------------------------
请输入区编号:
"""

functionMap = {}
functionMap["q"] = "回到上层"
functionMap["s"] = "登录到区"
functionMap["r"] = "重启服务器"
functionMap["p"] = "关闭服务器"

zoneTypeFuncMap = {}
zoneTypeFuncMap["0"] = ["q", "s"]
zoneTypeFuncMap["1"] = ["q", "s"]

checkAssertLogOption = """
--------------------------------------
q	回到上层
--------------------------------------
"""

def getSshCmdStr(zone):
	if not zone in zoneList:
		return ""
	zoneInfo = zoneList[zone];
	text = ""
	if osName == "Linux":
		text = SSH_COMMAND + zoneInfo["username"] + "@" + zoneInfo["ip"] + " -p " + zoneInfo["port"] + " -t \'cd " + zoneInfo["workdir"] + "; exec bash\'"
	elif osName == "Windows":
		text = "PLINK.EXE -ssh -i private.ppk " + zoneInfo["username"] + "@" + zoneInfo["ip"] + ":/data/isekai"
	return text

def initZoneList():
	zoneFile = open(zoneListFile, "r", encoding='UTF-8')
	zoneInfo = ""
	global zoneList, zoneOptionList
	zoneList = {}
	zoneOptionList = ""
	for zone in zoneFile:
		if zone.find("#") != -1: continue
		zoneInfo = zone.split("|")
		print(zoneInfo)
		tempDict = {}
		tempDict["id"] = zoneInfo[0].rstrip()
		tempDict["name"] = zoneInfo[1].rstrip()
		tempDict["type"] = zoneInfo[2].rstrip()
		tempDict["username"] = zoneInfo[3].rstrip()
		tempDict["ip"] = zoneInfo[4].rstrip()
		tempDict["port"] = zoneInfo[5].rstrip()
		tempDict["workdir"] = zoneInfo[6].rstrip()
		zoneList[zoneInfo[0]] = tempDict
		zoneOptionList += zoneInfo[0] + "\t" + zoneInfo[1] + "\n"


def showFunctionByZone(zone):
	zoneInfo = zoneList[zone]
	while True:
		print ("----------当前区：" + zone + "[" + zoneInfo["name"] + "]---------")
		for functionKey in zoneTypeFuncMap[zoneInfo["type"]]:
			print (functionKey + " " + functionMap[functionKey])
		print ("---------------请选择操作--------------")
		reply = input()
		initZoneList()
		if reply == "q":
			break
		elif reply == "s":
			logonToZone(zone, "")
		elif reply == "r":
			restartServer(zone)
		elif reply == "p":
			stopServer(zone)
		else:
			continue


def showZoneOption():
	reply = input(zoneOptionHead + zoneOptionList + zoneOptionTail)
	print ("--------------!!!!--------------")
	return reply


#return 0停止状态 1运行状态
#已AutoRun为标记，不准确，不启用
def getServerStatus(zone):
	if not zone in zoneList:
		return False
	
	zoneInfo = zoneList[zone]
	text = getSshCmdStr(zone) + " \"cd /data/isekai/; ps -ef | grep \"skynet.*gate-srv\" | grep -v grep | wc -l \""
	serverStatus = subprocess.check_output(text)
	return serverStatus

def logonToZone(zone, userName):
	if not zone in zoneList:
		return False

	zoneInfo = zoneList[zone]
	if userName == "":
		 userName = zoneInfo["username"]
	if zoneInfo["type"] != "3":
		print ("登录到服务器：" + userName + "@" + zoneInfo["ip"])
		text = getSshCmdStr(zone)
		os.system(text)
		return True

def restartServer(zone):
	if not zone in zoneList:
		return False
	
	zoneInfo = zoneList[zone]
	#内网版本,外网版本
	if zoneInfo["type"] == "0" or zoneInfo["type"] == "1":
		print("开始更新重启服务器!")
		text = getSshCmdStr(zone) + " \"cd /data/isekai/shell; chmod u+x ./restart.sh; (./restart.sh &) >/dev/null </dev/null 2>&1;\""
		os.system(text)
	else: return False
	print("服务器更新启动完成!")
	return True

def startServer(zone):
	if not zone in zoneList:
		return False
	
	if getServerStatus(zone) != "0":
		print ("!!!!!!!!!!!!!!!!服务器正在运行，无需启动!!!!!!!!!!!!!!!!!!!!")
		return True
	zoneInfo = zoneList[zone]
	#内网版本,外网版本
	if zoneInfo["type"] == "0" or zoneInfo["type"] == "1":
		text = getSshCmdStr(zone) + " \"cd /data/isekai/shell; chmod u+x ./restart.sh; (./restart.sh &) >/dev/null </dev/null 2>&1;\""
		os.system(text)
	else: return False

	return True

def stopServer(zone):
	if not zone in zoneList:
		return False
	
	zoneInfo = zoneList[zone]
	#内网版本,外网版本
	if zoneInfo["type"] == "0" or zoneInfo["type"] == "1":
		text = getSshCmdStr(zone) + " \"cd /data/isekai/shell; chmod u+x ./restart.sh; (./restart.sh &) >/dev/null </dev/null 2>&1;\""
		os.system(text)
	else: return False

	return True

def mainMenu():
	while True:
		reply = showZoneOption()
		initZoneList()
		if reply == "q":
			break
		elif reply in zoneList:
			showFunctionByZone(reply)
		else:
			continue

initZoneList()
#传参直接调用函数
if len(sys.argv) > 2:
	param = sys.argv[1]
	value = sys.argv[2]
	if param == "show_server_status":
		print (getServerStatus(value))
	elif param == "start_server":
		startServer(value)
	elif param == "restart_server":
		restartServer(value)
	elif param == "stop_server":
		stopServer(value)
#进入交互模式
else:
	mainMenu()

