import java.util.Scanner;

import edu.princeton.cs.introcs.StdOut;

public class SkunkTurnAction {
	private static Scanner playersChoice = new Scanner(System.in);	

	// A Player's Turn: Select option, see result, continue to play until player ends turn or gets skunk.
	// TODO: break into smaller bits... ?? 
		public static void playerTurn(SkunkPlayer inputPlayer, SkunkPlayer[] playersArrayRound){
			StdOut.println(inputPlayer.getName() + "'s turn...");
			SkunkTurnDiceRollsArray.resetRoundRollResult();
			int chipsBefore = inputPlayer.getPlayerChipsTotal();
			
			while(true){
				StdOut.println();
				int enteredOption = numericOptionSelection();
				
				if (enteredOption == 1)
					StdOut.println("\tCurrent Dice Total: " + SkunkTurnMain.getRoundDiceTotal());
				else if (enteredOption == 2)
					SkunkPlayerManagement.displayDiceAll(playersArrayRound);
				else if (enteredOption == 3)
					SkunkPlayerManagement.displayChipsAll(playersArrayRound);
				else if (enteredOption == 4)
					StdOut.println("\tKitty: " + SkunkKitty.getKitty());
				else if (enteredOption == 5) {
					if (completeDieRollEvent(inputPlayer, playersArrayRound) == true)
						break;
				}
				else if (enteredOption == 6) {
					endTurn(inputPlayer);
					break;
				}
				else{
					StdOut.println("Invalid input.");
					StdOut.println("Please select a valid option...");
				}
			}
			StdOut.println();
			StdOut.println("End Turn confirmed...");
			
			endOfTurnEvaluation(inputPlayer, chipsBefore);
		}

	// Selection menu: takes numeric user input; returning 999 renders the result invalid.
	//TODO: Maybe break into smaller bits?
		private static int numericOptionSelection() {
			StdOut.println("Select option: \n"
					+ "\t1. View current round's dice total\n"
					+ "\t2. View all players' dice points\n"
					+ "\t3. View all players' chips\n"
					+ "\t4. View kitty\n"
					+ "\t5. Roll dice\n"
					+ "\t6. End round");
			StdOut.println();
			
			StdOut.print("Option Selected: ");
			int optionSelected = getPlayerChoiceInput();
			StdOut.println();
			return optionSelected;
		}

		private static int getPlayerChoiceInput() {
			int choice;
			String inputOption = playersChoice.nextLine();
			
			try {
				choice = Integer.parseInt(inputOption);
			}
			catch (NumberFormatException e){
				choice = 999;
			}
			return choice;
		}
		
	// Option 5: Roll dice, add results to roundRollResult,
		// evaluate consequence (add to running total, or skunk penalty), then check if break due to skunk.
		private static boolean completeDieRollEvent(SkunkPlayer parInputPlayer, SkunkPlayer[] parPlayersArrayRound){
			int[] diceResult = Dice.rollingDice();
			SkunkTurnDiceRollsArray.addToRoundRollResult(diceResult[0], diceResult[1], diceResult[2]);
			rollEvaluation(parInputPlayer, parPlayersArrayRound, diceResult[0], diceResult[1], diceResult[2]);
			if (SkunkTurnPenaltyEvents.skunkCheckToBreak(diceResult[0], diceResult[1]) == true)
				return true;
			else
				return false;
		}

	// Imposes result of dice: add to running total, or penalizes for skunk.
		private static void rollEvaluation(SkunkPlayer player, SkunkPlayer[] playerArrayInput, int dice1, int dice2, int diceTotal) {
			StdOut.println("\tPlayer: " + player.getName());
			StdOut.println("\tRolled: " + dice1 + " and " + dice2 + ", for a total of " + diceTotal);
			if (dice1 == 1 && dice2 == 1)
				SkunkTurnPenaltyEvents.doubleSkunk(player);
			else if (diceTotal == 3)
				SkunkTurnPenaltyEvents.singleSkunkDeuce(player);
			else if (dice1 == 1 || dice2 == 1)
				SkunkTurnPenaltyEvents.singleSkunk(player);
			else {
				SkunkTurnMain.setRoundDiceTotal(diceTotal);
				StdOut.println("\tPoints in Current Turn: " + SkunkTurnMain.getRoundDiceTotal());
				StdOut.println("");
				StdOut.println("\t" + player.getName() + " has " + player.getPlayerDiceTotal() + " points.");
				int personalTotalIfQuitNow = player.getPlayerDiceTotal() + SkunkTurnMain.getRoundDiceTotal();
				StdOut.println("\tIf " + player.getName() + " ends their turn now, they will have " + personalTotalIfQuitNow + " points.");
			}
		}

	// Option 6: Player ends turn, so they get their round's points.
		private static void endTurn(SkunkPlayer inputPlayer) {
			StdOut.println("\t" + inputPlayer.getName() + " has chosen to end their turn...");
			inputPlayer.setPlayerDiceTotal(SkunkTurnMain.getRoundDiceTotal()); 
		}

	// End of Player's Turn Evaluation.
		private static void endOfTurnEvaluation(SkunkPlayer ParInputPlayer, int chipsBeforeInput) {
			StdOut.println("\tPlayer: " + ParInputPlayer.getName());
			int i = 0;
			if (SkunkTurnDiceRollsArray.roundRollResult[0] != null) {
				for (SkunkTurnDiceRollsArray printResults : SkunkTurnDiceRollsArray.roundRollResult) {
					i++;
					StdOut.println("\tRoll " + i + ": [Dice 1: " + printResults.getDice1Result() 
					+ "], [Dice 2: " + printResults.getDice2Result() 
					+ "], [Dice Total: " + printResults.getDiceTotalResult() + "]");
				}
			}
			StdOut.println("\tPoints Earned: " + SkunkTurnMain.getRoundDiceTotal());
			int chipsLost = chipsBeforeInput - ParInputPlayer.getPlayerChipsTotal();
			StdOut.println("\tChips Lost: " + chipsLost);
		}
}