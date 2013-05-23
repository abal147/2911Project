
public class BoardGenerator {
	public BoardGenerator () {
		
	}
	
	/**
	 * Creates a new sudoku puzzle for the player to solve.
	 * @return	The board representing the new game to be played.
	 */
	public Board newGame () {
		Board game;
		Board result;
		int boardCount = 0;
		do {
			game = new Board("");
			
			//Add all numbers 1 to 9 to minimise chances of unique solutions.
			//Only 8 of the numbers are needed but then one number would be constantly
			//ignored at the start.
			
			for (int i = 1; i <=9; i++) {
				while (!game.assign((int) (Math.random() * 9), (int) (Math.random() * 9), i));
			}
			
			//Keep adding numbers while ensuring there are either valid solutions
			//or we can just make sure the moves are valid and then solve after a 
			//few have been put in.
			for (int i = 1; i < 10; i++) {
				while (!game.assign((int) (Math.random() * 9), (int) (Math.random() * 9), (int) (Math.random() * 9 + 1)));
				//Again check there is a solution
			}
			
			//Call solver to ensure solution then return board.
			result = new ABSolve().solve(game);
			boardCount ++;
		} while (result == null);
		System.out.println("Boards checked: " + boardCount);
		return game;
	}
}
