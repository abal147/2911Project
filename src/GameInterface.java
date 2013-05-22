import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GameInterface {
/*
	private String difficulty;
	private String sBoard;
	private Board currentGame;
	
	public GameInterface (Board board) {
		
		this.sBoard = board.toString();
		
		JPanel difficultyPanel = new JPanel();
		difficultyPanel.setLayout(new BorderLayout());
		difficultyPanel.add(new JLabel("Difficulty: "+difficulty),
					BorderLayout.NORTH);
		
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(9, 9));
		for (int i = 0; i < sBoard.length(); i++) {
			final String value = sBoard.substring(i, i + 1);
			if (value.equals(".")) {
				JLabel blankButton = new JLabel(" ");
				boardPanel.add(blankButton);
//        	 blankButton.addActionListener(new ActionListener() {
//        	               public void actionPerformed(ActionEvent event) {
//        	                  blankButton.v
//        	               }
//        	            });
        	 // needs to be blank with some sort of action
        	 // to add the number
			} else {
				JLabel keyButton = new JLabel(value);
				boardPanel.add(keyButton);
			}
		}
		
		JPanel keyPanel = new JPanel();
		keyPanel.setLayout(new GridLayout(4, 3));
		
//		JButton keyArray[] = new JButton[9];
		String keyLabels = "123456789";
		for (int i = 0; i < keyLabels.length(); i++) {
			final String value = keyLabels.substring(i, i + 1);
			JButton numButton = new JButton(value);
//			keyArray[i] = numButton;
			keyPanel.add(numButton);
		}
		JButton hintButton = new JButton("Hint");
		JButton resetButton = new JButton("Reset");
		JButton solveButton = new JButton("Solve");
		keyPanel.add(hintButton);
		keyPanel.add(resetButton);
		keyPanel.add(solveButton);
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.add(difficultyPanel, BorderLayout.NORTH);
//		frame.add(keyPanel, BorderLayout.EAST);
//		frame.add(boardPanel, BorderLayout.CENTER);

//		JButton[][] arr = new JButton[9][9];
      
		Container pane = frame.getContentPane();
		GridBagConstraints c = new GridBagConstraints();
		pane.setLayout(new GridBagLayout());

  		c.gridx = 0;
  		c.gridy = 0;
  		pane.add(difficultyPanel, c);
//  		c.ipadx = 9;
//  		c.ipady = 9;
  		c.gridx = 1;
  		c.gridy = 9;
  		pane.add(keyPanel, c);
  		c.fill = GridBagConstraints.BOTH;
  		c.weightx = 1;
  		c.weighty = 1;
  		c.gridx = 0;
  		c.gridy = 9;
  		pane.add(boardPanel,c);

		frame.pack();
		frame.setSize(400, 400);
		frame.setVisible(true);
		
	}
	
	public void setDifficulty (String difficulty) {
		this.difficulty = difficulty;
	}
	
	public void setBoard (Board board) {
		this.sBoard = board.toString();
		currentGame = board;
	}
	*/
	private String sBoard;
	private JFormattedTextField[][] sudokuBoard;
	
	public GameInterface (Board board) {
		
		this.sBoard = board.toString();
		sudokuBoard = new JFormattedTextField[9][9];
		
		JPanel sudokuPanel = make3x3Grid();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					JPanel smallPanel = make3x3Grid();
					makeSudokuBoard (smallPanel, i, j, stringTo2DArray (sBoard));
					sudokuPanel.add(smallPanel);
				}
			}
		sudokuPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
//		insertNumbers(stringTo2DArray (sBoard));
		
		JFrame frame = new JFrame();
		frame.setTitle("Difficulty: <insert difficulty>");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(sudokuPanel, BorderLayout.CENTER);
		frame.add(makeSideButtons(), BorderLayout.EAST);
		
		frame.pack();
		frame.setSize(600, 600);
		frame.setVisible(true);
	}
	
	
	/**
	 * Converts a given String to a 2D Array
	 * @param string	the string that is to be converted
	 * @return			String[][]
	 */
	private String[][] stringTo2DArray (String string) {
		String[][] result = new String[9][9];
		for (int i = 0; i < 81; i++) {
			if (i % 9 == 0) {
				for (int j = 0; j < 9; j++) {
					result[i/9][j] = string.substring(i + j, (i + j) + 1);
				}
			}
		}
//		for (int p = 0; p < 81; p++) {
//			System.out.print(" " +string.charAt(p));
//		}
//		for (int k = 0; k < 9; k++) {
//			for (int l = 0; l < 9; l++) {
//				System.out.print(" " +result[k][l]);
//			}
//			System.out.println("");
//		}
		return result;
	}
	
//	private void insertNumbers (String[][] numbers) {
//		for (int i = 0; i < 9; i++) {
//			for (int j = 0; j < 9; j++) {
//				final String value = numbers[i][j];
//				if (!value.equals(".")) {
//					System.out.println(value);
//					JFormattedTextField nonEditableField = new JFormattedTextField();
//					nonEditableField.setEditable(false);
//					nonEditableField.setBackground(Color.WHITE);
//					nonEditableField.setText(value);
//					sudokuBoard[i][j] = nonEditableField;
//				}
//			}
//		}
//	}
	
	/**
	 * Creates the Sudoku Board
	 * @param panel			The 3x3 grid
	 * @param startRow		The row in which the 3x3 grid is in
	 * @param startColumn	The column in which the 3x3 grid is in
	 * @param numbers		A 2D Array that holds all the numbers on the sudoku board
	 */
	private void makeSudokuBoard (JPanel panel, int startRow, int startColumn, String[][] numbers) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int row = startRow * 3 + i;
				int column = startColumn * 3 + j;
				final String value = numbers[row][column];
				JFormattedTextField Field = new JFormattedTextField();
				Font font = new Font("Arial", Font.PLAIN, 30);
				Field.setFont(font);
				Field.setHorizontalAlignment(JLabel.CENTER);
				Field.setBorder(BorderFactory.createLineBorder(Color.black));
				if (!value.equals(".")) {
					Field.setEditable(false);
					Field.setBackground(Color.WHITE);
					Field.setText(value);
				}
				sudokuBoard[i][j] = Field;
				panel.add(Field);
			}
		}
	}
	
	/**
	 * Creates a small 3x3 grid
	 * @return	JPanel
	 */
    private JPanel make3x3Grid () {
        final GridLayout gridLayout = new GridLayout(3, 3, 1, 1);
        JPanel panel = new JPanel(gridLayout);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        return panel;
    }
    
    /**
     * Make the side buttons
     * @return	JPanel
     */
    private JPanel makeSideButtons () {
    	JPanel sideButtons = new JPanel();
		sideButtons.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		sideButtons.setPreferredSize(new Dimension(100, 400));
		JButton hintButton = new JButton("Hint");
		hintButton.setHorizontalAlignment(JLabel.CENTER);
		hintButton.setPreferredSize(new Dimension(70, 20));
		JButton resetButton = new JButton("Reset");
		resetButton.setHorizontalAlignment(JLabel.CENTER);
		resetButton.setPreferredSize(new Dimension(70, 20));
		JButton solveButton = new JButton("Solve");
		solveButton.setHorizontalAlignment(JLabel.CENTER);
		solveButton.setPreferredSize(new Dimension(70, 20));
		JLabel timerButton = new JLabel("Timer");
		timerButton.setHorizontalAlignment(JLabel.CENTER);
		timerButton.setPreferredSize(new Dimension(70, 20));
		JLabel blankLabel = new JLabel(" ");
	    c.weighty = 1;
	    c.gridx = 2;
		c.gridy = 2;
		sideButtons.add(blankLabel, c);
	    c.weighty = 0.25;
		c.gridx = 2;
		c.gridy = 6;
		sideButtons.add(hintButton, c);
		c.gridx = 2;
		c.gridy = 10;
		sideButtons.add(resetButton, c);
		c.gridx = 2;
		c.gridy = 14;
		sideButtons.add(solveButton, c);
		c.gridx = 2;
		c.gridy = 18;
		sideButtons.add(timerButton, c);
		c.weighty = 1;
		c.gridx = 2;
		c.gridy = 22;
		sideButtons.add(blankLabel, c);
		return sideButtons;
    }
}