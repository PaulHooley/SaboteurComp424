package student_player;

import java.util.ArrayList;

import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;
import Saboteur.cardClasses.SaboteurCard;
import Saboteur.cardClasses.SaboteurTile;

public class MyTools {
    public static double getSomething() {
        return Math.random();
    }
    // closestToGold should return the tile card and position to play if to make the move closest towards the gold
    public static void closestToGold(ArrayList<SaboteurMove> posMoves, SaboteurBoardState boardState, ArrayList<SaboteurCard> myHand) {
    	for (SaboteurCard card : myHand) {
    		if (card instanceof SaboteurTile) {
    			System.out.println(boardState.possiblePositions((SaboteurTile)card));
    		}
    	}
    }
}