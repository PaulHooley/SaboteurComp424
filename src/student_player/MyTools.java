package student_player;

import java.util.*;

import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;
import Saboteur.cardClasses.SaboteurCard;
import Saboteur.cardClasses.SaboteurTile;

public class MyTools {
    
    /* 
     * closestToGold should return the tile card and position to play if to make the move closest towards the middle hidden tile
     * needs to be optimized to move the towards the GOLD and with the proper card that connects to the gold
     */
    public static SaboteurMove closestToGold(ArrayList<SaboteurMove> posMoves, SaboteurBoardState boardState, ArrayList<SaboteurCard> myHand) {
    	
    	SaboteurMove nextMove = null;
    	SaboteurCard nextCard;
    	int[] nextPos;
    	double closestMove = 1000.00;
    	
    	for (SaboteurCard card : myHand) { // for all cards in my hand
    		if (card instanceof SaboteurTile) { // if it is a tile card
    			for (int[] tempPos: boardState.possiblePositions((SaboteurTile)card)) { // for all possible moves using this tile card
    				if (Math.sqrt( Math.pow(12-tempPos[0], 2) +  Math.pow(5-tempPos[1], 2))<closestMove) {
    					nextMove = new SaboteurMove(card, tempPos[0], tempPos[1], 260727150);
    					closestMove = Math.sqrt( Math.pow(12-tempPos[0], 2) +  Math.pow(5-tempPos[1], 2));
    				}
    			}
    		}
    	}
    	if (nextMove!=null) {
    		return nextMove;
    	}
    	else {
    		return boardState.getRandomMove();
    	}
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * numInHand should return a dictionary
     */
    public static Map numInHand(SaboteurBoardState boardState, ArrayList<SaboteurCard> myHand) {
    	Map<String, Integer> numCards = new HashMap<>();
	    	numCards.put("0", 0);
	   		numCards.put("1", 0);
	  		numCards.put("2", 0);
	  		numCards.put("3", 0);
	    	numCards.put("4", 0);
	    	numCards.put("5", 0);
	    	numCards.put("6", 0);
	    	numCards.put("7", 0);
	    	numCards.put("9", 0);
	    	numCards.put("10", 0);
	    	numCards.put("11", 0);
	    	numCards.put("12", 0);
	    	numCards.put("13", 0);
	    	numCards.put("14", 0);
	    	numCards.put("15", 0);
	    	numCards.put("destroy", 0);
	    	numCards.put("bonus", 0);
	    	numCards.put("malus", 0);
	    	numCards.put("map", 0); 
    	int x;
    	for (SaboteurCard card : myHand) { // for all cards in my hand
    		if (card.getName() == "0") {
    			x = numCards.get("0");
    			numCards.replace("0", x, x+1);
    		}
    		else if (card.getName() == "1") {
    			x = numCards.get("1");
    			numCards.replace("1", x, x+1);
    		}
    		else if (card.getName() == "2") {
    			x = numCards.get("2");
    			numCards.replace("2", x, x+1);
    		}
    		else if (card.getName() == "3") {
    			x = numCards.get("3");
    			numCards.replace("3", x, x+1);
    		}
    		else if (card.getName() == "4") {
    			x = numCards.get("4");
    			numCards.replace("4", x, x+1);
    		}
    		else if (card.getName() == "5") {
    			x = numCards.get("5");
    			numCards.replace("5", x, x+1);
    		}
    		else if (card.getName() == "6") {
    			x = numCards.get("6");
    			numCards.replace("6", x, x+1);
    		}
    		else if (card.getName() == "7") {
    			x = numCards.get("7");
    			numCards.replace("7", x, x+1);
    		}
    		else if (card.getName() == "8") {
    			x = numCards.get("8");
    			numCards.replace("8", x, x+1);
    		}
    		else if (card.getName() == "9") {
    			x = numCards.get("9");
    			numCards.replace("9", x, x+1);
    		}
    		else if (card.getName() == "10") {
    			x = numCards.get("10");
    			numCards.replace("10", x, x+1);
    		}
    		else if (card.getName() == "11") {
    			x = numCards.get("11");
    			numCards.replace("11", x, x+1);
    		}
    		else if (card.getName() == "12") {
    			x = numCards.get("12");
    			numCards.replace("12", x, x+1);
    		}
    		else if (card.getName() == "13") {
    			x = numCards.get("13");
    			numCards.replace("13", x, x+1);
    		}
    		else if (card.getName() == "14") {
    			x = numCards.get("14");
    			numCards.replace("14", x, x+1);
    		}
    		else if (card.getName() == "15") {
    			x = numCards.get("15");
    			numCards.replace("15", x, x+1);
    		}
    		else if (card.getName() == "malus") {
    			x = numCards.get("malus");
    			numCards.replace("malus", x, x+1);
    		}
    		else if (card.getName() == "map") {
    			x = numCards.get("map");
    			numCards.replace("map", x, x+1);
    		}
    		else if (card.getName() == "bonus") {
    			x = numCards.get("bonus");
    			numCards.replace("bonus", x, x+1);
    		}
    		else  {
    			x = numCards.get("destroy");
    			numCards.replace("destroy", x, x+1);
    		}
    	}
    	
    	return numCards;
    }
}