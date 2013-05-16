
public class Solver {

	public Solver() {

	}

	/**
	 * @param args
	 */
	public void sup (Board game){
		String cellString = new String();
		for (int i=0; i<9; i++){
			for (int j=0; j<9; j++){
				cellString = game.getOptions(i, j);
				if (cellString.length() == 2){
					game.assign(i, j, cellString.charAt(1));
				}
			}
		}
	}
	
}
