#---------------------------------------------
#Emily Conklin
#1/28/2015

#This program takes two files of BLAST data and compares genes, looking for potential matches
#It takes one file and creates a dictionary (gene from species 1: all potential genes from species 2)
#This dictionary is compared to the second file (species 1-->species 2-->species 1)
#And if matches are found
#It writes the matches to a new file (candidatespecies1species2.txt)

#Updates 2/5/2015
#Program runs through alternate comparison (species 2-->species 1-->species 2)
#Adds these matches to file, then deletes duplicates
#----------------------------------------------

import os

def makeDict(compareFileLines):
    '''
        function 'makeDict' takes species 1:species 2 file and creates a dictionary
        species 1 gene: list of all potential species 2 genes
        '''
    print("dictionary function begins") #test
    dictOut={}
    geneList=[]
    
    for aLine in compareFileLines[0:1]: #for the first line in file
        aLine = aLine.split()
        temp = aLine[0] #temp = first gene
    
    for aLine in compareFileLines:  #for all lines
        aLine = aLine.split()
        gene1 = aLine[0]
        gene2 = aLine[1]
        
        if gene1==temp:             #if current gene1 is the same as the past gene1
            geneList.append(gene2)  #add gene2 to the running list
        
        else:
            dictOut[temp]=geneList  #if current gene1 is NOT the same, add previous gene1+list to dict
            geneList=[]             #clear list
            geneList.append(gene2)  #add new gene2
        
        temp=gene1 #reset temp
    
    print("dictionary complete") #test
    return dictOut #profit

def findMatches(candidateFile,sp1_sp2list_dict,sp2_sp1list_dict):
    '''
        function 'findMatches' takes species 2:species 1 file
        and then looks for matches between the species 2 gene and the list of species 2 genes in dictionary
        if there is a match, gene names for both species are added to a new file
        '''
    print("match finding program begins") #test
    
    #input: {sp1: [sp2, sp2...]}
    #and {sp2: [sp1, sp1...]}
    
    for sp1Key in sp1_sp2list_dict:
        #print("Species 1 key: "+sp1Key)
        for oneSp2 in sp1_sp2list_dict[sp1Key]:
            #print("First Species 2 value: "+oneSp2)
            if oneSp2 in sp2_sp1list_dict.keys():
                #print("Value list in second dict: ",sp2_sp1list_dict[oneSp2])
                if sp2_sp1list_dict[oneSp2][0] == sp1Key:
                    #print("MATCH @: "+sp2_sp1list_dict[oneSp2][0])
                    candidateFile.write(sp1Key+"\t"+oneSp2)
                    candidateFile.write("\n")

    print("match finding complete")

def delDuplicates(canSp1Sp2, canSp2Sp1, finalFile):
    print("duplicate deletion begins")
    can1_2 = canSp1Sp2.readlines()
    can2_1 = canSp2Sp1.readlines()
    
    lines_seen = set() # holds lines already seen
    for line in can1_2:
        if line not in lines_seen:
            finalFile.write(line)
            lines_seen.add(line)

    for line in can2_1:
        newLine = line.split()
        newLine = newLine[1]+"\t"+newLine[0]+"\n"
        if newLine not in lines_seen:
            finalFile.write(newLine)
            lines_seen.add(newLine)

    print("duplicate deletion complete")

def makeChromoDict(infoTable):
    print("chromosome dict function begins") #test
    dictOut={}
    
    lines = infoTable.readlines()
    for aLine in lines:
        aLine=aLine.split()
        if aLine[0].startswith(">"):
            dictOut[aLine[0][1::]]=aLine[1]
    
    print("chromosome dict complete") #test
    return dictOut

def addChromoData(sp1ChromoDict,sp2ChromoDict,candidateFile,newFile):
    print("chromosome data adding begins")
    lines = candidateFile.readlines()
    for aLine in lines:
        aLine = aLine.split()
        gene1 = aLine[0]
        gene2 = aLine[1]
        chromo1 = sp1ChromoDict[gene1]
        chromo2 = sp2ChromoDict[gene2]
        newFile.write(gene1+"\t"+chromo1+"\t"+gene2+"\t"+chromo2+"\n")

    print("chromosome data adding complete")

def main(species1,species2,file_1,file_2):
    O1V2 = open(file_1,"r")
    O2V1 = open(file_2,"r")
    O1V2_lines = O1V2.readlines()
    O2V1_lines = O2V1.readlines()

    #comparing species1 --> species2 --> species1
    s1s2_Dict=makeDict(O1V2_lines)
    s2s1_Dict=makeDict(O2V1_lines)

    #set up candidate files
    candidate_species1_species2 = open("candidate"+str(species1)+str(species2)+".txt","w")
    candidate_species2_species1 = open("candidate"+str(species2)+str(species1)+".txt","w")

    #find back-matches between species
    findMatches(candidate_species1_species2,s1s2_Dict,s2s1_Dict)
    findMatches(candidate_species2_species1,s2s1_Dict,s1s2_Dict)
    candidate_species1_species2.close()
    candidate_species2_species1.close()
               
    #set up final candidate file
    candidate_species1_species2_read = open("candidate"+str(species1)+str(species2)+".txt","r")
    candidate_species2_species1_read = open("candidate"+str(species2)+str(species1)+".txt","r")
    final_candidate_species1_species = open("finCandidate"+str(species1)+str(species2)+".txt","w")

    #merge and delete duplicates
    delDuplicates(candidate_species1_species2_read,candidate_species2_species1_read, final_candidate_species1_species)
    os.remove("candidate"+str(species1)+str(species2)+".txt")
    os.remove("candidate"+str(species2)+str(species1)+".txt")
    final_candidate_species1_species.close()
    
    #rewrite file and include chromosome information
    sp1InfoTable = open(str(species1)+"infoTableNoDups.fa","r")
    sp2InfoTable = open(str(species2)+"infoTableNoDups.fa","r")
    final_candidate_species1_species_read = open("finCandidate"+str(species1)+str(species2)+".txt","r")
    candidateChromo = open(str(species1)+str(species2)+"Candidate.txt","w")
    sp1ChromoDict = makeChromoDict(sp1InfoTable)
    sp2ChromoDict = makeChromoDict(sp2InfoTable)
    addChromoData(sp1ChromoDict,sp2ChromoDict,final_candidate_species1_species_read,candidateChromo)
    os.remove("finCandidate"+str(species1)+str(species2)+".txt")
    
    O1V2.close()
    O2V1.close()
    candidate_species1_species2_read.close()
    candidate_species2_species1_read.close()
    final_candidate_species1_species.close()
    sp1InfoTable.close()
    sp2InfoTable.close()
    candidateChromo.close()
    

main("tetraodon","zebrafish","tetraodonVzebrafish.blastp","zebrafishVtetraodon.blastp")
