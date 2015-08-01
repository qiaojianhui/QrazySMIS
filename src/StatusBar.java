import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class StatusBar extends JPanel {
	private JLabel lblInfo;
	private JProgressBar progressBar;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4942410523704853090L;

	public StatusBar() {
		setPreferredSize(new Dimension(300, 20));

		setLayout(new BorderLayout());

		lblInfo = new JLabel();
		add(lblInfo, BorderLayout.CENTER);

		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(300, 20));
		progressBar.setStringPainted(true);
		progressBar.setVisible(false);
		add(progressBar, BorderLayout.EAST);
	}

	public void setStatusInfo(String msg) {
		lblInfo.setText(msg);
	}
	public void setProgressVisible(boolean aFlag) {
		progressBar.setVisible(aFlag);
	}

	public void setProgressValue(int proValue) { 
		progressBar.setValue(proValue);
		progressBar.updateUI();
	}

	public void setProgressMessage(String msg) {
		progressBar.setString(msg);
	}
	public void setStatusInfo(String msg,int proValue){
		progressBar.setVisible(true);
		setStatusInfo(msg);
		setProgressValue(proValue);
	}
}
