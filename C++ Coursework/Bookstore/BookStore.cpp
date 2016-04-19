//
//  BookStore.cpp
//  FinalProject1
//
//  Created by Jeff Hejna on 12/7/15.
//  Copyright © 2015 Jeff Hejna. All rights reserved.
//

#include "Inventory.h"
#include "Book.hpp"
#include "BookStore.h"
#include <stdio.h>
#include <ctype.h>
#include <fstream>
#include <sstream>
#include <vector>

BookStore::BookStore(){
    inventory = new Inventory;
}

BookStore::~BookStore(){
    delete inventory;
}

// reads file into inventory
BookStore::BookStore(std::string filename){
    std::ifstream infile("BookstoreInventory.txt");
    std::string title, haveStr, wantStr, wlStr;
    int have, want;
    
    if (infile) {
        while (infile) {
            std::string line;
            getline(infile, line);
            
            if (line!="EOF"){}
            
            //gets numTitles from first line
            std::string prefix = "NUM TITLES:";
            if(line.substr(0, prefix.size()) == prefix) {
                std::stringstream splitter (line);
                std::string text, numStr;
                getline(splitter, text, ':');
                getline(splitter, numStr, ':');
                
                //convert to int
                int numTitles;
                std::stringstream(numStr) >> numTitles;
                
                //create new inventory of correct size
                inventory = new Inventory(numTitles);
                
            }else{
                //get book info from file
                std::stringstream splitter2 (line);
                getline(splitter2, title, ':');
                getline(splitter2, haveStr, ':');
                getline(splitter2, wantStr, ':');
                getline(splitter2, wlStr, ':');
                
                //convert to int
                std::stringstream(haveStr) >> have;
                std::stringstream(wantStr) >> want;
                
                //create new book
                Book* temp = new Book(title,want);
                temp->setHave(have);
                
                //make waitlist, if it's not empty
                std::string prefix2 = "No wait list";
                if(wlStr.substr(0, prefix2.size()) != prefix) {
                    std::stringstream ss(wlStr);
                    std::vector<std::string> result;
                    
                    while( ss.good() )
                    {
                        std::string substr;
                        getline( ss, substr, ',' );
                        result.push_back( substr );
                    }
                    
                    ss.clear();
                    
                    for (int i=0;i<result.size();i++){
                        temp->addToWaitlist(result[i]);
                    }
                }
                
                //adds book to inventory
                inventory->addBook(temp);
            }
        }
    }
    else {
        throw FileNotFoundException();
    }
}

void BookStore::returnInvoice(std::string fileName){
    fileName = fileName + ".txt";
    std::ofstream outf(fileName);
    outf << "TAKE THE FOLLOW BOOKS OFF THE SHELF AND RETURN THEM:\n";
    for (int i = 0; i < inventory->getSize(); i++) {
        if (inventory->getBook(i)->getHave() > inventory->getBook(i)->getWant()) {
            outf << inventory->getBook(i)->getTitle() + ":";
            outf << inventory->getBook(i)->getHave();
            outf << ":";
            outf << inventory->getBook(i)->getWant();
            outf << "\n";
            inventory->getBook(i)->setHave(inventory->getBook(i)->getWant());
        }
    }
}

void BookStore::getDelivery(std::string fileName){
    //opens file
    std::ifstream infile(fileName+".txt");
    
    if (infile) {
        while (infile) {
            //split lines
            std::string line;
            getline(infile, line);
            std::stringstream splitter (line);
            std::string title, newHave;
            getline(splitter, title, ':');
            getline(splitter, newHave, ':');
            
            //convert to int
            int have;
            std::stringstream(newHave) >> have;
            
            //searches to see if book exists
            bool exists = inventory->boolSearch(title);
            
            //if it does exist, add books, remove waitlist names if there are any
            if (exists){
                Book* temp = inventory->search(title);
                int len = temp->getWaitListLength();
                
                if (len==0)
                    temp->increaseHave(have);
                //if we're getting more books than are on the waitlist
                else if (have >= len){
                    temp->deleteFromWaitList(len);
                    temp->increaseHave(have - len);
                }
                //if there are more names on the waitlist than in the delivery
                else if (len > have){
                    temp->deleteFromWaitList(have);
                }
            }
            
            //if it doesn't exist, make a new book
            Book* newBook = new Book(title,have);
            inventory->addBook(newBook);
        }
    }
    else {
        throw FileNotFoundException();
    }
}

void BookStore::printInventory(){
    std::cout << inventory->toString() << std::endl;
}

void BookStore::Menu(){
    std::cout<<"Welcome to the Bookstore!"<<std::endl;
    std::cout<<""<<std::endl;
    while (true){
        std::cout<<"Please input a command or press 'H' for summary of commands: ";
        char selection;
        std::cin>>selection;
        
        if (selection == 'H' || selection == 'h') {
            std::cout<<""<<std::endl;
            std::cout<<"User commands listed below"<<std::endl;
            std::cout<<"------------------------------"<<std::endl;
            std::cout<<"I : Display information for a specified title."<<std::endl;
            std::cout<<""<<std::endl;
            std::cout<<"L : Lists the information of the entire inventory in aplhabetical order"<<std::endl;
            std::cout<<""<<std::endl;
            std::cout<<"A : Add a book to the inventory"<<std::endl;
            std::cout<<""<<std::endl;
            std::cout<<"M : Modify the 'want' value for a specified title"<<std::endl;
            std::cout<<""<<std::endl;
            std::cout<<"O : Create a purchase order for additional books"<<std::endl;
            std::cout<<""<<std::endl;
            std::cout<<"D : Take information from a delivery shipment"<<std::endl;
            std::cout<<""<<std::endl;
            std::cout<<"R : Write a return invoice"<<std::endl;
            std::cout<<""<<std::endl;
            std::cout<<"S : Sell a book"<<std::endl;
            std::cout<<""<<std::endl;
            std::cout<<"Q : Quit (inventory will be saved)"<<std::endl;
            std::cout<<""<<std::endl;
        }
        
        else if(selection == 'Q' || selection =='q'){
            std::cout<<""<<std::endl;
            std::cout<<"Goodbye!"<<std::endl;
            break;
        }
        
        else if (selection == 'A' || selection == 'a'){
            std::cout<<""<<std::endl;
            std::cout<<"Enter the title of the book you would like to add to the inventory: ";
            std::string title;
            std::cin>>title;
            std::cout<<""<<std::endl;
            std::cout << "Enter number of copies desired: ";
            int num;
            std::cin >> num;
            std::cout<<""<<std::endl;
            Book* myBook = new Book(title, num);
            inventory->addBook(myBook);
            std::cout<<"Your book has successfully been added!"<<std::endl;
            std::cout<<""<<std::endl;
        }
        
        else if (selection == 'I' || selection == 'i'){
            std::cout<<""<<std::endl;
            std::cout<<"Enter the title you want info about: ";
            std::string title;
            std::cin>>title;
            std::cout<<""<<std::endl;
            try{
                inventory->search(title)->toString();
            }catch(std::exception){
                std::cout<<"We couldn't find that book in the inventory. Would you like to add this book? (Y/N): ";
                while (true) {
                    char choice;
                    std::cin>>choice;
                    if (choice == 'Y' || choice == 'y') {
                        std::cout<<""<<std::endl;
                        std::cout << "Enter number of copies desired: ";
                        int num;
                        std::cin >> num;
                        std::cout<<""<<std::endl;
                        Book* newBook = new Book(title, num);
                        inventory->addBook(newBook);
                        std::cout<<"Your book has successfully been added!"<<std::endl;
                        std::cout<<""<<std::endl;
                        break;
                    }
                    else if (choice == 'N' || choice == 'n'){
                        std::cout<<""<<std::endl;
                        std::cout<<"OK, the book was not added."<<std::endl;
                        break;
                    }
                    else{
                        std::cout<<"Invalid: Choose Y or N: ";
                    }
                }
            }
        }
        
        //create a purchase order
        else if (selection == 'O' || selection == 'o') {
            std::cout << "Enter a filename (without an extension) where the purchase order will be saved: ";
            std::string filename;
            std::cin >> filename;
            std::cout << "Creating purchase order..." << std::endl;
            std::cout <<std::endl;
            
            std::ofstream outf(filename+".txt");
            
            for (int i=0;i<inventory->getSize();i++){
                Book* temp = inventory->getBook(i);
                //if we want more books than we have
                if (temp->getWant() > temp->getHave()){
                    //convert difference to string
                    std::string difference;
                    std::ostringstream convert;
                    convert << temp->getWant()-temp->getHave();
                    difference = convert.str();
                    
                    //print to screen
                    std::cout << temp->getTitle();
                    std::cout << ":"+difference << std::endl;
                    
                    //write to file
                    outf << temp->getTitle();
                    outf << ":"+difference << std::endl;
                }
            }
            outf.close();
        }
        
        //read a delivery file and add shipment to inventory
        else if (selection == 'D' || selection == 'd'){
            std::cout << "Enter a filename (without an extension) where the delivery summary will be saved: ";
            std::string filename;
            std::cin >> filename;
            std::cout << "Adding delivered books to inventory..." << std::endl;
            std::cout << std::endl;
            
            getDelivery(filename);
        }
        else if (selection == 'R' || selection == 'r'){
            std::cout<<""<<std::endl;
            std::cout<<"Enter the filename (without an extension) where the return invoice will be saved: ";
            std::string fileName;
            std::cin>>fileName;
            std::cout<<""<<std::endl;
            try {
                returnInvoice(fileName);
                std::cout<<"The return invoice has been successfully written to " << fileName << " !" << std::endl;
                
            } catch (std::exception) {
                while (true) {
                    std::cout << "An error occured while writing to that file.  Please enter a new filename (without an extension: " << std::endl;
                    std::cin>>fileName;
                    try {
                        returnInvoice(fileName);
                        std::cout<<"The return invoice has been successfully written to " << fileName << " !" << std::endl;
                        break;
                    } catch(std::exception) {
                        continue;
                    }
                }
            }
            std::cout<<""<<std::endl;
        }
        else if (selection == 'L' || selection == 'l'){
            printInventory();
        }
    }
}