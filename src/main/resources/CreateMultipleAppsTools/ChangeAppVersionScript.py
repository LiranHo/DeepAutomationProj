import subprocess
import sys
import time
from xml.dom import minidom
import xml.etree.ElementTree as ET
import os

currentDir = os.path.dirname(os.path.realpath(__file__))
# currentDir = str(sys.argv[3])
# currentDir = "E:\\FilesLiran2\\Experitest_Git\\DeepAutomationProj\\src\main\\resources\\CreateMultipleAppsTools"
# Dir = "C:\\Users\\liran.hochman\\Documents\\A\\eribank"
print ("PYTHON currentDir: "+os.path.dirname(os.path.realpath(__file__)))


def decomplie(applicationName ):
    apk = currentDir+"\\"+applicationName+".apk"
    #target = "e1"
    subprocess.run(["java", "-jar", currentDir+"\\apktool_2.4.0.jar","-f", "decode", apk ,"-o",currentDir+"\\"+applicationName])
    print("decomplie finished")


def recomplie(applicationName):
    apkFolder = currentDir+"\\"+applicationName
    subprocess.run(["java", "-jar", currentDir+"\\apktool_2.4.0.jar", "b", apkFolder,applicationName+".apk"])


def changeVersion(applicationName, manifestName):
    versionName = str(int(round(time.time() * 1000))%1000000000)
    print(versionName)

    manifestPath = currentDir+"\\"+applicationName+"\\"+manifestName+'.xml'
    tree = ET.parse (manifestPath)
    root = tree.getroot ()
    for elem in root.iter ('manifest'):
        elem.set ('platformBuildVersionCode', versionName)
        elem.set ('platformBuildVersionName', "1."+versionName)
        elem.set ('ns0:versionCode', versionName)
        elem.set ('ns0:versionName', "1."+versionName)

    tree.write (manifestPath)

# decomplie("eribank")
# changeVersion("eribank","AndroidManifest")
# recomplie("eribank")

# print('Number of arguments:', len(sys.argv), 'arguments.')
Number_of_arguments = len(sys.argv)
print('Argument List:', str(sys.argv))
if(Number_of_arguments<3):
    print ("ERROR please enter application name and manifest name")
else:
    decomplie(str(sys.argv[1]))
    changeVersion(str(sys.argv[1]),str(sys.argv[2]))
    recomplie(str(sys.argv[1]))


# print('1:', str(sys.argv[1]))
# print('2:', str(sys.argv[2]))
# print('3:', str(sys.argv[3]))


