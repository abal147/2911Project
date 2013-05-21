
public class GamePlayer {
	private Board newGame;
	private Board currentGame;
	private GameInterface UI;
	private BoardGenerator generator;
	private Solver sudokuSolver;
	private Board solution;
	/**
	 * @param args
	 */
	public GamePlayer () {
		generator = new BoardGenerator();
		UI = new GameInterface(null);
		sudokuSolver = new Solver();
		newGame();
	}
	
	/**
	 * Checks if the current sudoku game has a solution.
	 */
	public boolean isGameLegal() {
		if (sudokuSolver.solve(currentGame) != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Tries to assign a cell in the sudoku board.
	 * @param row	The row of the cell to be assigned.
	 * @param col	The column of the cell to be assigned.
	 * @param num	The number to be assigned to the cell.
	 */
	public void assign (int row, int col, int num) {
		if (!currentGame.assign(row, col, num)) {
			//assign number failed
			
		} else {
			
		}
		if (currentGame.hasNoSolution()) {
			//game has no solution
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
	public void newGame () {
		newGame = generator.newGame();
		resetGame();
	}
	
	/**
	 * Reverts the current game of sudoku to it's original state.
	 */
	public void resetGame () {
		currentGame = newGame.clone();
		UI.setBoard(currentGame);
		solution = sudokuSolver.solve(currentGame);
	}
}
