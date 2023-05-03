#include <cstdlib>
#include "player.h"
#include "shuffler.h"

SmartPlayer::SmartPlayer(int index) : Player(index){
    team = "Team 063"; // TODO: Change this to your team number e.g. "Team 150"
}

int SmartPlayer::setPhoenixValue(Table* table, PlayerStatus* playerStatuses, int* numberOfCardsPerPlayer, Combination* lastCombinationOnTable){

    // TODO: Implement this function (some useful objects that you may use are the cards that can be retrieved from the hand
    // or the playable combinations that are given as a parameter)
    /// This function MUST return an integer value from 1 to 14
    return 1;
}

int SmartPlayer::decideAndPlay(Combination** playableCombinations, int numPlayableCombinations, Table* table, PlayerStatus* playerStatuses, int* numberOfCardsPerPlayer, Combination* lastCombinationOnTable){
    if(playerStatuses[0] = HASNTPLAYED && playerStatuses[1] = HASNTPLAYED && playerStatuses[2] = HASNTPLAYED && playerStatuses[3] = HASNTPLAYED){

    }
    // TODO: Implement this function (some useful objects that you may use are the cards that can be retrieved from the hand
    // or the combinations that are currently on the table and can be retrieved from the table or the number of cards or the statuses of other players)
    /// After that, it MUST return an integer index to the combinations available in this dynamic array (a Combination* object).
    /// This function can also return -1 if the player wishes to pass, however -1 CANNOT BE RETURNED if the last given
    /// combination (the 'Combination* lastCombinationOnTable' that is given as a parameter) is NULL
    return 0;
}

void SmartPlayer::watch(Table* table, PlayerStatus* playerStatuses, int* numberOfCardsPerPlayer){
    // TODO: This function is optional. It is called whenever the player cannot play in order to watch the game.
    /// Use this to watch what cards/combinations are played, or just simply leave it empty.
}
