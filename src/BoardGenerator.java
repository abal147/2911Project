
public class BoardGenerator {
	public BoardGenerator () {
		
	}
	
	/**
	 * Creates a new sudoku puzzle for the player to solve.
	 * @return	The board representing the new game to be played.
	 */
	public Board newGame (int difficulty) {
		Board game;
		Board result;
		int boardCount = 0;
		
		if (difficulty == 0) {
			return easyGame();
		}
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
			// TODO Change when solver complete 
			result = new ABSolve().solve(game);
			boardCount ++;
			if (boardCount % 100 == 0) {
				System.out.println(boardCount);
			}
		} while (result == null);
		System.out.println("Boards checked: " + boardCount);
		return game;
	}
	
	/**
	 * Generates a board of easy difficulty.
	 * This board can be solved by simply finding a cell with only one option.
	 * This can be solved without guessing using ony the basic sudoku logic.
	 * @return	The unsolved board to be completed by a player or solver.
	 */
	public Board easyGame () {
		Board result;
		do {
			result = new Board("");
			for (int i = 1; i <=9; i++) {
				while (!result.assign((int) (Math.random() * 9), (int) (Math.random() * 9), i));
			}
			for (int i = 1; i < 10; i++) {
				while (!result.assign((int) (Math.random() * 9), (int) (Math.random() * 9), (int) (Math.random() * 9 + 1)));
			}
			result = new ABSolve().solve(result);
		} while (result == null);
		
		for (int i = 0; i < 300; i++) {
			int row = (int) (Math.random() * 9);
			int col = (int) (Math.random() * 9);
			int temp = result.cellValue(row, col);
			result.clearCell(row, col);
			// TODO Change when solver complete
			if (new ABSolve().simpleSolve(result) == null) {
				result.assign(row, col, temp);
			}
		}
		
		return result;
	}
}
