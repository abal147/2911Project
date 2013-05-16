
public class BoardTest {
	private final static String test = "123456789.1.2.3.4.5.6.7.8.99.8.7.6.5.4.3.2.1.123456789.........987654321.........";

	public static void main (String[] args) {
		Board newBoard = new Board (test);
		newBoard.printToOut();
		System.out.println(newBoard.getOptions (1, 0));
		System.out.println(newBoard.getOptions (1, 8));
		System.out.println(newBoard.isLegal(1,  0,  6));
		System.out.println(newBoard.isLegal(1,  0,  7));
		
		newBoard.assign(0, 0, 9);
		newBoard.printToOut();
		
		System.out.println(newBoard.getOptions(8, 8));
		newBoard.removeOption(8, 8, 1);
		System.out.println(newBoard.getOptions(8, 8));
		System.out.println(newBoard.toString());
		
		BoardGenerator newGame = new BoardGenerator();
		newBoard = newGame.newGame();
		newBoard.printToOut();
		GameInterface game = new GameInterface(newBoard);
	}
}
