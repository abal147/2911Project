
public class GamePlayer {
	private Board newGame;
	private Board currentGame;
	private GameInterface UI;
	private BoardGenerator generator;
	private Solver sudokuSolver;
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
	public void isGameLegal() {
		Board result = sudokuSolver.solve(currentGame);
		if (result == null) {
			//warn no solution
			//UI.noSolution(); ???
		}
	}
	
	/**
	 * Tries to assign a cell in the sudoku board.
	 * @param row	The row of the cell to be assigned.
	 * @param col	The column of the cell to be assigned.
	 * @param num	The number to be assigned to the cell.
	 */
	public void assign (int row, int col, int num) {
		currentGame.assign(row, col, num);
		isGameLegal();
	}
	
	/**
	 * Clears the value in the given cell.
	 * @param row	The row of the cell.
	 * @param col	The column of the cell.
	 */
	public void clearCell (int row, int col) {
		currentGame.clearCell(row, col);
		isGameLegal();
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
	}
}
