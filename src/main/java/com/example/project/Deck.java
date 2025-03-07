package com.example.project;
import java.util.ArrayList;
import java.util.Collections;

public class Deck{
    private ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public  void initializeDeck(){ //hint.. use the utility class
        for(int i = 0; i < Utility.getRanks().length; i++){//uses the utility class to get the ranks and suits
            for(int k = 0; k < Utility.getSuits().length; k++){//uses a nested for loop to get every possible card in the deck 
                cards.add(new Card (Utility.getRanks()[i], Utility.getSuits()[k]));//each card has a different rank and suit
            }
        }
    }

    public  void shuffleDeck(){ //You can use the Collections library or another method. You do not have to create your own shuffle algorithm
        Collections.shuffle(cards);//uses the .shuffle method to shuffle the card
    }

    public Card drawCard(){
       if(cards.get(0)!=null){
        return cards.remove(0);
       }
        return null;
    }

    public  boolean isEmpty(){
        return cards.isEmpty();
    }

    public static void main(String[] args) {
        Deck deck1 = new Deck();
        System.out.println(deck1.getCards());
    }

   


}