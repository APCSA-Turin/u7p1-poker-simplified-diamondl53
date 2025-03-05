package com.example.project;
import java.util.ArrayList;


public class Game{
    public static String determineWinner(Player p1, Player p2,String p1Hand, String p2Hand,ArrayList<Card> communityCards){
        int rankingp1 = Utility.getHandRanking(p1Hand); //uses the utilityclass to set the ranks of the hands to integers that can be easily compared
        int rankingp2 = Utility.getHandRanking(p2Hand);
        
        if(rankingp1 > rankingp2){  
            //if the ranking of p1 is greater, they win
            return "Player 1 wins!";
        
        }else if(rankingp2 > rankingp1){
            //if the ranking of p2 is greatger, they win
            return "Player 2 wins!";
        
        }else if(rankingp2 == rankingp1){
            //if the ranking is the same, now we check who has the highest card in the hand
            
            if(Utility.getRankValue(p1.getHighCardinHand().getRank()) > Utility.getRankValue(p2.getHighCardinHand().getRank())){
                //if p1 has the higher card in the hand, they win
                return "Player 1 wins!";
            
            }else if(Utility.getRankValue(p2.getHighCardinHand().getRank()) > Utility.getRankValue(p1.getHighCardinHand().getRank())){
                //if p2 has the higher card in the hand, they win
                return "Player 2 wins!";
            
            }else{
                //if they both have the same ranking as their highest card in the hand, then it is a tie
                return "Tie!";
            }
        
        }else{
            return "Error!";
        }
    }

    public static void play(){ //simulate card playing
        
    }
        
        

}