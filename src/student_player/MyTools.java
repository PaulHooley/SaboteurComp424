package student_player;

import java.util.*;

import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;
import Saboteur.cardClasses.*;

public class MyTools {
    /* 
     * closestToGold should return the tile card and position to play if to make the move closest towards the middle hidden tile
     * needs to be optimized to move the towards the GOLD and with the proper card that connects to the gold
     */
    public static SaboteurMove closestToGold(SaboteurBoardState boardState, ArrayList<SaboteurCard> myHand) {
    	
    	SaboteurMove nextMove = null;
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
     * returns the index of the first instance of a given card type, -1 if you don't have an instance in your hand
     */
    public static int cardIndex(ArrayList<SaboteurCard> myHand, SaboteurCard card) {
    	int i=0;
    	for (SaboteurCard tempCard: myHand) {
    		if (tempCard.getName()==card.getName()) {
    			return i;
    		}
    		i++;
    	}
    	return -1;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * returns true if we are malused and have a bonus card
     * this should take HIGH priority in our move selection 
     * behind winning or save-from-losing moves though
     */
    public static SaboteurMove areMalused(SaboteurBoardState boardState, ArrayList<SaboteurCard> myHand, int numMalus, Map<String, Integer> numCards) { 
    	if (numMalus>=1) {
    		if (cardIndex( myHand, new SaboteurBonus())!=-1) {
	    		return new SaboteurMove(new SaboteurBonus(), 0, 0, 260727150);
    		}
    		else if (cardIndex( myHand, new SaboteurMap())!=-1) {
	    		SaboteurMove testMove = haveMap(boardState, myHand, numCards);
	    		if (testMove!=null) {
	    			return testMove;
	    		}
	    		else if (cardIndex(myHand, new SaboteurDestroy())!=-1) {
	    			return new SaboteurMove(new SaboteurDrop(), cardIndex(myHand, new SaboteurDestroy()), 0, 260727150);
	    		}
	    		else if (cardIndex(myHand, new SaboteurMalus())!=-1) {
	    			return new SaboteurMove(new SaboteurDrop(), cardIndex(myHand, new SaboteurMalus()), 0, 260727150);
	    		}
	    		else {
	    			return new SaboteurMove(new SaboteurDrop(), cardIndex(myHand, new SaboteurMap()), 0, 260727150);
	    		}
    		}
    		else {
    			return new SaboteurMove(new SaboteurDrop(), 0, 0, 260727150);
    		}
    	}
    	else {
    		return null;
    	}
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * returns true if we have a map
     */
    public static SaboteurMove haveMap(SaboteurBoardState boardState, ArrayList<SaboteurCard> myHand, Map<String, Integer> numCards) {
    	if (cardIndex( myHand, new SaboteurMap())!=-1) {
    		if (boardState.getHiddenBoard()[12][3]==null) {
    			return new SaboteurMove(new SaboteurMap(), 12, 3, 260727150);
    		}
    		else if (boardState.getHiddenBoard()[12][5]==null) {
    			return new SaboteurMove(new SaboteurMap(), 12, 5, 260727150);
    		}
    		else if (boardState.getHiddenBoard()[12][7]==null) {
    			return new SaboteurMove(new SaboteurMap(), 12, 7, 260727150);
    		}
    	}
    	return null;
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
    
    public static int[][] transpose(int[][] original) {
	    int[][] transposed = original;
    	for(int i=0;i<original.length;i++){    
	    	for(int j=0;j<original[0].length;j++){    
	    		transposed[i][j]=original[j][i];  
	    	}    
	    }
    	return transposed;
    }
    
    /*
     * throughPath returns the Move that results in creating a through path from curr[i,j] to [i',j'], returns null if you can't
     */
    public static SaboteurMove throughPath(SaboteurBoardState boardState, int[] start, int[] end, SaboteurCard card) {
    	ArrayList<SaboteurMove> legalMoves = boardState.getAllLegalMoves();
    	SaboteurMove potMove = new SaboteurMove(card, start[0], start[1], 260727150);
    	// if the tile we are trying to build a through path to is further than 1 tile gap away, it can't be done
    	if ((Math.abs(end[0]-start[0])+Math.abs(end[1]-start[1])) != 1) return null;
    	if (card instanceof SaboteurTile && isElement(potMove, legalMoves)) { // if it is a tile card
    		// this is if the goal path is two down
    		if (end[0]-start[0]==-1 && ((SaboteurTile)card).getPath()[1][0]==1 && ((SaboteurTile)card).getPath()[1][1]==1) {
    			return potMove;
    		}
    		// this is if the goal path is two down
    		else if (end[0]-start[0]==1 && ((SaboteurTile)card).getPath()[1][1]==1 && ((SaboteurTile)card).getPath()[1][2]==1) {
    			return potMove;
    		}
    		else if (end[1]-start[1]==-1 && ((SaboteurTile)card).getPath()[0][1]==1 && ((SaboteurTile)card).getPath()[1][1]==1) {
    			return potMove;
    		}
    		// this is if the goal path is two down
    		else if (end[1]-start[1]==1 && ((SaboteurTile)card).getPath()[1][1]==1 && ((SaboteurTile)card).getPath()[2][1]==1) {
    			return potMove;
    		}
    	}	
    	return null;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static ArrayList<int[]> notEmptySurr(int[] currPos, SaboteurBoardState boardState) {
    	ArrayList<int[]> surr = new ArrayList<int[]>();
    	for (int i = 0; i<4; i++) {
    		if (boardState.getHiddenBoard()[currPos[0]+1][currPos[1]]!=null) {
    			surr.add(new int[] {currPos[0]+1, currPos[1]});
    		}
    		if (boardState.getHiddenBoard()[currPos[0]-1][currPos[1]]!=null) {
    			surr.add(new int[] {currPos[0]-1, currPos[1]});
    		}
    		if (boardState.getHiddenBoard()[currPos[0]][currPos[1]+1]!=null) {
    			surr.add(new int[] {currPos[0], currPos[1]+1});
    		}
    		if (boardState.getHiddenBoard()[currPos[0]][currPos[1]-1]!=null) {
    			surr.add(new int[] {currPos[0], currPos[1]-1});
    		}
    	}
    	return surr;
    }
    ///////////////
    public static ArrayList<int[]> emptySurr(int[] currPos, SaboteurBoardState boardState) {
    	ArrayList<int[]> surr = new ArrayList<int[]>();
    	for (int i = 0; i<4; i++) {
    		if (boardState.getHiddenBoard()[currPos[0]+1][currPos[1]]==null) {
    			surr.add(new int[] {currPos[0]+1, currPos[1]});
    		}
    		if (boardState.getHiddenBoard()[currPos[0]-1][currPos[1]]==null) {
    			surr.add(new int[] {currPos[0]-1, currPos[1]});
    		}
    		if (boardState.getHiddenBoard()[currPos[0]][currPos[1]+1]==null) {
    			surr.add(new int[] {currPos[0], currPos[1]+1});
    		}
    		if (boardState.getHiddenBoard()[currPos[0]][currPos[1]-1]==null) {
    			surr.add(new int[] {currPos[0], currPos[1]-1});
    		}
    	}
    	return surr;
    }
    //////////////
    public static ArrayList<int[]> surr(int[] currPos, SaboteurBoardState boardState) {
    	ArrayList<int[]> surr = new ArrayList<int[]>();
    	for (int i = 0; i<4; i++) {
    		surr.add(new int[] {currPos[0]+1, currPos[1]});
    		surr.add(new int[] {currPos[0]-1, currPos[1]});
    		surr.add(new int[] {currPos[0], currPos[1]+1});
    		surr.add(new int[] {currPos[0], currPos[1]-1});
    	}
    	return surr;
    }
    public static boolean isElement(SaboteurMove move, ArrayList<SaboteurMove> moves) {
    	for (SaboteurMove tempMove: moves) {
    		if (move.equals(tempMove) ) {
    			return true;
    		}
    	}
    	return false;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static SaboteurMove inTight(SaboteurBoardState boardState, ArrayList<SaboteurCard> myHand) {
    	for (SaboteurCard card : myHand) { // for all cards in my hand
    		if (card instanceof SaboteurTile) { // if it is a tile card
		    	for (int[] tempPos: boardState.possiblePositions((SaboteurTile)card)) {// for all possible moves using this tile card
		    		for (int[] endPos : surr(tempPos, boardState)) {
		    			SaboteurMove option = throughPath(boardState, tempPos, endPos, card);
			    		if (option!=null) {
			    			return option;
			    		}
		    		}
		    	}
		    }
		}
    	return closestToGold(boardState, myHand);
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
    		if (card.getName().compareTo("0")==0) {
    			x = numCards.get("0");
    			numCards.replace("0", x, x+1);
    		}
    		else if (card.getName().compareTo("1")==0) {
    			x = numCards.get("1");
    			numCards.replace("1", x, x+1);
    		}
    		else if (card.getName().compareTo("2")==0) {
    			x = numCards.get("2");
    			numCards.replace("2", x, x+1);
    		}
    		else if (card.getName().compareTo("3")==0) {
    			x = numCards.get("3");
    			numCards.replace("3", x, x+1);
    		}
    		else if (card.getName().compareTo("4")==0) {
    			x = numCards.get("4");
    			numCards.replace("4", x, x+1);
    		}
    		else if (card.getName().compareTo("5")==0) {
    			x = numCards.get("5");
    			numCards.replace("5", x, x+1);
    		}
    		else if (card.getName().compareTo("6")==0) {
    			x = numCards.get("6");
    			numCards.replace("6", x, x+1);
    		}
    		else if (card.getName().compareTo("7")==0) {
    			x = numCards.get("7");
    			numCards.replace("7", x, x+1);
    		}
    		else if (card.getName().compareTo("8")==0) {
    			x = numCards.get("8");
    			numCards.replace("8", x, x+1);
    		}
    		else if (card.getName().compareTo("9")==0) {
    			x = numCards.get("9");
    			numCards.replace("9", x, x+1);
    		}
    		else if (card.getName().compareTo("10")==0) {
    			x = numCards.get("10");
    			numCards.replace("10", x, x+1);
    		}
    		else if (card.getName().compareTo("11")==0) {
    			x = numCards.get("11");
    			numCards.replace("11", x, x+1);
    		}
    		else if (card.getName().compareTo("12")==0) {
    			x = numCards.get("12");
    			numCards.replace("12", x, x+1);
    		}
    		else if (card.getName().compareTo("13")==0) {
    			x = numCards.get("13");
    			numCards.replace("13", x, x+1);
    		}
    		else if (card.getName().compareTo("14")==0) {
    			x = numCards.get("14");
    			numCards.replace("14", x, x+1);
    		}
    		else if (card.getName().compareTo("15")==0) {
    			x = numCards.get("15");
    			numCards.replace("15", x, x+1);
    		}
    		else if (card.getName().compareTo("malus")==0) {
    			x = numCards.get("malus");
    			numCards.replace("malus", x, x+1);
    		}
    		else if (card.getName().compareTo("map")==0) {
    			x = numCards.get("map");
    			numCards.replace("map", x, x+1);
    		}
    		else if (card.getName().compareTo("bonus")==0) {
    			x = numCards.get("bonus");
    			numCards.replace("bonus", x, x+1);
    		}
    		else if (card.getName().compareTo("destroy")==0) {
    			x = numCards.get("destroy");
    			numCards.replace("destroy", x, x+1);
    		}
    	}
    	return numCards;
    }
}


