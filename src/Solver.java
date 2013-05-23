
public class Solver {

	public Solver() {

	}

	/**
	 * @param args
	 */

	public void assignSingleCell (Board game){
		String cellString = new String();
		Board gameBoard = game.clone();
		for (int i=0; i<9; i++){
			for (int j=0; j<9; j++){
				cellString = game.getOptions(i, j);
				if (cellString.length() == 2){
					game.assign(i, j, cellString.charAt(1) - '0');
				}
			}
		}
		recursSolveBoard(gameBoard, 0, 0);
	}
	/*
	public void guessSingleCell(Board game){
		String cellString = new String();
		for (int i=0; i<9; i++){
			for (int j=0; j<9; j++){
				cellString = game.getOptions(i, j);
				for (int k=0; k < cellString.length(); k++){
					game.assign(i, j, cellString.charAt(k));
				}
			}
		}
	}

	public Board solveBoard (Board game){
		String cellString = new String();
		Board gameBoard = game.clone();
		for (int i=0; i <9; i++){
			for (int j=0; j<9; j++){
				cellString = game.getOptions(i,  j);
				if (cellString.length() == 2){
					gameBoard.assign(i, j, cellString.charAt(1));
				}
				else if (cellString.length() > 2){
					for (int k=3; k<cellString.length(); k++){
						game.assign(i, j, cellString.charAt(k));
					}
				}
			}
		}
	}
	*/
	public void recursSolveBoard (Board game, int row, int col){
		String cellString = new String();
		Board gameBoard = game.clone();
		
				cellString = gameBoard.getOptions(row, col);
		if (cellString.length() == 1){
			if (col == 8){
				recursSolveBoard (gameBoard, row+1, 0);
			} else {
				recursSolveBoard (gameBoard, row, col+1);
			}

		} else if (cellString.length() > 2) {
			for (int i=0; i<cellString.length(); i++){
				gameBoard.assign(row, col, cellString.charAt(i));
				if (col == 8){
					recursSolveBoard (gameBoard, row+1, 0);
				} else {
					recursSolveBoard (gameBoard, row, col+1);
				}
			}
		}
	}
}