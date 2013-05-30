/**
 * This class generates sudoku boards of three different difficulties.
 * This generator requires a sudoku solver with a variety of functions
 * to generate boards.
 * @author Aaron Balsara, Nicholas Figueira, David Loyzaga
 *
 */
public class BoardGenerator {
	public static final int EASY = 0;
	public static final int MEDIUM = 1;
	public static final int HARD = 2;
	
	/**
	 * Creates a new sudoku puzzle for the player to solve.
	 * @return	The board representing the new game to be played.
	 */
	public Board newGame (int difficulty) {
		Board game;
		Board result;
		if (difficulty == EASY || difficulty == MEDIUM) {
			return easyGame(difficulty);
		} else {
			return uniqueGame();
		}
	}
	
	/**
	 * Generates a board of easy or medium difficulty.
	 * This board can be solved by simply finding a cell with only one option.
	 * This can be solved without guessing using only the basic sudoku logic.
	 * 
	 * An easy game will remove roughly half the cells from the board, while
	 * a medium game will remove as many as possible.
	 * @return	The unsolved board to be completed by a player or solver.
	 */
	public Board easyGame (int difficulty) {
		Board result = generateSolvedGame();
		int limit = 50 + 100 * difficulty;
		for (int i = 0; i < limit; i++) {
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
	
	/**
	 * Generates a unique sudoku with roughly 25-27 cells filled in. 
	 * This game has only one solution and is considered to be of the
	 * hardest difficulty.
	 * @return
	 */
	public Board uniqueGame () {
		Board result = generateSolvedGame();
		
		for (int i = 0; i < 25; i++) {
			int row = (int) (Math.random() * 9);
			int col = (int) (Math.random() * 9);
			int temp = result.cellValue(row, col);
			result.clearCell(row, col);
			// TODO Change when solver complete
			if (new ABSolve().uniqueSolve(result) == null) {
				result.assign(row, col, temp);
			}
		}
		//Trys to remove cells by diagonally going across the board.
		for (int i = 0; i < 45; i++) {
			int row = ((i / 9) + (i % 9)) % 9;
			int col = (i * 2) % 9;
			int temp = result.cellValue(row, col);
			if (temp != 0) {
				result.clearCell(row, col);
				// TODO Change when solver complete
				if (new ABSolve().uniqueSolve(result) == null) {
					result.assign(row, col, temp);
				}	
			}
		}
		return result;
	}
	
	/**
	 * Generates a solved sudoku board.
	 * @return	A solved sudoku board.
	 */
	private Board generateSolvedGame() {
		Board game = new Board ("");
		do {
			game = new Board("");
			for (int i = 1; i <=9; i++) {
				while (!game.assign((int) (Math.random() * 9), (int) (Math.random() * 9), i));
			}
			for (int i = 1; i < 10; i++) {
				while (!game.assign((int) (Math.random() * 9), (int) (Math.random() * 9), (int) (Math.random() * 9 + 1)));
			}
			// TODO changed to actual solver
			game = new ABSolve().solve(game);
		} while (game == null);
		return game;
	}
}
