//
//  BookStore.h
//  FinalProject1
//
//  Created by Jeff Hejna on 12/7/15.
//  Copyright © 2015 Jeff Hejna. All rights reserved.
//

#ifndef BookStore_h
#define BookStore_h
#include "BookStoreADT.h"
#include "Inventory.h"

class BookStore : public BookStoreADT {
private:
    Inventory* inventory;
    
public:
    BookStore();
    BookStore(std::string filename);
    ~BookStore();
    void printInventory();
    void returnInvoice(std::string fileName);
    void getDelivery(std::string fileName);
    void Menu();
};

#endif /* BookStore_h */
