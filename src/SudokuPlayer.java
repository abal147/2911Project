/**
 * Runs the interactive sudoku game.
 * @author Aaron Balsara, Nicholas Figueira, David Loyzaga
 *
 */
public class SudokuPlayer {

	/**
	 * The main to run the program.
	 * @param args	input parameters.
	 */
	public static void main(String[] args) {
		GamePlayer game = new GamePlayer(new Solver());
	}
}