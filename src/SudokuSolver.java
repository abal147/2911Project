
public interface SudokuSolver {
	/**
	 * Solves the given sudoku board and returns the solution.
	 * @param game	The sudoku board to be solved.
	 * @return		A valid solution if it exists, null otherwise.
	 */
	public Board solve (Board game);
	/**
	 * Determines if a sudoku board has a unique solution.
	 * @param game	The sudoku game to be solved.
	 * @return		Returns a solution to the board if only one exists, null
	 * otherwise.
	 */
	public Board uniqueSolve (Board game);
	/**
	 * A method which solves the given board using only immediate constraints.
	 * If the board requires any guessing or logic outside the basic rules of
	 * sudoku, the solver will fail.
	 * @param game	The sudoku game to be solved.
	 * @return		The solution to the board if it exists within these bounds,
	 * null otherwise.
	 */
	public Board simpleSolve (Board game);
}
