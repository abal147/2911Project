//import java.util.ArrayList;
//
//
//public class ABSolve {
//
//	
//	/**
//	 * Determines if a given sudoku board hasa unique solution.
//	 * @param game	The sudoku game to be checked for uniqueness.
//	 * @return		Returns the solved board if it has a unique solution,
//	 * 				null if no unique solution.
//	 */
//	public Board uniqueSolve (Board game) {
//		ArrayList<Board> results = new ArrayList<Board>();
//		uniqueRecursiveSolve(game.clone(), results);
////		System.out.println("Solutions:");
////		for (Board board : results) {
////			board.printToOut();
////		}
////		System.out.println(results.size() + " solutions found");
//		if (results.size() == 1) {
//			return results.get(0);
//		}
//		return null;
//	}
//	
//	/**
//	 * Determines if a board has more one or more solutions.
//	 * The result is stored within the given ArrayList.
//	 * @param game		The sudoku board to be solved.
//	 * @param results	The list to store the result. If a result was found,
//	 * 					It will be in the first index of the ArrayList.
//	 */
//	private void uniqueRecursiveSolve (Board game, ArrayList<Board> results) {
//		if (results.size() > 1) {
//			return;
//		}
//		while (assignSingleCells(game));
//		if (isComplete(game)) {
//			results.add(game);
//			return;
//		}
//		if (game.hasNoSolution()) {
//			return;
//		}
//		int constrainedCell = mostConstrainedCell(game);
//		int row = constrainedCell / 10;
//		int col = constrainedCell % 10;
//			
//		String options = game.getOptions(row, col);
//		//options = options.replace("0", "");
//		for (int i = 0; i < options.length(); i++) {
//			Board newGame = game.clone();
//			if (newGame.assign(row, col, options.charAt(i) - '0')) {
//				if (!newGame.hasNoSolution()) {
//					uniqueRecursiveSolve(newGame, results);
//				}				
//			}
//		}
//		return;
//	}
//	
//	public Board simpleSolve (Board gameToSolve) {
//		Board game = gameToSolve.clone();
//		while(assignSingleCells(game));
//		if (isComplete(game)) {
//			return game;
//		} else {
//			return null;
//		}
//	}
//	
//	public boolean isComplete (Board board) {
////		if (board == null) {
////			return false;
////		}
////		String game = board.toString();
////		if (game.contains(".")) {
////			return false;
////		}
//		for (int row = 0; row < 9; row++) {
//			for (int col = 0; col < 9; col++) {
//				if (!board.getSet(row, col)) {
//					return false;
//				}
//			}
//		}
//		return true;
//	}
//	
//	private boolean assignSingleCells (Board game) {
//		//If there is a cell with only one available option,
//		//that option is assigned.
//		boolean numChanged = false;
//		for (int i=0; i<9; i++){
//			for (int j=0; j<9; j++){
//				if (game.getOptions(i, j).length() == 1){
//					if (game.getSet(i, j) == false) {
//						game.assign(i, j, Character.getNumericValue(game.getOptions(i, j).charAt(0)));
//						numChanged = true;						
//					}
//				}
//			}
//		}
//		return numChanged;
//	}
//	
//	/**
//	 * Finds the most constrained cell in the sudoku board.
//	 * @param game	The sudoku board to be searched.
//	 * @return		Returns 10 * row + column of the cell with the most
//	 * 				constraints.
//	 */
//	private int mostConstrainedCell (Board game) {
//		int row = -1;
//		int col = -1;
//		//Finds the first cell that is not set.
//		for (int i = 0; i < 9 && row == -1 && col == -1; i++) {
//			for (int j = 0; j < 9 && row == -1 && col == -1; j++) {
//				if (!game.getSet(i, j)) {
//					row = i;
//					col = j;
//				}
//			}
//		}
//		int constraints = game.getOptions(row, col).length();
////		if (constraints == 1) {
////			constraints = 11;
////		}
//		//Starting from the first cell that isn't set, look for the most
//		//constrained cell.
//		for (int i = row; i < 9; i++) {
//			for (int j = col; j < 9; j++) {
//				int tempConstraints = game.getOptions(i, j).length();
//				if (tempConstraints != 1) {
//					if (tempConstraints < constraints) {
//						row = i;
//						col = j;
//						constraints = tempConstraints;
//					}					
//				}
//				if (constraints <= 3) {
//					return row * 10 + col;
//				}
//			}
//		}
//		return row * 10 + col;
//	}
//}
//
