import java.util.ArrayList;

/**
 * A sudoku solver class which can solve using two methods. One will 
 * exhaustively look for a solution, while one will only look at immediate
 * constraints. Another method is available to determine if a given sudoku
 * board has a unique solution.
 * @author Aaron Balsara, Nicholas Figueira, David Loyzaga
 *
 */
public class Solver {
	/**
	 * Counts the number of recursive calls used by the solver.
	 * Used as an escape condition for long solves.
	 */
	private int count;
	/**
	 * Solves the given sudoku board.
	 * @param game	The sudoku game to be solved.
	 * @return		Returns a solved board if a solution was found.
	 * 				Returns null if the board has no solution.
	 */
	public Board solve (Board game) {
		count = 0;
		return recursiveSolve(game.clone());
	}
	
	/**
	 * The recursive section of the solver.
	 * This method assigns values to the most constrained cells and searches
	 * for a solution using a backtracking depth first search.
	 * @param game	The sudoku game to be solved.
	 * @return		Returns the solved board if it exists, null if there is
	 * 				no solution.
	 */
	private Board recursiveSolve (Board game) {
		Board result = null;
		if (count >= 1000) {
			return null;
		}
		
		while(assignSingleCells(game));
		if (isComplete(game)) {
			return game;
		}
		
		if (game.hasNoSolution()) {
			return null;
		}
		
		int constrainedCell = mostConstrainedCell(game);
		int row = constrainedCell / 10;
		int col = constrainedCell % 10;
			
		String options = game.getOptions(row, col);
		for (int i = 0; i < options.length(); i++) {
			count ++;
			Board newGame = game.clone();
			newGame.assign(row, col, options.charAt(i) - '0');

			if (!newGame.hasNoSolution()) {
				result = recursiveSolve(newGame);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}

	/**
	 * Determines if a given sudoku board hasa unique solution.
	 * @param game	The sudoku game to be checked for uniqueness.
	 * @return		Returns the solved board if it has a unique solution,
	 * 				null if no unique solution.
	 */
	public Board uniqueSolve (Board game) {
		ArrayList<Board> results = new ArrayList<Board>();
		uniqueRecursiveSolve(game.clone(), results);
		if (results.size() == 1) {
			return results.get(0);
		}
		return null;
	}
	
	/**
	 * Determines if a board has more one or more solutions.
	 * The result is stored within the given ArrayList.
	 * @param game		The sudoku board to be solved.
	 * @param results	The list to store the result. If a result was found,
	 * 					It will be in the first index of the ArrayList.
	 */
	private void uniqueRecursiveSolve (Board game, ArrayList<Board> results) {
		if (results.size() > 1) {
			return;
		}
		while (assignSingleCells(game));
		if (isComplete(game)) {
			results.add(game);
			return;
		}
		if (game.hasNoSolution()) {
			return;
		}
		int constrainedCell = mostConstrainedCell(game);
		int row = constrainedCell / 10;
		int col = constrainedCell % 10;
			
		String options = game.getOptions(row, col);
		for (int i = 0; i < options.length(); i++) {
			Board newGame = game.clone();
			if (newGame.assign(row, col, options.charAt(i) - '0')) {
				if (!newGame.hasNoSolution()) {
					uniqueRecursiveSolve(newGame, results);
				}				
			}
		}
		return;
	}
	
	/**
	 * A sudoku solver which only uses the immediate constraints of a cell
	 * to assign values to the board.
	 * @param gameToSolve	The sudoku game to be solved.
	 * @return		The solved board if there is a solution, null otherwise.
	 */
	public Board simpleSolve (Board gameToSolve) {
		Board game = gameToSolve.clone();
		while(assignSingleCells(game));
		if (isComplete(game)) {
			return game;
		} else {
			return null;
		}
	}
	
	/**
	 * Determines if a Board is a complete sudoku board or not.
	 * @param board	The board which needs to be checked.
	 * @return		True if the board is solved, false otherwise.
	 */
	public boolean isComplete (Board board) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (!board.getSet(row, col)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * A method which will attempt to assign cells of a sudoku board based on
	 * immediate constraints. This class will edit the contents of the given
	 * board.
	 * @param game	The game to be solved.
	 * @return	True if any changes were made, false otherwise.
	 */
	private boolean assignSingleCells (Board game) {
		boolean numChanged = false;
		for (int i=0; i<9; i++){
			for (int j=0; j<9; j++){
				if (game.getOptions(i, j).length() == 1){
					if (game.getSet(i, j) == false) {
						game.assign(i, j, Character.getNumericValue(game.getOptions(i, j).charAt(0)));
						numChanged = true;						
					}
				}
			}
		}
		return numChanged;
	}
	
	/**
	 * Finds the most constrained cell in the sudoku board.
	 * @param game	The sudoku board to be searched.
	 * @return		Returns 10 * row + column of the cell with the most
	 * 				constraints.
	 */
	private int mostConstrainedCell (Board game) {
		int row = -1;
		int col = -1;
		for (int i = 0; i < 9 && row == -1 && col == -1; i++) {
			for (int j = 0; j < 9 && row == -1 && col == -1; j++) {
				if (!game.getSet(i, j)) {
					row = i;
					col = j;
				}
			}
		}
		int constraints = game.getOptions(row, col).length();
		for (int i = row; i < 9; i++) {
			for (int j = col; j < 9; j++) {
				int tempConstraints = game.getOptions(i, j).length();
				if (tempConstraints != 1) {
					if (tempConstraints < constraints) {
						row = i;
						col = j;
						constraints = tempConstraints;
					}					
				}
				if (constraints <= 3) {
					return row * 10 + col;
				}
			}
		}
		return row * 10 + col;
	}
}