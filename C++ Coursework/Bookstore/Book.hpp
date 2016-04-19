//
//  Book.hpp
//  TermProject
//
//  Created by Emily Conklin on 11/15/15.
//  Copyright © 2015 Emily Conklin. All rights reserved.
//

#ifndef Book_hpp
#define Book_hpp

#include <stdio.h>
#include <iostream>
#include "QueueWithNodes.hpp"

class Book{
private:
    std::string title;
    int have;
    int want;
    QueueWithNodes* waitList;
    int waitListLen;

public:
    Book(std::string titleIn, int wantIn);
    //copy constructor
    //destructor
    
    void setTitle(std::string titleIn);
    std::string getTitle();
    void setHave(int haveIn);
    int getHave();
    void increaseHave(int incIn);
    void decreaseHaveByOne();
    void setWant(int wantIn);
    int getWant();
    void addToWaitlist(std::string nameIn);
    void deleteFromWaitList(int numIn);
    std::string getWaitList();
    int getWaitListLength();
    std::string toString();
    
};
#endif /* Book_hpp */
