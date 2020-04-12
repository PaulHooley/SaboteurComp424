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
    	double closestMove = Integer.MAX_VALUE;
    	ArrayList<SaboteurMove> legalMoves = boardState.getAllLegalMoves();
		/////////////////////////////////////////////////////////////////////
		// between the long dashed lines is the potential win / close to win case
		int[] goldLoc = getGold(boardState);
		int[] adjGoldLoc = new int[] {37,17};
		int[] goldLoc1 = new int[] {12, 3};
		int[] goldLoc2 = new int[] {12, 7};
		if (Arrays.equals(goldLoc, new int[] {-1,-1})) {
			// potential gold locs
			goldLoc = new int[] {12,5};
		}
		else {
			// exact gold locs
			adjGoldLoc = new int[] {goldLoc[0]*3+2, goldLoc[1]*3+2};
			if (goldLoc[1]==3) {
				goldLoc1 = new int[] {12, 5};
				goldLoc2 = new int[] {12, 7};
			}
			else if (goldLoc[1]==7) {
				goldLoc1 = new int[] {12, 3};
				goldLoc2 = new int[] {12, 5};
			}
		}
		int touchH = 0;
		for (SaboteurMove potMove : legalMoves) {
			if (potMove.getCardPlayed() instanceof SaboteurTile) {
				if (connected(new SaboteurTile("8"), goldLoc, (SaboteurTile)potMove.getCardPlayed(), potMove.getPosPlayed())) {
					for (int[] surr : surr(goldLoc, boardState)) {
						if  (boardState.getHiddenBoard()[surr[0]][surr[1]]!=null) {
							touchH+=1;
						}
					}
					if (touchH==0) {
						System.out.println("$$$ $$$ $$$ $$$");
						return potMove;
					}
				}
				touchH=0;
				if (connected(new SaboteurTile("8"), goldLoc1, (SaboteurTile)potMove.getCardPlayed(), potMove.getPosPlayed())) {
					for (int[] surr : surr(goldLoc1, boardState)) {
						if  (boardState.getHiddenBoard()[surr[0]][surr[1]]!=null) {
							touchH+=1;
						}
					}
					if (touchH==0) {
						System.out.println("@@@ @@@ @@@ @@@");
						return potMove;
					}
				}
				touchH=0;
				if (connected(new SaboteurTile("8"), goldLoc2, (SaboteurTile)potMove.getCardPlayed(), potMove.getPosPlayed())) {
					for (int[] surr : surr(goldLoc2, boardState)) {
						if  (boardState.getHiddenBoard()[surr[0]][surr[1]]!=null) {
							touchH+=1;
						}
					}
					if (touchH==0) {
						System.out.println("### ### ### ###");
						return potMove;
					}
				}
			}
		}
		//////////////////////////////////////////////////////////////////////
    	
    	Iterator<SaboteurCard> it = myHand.iterator();
    	List<SaboteurCard> newCards = new ArrayList<>();
    	while(it.hasNext()) {
    		SaboteurCard card = it.next();
    		if (card instanceof SaboteurTile) {
    			newCards.add(((SaboteurTile) card).getFlipped());
    		}
    	}
    	myHand.addAll(newCards);
    	for(SaboteurCard card: myHand) {
    		System.out.println(card.getName());
    	}
    	for (SaboteurCard card : myHand) { // for all cards in my hand
    		if (card instanceof SaboteurTile) { // if it is a tile card
    			if(((SaboteurTile) card).getPath()[1][1] == 1) { //If there is a through path
    				for (int[] tempPos: boardState.possiblePositions((SaboteurTile)card)) { // for all possible moves using this tile card
    					//Check Each through path of the tile
    					//System.out.println(card.getName());
    					int[][] intCard = ((SaboteurTile) card).getPath();
    					
    					//Hardcode the rotate because its pissing me off
    					int[][] intBoard = boardState.getHiddenIntBoard();
    					intBoard[tempPos[0] * 3][tempPos[1] * 3] = intCard[0][2];
    					intBoard[tempPos[0] * 3][tempPos[1] * 3 + 1] = intCard[1][2];
    					intBoard[tempPos[0] * 3][tempPos[1] * 3 + 2] = intCard[2][2];
    					
    					intBoard[tempPos[0] * 3 + 1][tempPos[1] * 3] = intCard[0][1];
    					intBoard[tempPos[0] * 3 + 1][tempPos[1] * 3 + 1] = intCard[1][1];
    					intBoard[tempPos[0] * 3 + 1][tempPos[1] * 3 + 2] = intCard[2][1];
    					
    					intBoard[tempPos[0] * 3 + 2][tempPos[1] * 3] = intCard[0][0];
    					intBoard[tempPos[0] * 3 + 2][tempPos[1] * 3 + 1] = intCard[1][0];
    					intBoard[tempPos[0] * 3 + 2][tempPos[1] * 3 + 2] = intCard[2][0];
    					

//    					System.out.println("This is boardGame time");// Print out the board with the new temp card
//    			        for(int[] row: intBoard) {
//    			        	System.out.println(Arrays.toString(row));
//    			        }
    			        
    			        int[][] visitBoard = new int[intBoard.length][intBoard[0].length];
    			        for(int i = 0; i < intBoard.length; i++) {
    			        	for(int j = 0; j < intBoard[0].length; j++) {
    			        		visitBoard[i][j] = 0;
    			        	}
    			        }
    			        int tmp = recursiveFindPlay(intBoard, visitBoard, Integer.MAX_VALUE, 17, 17, adjGoldLoc[1], adjGoldLoc[0]);
    			        //System.out.println("Tmp: " + tmp + " closestMove: "+ closestMove);
    			        if(closestMove > tmp) {
    			        	closestMove = tmp;
    			        	nextMove = new SaboteurMove(card, tempPos[0], tempPos[1],boardState.getTurnPlayer());
    			        	System.out.println("Card: " + nextMove.getCardPlayed().getName() + "Pos: " + Arrays.toString(tempPos) );
    			        }
    				}
    			}
    		}
    	}
    	if (nextMove!=null) {
    		System.out.println("&&& &&& &&& &&&");
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
    		if (move.getCardPlayed().equals(tempMove.getCardPlayed()) && Arrays.equals(move.getPosPlayed(), (tempMove.getPosPlayed()))) {
        		System.out.println("%%%%%%%%%%%%");
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
    	for(SaboteurCard card : myHand) {
    		int count = numCards.getOrDefault(card.getName(), 0);
    		numCards.put(card.getName(), count+1);
    	}
    	return numCards;
    }
    ////////////////////////////
    public static int recursiveFindPlay(int[][] intBoard, int[][] visitBoard, int curShortestDistToGold,int x, int y, int goldX, int goldY) {
    	visitBoard[y][x] = 1;
    	int v1 = Integer.MAX_VALUE;
    	int v2 = Integer.MAX_VALUE;
    	int v3 = Integer.MAX_VALUE;
    	int v4 = Integer.MAX_VALUE;
//    	System.out.println("x: " + x + " y: " + y + " goldX: " + goldX + " goldY: " + goldY);
    	if(curShortestDistToGold > Math.abs(x-goldX) + Math.abs(y-goldY)) {
    		curShortestDistToGold = Math.abs(x-goldX) + Math.abs(y-goldY);
    	}
    	
    	if(intBoard[y+1][x] == 1 && visitBoard[y+1][x] != 1) {
    		visitBoard[y+1][x] = 1;
    		v1 = Math.min(curShortestDistToGold, recursiveFindPlay(intBoard, visitBoard, curShortestDistToGold, x, y+1, goldX, goldY));
    		
    	}
    	
    	if(intBoard[y][x+1] == 1 && visitBoard[y][x + 1] != 1) {
    		visitBoard[y][x + 1] = 1;
    		v2 = Math.min(curShortestDistToGold, recursiveFindPlay(intBoard, visitBoard, curShortestDistToGold, x+1, y, goldX, goldY));
    	}
    	
    	if(intBoard[y][x-1] == 1 && visitBoard[y][x - 1] != 1) {
    		visitBoard[y][x - 1] = 1;
    		v3 = Math.min(curShortestDistToGold, recursiveFindPlay(intBoard, visitBoard, curShortestDistToGold, x-1, y, goldX, goldY));
    	}
    	
    	if(intBoard[y-1][x] == 1 && visitBoard[y-1][x] != 1) {
    		visitBoard[y-1][x] = 1;
    		v4 = Math.min(curShortestDistToGold, recursiveFindPlay(intBoard, visitBoard, curShortestDistToGold, x, y-1, goldX, goldY));
    	}
    	int v5 = Math.min(v1, v2);
    	int v6 = Math.min(v3, v4);
    	int v = Math.min(v5, v6);
    	return v;
    }
    /////////////////////////////////////////
    /*
     *  connected returns true if the two tiles played at their given positions will have a connected path between them
     */
    public static boolean connected(SaboteurTile t1, int[] p1, SaboteurTile t2, int[] p2) {
    	int[][] t1path = t1.getPath();
    	int[][] t2path = t2.getPath();
    	if (Math.abs(p1[0]-p2[0])+Math.abs(p1[1]-p2[1])>1 || t1==null || t2==null || t1path[1][1]!=1 || t2path[1][1]!=1) {
    		return false;
    	}
    	else {
    		// if p1 is below p2
    		if (p1[0]-p2[0]==1) {
    			if (t1path[1][2]==1 && t2path[1][0]==1) {
    				return true;
    			}
    		}
    		// if p1 is above p2
    		else if (p1[0]-p2[0]==-1) {
    			if (t1path[1][0]==1 && t2path[1][2]==1) {
    				return true;
    			}
    		}
    		// if p1 is to the right of p2
    		else if (p1[1]-p2[1]==1) {
    			if (t1path[0][1]==1 && t2path[2][1]==1) {
    				return true;
    			}
    		}
    		// if p1 is to the left of p2
    		else if (p1[1]-p2[1]==-1) {
    			if (t1path[2][1]==1 && t2path[0][1]==1) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    public static int[] getGold(SaboteurBoardState boardState) {
    	SaboteurTile[][] board = boardState.getHiddenBoard();
    	if (board[12][3].getName()=="nugget") {
    		return new int[] {12,3};
    	}
    	else if (board[12][5].getName()=="nugget") {
    		return new int[] {12,5};
    	}
    	else if (board[12][7].getName()=="nugget") {
    		return new int[] {12,7};
    	}
    	else {
    		return new int[] {-1,-1};
    	}
    }
    
    public static SaboteurMove earlyMove(SaboteurBoardState boardState, ArrayList<SaboteurCard> myHand) {
    	ArrayList<SaboteurMove> legalMoves = boardState.getAllLegalMoves();
    	for (int i = 11; i>5; i--) {
    		for (int j = 3; j<8; j++) {
    			for (SaboteurMove move : legalMoves) {
    				if (move.getPosPlayed()[0] == i && move.getPosPlayed()[1] == j) {
	    				if (move.getCardPlayed().getName().contains("0")) {
	    					System.out.println("^^^^^^^^^^^^^^ ^^^^^^^^^^^^^^");
	    					return move;
	    				}
	    				else if (move.getCardPlayed().getName().contains("6")) {
	    					System.out.println("^^^^^^^^^^^^^^ ^^^^^^^^^^^^^^");
	    					return move;
	    				}
//	    				else if (move.getCardPlayed().getName()=="6_flip") {
//	    					System.out.println("^^^^^^^^^^^^^^ ^^^^^^^^^^^^^^");
//	    					return move;
//	    				}
	    				else if (move.getCardPlayed().getName().contains("8")) {
	    					System.out.println("^^^^^^^^^^^^^^ ^^^^^^^^^^^^^^");
	    					return move;
	    				}
//	    				else if (move.getCardPlayed().getName()=="9_flip") {
//	    					System.out.println("^^^^^^^^^^^^^^ ^^^^^^^^^^^^^^");
//	    					return move;
//	    				}
    				}
    			}
    		}
    	}
    	return closestToGold(boardState,myHand);
    }
 
}


