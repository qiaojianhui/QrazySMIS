import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import listener.ImportExcelListener;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7018583495088202767L;
	private final JPanel panelTable = new JPanel();
	private JTabbedPane tabPane;
	private static MainFrame instance;
	private controls.StatusBar statusBar;

	private MainFrame(String title) {

		setTitle(title);
		java.net.URL imgURL;

		getContentPane().setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setRollover(true);
		toolBar.setBounds(20, 60, 154, -30);
		getContentPane().add(toolBar, BorderLayout.NORTH);

		tabPane=new JTabbedPane();		
		getContentPane().add(tabPane, BorderLayout.CENTER);

		JButton btnImpExcel = new JButton("����Excel");
		imgURL = this.getClass().getResource("/images/excel.png");
		if (imgURL != null) {
			btnImpExcel.setIcon(new ImageIcon(imgURL.getFile()));
		}
		btnImpExcel.setToolTipText("��Excel�ļ�����ʾ�����ݲ����뵽���ݿ���");
		btnImpExcel.addActionListener(new ImportExcelListener(tabPane));
				
		//ˢ�������б�
		JButton btnRefreshDB = new JButton("\u5237\u65B0\u6570\u636E");
		btnRefreshDB.setToolTipText("ˢ�������б�");
		imgURL = this.getClass().getResource("/images/refresh.png");
		if (imgURL != null) {
			btnRefreshDB.setIcon(new ImageIcon(imgURL.getFile()));
		}
		btnRefreshDB.addActionListener(new listener.DataRefreshListener(tabPane,panelTable));

		// �鿴��ʷ��¼
		JButton btnViewImpRec = new JButton("\u6587\u4EF6\u5BFC\u5165\u8BB0\u5F55");
		imgURL = this.getClass().getResource("/images/records32.png");
		if (imgURL != null) {
			btnViewImpRec.setIcon(new ImageIcon(imgURL.getFile()));
		}

		btnViewImpRec.setToolTipText("�鿴���������ʷ�����ļ������е�������ļ����б���ʽչʾ����");
		btnViewImpRec.addActionListener(new listener.ViewImpRecListener(tabPane)); 

		// ͳ��
		JButton btnStatistics = new JButton("����ͳ��");
		imgURL = this.getClass().getResource("/images/piechart.png");
		if (imgURL != null) {
			btnStatistics.setIcon(new ImageIcon(imgURL.getFile()));
		}

		btnStatistics.setToolTipText("���������ݽ���ͳ�Ʒ���");
		btnStatistics.addActionListener( new listener.StatisticsSalesRecordListener(tabPane));

		// ������Ĺ��߰�ť�����������
		toolBar.add(btnRefreshDB); // ˢ������
		toolBar.add(btnImpExcel);		// ����excel
		toolBar.add(btnViewImpRec); // �鿴�����¼
		toolBar.add(btnStatistics);
		toolBar.addSeparator();

		tabPane.addTab("���ۼ�¼",  panelTable );
		//		tabPane.addTab("���ۼ�¼",  new javax.swing.JLabel() );
		for(int i=1;i<tabPane.getTabCount();i++){
			tabPane.setTabComponentAt(i,
					new utilities.ButtonTabComponent(tabPane));
		}

		statusBar = new controls.StatusBar();

		// ���һ��״̬��
		getContentPane().add(statusBar, BorderLayout.SOUTH);
	}

	/**
	 * �������ʵ��
	 * 
	 * @param title
	 * @return
	 */
	public static MainFrame getInstance(String title) {
		if (instance == null)
			instance = new MainFrame(title);
		return instance;
	}
}
