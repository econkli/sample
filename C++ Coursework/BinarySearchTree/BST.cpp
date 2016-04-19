//
//  BST.cpp
//  TreeProject
//
//  Created by Toby Dragon on 11/13/14.
//  Copyright (c) 2014 Toby Dragon. All rights reserved.
//
//#include "stdafx.h"

#include "BST.h"
#include <iostream>
#include <cstdlib>
#include <fstream>
#include <sstream>

BST::BST(){
    root = nullptr;
}

//creates a BST from a file written by the toFile function
BST::BST(std::string filename){
    std::ifstream infile(filename);
    
    if (infile) {
        int count=0;
    
        //gets length of file
        while (infile) {
            std::string line;
            getline(infile, line);
            std::cout << line << std::endl;
            count++;
        }
                
        //sets up arrays - one for line length, one for data
        int* len = new int[count];
        int* data = new int[count];
        int count2 = 0;
        
        std::ifstream infile(filename);
        
        //fills them
        while (infile) {
            std::string line;
            getline(infile, line);
            int whitespace = line.length();
            len[count2] = whitespace;
            
            //removes spaces from line, converts to int
            line.erase(std::remove(line.begin(), line.end(), ' '), line.end());
            int temp;
            std::stringstream(line) >> temp;
            data[count2] = temp;
            
            count2++;
        }
        
        //starts building tree
        bool condition = true;
        bool checkCurrLen = true;
        int currLen = 1;
        int nodeCount = 0;
        
        //below is an UGLY LOOPY MESS but it works
        //sorry Toby
        
        //checks to see whether array has been emptied out yet
        while (condition){
            condition = false;
            checkCurrLen = false;
            
            //goes through whole array
            for (int i=0;i<count;i++){
                if (len[i]>0) {
                    //if array not empty, keep going
                    condition = true;
                    if (len[i]==currLen){
                        //if line length is at current whitespace
                        //e.g. finds next smallest line
                        checkCurrLen = true;
                        //if this is the first node in, make it the root
                        if (nodeCount == 0){
                            BTNode* newNode = new BTNode(data[i]);
                            root = newNode;
                            nodeCount++;
                        }
                        //otherwise, just stick it in there where it wants to go
                        else {
                            add(data[i]);
                            nodeCount++;
                        }
                        //"empties" array at index
                        len[i] = -1;
                    }
                }
            }
            //if no lines were found with current size, increase it to find next minimum
            if (checkCurrLen==false){
                currLen++;
            }
        }
    }
}

//destructor
BST::~BST(){
    //recursively deletes left and right subtrees, post-order
    delTree(root);
}

//stub function for destructor - delete entire sub-tree with current as the root
//post-order traversel (child, child, parent)
void BST::delTree(BTNode* current){
    //if we haven't hit the bottom of the left subtree yet, move further down
    if (current->getLeft()!=nullptr && current!=nullptr) {
        delTree(current->getLeft());
    }
    //if we haven't hit the bottom of the right subtree yet, move further down
    if (current->getRight()!=nullptr && current!=nullptr) {
        delTree(current->getRight());
    }
    //delete on the way back up
    delete current;
}

void BST::add(ItemType newValue){
    if (root == nullptr){
        root = new BTNode(newValue);
    }
    else {
        add(root, newValue);
    }
}

void BST::add(BTNode* current, ItemType newValue){
    if (newValue == current->getItem()){
        throw DuplicateValueException();
    }
    else if (newValue < current->getItem()){
        BTNode* child = current->getLeft();
        if (child != nullptr){
            add(child, newValue);
        }
        else {
            current->setLeft(new BTNode(newValue));
        }
    }
    else { //newValue > current->getItem()
        BTNode* child = current->getRight();
        if (child != nullptr){
            add(child, newValue);
        }
        else {
            current->setRight(new BTNode(newValue));
        }
    }
}

std::string BST::toString() const{
    return toString(root, "");
}

std::string BST::toString(const BTNode* const current, const std::string indent) const{
    if (current != nullptr){
        std::string right = toString(current->getRight(), indent+"\t");
        std::string thisNode = indent + genericItemToString(current->getItem()) + "\n";
        std::string left = toString(current->getLeft(), indent+"\t");
        return right + thisNode + left;
    }
    else {
        return "";
    }
}

//returns a sortedArray of all items from the tree
//sets the value of the returnedArraySize
//in-order traversal (child-parent-child)
//NOTE: returned array must be deleted by the user
ItemType* BST::sortedArray(int& returnedArraySize){
    returnedArraySize = countNodes(root);
    ItemType* arrayOut = new ItemType[returnedArraySize];
    int index = fillArray(root,arrayOut,0);
    return arrayOut;
}

//adds all items from the sub-tree with current as root to the array, starting at current index
//returns the number of items in the array when finished
int BST::fillArray(BTNode* current, ItemType* array, int currIndex){
    //fills left subtree
    if (current->getLeft() != nullptr) {
        currIndex = fillArray(current->getLeft(), array, currIndex);
    }
    
    //puts parent in the middle
    currIndex++;
    array[currIndex] = current->getItem();
    
    //fills right subtree
    if (current->getRight() != nullptr) {
        currIndex = fillArray(current->getRight(), array, currIndex);
    }
    return currIndex; // return the last index filled
}

//writes the tree to a file for storage
//this file can be read by the constructor below to re-create the tree
void BST::toFile(std::string filename) const{
    std::string toWrite = toFile(root, "");
    std::ofstream outf(filename);
    outf << toWrite << std::endl;
    outf.close();
}

//stub function for toFile
//pre-order (parent, child, child)
std::string BST::toFile(const BTNode* const current, const std::string indent) const{
    //if we haven't reached the bottom of the tree yet, add right and left subtrees
    //builds on the way up
    if (current != nullptr){
        std::string right = toString(current->getRight(), indent+"\t");
        std::string thisNode = indent + genericItemToString(current->getItem()) + "\n";
        std::string left = toString(current->getLeft(), indent+"\t");
        return right + thisNode + left;
    }
    else {
        return "";
    }
}

int BST::countNodes(){
    return countNodes(root);
}

int BST::countNodes(BTNode* current){
    int count = 0;
    if (current != nullptr) {
        count = 1 + countNodes(current->getLeft()) + countNodes(current->getRight());
    }
    return count;
}
