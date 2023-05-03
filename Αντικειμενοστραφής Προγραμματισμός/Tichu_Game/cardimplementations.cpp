#include "card.h"

using namespace std;

SimpleCard::SimpleCard(int value , CardSuit suit) : Card()
{
    this->value = value;
    this->suit = suit;
}

int SimpleCard::getPoints(){
    if(value == 5){
        return 5;
    }
    else if(value == 10 || value == 13){
        return 10;
    }
    else{
        return 0;
    }
}

bool SimpleCard::canBeInCombination(){
    return true;
}

bool SimpleCard::canBeInBomb(){
    return true;
}

SpecialCard::SpecialCard(CardSuit suit) : Card()
{
    this->suit = suit;
    if(suit == MAHJONG){
        value = 1;
    }
    else if(suit == DRAGON){
        value = 15;
    }
    else if(suit == PHOENIX){
        value = -1;
    }
    else if(suit == DOG){
        value = -1;
    }
}

int SpecialCard::getPoints(){
    if(suit == DRAGON){
        return 25;
    }
    else if(suit == PHOENIX){
        return -25;
    }
    else{
        return 0;
    }
}

bool SpecialCard::canBeInCombination(){
    if(suit == MAHJONG || suit == PHOENIX){
        return true;
    }
    else if(suit == DRAGON || suit == DOG){
        return false;
    }
}

bool SpecialCard::canBeInBomb(){
    return false;
}
// TODO: Implement here the methods of SimpleCard and SpecialCard
// Copy your implementation from the fourth deliverable

