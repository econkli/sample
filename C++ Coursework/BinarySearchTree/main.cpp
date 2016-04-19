//
//  main.cpp
//  TreeProject
//
//  Created by Toby Dragon on 11/13/14.
//  Copyright (c) 2014 Toby Dragon. All rights reserved.
//
//#include "stdafx.h"

#include <string>
#include <iostream>
#include <cstdlib>
#include "BST.h"
#include "ArrayLibrary.h"

void testStringBST(){
//    BST myTree = BST();
//    myTree.add("Jake");
//    myTree.add("Nancy");
//    myTree.add("Jamie");
//    std::cout << myTree.toString() << std::endl << std::endl;
//    
//    myTree.add("Amy");
//    myTree.add("Alice");
//    myTree.add("Zed");
//    myTree.add("Bob");
//    std::cout << myTree.toString() << std::endl;
//    
//    int size;
//    std::string* myArray = myTree.sortedArray(size);
//    std::cout << toString(myArray, size) << std::endl << std::endl;
//    delete[] myArray;
//    myArray = nullptr;
//    
//    myTree.toFile("tree.txt");
}

void testIntBST(){
    const int NUM_ITEMS = 100;
    
    BST myTree = BST();
    int duplicates = 0;
    for (int i=0; i< NUM_ITEMS; i++){
            try {
                myTree.add(rand()%NUM_ITEMS);
            }
            catch (DuplicateValueException e){
                duplicates++;
            }
    }
    int* arraySize = new int(0);
    int* array = myTree.sortedArray(*arraySize);
    for (int i=0;i<*arraySize;i++){
        std::cout << *array++ << std::endl;
    }
    
    
//    
//    myTree.toFile("treeToFile.txt");
//    
//    std::cout << "done!" << std::endl;
//    
//    BST myTreeFromFile = BST("treeToFile.txt");
//    std::cout << myTreeFromFile.toString();
    
//    int size;
//    int* myArray = myTree.sortedArray(size);
//    std::cout << size << " + " << duplicates << " should equal " << NUM_ITEMS << std::endl << std::endl;
//    std::cout << toString(myArray, size) << std::endl << std::endl;
//    delete[] myArray;
//    myArray = nullptr;
//    
//    myTree.toFile("tree.txt");
//    
//    BST treeFromFile = BST("tree.txt");
//    myArray = myTree.sortedArray(size);
//    std::cout << size << " + " << duplicates << " should equal " << NUM_ITEMS << std::endl << std::endl;
//    std::cout << toString(myArray, size) << std::endl << std::endl;
//    delete[] myArray;
//    myArray = nullptr;
    
}

int main(){
    srand((int)time(NULL));
    
    testStringBST();
    testIntBST();
    
    return 0;
}
