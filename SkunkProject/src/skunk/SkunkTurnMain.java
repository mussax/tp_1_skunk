package skunk;
import edu.princeton.cs.introcs.StdOut;

public class SkunkTurnMain{
	private static int roundDiceTotal = 0;

// roundDiceTotal: getter, setter, reset 
	public static void setRoundDiceTotal(int addDiceToRound) {
		roundDiceTotal += addDiceToRound;
	}
	
	public static void resetRoundDiceTotal() {
		roundDiceTotal = 0;
	}
	
	public static int getRoundDiceTotal() {
		return roundDiceTotal;
	}
	
	public static void displayRoundDiceTotal() {
		StdOut.println("\tCurrent Dice Total: " + SkunkTurnMain.getRoundDiceTotal());
	}
	
//	public static void main(String[] args){
//		SkunkPlayer[] testPlayerArray = new SkunkPlayer[1];
//		testPlayerArray[0] = new SkunkPlayer("Test Player");
//		SkunkTurnAction.playerTurn(testPlayerArray[0], testPlayerArray);
//	}
}