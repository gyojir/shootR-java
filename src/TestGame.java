import java.awt.Container;

import javax.swing.JFrame;

public class TestGame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestGame(){
		setTitle("shootR");
		setResizable(false);
		
		MainPanel panel = new MainPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);
		
		pack();
	}

	public static void main(String[] args) {
		TestGame frame = new TestGame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
