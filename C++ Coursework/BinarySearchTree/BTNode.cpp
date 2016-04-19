//
//  BSTNode.cpp
//  TreeProject
//
//  Created by Toby Dragon on 11/13/14.
//  Copyright (c) 2014 Toby Dragon. All rights reserved.
//
//#include "stdafx.h"

#include "BTNode.h"

BTNode::BTNode(ItemType itemIn, BTNode* leftIn, BTNode* rightIn){
    myItem = itemIn;
    left = leftIn;
    right = rightIn;
}
ItemType BTNode::getItem() const{
    return myItem;
}
BTNode* BTNode::getLeft() const{
    return left;
}
BTNode* BTNode::getRight() const{
    return right;
}
void BTNode::setLeft(BTNode* leftIn){
    left = leftIn;
}
void BTNode::setRight(BTNode* rightIn){
    right = rightIn;
}