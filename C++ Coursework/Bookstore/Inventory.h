//
//  Inventory.h
//  Bookstore Project
//
//  Created by Joe Menduni on 11/19/15.
//  Copyright (c) 2015 JoeMenduni. All rights reserved.
//

#ifndef __Bookstore_Project__Inventory__
#define __Bookstore_Project__Inventory__

#include <stdio.h>
#include "Book.hpp"

class Inventory{
    
private:
    
    Book** inventoryArray;
    int numTitles;
    int maxTitles;
    
public:
    
    // constructors
    Inventory();
    Inventory(int numTitlesIn);
    
    // destructor
    ~Inventory();
    
    // search inventory, returns book
    Book* search(std::string titleIn);
    
    // search inventory, return boolean
    bool boolSearch(std::string titleIn);
    
    // adds book, keeping array sorted
    void addBook(Book* bookIn);
    
    // sorts array
    void sortArray();
    
    // to string
    std::string toString();
    
    // saves inventory to file
    void saveInv();
    
    // returns size of inventory
    int getSize();
    
    // returns book at given index
    Book* getBook(int index);
    
};
#endif /* defined(__Bookstore_Project__Inventory__) */
