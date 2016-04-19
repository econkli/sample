#-------------------------------
#Emily Conklin
#5/4/2015
#This program takes a list of protein sequences
#And splits up the sequences into seperate files by species
#-------------------------------

def writeFiles(nameList, file):
    '''
    creates file for each species 
    and writes corresponding sequences to the file
    '''
    for name in nameList:   
        tempFile = open(name+"CARTFASTA.txt","w") #opens a new file for each species found
        for num in range(len(file)):    #goes through each line in the list
            if name in file[num]:       #if name matches line in file, write name and sequence to new file
                tempFile.write(file[num].split(",")[0]+"\n")
                tempFile.write(file[num+1])
        
        tempFile.close()

def main():
    '''
    obtains a list of species names from file
    '''
    #opens a file of protein sequences not split by species
    fastaSeq = open("ComparativeCART - FASTA.csv","r")
    fastaSeq = fastaSeq.readlines()
    
    nameList = []
    
    for line in fastaSeq:   #goes through each line in the file
        #below code parses to get species names
        if line.startswith('>'):
            name = line.split(",")[0][1::]
            if name != "human" and name!= "mouse" and name!= "ciona":
                name=name[:-1]
            name = name.split('3')[0]
            name = name.split('2')[0]
            #if name has not already been added, add it to the list
            if name not in nameList:
                nameList.append(name)

    #write files with the new list of names
    writeFiles(nameList,fastaSeq)

main()