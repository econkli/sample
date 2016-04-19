#---------------------------------------------
#Emily Conklin
#2/10/2015

#This program takes conserved synteny data as input and generates heat maps
#Creates grid with the chromosomes of one species along the x axis and
#chromosomes of another species along the y axis. Chromosomes pairs with
#more conservation are indicated by hot-colored squares in the grid, while
#pairs with less conservation are indicated by cool-colored squares
#----------------------------------------------

#!/usr/bin/python
import numpy as np
import matplotlib.pyplot as plt
import re

# setup a grid populated by random integers
# eventually this will be your oxford grid data
candidateFile = open("tetraodonzebrafishCandidate.txt","r")
lines = candidateFile.readlines()

lines1_seen = []
lines2_seen = []
sp1List = []
sp2List = []

#set up lists of chromosomes
for aLine in lines:
    aLine = aLine.split()
    chromo1 = aLine[1]
    chromo2 = aLine[3]
    if chromo1 not in lines1_seen and chromo1[0].isdigit() and "random" not in chromo1:
        lines1_seen.append(chromo1)
    
    '''
    #for anolis
    if chromo1 not in lines1_seen and chromo1.startswith("LG"):
        lines1_seen.append(chromo1)
    #for human
    if chromo1 not in lines1_seen and (chromo1=="X" or chromo1=="Y"):
        lines1_seen.append(chromo1)
   
    if chromo2 not in lines2_seen and chromo2[0].isdigit():
        lines2_seen.append(chromo2)
    '''
    if chromo2 not in lines2_seen and chromo2[0].isdigit():
        lines2_seen.append(chromo2)
    '''
    #for stickle
    if chromo2 not in lines2_seen and chromo2.startswith('g'):
        lines2_seen.append(chromo2)
    #for chicken
    if chromo2 not in lines2_seen and (chromo2=="Z" or chromo2=="W"):
        lines2_seen.append(chromo2)
    '''
#size of array
NCSp1 = len(lines1_seen)
NCSp2 = len(lines2_seen)

print((NCSp1))
print((NCSp2))
print(sorted(lines1_seen))
print(sorted(lines2_seen))

x = np.zeros((NCSp1+1,NCSp2+1))
x = x.astype(int)
print x

#for anolis
#chromoAnolisDict = {'LGa':7,'LGb':8,'LGc':9, 'LGd':10, 'LGf':11, 'LGg':12, 'LGh':13}

#for chicken
#chromoChickenDict = {'Z':29,'W':30}

#for human
#chromoHumanDict = {'X':23,'Y':24}

#for mouse
chromoMouseDict = {'X':20,'Y':21}

#for stickle
chromoStickleDict = {"groupI":1,"groupII":2,"groupIII":3,"groupIV":4,"groupIX":9,"groupV":5,"groupVI":6,"groupVII":7,"groupVIII":8,"groupX":10,"groupXI":11,"groupXII":12,"groupXIII":13,"groupXIV":14,"groupXIX":15,"groupXV":16,"groupXVI":17,"groupXVII":18,"groupXVIII":19,"groupXX":20,"groupXXI":21}

#adds to array
for aLine in lines:
    aLine = aLine.split()
    chromo1 = aLine[1]
    chromo2 = aLine[3]
    if (chromo1 in lines1_seen) and (chromo2 in lines2_seen):
        #for anolis
        #if chromo1.startswith('L'):
            #chromo1=chromoAnolisDict[chromo1]
        
        #if chromo1.startswith('X') or chromo1.startswith('Y'):
            #chromo1=chromoHumanDict[chromo1]
        
        #for human
        #if chromo1.startswith('X') or chromo1.startswith('Y'):
            #chromo1=chromoHumanDict[chromo1]
        
        #for chicken
        #if chromo2.startswith('Z') or chromo2.startswith('W'):
            #chromo2=chromoChickenDict[chromo2]
     
        #if chromo1.startswith('X') or chromo1.startswith('Y'):
        #chromo1=chromoHumanDict[chromo1]
        
        #for stickle
        #if chromo2.startswith('g'):
            #chromo2=chromoStickleDict[chromo2]
        
        #for mouse
        #if chromo2.startswith('X') or chromo2.startswith('Y'):
            #chromo2=chromoMouseDict[chromo2]
        
        x[int(chromo1),int(chromo2)]+=1

print(x)

# show the grid as a heat map
#sp2
pic = plt.subplot(1,1,1)
plt.xlabel('Zebrafish Chromosomes')
pic.xaxis.set_label_position('top')
pic.set_xlim([.5, NCSp2+.5])
pic.set_ylim([NCSp1+.5, .5])

#sp1
plt.ylabel('Tetraodon Chromosomes')
plt.yticks(range(1,NCSp1+1))
plt.xticks(range(1,NCSp2+1))
pic.xaxis.tick_top()
pic.imshow(x, interpolation = 'nearest')
plt.show()