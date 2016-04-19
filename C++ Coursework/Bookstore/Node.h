//
//  Node.h
//  220-19-Nodes
//
//  Created by Toby Dragon on 10/8/15.
//  Copyright (c) 2015 Toby Dragon. All rights reserved.
//

#ifndef ___20_19_Nodes__Node__
#define ___20_19_Nodes__Node__

#include "GenericItemType.h"

class Node{
private:
    ItemType item;
    Node* next;
    
public:
    Node(ItemType itemIn);
    ItemType getData();
    Node* getNext();
    void setNext(Node* nextIn);
};

#endif /* defined(___20_19_Nodes__Node__) */
