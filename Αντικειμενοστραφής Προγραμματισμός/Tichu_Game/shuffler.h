#include<iostream>
#include <cstdlib>
#include <ctime>

using namespace std;

/**
 * Shuffles an array using an algorithm that initially iterates an index i from the last to second element.
 * And then a second index j is used to select an element randomly between the first element and the i-th element.
 * And finally The elements in positions i and j are swapped.
 *
 * @param myArray the array to be shuffled.
 * @param myArraySize the index of the array to be shuffled.
 */
template <class X> void shuffle(X** myArray, int myArraySize){
    int i , j;
    for(i = myArraySize - 1 ; i > 0 ; i--){
        X* temp;
        srand(time(NULL));
        j = rand() % (i + 1);
        temp = myArray[j];
        myArray[j] = myArray[i];
        myArray[i] = temp;
    }
    // TODO: Implement here the shuffle algorithm
    // Copy your implementation from the fourth deliverable
}

/**
 * "Cuts" an array at a random point similarly to a deck of cards (i.e. getting the last part of
 * the cards and moving it before the first part of the cards).
 *
 * @param myArray the array to be "cut".
 * @param myArraySize the index of the array to be "cut".
 */
template <class X> void cut(X** myArray, int myArraySize){
    X** tempArray;
    int cutPoint;
    srand(time(NULL));
    cutPoint = rand() % (myArraySize + 1);
    tempArray = new X*[cutPoint];
    int j = 0 , i;
    for(i = 0 ; i < cutPoint ; i++){
        tempArray[i] = myArray[i];
    }
    for(i = cutPoint ; i < myArraySize ; i++){
        myArray[j++] = myArray[i];
    }
    for(i = 0 ; i < cutPoint ; i++){
        myArray[myArraySize - (cutPoint - i)] = tempArray[i];
    }
    // TODO: Implement here the cut algorithm
    // Copy your implementation from the fourth deliverable
}
