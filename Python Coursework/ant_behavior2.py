#---------------------------------------------
#Emily Conklin
#12/7/2015

#This program works in tandem with a pre-generated scene in Maya
#It simulates simple "ant" actor behavior by generating a given number of
#ants around a home base. Ants move in a randomly generated forward direction,
#laying a visible trail of pheromones that fade over time. If an ant senses a
#pheromone, it will turn toward it. If an ant senses food, it will pause to
#"pick up" food. Pheromones laid by an ant carrying food are "stronger" and
#have a longer lifespan. Emergent paths can be seen as the program runs.
#----------------------------------------------

import maya.cmds as cmds
import random
import numpy as np
import math

global totalNumPheromones

#ant objects
class Ant:
    def __init__(self,objectIn,xIn,zIn,yrIn):
        self.name = objectIn
        self.time = 0
        self.food = 0
        self.sinceLastPheromone = 0
        self.numPheromones = 0
        self.hasFood = False
        self.pherList = []
        self.dontFollow = [0,0,0,0,0,0]
        self.dontFollowInd = 0

    #moves ants forward at a rate of two units/second
    def walk(self):
        cmds.move(0,0,-2,self.name,r=True,os=True,wd=True)
        self.sinceLastPheromone+=1
        self.time+=1

    #update stuff (pheromones)
    def update(self):
        #sets new pheromone every other step
        if self.sinceLastPheromone == 1:
            global totalNumPheromones
            totalNumPheromones+=1
            pher = Pheromone(self,self.getXPos(),self.getZPos(),self.getHasFood(),totalNumPheromones)
            self.numPheromones+=1
            self.sinceLastPheromone = 0
            pher.showPher(self.time)
            self.pherList.append(pher)
            
            #holds the three most recently laid pheromones 
            if self.dontFollowInd < 3:
                self.dontFollow[self.dontFollowInd] = pher
                self.dontFollowInd+=1
            else:
                self.dontFollow[0] = self.dontFollow[1]
                self.dontFollow[1] = self.dontFollow[2]
                self.dontFollow[2] = pher
            
        #updates pheromone state
        for pher in self.pherList:
            self.pherIndex = pher.update(self.time)

    #checks for surrounding pheromones to turn toward
    #NOT DONE AS OF 12/6
    def checkPheromone(self):
        xAnt = self.getXPos()
        zAnt = self.getZPos()
        rAnt = self.getRotation()
        closePheromones = []
        seenPheromones = []
        seenPheromoneTheta = []
        
        #calculate distance from ant to each pheromone
        #dontFollow is the last three laid + last three followed
        for pher in list(set(self.pherList) - set(self.dontFollow)):
            xPher = pher.getXPos()
            zPher = pher.getZPos()
            
            #distance between ant & pheromone
            d = math.sqrt((xAnt - xPher)**2 + (zAnt - zPher)**2)
            
            #find close pheromones for next step
            if 0 < d <= 5:
                closePheromones.append(pher)
        
        for pher in closePheromones:
            xPher = pher.getXPos()
            zPher = pher.getZPos()
            
            #angle between ant & pheromone
            if (xPher - xAnt)>0:
                theta = math.atan((zPher - zAnt) / (xPher - xAnt))
                theta = math.degrees(theta)
                if theta > 180:
                    theta = -(360-theta)
                
                #finds angles in field of vision (80 degrees)
                if -40 <= theta <= 40:
                    seenPheromones.append(pher)
                    seenPheromoneTheta.append(theta)
                
        #if the ant can see any pheromones
        if len(seenPheromones)>0:
            thetaToFollow = 0
            #find the highest priority one
            highestImport = 0
            for pher in seenPheromones:
                imp = pher.getImportance()
                if imp > highestImport:
                    highestImport = imp
                    toFollow = pher
                    thetaToFollow = seenPheromoneTheta[seenPheromones.index(pher)]
            #turn toward highest priority pheromone! if it's visible
            str = pher.getName()+'.visibility'
            if cmds.getAttr(str)==1:
                ant.turnAngle(thetaToFollow)
        
    #pause for one second
    def quickPause(self):
        self.time+=1
        updateTime = str(self.time)+"sec"
        cmds.setKeyframe(self.name,t=updateTime)
        self.time+=1
        updateTime = str(self.time)+"sec"
        cmds.setKeyframe(self.name,t=updateTime)
        
    #make a 90 degree turn to the left
    def turnLeft(self):
        self.time+=.1
        updateTime = str(self.time)+"sec"
        cmds.setKeyframe(self.name,t=updateTime)
        
        cmds.rotate(0,-90,0,self.name,r=True,os=True)
        
        self.time+=.5
        updateTime = str(self.time)+"sec"
        cmds.setKeyframe(self.name,t=updateTime)
        
    #make a 90 degree turn to the right    
    def turnRight(self):
        self.time+=.1
        updateTime = str(self.time)+"sec"
        cmds.setKeyframe(self.name,t=updateTime)
        
        cmds.rotate(0,90,0,self.name,r=True,os=True)
        
        self.time+=.5
        updateTime = str(self.time)+"sec"
        cmds.setKeyframe(self.name,t=updateTime)
        
    #make a turn of specified angle
    def turnAngle(self, angleIn):
        self.time+=.1
        updateTime = str(self.time)+"sec"
        cmds.setKeyframe(self.name,t=updateTime)
        
        cmds.rotate(0,angleIn,0,self.name,r=True,os=True)
        
        self.time+=.5
        updateTime = str(self.time)+"sec"
        cmds.setKeyframe(self.name,t=updateTime)
        
        
    #make a 180 turn    
    def turnAround(self):
        self.time+=.1
        updateTime = str(self.time)+"sec"
        cmds.setKeyframe(self.name,t=updateTime)
        
        cmds.rotate(0,180,0,self.name,r=True,os=True)
        
        self.time+=.5
        updateTime = str(self.time)+"sec"
        cmds.setKeyframe(self.name,t=updateTime)
        
    #drops food
    def dropFood(self):
        if self.hasFood==True:
            updateTime = str(self.time)+"sec"
            self.food = 0
            self.hasFood = False
        
    #picks up food
    def increaseFood(self):
        if self.hasFood== False:
            updateTime = str(self.time)+"sec"
            self.food+=1
            self.hasFood = True
        
    def getFood(self):
        return self.food
        
    def getName(self):
        return self.name
        
    def getXPos(self):
        string = self.name+'.translateX'
        return cmds.getAttr(string)
        
    def getYPos(self):
        string = self.name+'.translateY'
        return cmds.getAttr(string)
        
    def getZPos(self):
        string = self.name+'.translateZ'
        return cmds.getAttr(string)
        
    def getRotation(self):
        string = self.name+'.rotateY'
        return cmds.getAttr(string)
        
    def getTime(self):
        return self.time
    
    def getHasFood(self):
        return self.hasFood
        
#food objects        
class Food:
    def __init__(self,objectIn):
        self.numFood = 10
        self.name = objectIn
        
    def decrease(self):
        if (self.numFood>0):
            self.numFood-=1
        elif (self.numFood==0):
            cmds.delete(self.name)

#pheromone objects
class Pheromone:
    def __init__(self,antIn,x,z,hasFood,total):
        self.xPos = x
        self.zPos = z
        self.name = "pheromone"+str(total)
        
        cmds.polySphere()
        cmds.scale(.10,.10,.10)
        cmds.move(self.xPos, 0, self.zPos)
        
        tempName = "pSphere1"
        cmds.rename(tempName,self.name)
        cmds.setAttr(self.name+'.visibility', 0) 
        #if the ant has food, the pheromone lasts longer
        if hasFood==True:
            shader = "blinn5SG"
            self.lifespan = 20
            self.importance = 2
        else:
            shader = "blinn6SG"
            self.lifespan = 3
            self.importance = 1
        cmds.sets( e=True, forceElement= shader )
        cmds.setKeyframe(self.name,t="0sec",at='visibility')
        
    def update(self,time):
        if self.lifespan > 0:
            self.lifespan-=1
        elif self.lifespan == 0:
            updateTime = str(time)+"sec" 
            cmds.setAttr(self.name+'.visibility', 0)
            cmds.setKeyframe(self.name,t=updateTime,at='visibility')
    
    def showPher(self,time):
        updateTime = str(time)+"sec"
        cmds.setKeyframe(self.name,t=updateTime,at='visibility') 
        cmds.setAttr(self.name+'.visibility', 1)
        cmds.setKeyframe(self.name,t=updateTime,at='visibility')
    
    def getXPos(self):
        return self.xPos
    
    def getZPos(self):
        return self.zPos
    
    def getName(self):
        return self.name
        
    def getImportance(self):
        return self.importance

pathOfFiles = "/Users/Emily/Desktop/"
fileType = "obj"

files = cmds.getFileList(folder=pathOfFiles, filespec='*.%s' % fileType)
if len(files) == 0:
    cmds.warning("No files found")
else:
    
    #initializes global numPheromones
    global totalNumPheromones
    totalNumPheromones = 0
    
    #"home" pyramid is the origin (0,0,0) (it glows gold)
    cmds.polyPyramid()
    cmds.move(0,0.3,0)
    cmds.sets( e=True, forceElement= 'blinn2SG' )
    
    #food piles at corners (each has 10 food)
    cmds.polyPyramid()
    cmds.scale(.50,.50,.50)
    cmds.move(9,.25,9)
    cmds.sets( e=True, forceElement= 'blinn3SG' )
    food1 = Food("pPyramid2")
    
    cmds.polyPyramid()
    cmds.scale(.50,.50,.50)
    cmds.move(-9,.25,9)
    cmds.sets( e=True, forceElement= 'blinn3SG' )
    food2 = Food("pPyramid3")
   
    cmds.polyPyramid()
    cmds.scale(.50,.50,.50)
    cmds.move(9,.25,-9)
    cmds.sets( e=True, forceElement= 'blinn3SG' )
    food3 = Food("pPyramid4")
    
    cmds.polyPyramid()
    cmds.scale(.30,.30,.30)
    cmds.move(-9,.25,-9)
    cmds.sets( e=True, forceElement= 'blinn3SG' )
    food4 = Food("pPyramid5")

    #builds specified number of ants, places them randomly
    numAnts = 30
    antList = []
    for f in files:
        for i in range(0,numAnts):
            name = "ant"+str(i+1)
            cmds.file(pathOfFiles + f, i=True)
            try:
                cmds.rename("pSphere1",name)
            
            except:
                cmds.rename("antObj_pSphere"+str(i),name)
            
            cmds.scale(.15,.15,.15,name)
            chance1 = random.random()
            chance2 = random.random()
            if chance1>.5:
                randX = random.random()+random.uniform(-3,-.5)
            else:
                randX = random.random()+random.uniform(.5,3)
                
            if chance2>.5:
                randZ = random.random()+random.uniform(-3,-.5)
            else:
                randZ = random.random()+random.uniform(.5,3)
            
            cmds.move(randX,0,randZ,name)
            randOrient = random.randrange(180)
            
            cmds.rotate(0,randOrient,0,name)
            cmds.sets( name, e=True, forceElement= 'blinn4SG' )
            antObject = Ant(name, randX, randZ, randOrient)
            antList.append(antObject)
           
    #sets move context to object instead of world           
    cmds.manipMoveContext('Move',e=1,mode=0)

    #sets first keyframe
    for ant in antList:
        cmds.setKeyframe(ant.getName(),t="0sec")
        
    
    #start animation code
    posBound = 11
    negBound = -11
    homeBound = .5
    negHomeBound = -.5
    posFoodBound1 = 7
    posFoodBound2 = 11
    negFoodBound1 = -11
    negFoodBound2 = -7
    time = 0
    
    for ant in antList:
        for num in range(0,50):
            x = ant.getXPos()
            z = ant.getZPos()
            #checks to see if ant is within bounds
            if x>posBound or x<negBound or z>posBound or z<negBound:
                #if not, turn them randomly
                randInt = random.randrange(3)
                if randInt==0:
                    ant.turnLeft()
                elif randInt==1:
                    ant.turnRight()
                else:
                    ant.turnAround()
            #checks to see if ant is close to home pyramid
            if negHomeBound<x<homeBound or negHomeBound<z<homeBound:
                #if so, pause and drop off food if they have it
                if ant.getFood()>0:
                    ant.quickPause()
                    ant.dropFood()
            #checks to see if ant is close to food
            if posFoodBound1<x<posFoodBound2 and posFoodBound1<z<posFoodBound2:
                ant.quickPause()
                ant.increaseFood()
            elif posFoodBound1<x<posFoodBound2 and negFoodBound1<z<negFoodBound2:
                ant.quickPause()
                ant.increaseFood()
            elif negFoodBound1<x<negFoodBound2 and posFoodBound1<z<posFoodBound2:
                ant.quickPause()
                ant.increaseFood()
            elif negFoodBound1<x<negFoodBound2 and negFoodBound2<z<negFoodBound2:
                ant.quickPause()
                ant.increaseFood()
            #if all's well, move the ant forward
            ant.walk()
            ant.checkPheromone()
            ant.update()