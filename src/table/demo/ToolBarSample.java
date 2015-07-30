package table.demo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class ToolBarSample {

	private static final int STRING_POSITION = 1;
	static Object buttonColors[][] = { { Color.RED, "RED" }, { Color.BLUE, "BLUE" }, { Color.GREEN, "GREEN" },
			{ Color.BLACK, "BLACK" }, null, // separator
			{ Color.CYAN, "CYAN" } };

	public static class TheActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println(event.getActionCommand());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	// TODO Auto-generated method stub Runnable runner = new Runnable() {
 
		JFrame frame = new JFrame("JToolBar Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ActionListener actionListener = new TheActionListener();
		JToolBar toolbar = new JToolBar();
		toolbar.setRollover(true);
		for (Object[] color : buttonColors) {
			if (color == null) {
				toolbar.addSeparator();
			} else {
				 
				JButton button = new JButton();
				button.setActionCommand((String) color[STRING_POSITION]);
				button.addActionListener(actionListener);
				toolbar.add(button);
			}
		} 
		Container contentPane = frame.getContentPane();
		contentPane.add(toolbar, BorderLayout.NORTH);
		JTextArea textArea = new JTextArea();
		JScrollPane pane = new JScrollPane(textArea);
		contentPane.add(pane, BorderLayout.CENTER);
		frame.setSize(350, 150);
		frame.setVisible(true);
	};
 
}
 
