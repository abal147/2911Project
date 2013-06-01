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
	
	private int difficulty;

	private int hints;
	private int maxHints;
	
	private final int EASYGAME = 81;
	private final int MEDIUMGAME = 5;
	private final int HARDGAME = 3;
	
	/**
	 * The constructor for the GamePlayer class. It initialises all 
	 * necessary fields and begins a new game in easy mode.
	 */
	public GamePlayer () {
		generator = new BoardGenerator();
		UI = new GameInterface(this);
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
			UI.updateStatus(num + " Assigned");
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
		this.difficulty = difficulty;
		resetGame();
		resetHints (difficulty);
	}
	
	public void resetHints (int difficulty) {
		hints = 0;
		if (difficulty == BoardGenerator.EASY) {
			maxHints = EASYGAME;
		} else if (difficulty == BoardGenerator.MEDIUM) {
			maxHints = MEDIUMGAME;
		} else {
			maxHints = HARDGAME;
		}
	}
	
	/**
	 * This method is called by the UI. This method will solve the current game
	 * and allow the UI to display the solution to the current puzzle.
	 */
	public void solveBoard () {
		if (solution == null) {
			solution = sudokuSolver.solve(currentGame);
		}
		currentGame = solution.clone();	
		if (solution == null) {
			UI.updateStatus("No solution");
		} else {
			UI.setBoard(solution);	
		}
	}
	
	/**
	 * Reverts the current game of sudoku to it's original state.
	 */
	public void resetGame () {
		currentGame = newGame.clone();
		resetHints(difficulty);
		solution = sudokuSolver.solve(currentGame);	
		UI.setBoard(currentGame);
	}

	/**
	 * Reveals a cell to aid the player. Does nothing if the cell is filled in,
	 * even if the cell is filled in incorrectly.
	 */
	public void hint(int row, int col) {
		if (isComplete(currentGame)) {
			return;
		}
		if (hints >= maxHints) {
			UI.updateStatus("No more hints");
			return;
		}
		int value = solution.cellValue(row, col);
		if (value == 0) {
			return;
		}
		hints++;
		currentGame.assign(row, col, value);
		UI.setBoard(currentGame);
		UI.updateStatus("Hint Given");
	}
	
	public int hintsLeft () {
		return maxHints - hints;
	}
	
	public void solverMode () {
		newGame = new Board("");
		currentGame = new Board("");
		resetGame();
		solution = null;
		UI.setBoard(currentGame);
	}

	/**
	 * Checks if the given sudoku board is complete.
	 * @param game	The board to be checked.
	 * @return		True if the board is a completed game of sudoku.
	 */
	public boolean isComplete(Board game) {
		return game.isComplete();
	}
}
