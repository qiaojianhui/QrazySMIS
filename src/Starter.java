import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Starter {

	public static void main(String[] args) {

		MainFrame f = new MainFrame("Jrame���ɵĴ���");
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				int ret = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ�˳�ϵͳ��", "ϵͳ��ʾ", JOptionPane.OK_OPTION);
				if (ret == JOptionPane.OK_OPTION)
					System.exit(0);
			}
		});

		f.setSize(600, 400); // ������Ļ��ʼ��С
		 f.setExtendedState( Frame.MAXIMIZED_BOTH ); // �����ʾ
		f.setLocationRelativeTo(null); // ��ָ������ʾ����Ļ����
		f.setVisible(true);
	}

}
