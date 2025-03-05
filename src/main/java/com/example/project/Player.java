package com.example.project;
//import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;


public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    public Player(){
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    public void addCard(Card c){
        hand.add(c);
        allCards.add(c);//adds the card to the hand as well as the allCards list//
    }

    public boolean isHighCard(){
        sortAllCards();//first sort the cards 
        for(int i = 0; i < hand.size(); i++){
            if(hand.get(i).equals(getHighestCard())){//uses the getHighestCard method to see if the highest card in the hand is the highest card in the deck//
                return true;//if so, then it returns true 
            }
        }
        return false;
    }

    public Card getHighCardinHand(){//helper method to return the highest card in hand that I used in the isHighCard method
        Card highestHandCard = hand.get(0);//initializes the highest card to the first value
        for(int i = 0; i < hand.size(); i++){
            if(Utility.getRankValue(hand.get(i).getRank()) > Utility.getRankValue(highestHandCard.getRank())){//iterates through the hand to see if there is a higher card
                highestHandCard = hand.get(i);//if so, then it sets that value to the highest card
            }
        }
        return highestHandCard;
    }

    public Card getHighestCard(){
        sortAllCards();
        return allCards.get(allCards.size()-1);//sorts all of the cards and returns the last value in the list to return the highest card
    }
    
    public boolean isOnePair(){
        int count = 0;
        ArrayList<Integer> ranks = findRankingFrequency();//uses the findRankingFrequency method and counts the amount of times the value is >=2
        for(int i = 0; i < ranks.size(); i++){
            if(ranks.get(i)>=2){
                count++;
            }
        }
        if(count>=1){//if the frequency of a card was at least 2 at 1 incident, it is a one pair
            return true;
        }else{
            return false;
        }
    }
    public boolean isTwoPair(){
        int count = 0;
        ArrayList<Integer> ranks = findRankingFrequency();
        for(int i = 0; i < ranks.size(); i++){
            if(ranks.get(i)>=2){//same logic as the isOnePair
                count++;
            }
        }
        if(count>=2){//here, the frequency has to be 2 for at least 2 incidents in order to be a two pair
            return true;
        }else{
            return false;
        }
    }
    public boolean isThreeOfAKind(){
        ArrayList<Integer> rankFrequency = findRankingFrequency();
        for(int i = 0; i < rankFrequency.size(); i++){
            if(rankFrequency.get(i)==3){//used the findRankingFrequency method to see if a card of the same rank occured at least 3 times in the list
                return true;
            }
        }
        return false;
    }

    public boolean isFourOfAKind(){
        ArrayList<Integer> rankFrequency = findRankingFrequency();
            for(int i = 0; i < rankFrequency.size(); i++){
                if(rankFrequency.get(i)==4){//same logic as isThreeOfAKind but this time 4 cards of the same rank need to be in the list
                    return true;
                }
            }
            return false;
    }
    
    
    public boolean isStraight(){
        sortAllCards();//sorts the cards in order to make easier
        for(int i = 0; i < allCards.size()-1; i++){
            if(Utility.getRankValue(allCards.get(i+1).getRank()) != Utility.getRankValue(allCards.get(i).getRank())+1){
                return false;//if at any moment, the value in the list at an index is not the same as the value in the list in the next index, return false
            }
        }
        return true;//otherwise, it is a straight as all the cards are in increasing rank value by a value of one each time
    }

    public boolean isFlush(){
        String suits = allCards.get(0).getSuit();//initializes the suit that will be compared to the first vale in the cards list//
        if (allCards.size() == 0) {
            return false;  //if the length is 0, return false
        }
        for(int i = 1; i < allCards.size(); i++){
            if(!allCards.get(i).getSuit().equals(suits)){//from index 1 to the end, check if the suit of that card is not equal to the suit of index 0
                return false;//if at any point its not equal, return false
            }
        }
        return true;//otherwise, return true as all the suits in the list have the same suit so it is a flush
    }

    public boolean isFullHouse(){
        ArrayList<Integer> rankFrequency = findRankingFrequency();
        boolean hasTwoPair = false;//initializes two booleans to false and this method returns true if both are true
        boolean hasThreePair = false;
        for(int i = 0; i < rankFrequency.size(); i++){
            if(rankFrequency.get(i)==2){//iterates through the rankfrequency list to see if there is a is a TwoPair
                hasTwoPair = true;//sets this boolean to true
            }
            if(rankFrequency.get(i)==3){//iterates through the rankfrequency list to see if there is a ThreePair
                hasThreePair = true;//sets this boolean to true
            }
        }
        if(hasTwoPair && hasThreePair){//only if both booleans are true, then the hand is a full house and it returns true
            return true;
        }
        return false;
    }

    public boolean isStraightFlush(){
        if(isStraight() && isFlush()){//only if the isStraight method and isFlush method both are true, then the hand is a straightFlush
            return true;
        }else{
            return false;
        }
        
    }

    public boolean isRoyalFlush(){
        if(isStraightFlush() && getHighestCard().getRank().equals("A")){
            //for a royal flush to happen, it must be a straight flush with the highest card rank being an Ace, meaning the deck has to have ranks 10, jack, queen, king, ace//
            return true;
        }
        return false;
    }

    public boolean containsCommunity(ArrayList<Card> community){
        //helper method that I created due to some bugs that checks if the allCards list already has the community cards so I know whether or not I need to add the community cards to the allCards list
        int count = 0;
        for(int i = 0; i < allCards.size(); i++){
            for(int j = 0; j < community.size(); j++){
                if(allCards.get(i)==community.get(j)){//uses a nested for loop to individually check if a card in allCards is equal to any community card
                    count++;//increase count and break the inner loop to move on to the next card in the allCards list
                    break;
                }
            }
        }
        return count == 3; //since there are three community cards, if the count is three then the allCards already contains all the community cards
    }
    
    public String playHand(ArrayList<Card> communityCards){      
        if(!containsCommunity(communityCards)){
            for(int i = 0; i < communityCards.size(); i++){//uses the containsCommunity method and adds the community cards only if that method returns false
                allCards.add(communityCards.get(i));
            }
        }
        
        sortAllCards();//first sort all the cards in the list 
        if(isRoyalFlush()){//starts from the highest hand and iterates down the hand rankings until to lowest hand such that the highest hand of he deck is returned
            return "Royal Flush";
        }else if(isStraightFlush()){
            return "Straight Flush";
        }else if(isFourOfAKind()){
            return "Four of a Kind";
        }else if(isFullHouse()){
            return "Full House";
        }else if(isFlush()){
            return "Flush";
        }else if(isStraight()){
            return "Straight";
        }else if(isThreeOfAKind()){
            return "Three of a Kind";
        }else if(isTwoPair()){
            return "Two Pair";
        }else if(isOnePair()){
            return "A Pair";
        }else if(isHighCard()){
            return "High Card";
        }else{
            return "Nothing";
        }
    }

    public void sortAllCards(){//uses the insertion technique to sort the cards in ranking order
        for(int i = 1; i < allCards.size(); i++){//starts at the first index
            Card card = allCards.get(i);
            int index = i;
            while(index > 0 && Utility.getRankValue(card.getRank())< Utility.getRankValue(allCards.get(i-1).getRank())){//compares the card rank at the index to the one before 
                allCards.set(index, allCards.get(index-1));//if the one before is less, then set the card at the index to the card at the previous index
                index--;//subtract the index by one to keep going backwards to check for smaller values beforehand
            }
            allCards.set(index, card);//if the value at the previous index is not smaller or if you had reached the beginning end of the list, the loop breaks and set the value of that index to the card 
        }
    }
    
    public ArrayList<Integer> findRankingFrequency(){
        String rank;
        int index;
        
        ArrayList<Integer> rankFrequency = new ArrayList<Integer>();
        for(int i = 0; i < 13; i++){
            rankFrequency.add(0);//initially adds 0 to all the values in the list
        }
        for(int i = 0; i < allCards.size(); i++){//iterates through the deck to get the rank value which can be used to get the index of where to increment rankFrequency by
            rank = allCards.get(i).getRank();
            index = Utility.getRankValue(rank)-2;
            rankFrequency.set(index, rankFrequency.get(index)+1);//increments the rankFrequency at that index by one//
        }
        return rankFrequency; 
    }

    public ArrayList<Integer> findSuitFrequency(){
        String suit;
        int index = 0;
        ArrayList<Integer> suitFrequency = new ArrayList<Integer>();
        for(int i = 0; i < 4; i++){
            suitFrequency.add(0);//initially sets all the suitFrequencies to 0 
        }
        for(int i = 0; i < allCards.size(); i++){
            suit = allCards.get(i).getSuit();
            for(String currentSuit: suits){//nested for loop to check what kind of suit the card at that index is
                if(currentSuit.equals(suit)){
                    if(suit.equals("♠")){//sets the index accordingly based on the type of suit//
                        index = 0;
                    }
                    if(suit.equals("♥")){
                        index = 1;
                    }
                    if(suit.equals("♣")){
                        index = 2;
                    }
                    if(suit.equals("♦")){
                        index = 3;
                    }
                    suitFrequency.set(index, suitFrequency.get(index)+1);//increments the suitFrequency at that index by 1 if the suit is found//
                }
            }
        }
    return suitFrequency; 
    }

   
    @Override
    public String toString(){
        return hand.toString();
    }

    public static void main(String[] args) {
        // Player player = new Player();
        // player.addCard(new Card("10", "♠"));
        // player.addCard(new Card("J", "♠"));
        
        // // Community Cards
        // ArrayList<Card> communityCards = new ArrayList<>();
        // communityCards.add(new Card("Q", "♠"));
        // communityCards.add(new Card("K", "♠"));
        // communityCards.add(new Card("A", "♠"));
        
        // player.playHand(communityCards);
        // String handResult = player.playHand(communityCards);

        // System.out.println(handResult);

        // Player player2 = new Player();
        // player.addCard(new Card("10", "♠"));
        // player.addCard(new Card("J", "♠"));
        
        // // Community Cards
        // ArrayList<Card> communityCards2 = new ArrayList<>();
        // communityCards2.add(new Card("3", "♠"));
        // communityCards2.add(new Card("7", "♠"));
        // communityCards2.add(new Card("A", "♠"));
        
        // player.playHand(communityCards2);
        // String handResult2 = player.playHand(communityCards2);
        // System.out.println(handResult2);

        // Player player = new Player();
        // player.addCard(new Card("A", "♦"));
        // player.addCard(new Card("9", "♦"));
        
        // // Community Cards
        // ArrayList<Card> communityCards = new ArrayList<>();
        // communityCards.add(new Card("9", "♣"));
        // communityCards.add(new Card("9", "♥"));
        // communityCards.add(new Card("9", "♠"));     
        // player.playHand(communityCards);
        // String handResult = player.playHand(communityCards);
        // System.out.println(handResult);

        Player player = new Player();
        player.addCard(new Card("5", "♠"));
        player.addCard(new Card("7", "♦"));
        System.out.println(player.getAllCards());
        // Community Cards
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("6", "♠"));
        communityCards.add(new Card("8", "♣"));
        communityCards.add(new Card("9", "♠"));
        System.out.println(player.getAllCards());

        player.playHand(communityCards);
        System.out.println(player.getAllCards());

        String handResult = player.playHand(communityCards);
        System.out.println(player.getAllCards());
        
    }




}
