//
//  BST.h
//  TreeProject
//
//  Created by Toby Dragon on 11/13/14.
//  Copyright (c) 2014 Toby Dragon. All rights reserved.
//

#ifndef __TreeProject__BST__
#define __TreeProject__BST__

#include <string>
#include "GenericItemType.h"
#include "BTNode.h"
#include <fstream>

class DuplicateValueException : std::exception{};
class CantWriteFileException : std::exception{};
class CantReadFileException : std::exception{};

class BST{
private:
    BTNode* root;
    void add(BTNode* current, ItemType newItem);
    std::string toString(const BTNode* const current, const std::string indent) const;
    std::string toFile(const BTNode* const current, const std::string indent) const;
    //returns the number of total nodes in the sub-tree with current as root
    int countNodes();
    int countNodes(BTNode* current);
    
public:
    BST();
    void add(ItemType newItem);
    std::string toString() const;
    
    //returns a sortedArray of all items from the tree
    //sets the value of the returnedArraySize
    //NOTE: returned array must be deleted by the user
    ItemType* sortedArray(int& returnedArraySize);
    
    //writes the tree to a file for storage
    //this file can be read by the constructor below to re-create the tree
    void toFile(std::string filename) const;
    
    //creates a BST from a file written by the toFile function
    BST(std::string filename);
    
    //adds all items from the sub-tree with current as root to the array, starting at current index
    //returns the number of items in the array when finished
    int fillArray(BTNode* current, ItemType* array, int currIndex);
    
    //deletes the entire sub-tree with current as root
    void delTree(BTNode* current);
    
    //destructor
    ~BST();

};

#endif /* defined(__TreeProject__BST__) */