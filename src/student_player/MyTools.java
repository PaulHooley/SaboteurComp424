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
    	double closestMove = Double.MAX_VALUE;

    	
    	for (SaboteurCard card : myHand) { // for all cards in my hand
    		if (card instanceof SaboteurTile) { // if it is a tile card
    			if(((SaboteurTile) card).getPath()[1][1] == 1) { //If there is a through path
    				for (int[] tempPos: boardState.possiblePositions((SaboteurTile)card)) { // for all possible moves using this tile card
    					//Check Each through path of the tile
    					if(((SaboteurTile) card).getPath()[0][1] == 1 && closestMove > distToGold(tempPos[0]-1, tempPos[1])) { //Above
    						nextMove = new SaboteurMove(card, tempPos[0], tempPos[1], 260727150);
    						closestMove = distToGold(tempPos[0], tempPos[1]);
    					}
    					if(((SaboteurTile) card).getPath()[1][0] == 1 && closestMove > distToGold(tempPos[0], tempPos[1])-1) { // Left
    						nextMove = new SaboteurMove(card, tempPos[0], tempPos[1], 260727150);
    						closestMove = distToGold(tempPos[0], tempPos[1]);
    					}
    					if(((SaboteurTile) card).getPath()[1][2] == 1 && closestMove > distToGold(tempPos[0], tempPos[1])+1) { //Right
    						nextMove = new SaboteurMove(card, tempPos[0], tempPos[1], 260727150);
    						closestMove = distToGold(tempPos[0], tempPos[1]);
    					}
    					if(((SaboteurTile) card).getPath()[2][1] == 1 && closestMove > distToGold(tempPos[0]+1, tempPos[1])) { //Below
    						nextMove = new SaboteurMove(card, tempPos[0], tempPos[1], 260727150);
    						closestMove = distToGold(tempPos[0], tempPos[1]);
    					}
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
    public static double distToGold(int x, int y) {
    	return Math.sqrt( Math.pow(12-x, 2) +  Math.pow(5-y, 2));
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * returns true if we are malused and have a bonus card
     * this should take HIGH priority in our move selection 
     * behind winning or save-from-losing moves though
     */
    public static boolean canRemoveMalus(SaboteurBoardState boardState, ArrayList<SaboteurCard> myHand, int numMalus, Map<String, Integer> numCards) {
    		if (numMalus>=1 && numCards.get("bonus")>=1) {
    			return true;
    		}
    		else {
    			return false;
    		}
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * returns true if we have a map
     */
    public static boolean haveMap(SaboteurBoardState boardState, ArrayList<SaboteurCard> myHand, int numMalus, Map<String, Integer> numCards) {
		if (numCards.get("map")>=1) {
			return true;
		}
		else {
			return false;
		}
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * returns true if we have a destroy
     */
    public static boolean haveDestroy(SaboteurBoardState boardState, ArrayList<SaboteurCard> myHand, int numMalus, Map<String, Integer> numCards) {
		if (numCards.get("destroy")>=1) {
			return true;
		}
		else {
			return false;
		}
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * returns true if we have a malus card
     */
    public static boolean haveMalus(SaboteurBoardState boardState, ArrayList<SaboteurCard> myHand, int numMalus, Map<String, Integer> numCards) {
		if (numCards.get("malus")>=1) {
			return true;
		}
		else {
			return false;
		}
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * return current path or how long a path exists to (or from) the gold
     * altered (should try to prioritize movement downwards) depth first search
     * build a path that gets us as close to the goal as possible?
     * INCOMPLETE
     */
    public static ArrayList<int[]> findPath(SaboteurBoardState boardState, int[] start, int[] end) {
    	
    	ArrayList<int[]> path = new ArrayList<>();
    	return path;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * numInHand should return a dictionary defining our current hand
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