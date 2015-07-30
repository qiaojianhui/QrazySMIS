import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Starter {

	public static void main(String[] args) {

		MainFrame f = new MainFrame("Jrame生成的窗体");
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				int ret = JOptionPane.showConfirmDialog(null, "确定要退出系统吗？", "系统提示", JOptionPane.OK_OPTION);
				if (ret == JOptionPane.OK_OPTION)
					System.exit(0);
			}
		});

		f.setSize(600, 400); // 设置屏幕初始大小
		 f.setExtendedState( Frame.MAXIMIZED_BOTH ); // 最大化显示
		f.setLocationRelativeTo(null); // 是指窗体显示在屏幕中央
		f.setVisible(true);
	}

}
