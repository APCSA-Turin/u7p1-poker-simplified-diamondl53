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
        for(int i = 0; i < Utility.getRanks().length; i++){
            for(int k = 0; k < Utility.getSuits().length; k++){
                cards.add(New Car ())
            }
        }
    }

    public  void shuffleDeck(){ //You can use the Collections library or another method. You do not have to create your own shuffle algorithm
        
    }

    public  Card drawCard(){
       return new Card("","");
    }

    public  boolean isEmpty(){
        return cards.isEmpty();
    }

   


}