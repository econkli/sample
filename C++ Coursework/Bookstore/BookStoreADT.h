//
//  BookStoreADT.h
//  TermProject
//
//  Created by Emily Conklin on 12/9/15.
//  Copyright © 2015 Emily Conklin. All rights reserved.
//

#ifndef BookStoreADT_h
#define BookStoreADT_h

class FileNotFoundException : std::exception {};

class BookStoreADT {

public:
    virtual void openInv()=0;
    virtual void Menu()=0;
    virtual ~BookStoreADT(){};
};

#endif /* BookStoreADT_h */
