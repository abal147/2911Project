import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GameInterface {
	
	private String difficulty;
	private String sBoard;
	
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
        	 
        	 JButton blankButton = new JButton(" ");
        	 boardPanel.add(blankButton);
//        	 blankButton.addActionListener(new ActionListener() {
//        	               public void actionPerformed(ActionEvent event) {
//        	                  blankButton.v
//        	               }
//        	            });
        	 // needs to be blank with some sort of action
        	 // to add the number
         } else {
	         JButton keyButton = new JButton(value);
	         boardPanel.add(keyButton);
         }
      }
      
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(difficultyPanel, BorderLayout.NORTH);
      frame.add(boardPanel, BorderLayout.CENTER);

//      JButton[][] arr = new JButton[9][9];
//      
//      Container pane = frame.getContentPane();
//	  GridBagConstraints c = new GridBagConstraints();
//	  pane.setLayout(new GridBagLayout());

      frame.pack();
      frame.setVisible(true);
	      
	}
	
	public void setDifficulty (String difficulty) {
		this.difficulty = difficulty;
	}
	
	public void setBoard (String board) {
		this.sBoard = board;
	}
}
