import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameInterface {
	
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
}

