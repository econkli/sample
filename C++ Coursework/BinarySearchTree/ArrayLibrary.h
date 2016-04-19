//
//  ArrayLibrary.h
//  Lab2-ArrayLibrary
//
//  Created by Toby Dragon on 9/2/15.
//  Copyright (c) 2015 Toby Dragon. All rights reserved.
//

#ifndef __Lab2_ArrayLibrary__ArrayLibrary__
#define __Lab2_ArrayLibrary__ArrayLibrary__

#include <stdio.h>
#include <string>
#include "GenericItemType.h"

//returns a string representing the array in a readable form, format shown below
// {1, 2, 3, 4, 5}
std::string toString(const ItemType* arrayPtr, const int size);

//returns the first index of the numToFind if it is present, otherwise returns -1
int find(const int* arrayPtr, const int size, const int numToFind);

//returns the index of the maximum value in the array
int maxIndex(const int* arrayPtr, const int size);

//returns the sum of all values in the array
int sum(const int* arrayPtr, const int size);

//generates a copy of the array passed in
//Returns a pointer to the array, which must be deleted by the user
int* copyArray(const int* arrayToCopy, const int size);

#endif /* defined(__Lab2_ArrayLibrary__ArrayLibrary__) */
