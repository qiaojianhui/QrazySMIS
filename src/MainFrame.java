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

		JButton btnImpExcel = new JButton("导入Excel");
		imgURL = this.getClass().getResource("/images/excel.png");
		if (imgURL != null) {
			btnImpExcel.setIcon(new ImageIcon(imgURL.getFile()));
		}
		btnImpExcel.setToolTipText("打开Excel文件，显示表单内容并存入到数据库中");
		btnImpExcel.addActionListener(new ImportExcelListener(tabPane));
				
		//刷新数据列表
		JButton btnRefreshDB = new JButton("\u5237\u65B0\u6570\u636E");
		btnRefreshDB.setToolTipText("刷新数据列表");
		imgURL = this.getClass().getResource("/images/refresh.png");
		if (imgURL != null) {
			btnRefreshDB.setIcon(new ImageIcon(imgURL.getFile()));
		}
		btnRefreshDB.addActionListener(new listener.DataRefreshListener(tabPane,panelTable));

		// 查看历史记录
		JButton btnViewImpRec = new JButton("\u6587\u4EF6\u5BFC\u5165\u8BB0\u5F55");
		imgURL = this.getClass().getResource("/images/records32.png");
		if (imgURL != null) {
			btnViewImpRec.setIcon(new ImageIcon(imgURL.getFile()));
		}

		btnViewImpRec.setToolTipText("查看导入过的历史数据文件，所有导入过的文件以列表形式展示出来");
		btnViewImpRec.addActionListener(new listener.ViewImpRecListener(tabPane)); 

		// 统计
		JButton btnStatistics = new JButton("销售统计");
		imgURL = this.getClass().getResource("/images/piechart.png");
		if (imgURL != null) {
			btnStatistics.setIcon(new ImageIcon(imgURL.getFile()));
		}

		btnStatistics.setToolTipText("对销售数据进行统计分析");
		btnStatistics.addActionListener( new listener.StatisticsSalesRecordListener(tabPane));

		// 将定义的工具按钮添加至工具栏
		toolBar.add(btnRefreshDB); // 刷新数据
		toolBar.add(btnImpExcel);		// 导入excel
		toolBar.add(btnViewImpRec); // 查看导入记录
		toolBar.add(btnStatistics);
		toolBar.addSeparator();

		tabPane.addTab("销售记录",  panelTable );
		//		tabPane.addTab("销售记录",  new javax.swing.JLabel() );
		for(int i=1;i<tabPane.getTabCount();i++){
			tabPane.setTabComponentAt(i,
					new utilities.ButtonTabComponent(tabPane));
		}

		statusBar = new controls.StatusBar();

		// 添加一个状态栏
		getContentPane().add(statusBar, BorderLayout.SOUTH);
	}

	/**
	 * 返回类的实例
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
