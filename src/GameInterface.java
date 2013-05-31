import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import sun.misc.Cleaner;

public class GameInterface implements FocusListener{

	private Board currentGame;
	private String sBoard;
	private JFormattedTextField[][] sudokuBoard;
	private GamePlayer gamePlayer;
	private final JLabel statusIndicator;
	
	private final String hintTip = "Gives a random number in the sudoku";
	private final String ResetTip = "Resets the sudoku to the orginal numbers";
	private final String SolveTip = "Solves the sudoku puzzle";
	
	public GameInterface (GamePlayer gamePlayer) {
		
		sudokuBoard = new JFormattedTextField[9][9];
		this.gamePlayer = gamePlayer;
		
		statusIndicator = new JLabel(" ");
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(new KeyEventDispatcher() {
		      /*
		      public boolean keyTyped (KeyEvent e) {
		    	  System.out.println("test " +e.getKeyCode());
		    	  if (e.getKeyCode() == KeyEvent.VK_F1) {
		    		  return true;
		    	  }
		    	  return false;
		      }*/
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
		    	  if (e.getID() == KeyEvent.KEY_TYPED && e.getKeyChar() == 'h') {
		    		  		helpAction();
		    	  }
		        return false;
		      }
		});
		
		selectDifficulty ();
	}
	
	/**
	 * Makes the Sudoku board using 9 3x3 grids within each 3x3 grid
	 * @param difficulty	The selected difficulty of the game
	 */
	private void makeSudokuBoard (String difficulty) {

		JFrame frame = new JFrame();
		if (difficulty == null) {
			frame.setTitle("Sudoku solver");
		} else {
			frame.setTitle("Difficulty: " + difficulty);	
		}
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel sudokuPanel = make3x3Grid();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JPanel smallPanel = make3x3Grid();
				if (difficulty != null) {
					makeSudokuCell (smallPanel, i, j, stringTo2DArray (sBoard));	
				} else {
					currentGame = new Board (Board.EMPTYBOARD);
					sBoard = currentGame.toString();
					makeSudokuCell (smallPanel, i, j, stringTo2DArray (sBoard));
				}
				sudokuPanel.add(smallPanel);
			}
		}
		updateBoard();
		sudokuPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
	//	insertNumbers(stringTo2DArray (sBoard));
		
		for (int i = 0; i < 81; i++) {
			int row = i % 9;
			int col = i / 9;
			JFormattedTextField field = sudokuBoard[row][col];
			field.addFocusListener(this);
		}

		frame.add(sudokuPanel, BorderLayout.CENTER);
		
		if (difficulty != null) {
			frame.add(makeSideButtons(frame), BorderLayout.EAST);	
		} else {
			
		}
		
		frame.pack();
		frame.setSize(765, 660);
		//frame.setSize(794, 693);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
    private void makeEmptyBoard () {
    	makeSudokuBoard(null);
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
		return result;
	}
	
	/**
	 * Creates a cell in the Sudoku Board
	 * @param panel			The 3x3 grid
	 * @param startRow		The row in which the 3x3 grid is in
	 * @param startColumn	The column in which the 3x3 grid is in
	 * @param numbers		A 2D Array that holds all the numbers on the sudoku board
	 */
	private void makeSudokuCell (JPanel panel, int startRow, int startColumn, String[][] numbers) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				final int row = startRow * 3 + i;
				final int column = startColumn * 3 + j;
				final String value = numbers[row][column];
				JFormattedTextField field = new JFormattedTextField();
///////////////////////////////////////////////////////////////////////////////////////				
				
				// Need to fix
				// Creates a space but only takes in numbers 1-9
//				try {
//					MaskFormatter formatter = new MaskFormatter("#");
//					//formatter.setValidCharacters("123456789\b");
//		            field.setFormatterFactory(new DefaultFormatterFactory(formatter));
//		        } catch (java.text.ParseException ex) {}
				
///////////////////////////////////////////////////////////////////////////////////////					
				Font font = new Font("Arial", Font.PLAIN, 30);
				field.setFont(font);
				field.setHorizontalAlignment(JLabel.CENTER);
				field.setBorder(BorderFactory.createLineBorder(Color.black));
				if (!value.equals(".")) {
					field.setEditable(false);
					field.setBackground(new Color (220, 220, 220));
//					field.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
					field.setFocusable(false);
					//Field.setText(value);
				}
				
				/*
				 * Attempted to use property change listener
				 * Does not work!!
				 *
				field.addPropertyChangeListener("value", new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent event) {
						
						if (event.getNewValue() != null) {	
							String enteredValue = (String) event.getNewValue();
							
							//int row = -1;
							int col = -1;
							for (int i = 0; i < 9; i++) {
								for (int j = 0; j < 9; j++) {
									if (sudokuBoard[i][j].equals(enteredValue)) {
										//row = i;
										col = j;
										System.out.println("REACHED " + i + " " + j);
									}
								}
							}
							System.out.println("NICK: " + row + " " + column);
							gamePlayer.assign(row, column, Integer.valueOf(enteredValue));
						}
					}
				});
				*/
				
				field.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent event) {
						JFormattedTextField me = (JFormattedTextField) event.getSource();
						String text = me.getText();
						
						System.out.println("ACTION LISTENER");
						editTextField(me);
//						int row = -1;
//						int col = -1;
//						for (int i = 0; i < 9; i++) {
//							for (int j = 0; j < 9; j++) {
//								if (sudokuBoard[i][j].equals(me)) {
//									row = i;
//									col = j;
//								}
//							}
//						}
						
//						if (text.length() >= 1) {
//							me.setText(text.substring(0,1));
//							if (!gamePlayer.assign(row, column, Integer.parseInt(text))) {
//								if (!currentGame.getSet(row, column)) {
//									me.setText("");
//									gamePlayer.clearCell(row, column);	
//								}
//								
//							} else {
//								if (gamePlayer.isComplete(currentGame)) {
//									updateStatus("GAME WON!!!");
//									//System.out.println("GAME WON!!!");
//								}
//							}
//						} else if (text.length() == 0) {
//							gamePlayer.clearCell(row, column);
//						}

					}
				});
				

				//System.out.println(row + " " + column);
				sudokuBoard[row][column] = field;
				panel.add(field);
			}
		}
	}
	
    public void focusLost(FocusEvent e) {
    	JFormattedTextField eventTrigger = (JFormattedTextField) e.getComponent();
    	editTextField(eventTrigger);
    }
    
    private void editTextField (JFormattedTextField me) {
		me.setBackground(new Color (255, 255, 255));
    	int row = 0;
    	int col = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sudokuBoard[i][j].equals(me)) {
					row = i;
					col = j;
					System.out.println("REACHED " + i + " " + j);
				}
			}
		}
    	
    	
    	String text = me.getText();
    	
    	if (text.equals("")) {
    		gamePlayer.clearCell(row, col);
    		return;
    	}
		if (text.length() > 1) {
			text = text.substring(0, 1);	
		}
		
		char input = text.charAt(0);
		if (!Character.isDigit(input) || input == '0') {
			text = "";
			me.setValue(text);
			return;
		}
		
		int num = Character.getNumericValue(input);
		
		if (currentGame.getSet(row, col)) {
			if (currentGame.cellValue(row, col) == num) {
				return;
			} else {
				gamePlayer.clearCell(row, col);
				if (!gamePlayer.assign(row, col, num)) {
					gamePlayer.clearCell(row, col);
					text = "";
					me.setValue(text);
					return;
				}
			}
		} else {
			if (!gamePlayer.assign(row, col, num)) {
				gamePlayer.clearCell(row, col);
				text = "";
				me.setValue(text);
				return;
			}
		}
		
		me.setText(text);
		if (currentGame.getSet(row, col)) {
			
		}
    }
    
	@Override
	public void focusGained(FocusEvent e) {
		JFormattedTextField eventTrigger = (JFormattedTextField) e.getComponent();
		
		eventTrigger.setBackground(new Color (200, 255, 200));
		
	}
	
	public void updateBoard () {
		String[][] numbers = stringTo2DArray(currentGame.toString());
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!numbers[i][j].equals(".")) {
					sudokuBoard[i][j].setText(numbers[i][j]);
				} else {
					sudokuBoard[i][j].setText("");
				}
			}
		}
	}
	
	/**
	 * Creates a small 3x3 grid
	 * @return	JPanel
	 */
    private JPanel make3x3Grid () {
        final GridLayout gridLayout = new GridLayout(3, 3);
        JPanel panel = new JPanel(gridLayout);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        return panel;
    }
    
    /**
     * Make the side buttons
     * @return	JPanel
     */
    private JPanel makeSideButtons (final JFrame frame) {
    	JPanel sideButtons = new JPanel();
		sideButtons.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		sideButtons.setPreferredSize(new Dimension(120, 600));
		
		JButton newGameButton = initComponent("New Game", "Starts a new game");
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				updateStatus(" ");
				selectDifficulty();
			}
		});
		final JButton hintButton = initComponent("Hint", hintTip);
		hintButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gamePlayer.hint();
				//updateStatus("Hint given");
				updateBoard();
				if (gamePlayer.hintsLeft() == 0) {
					hintButton.setEnabled(false);
				}
			}
		});
		final JButton resetButton = initComponent("Reset", ResetTip);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePlayer.resetGame();
				updateStatus("Board reset");
				updateBoard();
				hintButton.setEnabled(true);
			}
		});
		JButton solveButton = initComponent("Solve", SolveTip);
		solveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				gamePlayer.solveBoard();
				updateStatus("Board solved");
				updateBoard();
				resetButton.setEnabled(false);
			}
		});
		
		JLabel timerButton = new JLabel("Timer");
		timerButton.setHorizontalAlignment(JLabel.CENTER);
		timerButton.setPreferredSize(new Dimension(110, 20));
		
		JButton helpButton = initComponent("Help", "Help Button");
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				helpAction ();
			}
		});
		
		JLabel blankLabel1 = new JLabel(" ");
		JLabel blankLabel2 = new JLabel(" ");
	    c.weighty = 1;
		c.gridy = 2;
		sideButtons.add(blankLabel1, c);
	    c.weighty = 0.25;
	    c.gridy = 3;
	    sideButtons.add(statusIndicator, c);
	    c.gridy = 4;
	    sideButtons.add(newGameButton, c);
		c.gridy = 6;
		sideButtons.add(hintButton, c);
		c.gridy = 10;
		sideButtons.add(resetButton, c);
		c.gridy = 14;
		sideButtons.add(solveButton, c);
		c.gridy = 18;
		sideButtons.add(timerButton, c);
		c.gridy = 19;
		sideButtons.add(helpButton, c);
		c.weighty = 1;
		c.gridy = 22;
		sideButtons.add(blankLabel2, c);
		return sideButtons;
    }
    
    /***************************************************************************
     * HEYDEHO BRO IGNORE ME UNTIL LATER BABE
     * 
     * @param frame
     * @return
     **************************************************************************/
    private JPanel makeSolverSideButtons (final JFrame frame) {
    	JPanel sideButtons = new JPanel();
		sideButtons.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		sideButtons.setPreferredSize(new Dimension(120, 600));
		
		JButton newGameButton = initComponent("New Game", "Starts a new game");
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				updateStatus(" ");
				selectDifficulty();
			}
		});
		final JButton hintButton = initComponent("Hint", hintTip);
		hintButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gamePlayer.hint();
				//updateStatus("Hint given");
				updateBoard();
				if (gamePlayer.hintsLeft() == 0) {
					hintButton.setEnabled(false);
				}
			}
		});
		final JButton resetButton = initComponent("Reset", ResetTip);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePlayer.resetGame();
				updateStatus("Board reset");
				updateBoard();
				hintButton.setEnabled(true);
			}
		});
		return null;
    }
    
    private void helpAction () {
    	JFrame frame = new JFrame();
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	URL helpURL = GameInterface.class.getResource("Help.html");
		JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
		try {
			editorPane.setPage(helpURL);
		} catch (IOException e) {
			System.err.println("Attempted to read a bad URL: " + helpURL);
		}
        frame.add(editorPane);
        frame.setTitle("Sudoku Help");
		frame.pack();
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
    }
    
    /**
     * Pops up the initial window that asks the user to select a 
     * difficulty. It then closes and runs the game with the 
     * selected difficulty.
     */
    public void selectDifficulty () {
    	final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
    	JButton easy = initComponent("Easy", "Easy mode");
    	easy.addActionListener(new ActionListener() {
///////////////////////////////////////////////////////////////////////			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				gamePlayer.newGame(BoardGenerator.EASY);
				makeSudokuBoard ("Easy");
			}
		});
    	JButton medium = initComponent("Medium", "Medium mode");
    	medium.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				gamePlayer.newGame(BoardGenerator.MEDIUM);
				makeSudokuBoard ("Medium");
			}
		});
    	JButton hard = initComponent("Hard", "Hard mode");
    	hard.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				gamePlayer.newGame(BoardGenerator.HARD);
				makeSudokuBoard ("Hard");
			}
		});
    	
    	JButton solveButton = initComponent("Solver", "Sudoku solver");
    	// solveButton.setEnabled(false);
    	solveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				gamePlayer.solverMode();
				makeEmptyBoard ();
			}
		});
    	
    	JPanel difficulties = new JPanel (new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
    	JLabel blankLabel1 = new JLabel(" ");
		JLabel blankLabel2 = new JLabel(" ");
	    c.weighty = 1;
		c.gridy = 2;
		difficulties.add(blankLabel1, c);
	    c.weighty = 0.25;
		c.gridy = 6;
		difficulties.add(easy, c);
		c.gridy = 10;
		difficulties.add(medium, c);
		c.gridy = 14;
		difficulties.add(hard, c);
		c.gridy = 15;
		difficulties.add(solveButton, c);
		c.weighty = 1;
		c.gridy = 18;
		difficulties.add(blankLabel2, c);
    	
		frame.add(difficulties);
		frame.pack();
		frame.setSize(120, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
    }
    

    
    /**
     * Creates a JButton with a given name and roll over tool tip
     * @param buttonName	The String the JButton is called
     * @param toolTip		The String of the tool tip
     * @param width			The width of the JButton
     * @return				The JButton
     */
    private JButton initComponent (String buttonName, String toolTip) {
    	JButton button = new JButton (buttonName);
    	button.setToolTipText(toolTip);
    	button.setHorizontalAlignment(JButton.CENTER);
    	button.setPreferredSize(new Dimension(110, 20));
    	return button;
    }
    
    public void updateStatus (String newStatus) {
    	statusIndicator.setText(newStatus);
    }
    
	public void setBoard (Board board) {
		currentGame = board;
		this.sBoard = board.toString();
	}


}