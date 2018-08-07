from os import listdir
from os.path import isfile, join


mypath = "/home/david/Desktop/Programieren/java/SpaceZero/src/main/resources/icons/files/"
string = "["
onlyfiles = [f for f in listdir(mypath) if isfile(join(mypath, f))]
for file in onlyfiles:
    string += "\"" + file[:-4] + "\", ";

print(string + "]")
