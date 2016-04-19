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
#include "Book.h"

class Inventory{
    
private:
    
    Book *inventoryArray;
    int numTitles;
    
public:
    
    // constructor
    Inventory(Book firstBook);
    
    // destructor
    ~Inventory();
    
    // search inventory
    Book search(std::string titleIn);
    
    // adds book, keeping array sorted
    void addBook(Book bookIn);

    // sorts array
    void sortArray();
    
    
    
    // to string
    std::string toString();
    
};
    
#endif /* defined(__Bookstore_Project__Inventory__) */
