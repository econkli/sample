//
//  ArrayLibrary.cpp
//  Lab2-ArrayLibrary
//
//  Created by Toby Dragon on 9/2/15.
//  Copyright (c) 2015 Toby Dragon. All rights reserved.
//

#include <cstdlib>
#include "ArrayLibrary.h"


std::string toString(const ItemType* arrayPtr, const int size){
    std::string str = "{";
    for (int i=0; i<size-1; i++){
        str += genericItemToString(arrayPtr[i]) + ", ";
    }
    str += genericItemToString(arrayPtr[size-1]) + "}";
    return str;
}

int find(const ItemType* arrayPtr, const int size, const ItemType itemToFind){
    for (int i=0; i<size; i++){
        if (arrayPtr[i] == itemToFind){
            return i;
        }
    }
    return -1;
}

int maxIndex(const ItemType* arrayPtr, const int size){
    int maxIndex = 0;
    for (int i=0; i<size; i++){
        if (arrayPtr[i] >= arrayPtr[maxIndex]){
            maxIndex = i;
        }
    }
    return maxIndex;
}

ItemType sum(const ItemType* arrayPtr, const int size){
    ItemType sum = ItemType();
    for (int i=0; i<size; i++){
        sum += arrayPtr[i];
    }
    return sum;
}

int* copyArray(const int* arrayToCopy, const int size){
    int *arrPtr = new int [size];
    for(int i = 0; i < size; i++){
        arrPtr[i] = arrayToCopy[i];
    }
    return arrPtr;
}

