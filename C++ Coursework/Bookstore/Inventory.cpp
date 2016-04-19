//
//  Inventory.cpp
//  Bookstore Project
//
//  Created by Joe Menduni on 11/19/15.
//  Copyright (c) 2015 JoeMenduni. All rights reserved.
//

#include "Inventory.h"
#include "Book.hpp"
#include <stdio.h>
#include <ctype.h>
#include <fstream>

// constructor
Inventory::Inventory() {
    inventoryArray = new Book*[100];
    numTitles = 0;
    maxTitles = 100;
}

Inventory::Inventory(int numTitlesIn) {
    numTitles = numTitlesIn;
    inventoryArray = new Book*[numTitles];
}

Inventory::~Inventory() {
    for (int i=0;i<numTitles;i++){
        delete inventoryArray[i];
        inventoryArray[i] = nullptr;
    }
}

void Inventory::sortArray() {
    for (int i = 0; i < numTitles; i++) {
        for (int k = 0; k < numTitles; k++) {
            const char *bookI = inventoryArray[i]->getTitle().c_str();
            const char *bookK = inventoryArray[k]->getTitle().c_str();
            if (std::strcmp(bookI, bookK) < 0) {
                Book* tmp = inventoryArray[i];
                inventoryArray[i] = inventoryArray[k];
                inventoryArray[k] = tmp;
            }
        }
    }
}

// adds book, keeping array sorted
void Inventory::addBook(Book* bookIn) {
    if (numTitles >= maxTitles) {
        Book** inventoryArray2 = new Book*[maxTitles*2];
        maxTitles = maxTitles + 100;
        for (int i = 0; i < numTitles; i ++) {
            inventoryArray2[i] = inventoryArray[i];
        }
        delete inventoryArray;
        inventoryArray = inventoryArray2;
        inventoryArray2 = nullptr;
    }
    inventoryArray[numTitles] = bookIn;
    numTitles++;
    sortArray();
}

// searches for a specified title, returns boolean
bool Inventory::boolSearch(std::string titleIn) {
    int first = 0,
    last = numTitles - 1,
    middle,
    position = -1;
    bool found = false;
    
    while (!found && first <= last) {
        middle = (first + last) / 2;
        std::string theMid = inventoryArray[middle]->getTitle();
        if (theMid == titleIn) {
            found = true;
            position = middle;
        }
        else if (theMid > titleIn) {
            last = middle - 1;
        }
        else {
            first = middle + 1;
        }
    }
    if (position >= 0) {
        return true;
    }
    else {
        return false;
    }
}

// searches for a specified title, returns book
Book* Inventory::search(std::string titleIn) {
    int first = 0,
    last = numTitles - 1,
    middle,
    position = -1;
    bool found = false;
    
    while (!found && first <= last) {
        middle = (first + last) / 2;
        std::string theMid = inventoryArray[middle]->getTitle();
        if (theMid == titleIn) {
            found = true;
            position = middle;
        }
        else if (theMid > titleIn) {
            last = middle - 1;
        }
        else {
            first = middle + 1;
        }
    }
    if (position >= 0) {
        return inventoryArray[position];
    }
    else {
        return nullptr;
    }
}

// saves inventory into file
void Inventory::saveInv(){
    std::ofstream outf("BookstoreInventory.txt");
    outf << "NUM TITLES:"+std::to_string(numTitles)+"\n";
    for (int i = 0; i < numTitles; i++){
        outf << inventoryArray[i]->getTitle() + ":";
        outf << inventoryArray[i]->getHave();
        outf << ":";
        outf << inventoryArray[i]->getWant();
        outf << ":" + inventoryArray[i]->getWaitList() +"\n";
    }
    outf.close();
}

// get inventory size
int Inventory::getSize() {
    return numTitles;
}

// get book at index of array
Book* Inventory::getBook(int index) {
    return inventoryArray[index];
}

// to string
std::string Inventory::toString() {
    std::string returnString = "";
    for (int i = 0; i < numTitles; i ++) {
        returnString += inventoryArray[i]->toString() + "\n\n";
    }
    return returnString;
}