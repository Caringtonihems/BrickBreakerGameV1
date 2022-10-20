package dbzBrickbreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		Gameplay newgame = new Gameplay();
		
		frame.setBounds(10,10,700,600);
		frame.setTitle("DBZ BrickBreaker");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(newgame);

	}

}
