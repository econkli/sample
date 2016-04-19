//
//  GenericItemType.h
//  Lab6-StacksAndQueues
//
//  Created by Toby Dragon on 10/8/15.
//  Copyright (c) 2015 Toby Dragon. All rights reserved.
//

#ifndef Lab6_StacksAndQueues_GenericItemType_h
#define Lab6_StacksAndQueues_GenericItemType_h

#include <string>

#define ItemType int
inline ItemType genericItemFromString (std::string strRep){ return stoi(strRep); }
inline std::string genericItemToString (ItemType item){ return std::to_string(item); }

//#define ItemType std::string
//inline ItemType genericItemFromString (std::string strRep){ return strRep; }
//inline ItemType genericItemToString (ItemType item){ return item; }


#endif
