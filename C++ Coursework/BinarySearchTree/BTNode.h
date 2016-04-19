//
//  BSTNode.h
//  TreeProject
//
//  Created by Toby Dragon on 11/13/14.
//  Copyright (c) 2014 Toby Dragon. All rights reserved.
//

#ifndef __TreeProject__BSTNode__
#define __TreeProject__BSTNode__

#include "GenericItemType.h"

class BTNode{
private:
    ItemType myItem;
    BTNode* left;
    BTNode* right;
    
public:
    BTNode(ItemType itemIn, BTNode* leftIn=nullptr, BTNode* rightIn=nullptr);
    ItemType getItem() const;
    BTNode* getLeft() const;
    BTNode* getRight() const;
    
    void setLeft(BTNode* leftIn);
    void setRight(BTNode* rightIn);
};

#endif /* defined(__TreeProject__BSTNode__) */
