
/**
 * 
 */
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

/**
 * @author mylxiaoyi
 *
 */
public class MenuSample {
	static class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Selected: " + event.getActionCommand());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Runnable runner = new Runnable() {
			public void run() {
				MenuActionListener menuListener = new MenuActionListener();
				JFrame frame = new JFrame("Menu Sample");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JMenuBar menuBar = new JMenuBar();
				JMenu fileMenu = new JMenu("File");
				fileMenu.setMnemonic(KeyEvent.VK_F);
				menuBar.add(fileMenu);
				JMenuItem newMenuItem = new JMenuItem("New", KeyEvent.VK_N);
				newMenuItem.addActionListener(menuListener);
				fileMenu.add(newMenuItem);
				JMenuItem openMenuItem = new JMenuItem("Open", KeyEvent.VK_O);
				openMenuItem.addActionListener(menuListener);
				fileMenu.add(openMenuItem);
				JMenuItem closeMenuItem = new JMenuItem("Close", KeyEvent.VK_C);
				closeMenuItem.addActionListener(menuListener);
				fileMenu.add(closeMenuItem);
				fileMenu.addSeparator();
				JMenuItem saveMenuItem = new JMenuItem("Save", KeyEvent.VK_S);
				saveMenuItem.addActionListener(menuListener);
				fileMenu.add(saveMenuItem);
				fileMenu.addSeparator();
				JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
				exitMenuItem.addActionListener(menuListener);
				fileMenu.add(exitMenuItem);
				JMenu editMenu = new JMenu("Edit");
				editMenu.setMnemonic(KeyEvent.VK_E);
				menuBar.add(editMenu);
				JMenuItem cutMenuItem = new JMenuItem("Cut", KeyEvent.VK_T);
				cutMenuItem.addActionListener(menuListener);
				KeyStroke ctrlXKeyStroke = KeyStroke.getKeyStroke("control X");
				cutMenuItem.setAccelerator(ctrlXKeyStroke);
				editMenu.add(cutMenuItem);
				JMenuItem copyMenuItem = new JMenuItem("Copy", KeyEvent.VK_C);
				copyMenuItem.addActionListener(menuListener);
				KeyStroke ctrlCKeyStroke = KeyStroke.getKeyStroke("control C");
				copyMenuItem.setAccelerator(ctrlCKeyStroke);
				editMenu.add(copyMenuItem);
				JMenuItem pasteMenuItem = new JMenuItem("Paste", KeyEvent.VK_P);
				pasteMenuItem.addActionListener(menuListener);
				KeyStroke ctrlVKeyStroke = KeyStroke.getKeyStroke("control V");
				pasteMenuItem.setAccelerator(ctrlVKeyStroke);
				editMenu.add(pasteMenuItem);
				editMenu.addSeparator();
				JMenuItem findMenuItem = new JMenuItem("Find", KeyEvent.VK_F);
				findMenuItem.addActionListener(menuListener);
				KeyStroke f3KeyStroke = KeyStroke.getKeyStroke("F3");
				findMenuItem.setAccelerator(f3KeyStroke);
				editMenu.add(findMenuItem);
				JMenu findOptionsMenu = new JMenu("Options");
				Icon atIcon = new ImageIcon("at.gif");
				findOptionsMenu.setIcon(atIcon);
				findOptionsMenu.setMnemonic(KeyEvent.VK_O);
				ButtonGroup directionGroup = new ButtonGroup();
				JRadioButtonMenuItem forwardMenuItem = new JRadioButtonMenuItem("Forward", true);
				forwardMenuItem.addActionListener(menuListener);
				forwardMenuItem.setMnemonic(KeyEvent.VK_F);
				findOptionsMenu.add(forwardMenuItem);
				directionGroup.add(forwardMenuItem);
				JRadioButtonMenuItem backMenuItem = new JRadioButtonMenuItem("Back");
				backMenuItem.addActionListener(menuListener);
				backMenuItem.setMnemonic(KeyEvent.VK_B);
				findOptionsMenu.add(backMenuItem);
				directionGroup.add(backMenuItem);
				findOptionsMenu.addSeparator();
				JCheckBoxMenuItem caseMenuItem = new JCheckBoxMenuItem("Case Sensitive");
				caseMenuItem.addActionListener(menuListener);
				caseMenuItem.setMnemonic(KeyEvent.VK_C);
				findOptionsMenu.add(caseMenuItem);
				editMenu.add(findOptionsMenu);
				frame.setJMenuBar(menuBar);
				frame.setSize(350, 250);
				frame.setVisible(true);
			}
		};
		EventQueue.invokeLater(runner);
	}
}