package controls;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JLabel;

/**
 * 
 */

/**
 * @author qiaojianhui
 *
 */
public class QStatusBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1008476765815696562L;
	
	public QStatusBar(){
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JLabel lblMessage = new JLabel("11111111111111");
		lblMessage.setSize(WIDTH/3, HEIGHT);
		add(lblMessage);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setSize(WIDTH/3, HEIGHT);
		progressBar.setStringPainted(true);
		progressBar.setValue(40);
		add(progressBar);
//		progressBar.setVisible(false);
		JPanel panelICOS = new JPanel();
		panelICOS.setSize(WIDTH/3, HEIGHT);
		panelICOS.setOpaque(true);
		add(panelICOS);
//		setOpaque(false);
	}
	public static void main(String[] args) {
		javax.swing.JFrame f =new JFrame("xxxxxxxxxxxxx");
	f.setSize(800, 500);
		QStatusBar qs=new QStatusBar();
 qs.setOpaque(false);
 
	
		f.getContentPane().add(qs,BorderLayout.SOUTH);
		f.setVisible(true);
	}
}
