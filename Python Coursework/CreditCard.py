#------------------------------------------------
#Emily Conklin
#11/18/14
#This program performs various credit card operations

#Takes a user's credit card details (name, card number, credit score)
#Sets original balance at zero, calculates a card limit and APR based on the credit score
#Allows user to charge, pay off, add monthly interest to balance, or transfer funds from one card to another
#Rejects charges or transfers that exceed limit
#------------------------------------------------

import random

class CreditCard:       #sets up class to create and store credit card information
    '''
    Class 'CreditCard' stores individual credit card information,
    performs charge, payoff, interest, & display operations
    '''
    def __init__(self,nameIn,numberIn,creditIn):    #takes initial credit card information
        '''
        Sets up credit card given user information,
        sets up intiial balance of zero,
        calculates limit and APR based on credit score
        '''
        self.name = nameIn          #card holder's name
        self.number = numberIn      #card holder's number
        self.credit = creditIn      #card holder's credit score

        self.balance = 0

        if self.credit>750:         #if credit score is over 750, limit is $10000
            self.limit=10000
            self.apr=8
        elif 750>self.credit>600:   #if credit score is between 600 and 750, limit is $5000
            self.limit=5000
            self.apr=12
        elif self.credit<600:       #if credit score is under 600, limit is $2000
            self.limit=2000
            self.apr=20

    def charge(self,numberIn,chargeIn):     #method: charge an amount to a card
        '''
        Takes desired amount to charge
        if charge sends balance over the limit, charge is denied
        otherwise, adds charge to balance
        '''
        print("Balance before charge:",self.balance)
        testBalance=self.balance            #sets up test balance to see if card will be rejected
        
        if numberIn==self.number:           #if card number is accepted
            testBalance+=chargeIn           #add charge to test balance
            if testBalance>self.limit:      #if test balance is greater than the limit, card is rejected
                print("Card denied. Operation not completed.")
                print("Balance after charge:",self.balance)
                print()
                return False
            else:
                self.balance+=chargeIn      #if test balance is not greater than limit, add charge to real balance
                print("Balance after charge:",self.balance)
                print()
                return True
        else:                               #if card number is not accepted
            return False

    def payoff(self,numberIn,payIn):        #method: payoff an amount to a card
        '''
        Takes desired amount to pay off
        and subtracts it from the balance
        '''
        if numberIn==self.number:           #if card number is accepted
            self.balance-=payIn             #subtract amount from balance
            return True
        else:                               #if card number is not accepted
            return False

    def monthlyInterest(self,numberIn):     #method: calculate and add a monthly interest
        '''
        Calculates monthly interest based on APR
        and adds it to the balance
        '''
        print("Balance before interest:",self.balance)
        if numberIn==self.number:           #if card number is accepted
            if self.balance>0:              #if there is a positive balance
                toAdd=self.balance*(self.apr/100)       #calculates amount to add based on APR
                self.balance+=toAdd                     #adds interest to balance
            print("Balance after interest:",self.balance)
            print()
            return True
        else:                               #if card number is not accepted
            return False

    def displayCard(self,numberIn):         #method: display credit card information
        '''
        Displays a user's credit card information on request
        '''
        if numberIn==self.number:           #if card number is accepted
            print("Name:",self.name)
            print("Credit card number:",self.number)
            print("Credit score:",self.credit)
            print("Credit limit:",self.limit)
            print("APR:",self.apr)
            print("Current balance:",self.balance)
            print()
            return True
        else:                               #if card number is accepted
            return False

def main():     #runs seven requested tests
    '''
    Sets up individual credit cards and performs defined operations on these credit cards
    '''
    print("TEST 1: Create two different cards")
    print()
    creditNumber1 = random.randrange(1000000000000000,9999999999999999)
    card1 = CreditCard("Peter Parker",creditNumber1,500)
    card1.displayCard(creditNumber1)

    creditNumber2 = random.randrange(1000000000000000,9999999999999999)
    card2 = CreditCard("Tony Stark",creditNumber2,800)
    card2.displayCard(creditNumber2)

    input("Press any key for next test (2/7): ")
    print("TEST 2: Make working charges to cards")
    print()
    print("Card 1:")
    card1.charge(creditNumber1,50)

    input("Press any key for next test (3/7): ")
    print("TEST 3: Make declined charges to cards")
    print()
    print("Card 2:")
    card2.charge(creditNumber2,50000)

    input("Press any key for next test (4/7): ")
    print("TEST 4: Calculate monthly interest")
    print()
    print("Card 1:")
    card1.monthlyInterest(creditNumber1)

    input("Press any key for next test (5/7): ")
    print("TEST 5: Calculate monthly interest on a card with negative balance")
    print()
    print("Card 2:")
    card2.payoff(creditNumber2,50)
    card2.monthlyInterest(creditNumber2)
    
    input("Press any key for next test (6/7): ")
    print("TEST 6: Make a transfer")
    print()
    print("Before card 2 transferring $50 to card 1:")
    card1.displayCard(creditNumber1)
    card2.displayCard(creditNumber2)
    transfer(creditNumber1,creditNumber2,card1,card2,50)
    print("After card 2 transferring $50 to card 1:")
    card1.displayCard(creditNumber1)
    card2.displayCard(creditNumber2)

    input("Press any key for next test (7/7): ")
    print("TEST 7: Make a declined transfer")
    print()
    print("Before card 2 transferring $1000000 to card 1:")
    card1.displayCard(creditNumber1)
    card2.displayCard(creditNumber2)
    transfer(creditNumber1,creditNumber2,card1,card2,1000000)
    print("After card 2 transferring $1000000 to card 1:")
    card1.displayCard(creditNumber1)
    card2.displayCard(creditNumber2)


def transfer(firstNumber,secondNumber,firstCard,secondCard,toTransfer): #allows user to transfer funds from one card to the other
    '''
    Transfers balance from one card to another
    '''
    success=secondCard.charge(secondNumber,toTransfer)      #charges first card a specified amount
    if success==True:                                       #if charge is accepted, payoff the same amount to the second card
       firstCard.payoff(firstNumber,toTransfer)

main()
