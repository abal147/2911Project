import java.awt.event.InputMethodEvent;


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

	/**
	 * The constructor for the GamePlayer class. It initialises all 
	 * necessary fields and begins a new game in easy mode.
	 */
	public GamePlayer () {
		generator = new BoardGenerator();
		newGame(0);
		UI = new GameInterface(this, currentGame);
		sudokuSolver = new Solver();
		
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
		//solution = sudokuSolver.solve(currentGame);
		solution = new ABSolve().solve(currentGame);
		solution.printToOut();
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
