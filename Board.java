
public class Board {
	/**
	 * The default value of a cell if is empty.
	 * This value represents all the possible numbers that 
	 * can be put into this cell.
	 * 
	 * The 0 is put in as a flag to distinguish between when the value has been 
	 * assigned by the user and when there is only one option remaining.
	 * For example, if a cell can only have the number 1, its string value
	 * will be "01" until assign is called and its value will be "1".
	 */
	private final String DEFAULT = "0123456789";
	/**
	 * The array of strings representing the board.
	 */
	private String[][] board;
	
	/**
	 * Creates a sudoku board from a given string.
	 * 
	 * The string should be in the form of 81 characters, with numbers
	 * representing numbers in the board and a '.' representing whitespace.
	 * @param layout	The string to create the board from.
	 */
	public Board (String layout) {
		board = new String[9][9];
		
		if (layout != "") {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (layout.charAt(9 * i + j) == '.') {
						board[i][j] = DEFAULT;
					} else {
						board[i][j] = String.valueOf(layout.charAt(9 * i + j));
					}
				}
			}	
		} else {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					board[i][j] = DEFAULT;
				}
			}	
		}
	}
	
	/**
	 * Returns a string which shows the possible moves for this cell.
	 * 
	 * This cell will need to be edited by a solver which utilises
	 * removeOption() to return a correct value, otherwise all possible
	 * options will be returned regardless of legality.
	 * @param row	The row of the cell.
	 * @param col	The column of the cell.
	 * @return		The cell's possible values.
	 */
	public String getOptions (int row, int col) {
		return board[row][col];		
	}
	
	/**
	 * Assigns a cell a particular value.
	 * @param row	The row of the cell to be changed (0-8).
	 * @param col	The column of the cell to be changed (0-8).
	 * @param num	The new value of that cell.
	 * @return		Returns true if the assignment was made.
	 */
	public boolean assign (int row, int col, int num) {
		if (num >= 1 && num <= 9) {
			board[row][col] = String.valueOf(num);
			return true;
		}
		return false;
	}
	
	/**
	 * Removes an option for a number 
	 * @param row	The row of cell to be edited (0-8).
	 * @param col	The column of the cell to be edited (0-8).
	 * @param num	The number which should be removed from that cell.
	 */
	public void removeOption (int row, int col, int num) {
		if (num >= 1 && num <= 9) {
			String remove = String.valueOf(num);
			board[row][col] = board[row][col].replace(remove, "");
		}
	}
	
	/**
	 * Returns the string value of this board.
	 * 
	 * If a new board is used using this value the cells will initially
	 * show all options, regardless of legality.
	 * @return the string value of this board.
	 */
	public String toString () {
		String value = new String();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j].length() == 1) {
					value = value.concat(board[i][j]);
				} else {
					value = value.concat(".");
				}
			}
		}
		
		return value;
	}
	
	/**
	 * Creates another copy of this board.
	 * This new copy will not have any cells constrained.
	 * @return	A copy of this board.
	 */
	public Board clone () {
		return new Board(toString());
	}
	
	/**
	 * Prints the board to the standard System.out.
	 */
	public void printToOut () {
		System.out.println("_____________________________________");
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j].length() == 1) {
					System.out.print("| " + board[i][j] + " ");
				} else {
					System.out.print("|   ");
				}
			}
			System.out.println("|");
			System.out.println("_____________________________________");
		}	
	}
}
