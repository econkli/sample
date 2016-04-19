//
//  Inventory.cpp
//  Bookstore Project
//
//  Created by Joe Menduni on 11/19/15.
//  Copyright (c) 2015 JoeMenduni. All rights reserved.
//

#include "Inventory.h"
#include <stdio.h>
#include <ctype.h>

// constructor
Inventory::Inventory(Book firstBook) {
    inventoryArray = new Book[10];
    inventoryArray[0] = firstBook;
    numTitles = 1;
}

    
void Inventory::sortArray()
{
    for (int i = 0; i < numTitles; i++) {
        for (int k = 0; k < numTitles; k++) {
            const char *bookI = inventoryArray[i].getTitle().c_str();
            const char *bookK = inventoryArray[k].getTitle().c_str();
            if (std::strcmp(bookI, bookK) < 0) {
                Book tmp = inventoryArray[i];
                inventoryArray[i] = inventoryArray[k];
                inventoryArray[k] = tmp;
            }
        }
    }

    
}

// adds book, keeping array sorted
void Inventory::addBook(Book bookIn) {
    inventoryArray[numTitles] = bookIn;
    numTitles ++;
    sortArray();
}

// to string
std::string Inventory::toString() {
    std::string returnString = "";
    for (int i = 0; i < numTitles; i ++) {
        returnString += inventoryArray[i].toString() + "\n";
    }
    return returnString;
}

Inventory::~Inventory() {
    delete[] inventoryArray;
}