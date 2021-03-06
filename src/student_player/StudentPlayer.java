package student_player;

import boardgame.Move;

import Saboteur.SaboteurPlayer;
import Saboteur.cardClasses.SaboteurCard;
import Saboteur.cardClasses.SaboteurTile;

import java.util.ArrayList;
import java.util.Map;

import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;

/** A player file submitted by a student. */
public class StudentPlayer extends SaboteurPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260727150");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(SaboteurBoardState boardState) {
//    	System.out.println(MyTools.connected(new SaboteurTile("0"), new int[] {1,1},new SaboteurTile("0"), new int[] {2,1}));
//    	System.out.println(MyTools.connected(new SaboteurTile("0"), new int[] {1,1},new SaboteurTile("1"), new int[] {2,1}));
//    	System.out.println(MyTools.connected(new SaboteurTile("0"), new int[] {1,1},new SaboteurTile("2"), new int[] {2,1}));
//    	System.out.println(MyTools.connected(new SaboteurTile("0"), new int[] {1,1},new SaboteurTile("3"), new int[] {2,1}));
//    	System.out.println(MyTools.connected(new SaboteurTile("0"), new int[] {1,1},new SaboteurTile("4"), new int[] {2,1}));
//    	System.out.println(MyTools.connected(new SaboteurTile("0"), new int[] {1,1},new SaboteurTile("8"), new int[] {2,1}));
//    	System.out.println(MyTools.connected(new SaboteurTile("0"), new int[] {1,1},new SaboteurTile("12"), new int[] {2,1}));
//    	System.out.println(MyTools.connected(new SaboteurTile("0"), new int[] {1,1},new SaboteurTile("7"), new int[] {2,1}));
    	ArrayList<SaboteurCard> myHand = boardState.getCurrentPlayerCards();
    	SaboteurTile[][] hiddenBoard = boardState.getHiddenBoard();
    	ArrayList<SaboteurMove> posMoves = boardState.getAllLegalMoves();
    	Map<String, Integer> numCards = MyTools.numInHand(boardState, myHand);
    	
    	int numMalus;
    	int oppNumMalus;
    	if (boardState.getTurnPlayer()==1) {
    		numMalus = boardState.getNbMalus(1);
    		oppNumMalus = boardState.getNbMalus(2);
    	}
    	else {
    		numMalus =  boardState.getNbMalus(2);
    		oppNumMalus = boardState.getNbMalus(1);
    	}
    	
    	/////////////////////////////////////
   
    	/////////////////////////////////////
    	
    	//////////////////////////////////
//    	int[] gold = {-1, -1, -1};
//        Boolean goldKnown = false;
//        Boolean malus = false;
//        Boolean opponentMalus = false;
    	///////////////////////////////////

        
    	SaboteurMove testMove = MyTools.twoAway(boardState, myHand);
        if (testMove!=null) {
        	return testMove;
        }
        
        // If we are malused
        testMove = MyTools.areMalused(boardState, myHand, numMalus, numCards);
        if (testMove!=null) {
        	return testMove;
        }
        // if we can play to win
        testMove = MyTools.canWin(boardState, myHand);
        if (testMove!=null) {
        	return testMove;
        }
        
        // if we have a map and not all hidden tiles are revealed
        testMove = MyTools.haveMap(boardState, myHand, numCards);
        if (testMove!=null) {
        	return testMove;
        }
        if (boardState.getTurnNumber()<=5) {
	        testMove = MyTools.dropGarbage(myHand);
	        if(testMove != null) return testMove;
        }

        testMove = MyTools.earlyMove(boardState, myHand);

        // if we are far enough away from the gold

        // if we are close to the gold
        //testMove = MyTools.closestToGold(boardState, myHand);
        return testMove;
//        // if we are far enough away from the gold
//        testMove = MyTools.earlyMove(boardState, myHand);
//        if (boardState.getTurnNumber()<=20) {
//        	return testMove;
//        }
//        // if we are close to the gold
//        testMove = MyTools.closestToGold(boardState, myHand);
//        return testMove;

        // Return your move to be processed by the server
    	
        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...

        //General strat - hoard cards then slowly build towards destination, once 2 tiles away from winning start spamming malus,
        //if they build to make it 1 away then we win. Our only loss is that they begin turn with gold 2 tiles away and we are malus'ed
        //We need some solid logic for tile plays to optimize our paths
    	
    	// Use maps early
    	
    	// Try to keep in hand at least one through path in each direction (left->right and top->bottom)
    	// Try to keep one malus in hand -> use this if they get within 2 tiles
    	// *******************************

        //Big assumption is that there is a max number of cards we can hold if there is not then just mf grab a whole bunch as long as we have more then them 
        
        
        //If we are Malus 
            //blow up mine if they have less than 1 build tile and have a winning play -- one specific usecase that might save us late game
            //Otherwise use bonus -- loss condition is their turn 2 away to gold and we are malus'ed
        //Else if we have map and dont know where gold is and all targets >3 away (gives us breathing room)
            //Play map
        //Else if opponent is malus + gold is 2 card plays away - win condition
            //Play directly towards gold
        //Else if gold is 1 away 
            //Win if possible, draw card build tile if opponent is malus and we cant win, else blow up 
            //else loss condition (draw card hope for good build and opponent misplay)
        //Else if we have full hand
            //If holding !=0 maps discard one
            //play tile towards gold -- or blow up if we can create a path all the way but a tile messes it up
        //Else 
            //Draw either tile or action card -- depending on scenario
    	
    }
}