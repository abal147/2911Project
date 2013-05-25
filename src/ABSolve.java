import java.util.ArrayList;


public class ABSolve {
	public long count;
	
	public Board solve (Board game) {
		count = 0;
		return recursiveSolve(game.clone());
	}
	
	public Board uniqueSolve (Board game) {
		Board test = game.clone();
		ArrayList<Board> results = new ArrayList<Board>();
		Board result = null;
		uniqueRecursiveSolve(test, results);
//		System.out.println("Solutions:");
//		for (Board board : results) {
//			board.printToOut();
//		}
//		System.out.println(results.size() + " solutions found");
		if (results.size() == 1) {
			return results.get(0);
		}
		return null;
	}
	
	public void uniqueRecursiveSolve (Board game, ArrayList<Board> results) {
		if (results.size() > 1) {
			return;
		}
		while(assignSingleCells(game));
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
		options = options.replace("0", "");
		for (int i = 0; i < options.length(); i++) {
			Board newGame = game.clone();
			newGame.assign(row, col, options.charAt(i) - '0');

			if (!newGame.hasNoSolution()) {
				uniqueRecursiveSolve(newGame, results);
			}
		}
		return;
	}
	
	public Board simpleSolve (Board gameToSolve) {
		Board game = gameToSolve.clone();
		while(assignSingleCells(game));
		if (isComplete(game)) {
			return game;
		} else {
			return null;
		}
	}
	
	private Board recursiveSolve (Board game) {
		Board result = null;
		if (count >= 1000) {
			return null;
		}
		while(assignSingleCells(game));
		if (isComplete(game)) {
			//System.out.println("Complete!");
			return game;
		}
		if (game.hasNoSolution()) {
			//System.out.println("No solution, returning");
			return null;
		}
		int constrainedCell = mostConstrainedCell(game);
		int row = constrainedCell / 10;
		int col = constrainedCell % 10;
			
		String options = game.getOptions(row, col);
		//System.out.println("Options for " + row + " " + col + " " + options.replace("0",  ""));
		options = options.replace("0", "");
		for (int i = 0; i < options.length(); i++) {
			
			count ++;
			
			Board newGame = game.clone();
			//System.out.println("assigning " + options.charAt(i));
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
	
	public boolean isComplete (Board board) {
		if (board == null) {
			return false;
		}
		String game = board.toString();
		if (game.contains(".")) {
			return false;
		}
		return true;
	}
	
	private boolean assignSingleCells (Board game) {
		//If there is a cell with only one available option,
		//that option is assigned.
		boolean numChanged = false;
		for (int i=0; i<9; i++){
			for (int j=0; j<9; j++){
				if (game.getOptions(i, j).length() == 2){
					game.assign(i, j, Character.getNumericValue(game.getOptions(i, j).charAt(1)));
					numChanged = true;
				}
			}
		}
		return numChanged;
	}
	
	private int mostConstrainedCell (Board game) {
		int row = 0;
		int col = 0;
		int constraints = game.getOptions(row, col).length();
		if (constraints == 1) {
			constraints = 11;
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
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

