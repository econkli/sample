//
//  QueueWithNodes.cpp
//  LAB6
//
//  Created by Emily Conklin on 11/4/15.
//  Copyright © 2015 Emily Conklin. All rights reserved.
//

#include "QueueWithNodes.hpp"

QueueWithNodes::QueueWithNodes(){
    numNodes = 0;
    timer = 3;
    last = nullptr;
    first = nullptr;
}

//QueueWithNodes :: QueueWithNodes(const QueueWithNodes& queueToCopy){
//    numNodes = queueToCopy.numNodes;
//    timer = queueToCopy.timer;
//    if (queueToCopy.numNodes == 0) {
//        first = nullptr;
//        last = nullptr;
//    }
//    else{
//        //sets up temporary values for copying list
//        Node* toPaste = new Node(queueToCopy.first->getData());
//        first = toPaste;
//        Node* toCopy = queueToCopy.first;
//  
//        for (int i=0;i<numNodes-1;i++){
//            //steps through old list
//            toCopy = toCopy->getNext();
//            
//            //makes copy of old node, connects to new list
//            Node* newNode = new Node(toCopy->getData());
//            toPaste->setNext(newNode);
//            
//            //steps through new list
//            toPaste = toPaste->getNext();
//        }
//        last = new Node(toCopy->getData());
//        toPaste->setNext(last);
//        
//        toPaste = nullptr;
//        toCopy = nullptr;
//    }
//}

bool QueueWithNodes::isEmpty(){
    if (first==nullptr){
        timer++;
        return true;
    }
    else{
        timer++;
        return false;
    }
}

void QueueWithNodes::enqueue(ItemType newItem){
    Node* newNode = new Node(newItem);
    numNodes++;
    timer+=3;
    
    if (isEmpty()){
        timer++;
        first = newNode;
        last = first;
    }
    else{
        last->setNext(newNode);
    }
}

ItemType QueueWithNodes::dequeue(){
    if (isEmpty()){
        timer++;
        throw DequeueFromEmptyException();
    }
    else{
        ItemType toGet = first->getData();
        Node* next = first->getNext();
        numNodes--;
        delete first;
        first = next;
        timer+=6;
        return toGet;
    }
}

int QueueWithNodes::calcMemUsage(){
    int mem;
    Node* next = first;
    timer+=2;
    
    while (next!=nullptr){
        timer+=2;
        mem+=(sizeof(next) + sizeof(next->getData()));
        next = next->getNext();
    }
    timer+=2;
    mem+=sizeof(timer);
    mem+=sizeof(numNodes);
    return mem;
}

std::string QueueWithNodes::toString(){
    std::string out = "";
    std::string newOut;
    
    if (first!=nullptr){
        Node* next = first;
        while (next!=nullptr){
            out+=next->getData()+", ";
            next = next->getNext();
        }
        //removes comma and space from the end
        newOut = out.substr(0, out.size()-2);
    }
    else{
        newOut = "No wait list for this title.";
    }
    return newOut;
}

int QueueWithNodes::getElapsedTime(){
    timer++;
    return timer;
}

int QueueWithNodes::getNumNodes(){
    return numNodes;
}