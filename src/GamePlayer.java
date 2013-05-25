import java.awt.event.InputMethodEvent;

/**
 * This class manages the sudoku game system.
 * It uses a generator to make new games, it represents the board using a GUI
 * and uses a solver to provide hints to the user.
 * @author Aaron Balsara, Nicholas Figueira, David Loyzaga
 *
 */
public class GamePlayer {
	/**
	 * The board that stores a new game. When reset is called this board
	 * will replace the current game regardless of it's state of completion.
	 */
	private Board newGame;
	/**
	 * The current game. This is shared with the UI and the UI will use this
	 * board to print to screen.
	 */
	private Board currentGame;
	/**
	 * The game interface class which will graphically represent the sudoku
	 * game.
	 */
	private GameInterface UI;
	/**
	 * The board generator that will be used to generate new games.
	 */
	private BoardGenerator generator;
	/**
	 * The solver which will solve games so hints can be given.
	 */
	private Solver sudokuSolver;
	/**
	 * The board that will store the solution to the current sudoku game.
	 */
	private Board solution;

	private int hints;
	private int maxHints;
	
	private final int EASYGAME = -1;
	private final int HARDGAME = 5;
	
	/**
	 * The constructor for the GamePlayer class. It initialises all 
	 * necessary fields and begins a new game in easy mode.
	 */
	public GamePlayer () {
		generator = new BoardGenerator();
		newGame(BoardGenerator.EASY);
		UI = new GameInterface(this, currentGame);
		sudokuSolver = new Solver();
		hints = 0;
		maxHints = EASYGAME;
		
	}
	
	/**
	 * Tries to assign a cell in the sudoku board.
	 * @param row	The row of the cell to be assigned.
	 * @param col	The column of the cell to be assigned.
	 * @param num	The number to be assigned to the cell.
	 */
	public boolean assign (int row, int col, int num) {
		if (!currentGame.assign(row, col, num)) {
			return false;
		} else {
			return true;
		}
		
	}
	
	/**
	 * Clears the value in the given cell.
	 * @param row	The row of the cell.
	 * @param col	The column of the cell.
	 */
	public void clearCell (int row, int col) {
		currentGame.clearCell(row, col);
	}
	
	/**
	 * Starts a new game of sudoku.
	 */
	public void newGame (int difficulty) {
		newGame = generator.newGame(difficulty);
		resetGame();
	}
	
	/**
	 * This method is called by the UI. This method will solve the current game
	 * and allow the UI to display the solution to the current puzzle.
	 */
	public void solveBoard () {
		currentGame = new ABSolve().solve(currentGame);
		solution = currentGame.clone();
		UI.setBoard(solution);
	}
	
	/**
	 * Reverts the current game of sudoku to it's original state.
	 */
	public void resetGame () {
		currentGame = newGame.clone();
		//UI.setBoard(currentGame);
		// TODO change when done
		//if (recalculateSolution()) {
			solution = new ABSolve().solve(currentGame);	
		//}
	}
	
	/**
	 * When the reset button is pressed this method will revert the 
	 * displayed board to it's original state.
	 */
	public void resetBoard () {
		currentGame = newGame.clone();

		solution = new ABSolve().solve(currentGame);
		UI.setBoard(currentGame);
	}

	/**
	 * Reveals a cell at random to aid the player.
	 */
	public void hint() {
		if (isComplete(currentGame)) {
			return;
		}
		if (hints >= maxHints && maxHints != EASYGAME) {
			return;
		}
		hints++;
		int row;
		int col;
		do {
			row = (int) (Math.random() * 9);
			col = (int) (Math.random() * 9);
		} while (currentGame.cellValue(row, col) != 0);
		// TODO CHANGE THIS WHEN SOLVER DONE
		//solution = sudokuSolver.solve(currentGame);
		solution = new ABSolve().solve(currentGame);
		int value = solution.cellValue(row, col);
		currentGame.assign(row, col, value);
		UI.setBoard(currentGame);
	}
	
	/**
	 * Compares the currentGame and the solution to determine if the 
	 * solution is still valid for this board. 
	 * For every value that has been assigned into the current game, 
	 * the solution is considered valid if it has the same value in the same
	 * cell. If the solution has a different value, the player is potentially 
	 * discovering  different solution that requires a new calculation.
	 * @return	True if the board needs to be recalculated.
	 */
//	public boolean recalculateSolution () {
//		if (solution == null) {
//			return true;
//		}
//		for (int row = 0; row < 9; row++) {
//			for (int col = 0; col < 9; col++) {
//				int cellValue = currentGame.cellValue(row, col);
//				if (cellValue != 0) {
//					if (solution.cellValue(row, col) != cellValue) {
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}

	/**
	 * Checks if the given sudoku board is complete.
	 * @param game	The board to be checked.
	 * @return		True if the board is a completed game of sudoku.
	 */
	public boolean isComplete(Board game) {
		if (new ABSolve().isComplete(game)) {
			return true;
		}
		return false;
	}
}
