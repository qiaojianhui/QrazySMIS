import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7018583495088202767L;
	private final JPanel panelTable = new JPanel();
	private JTabbedPane tabPane;
	private static MainFrame instance;
	private StatusBar statusBar;

	private MainFrame(String title) {

		setTitle(title);
		java.net.URL imgURL;

//		JMenuBar menuBar = new JMenuBar();
//
//		JMenu mnFile = new JMenu("File");
//		menuBar.add(mnFile);
//
//		JMenuItem mnOpen = new JMenuItem("Open");
//		mnOpen.setAccelerator(KeyStroke.getKeyStroke("control O"));
//		mnFile.add(mnOpen);
//
//		JMenuItem mnSave = new JMenuItem("Save");
//		mnSave.setAccelerator(KeyStroke.getKeyStroke("control S"));
//		mnFile.add(mnSave);
//		mnFile.addSeparator();
//		JMenuItem mnExit = new JMenuItem("Exit");
//		mnExit.setAccelerator(KeyStroke.getKeyStroke("control Q"));
//		mnFile.add(mnExit);
//
//		JMenu mnEdit = new JMenu("Edit");
//		menuBar.add(mnEdit);
//
//		JMenuItem menuItem = new JMenuItem("New menu item");
//		mnEdit.add(menuItem);
//
//		JMenu mnHelp = new JMenu("Help");
//		menuBar.add(mnHelp);
//
//		JMenuItem mnItemHelpAbout = new JMenuItem("关于");
//		mnItemHelpAbout.setAccelerator(KeyStroke.getKeyStroke("control A"));
//		mnHelp.add(mnItemHelpAbout);
//
//		JMenuItem mnItemHelpHelp = new JMenuItem("帮助");
//		mnItemHelpHelp.setAccelerator(KeyStroke.getKeyStroke("F1"));
//		// mnItemHelpHelp.addMouseListener(new MouseAdapter(){
//		// @Override
//		// public void mouseClicked(MouseEvent arg0) {
//		//// JOptionPane.showConfirmDialog(null, "测试帮助");
//		// JFileChooser f=new JFileChooser();
//		// f.showSaveDialog(null);
//		// }
//		// });
//		mnItemHelpHelp.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JOptionPane.showMessageDialog(null, "测试帮助");
//			}
//		});
//		mnHelp.add(mnItemHelpHelp);
//
//		// this.setJMenuBar(menuBar);

		getContentPane().setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setRollover(true);
		toolBar.setBounds(20, 60, 154, -30);
		getContentPane().add(toolBar, BorderLayout.NORTH);

		tabPane=new JTabbedPane();
//		tabPane.addChangeListener(new ChangeListener() {
//			
//			@Override
//			public void stateChanged(ChangeEvent arg0) {
//				
//				// TODO Auto-generated method stub
//				for(int i=0;i<tabPane.getTabCount();i++){
//					tabPane.setTabComponentAt(i,
//				                 new ButtonTabComponent(tabPane));
//				}
//			}
//		});
		
		getContentPane().add(tabPane, BorderLayout.CENTER);
		
		
		JButton btnImpExcel = new JButton("导入Excel");
		imgURL = this.getClass().getResource("/images/excel.png");
		if (imgURL != null) {
			btnImpExcel.setIcon(new ImageIcon(imgURL.getFile()));
		}
		btnImpExcel.setToolTipText("打开Excel文件，显示表单内容并存入到数据库中");
		btnImpExcel.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				setCursor(java.awt.Cursor.WAIT_CURSOR);
				panelTable.removeAll();
				// TODO Auto-generated method stub
				Vector colum = new Vector();
				Vector rows = new Vector();
statusBar.setStatusInfo("准备导入excel文件数据……");
				try {

					PoiExcelHelper helper = new PoiXlsxHelper();
					JFileChooser dlg = new JFileChooser();
					dlg.setDialogTitle("请选择Excel文件");
					int result = dlg.showOpenDialog(null); // 打开"打开文件"对话框
					// int result = dlg.showSaveDialog(this); // 打"开保存文件"对话框
					File file = null;
					if (result == JFileChooser.APPROVE_OPTION) {
						file = dlg.getSelectedFile();
					} else {
						repaint();
						return;
					}
					if (file == null)
						return;

					// 获取文件的md5值
					SQLiteHelper sqlHelper = SQLiteHelper.getInstance("db");
					String md5 = FileIdentifier.getMD5(file);
					String sql = "select count(*) from t_import_files where md5='" + md5 + "'";
					java.sql.ResultSet rs = sqlHelper.excuteQuery(sql);
					int r = rs.getInt(1);
					if (r > 0) {
						JOptionPane.showMessageDialog(null, "文件'" + file.getAbsolutePath() + "'中的数据已经导入过，不允许重复导入。");
						statusBar.setStatusInfo("中止导入……");
						return;
					}

					if (file.getName().endsWith(".xls"))
						helper = new PoiXlsHelper();

					// 读取excel文件数据
					ArrayList<ArrayList<String>> dataList = helper.readExcel(file.getPath(), 0);

					ArrayList columnHeader = dataList.get(0);
					for (int k = 0; k < columnHeader.size(); k++) {
						colum.addElement(columnHeader.get(k));
					}

					dataList.remove(0);
					UUID uuid = null;
					for (ArrayList curList : dataList) {
						Vector currow = new Vector();
						for (int j = 0; j < curList.size(); j++) {
							currow.addElement(curList.get(j));
						}
						rows.addElement(currow);

						uuid = UUID.randomUUID();
						sql = "INSERT INTO T_SALESRECORD_EE VALUES('" + uuid.toString() + "','" + curList.get(0) + "','"
								+ curList.get(1) + "','" + curList.get(2) + "','" + curList.get(3) + "','"
								+ curList.get(4) + "','" + curList.get(5) + "','" + curList.get(6) + "','"
								+ curList.get(7) + "'," + curList.get(8) + "," + curList.get(9) + "," + curList.get(10)
								+ ",'" + curList.get(11) + "')";
						sqlHelper.executeUpdate(sql);
						double i=dataList.indexOf(curList);
					double p=i/dataList.size(); 
						statusBar.setStatusInfo("正在导入excel数据……" ,100*(int)p);
						repaint();
					}
 
					// 将md5值存入数据库
					sql = "insert into T_IMPORT_FILES (FILENAME,FULLPATH,MD5,TIME) VALUES('" + file.getName() + "','"
							+ file.getAbsolutePath() + "','" + md5 + "',datetime('now','localtime'))";
					sqlHelper.executeUpdate(sql);

					// 显示内容
					JTable table = new JTable(rows, colum);

					panelTable.setLayout(new BorderLayout());
					panelTable.add(table, BorderLayout.CENTER);
					table.setVisible(true);
					table.setRowHeight(20);

					panelTable.add(new JScrollPane(table), BorderLayout.CENTER);

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					setCursor(java.awt.Cursor.DEFAULT_CURSOR);
					
					statusBar.setStatusInfo("完成导入。",100);
					
				}
			}
		});

		JButton btnRefreshDB = new JButton("\u5237\u65B0\u6570\u636E");
		btnRefreshDB.setToolTipText("刷新数据列表");
		imgURL = this.getClass().getResource("/images/refresh.png");
		if (imgURL != null) {
			btnRefreshDB.setIcon(new ImageIcon(imgURL.getFile()));
		}

		btnRefreshDB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String sql = "SELECT no as 序号,BILLNO as 单据编号,BILLDATE as 单据日期,client as 往来单位,medicinename as 药品名称,"
							+ "MEDICINESCALE as 药品规格,MEDICINECERNO as 批号,MEDICINEVALIDITY as 有效期,SALESNUMBER as 销售数量,"
							+ " SALESPRICE as 销售单价,SALESAMOUNT as 销售金额, EMPLOYEE as 经办人员 " + " FROM t_salesrecord_ee";

					JTable table = SQLiteHelper.getInstance("db").queryTable(sql);
					panelTable.setLayout(new BorderLayout());
					panelTable.add(table, BorderLayout.CENTER);
					table.setVisible(true);
					table.setRowHeight(20);

					panelTable.add(new JScrollPane(table), BorderLayout.CENTER);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	

		JButton btnViewImpRec = new JButton("\u6587\u4EF6\u5BFC\u5165\u8BB0\u5F55");
		imgURL = this.getClass().getResource("/images/records32.png");
		if (imgURL != null) {
			btnViewImpRec.setIcon(new ImageIcon(imgURL.getFile()));
		}

		btnViewImpRec.setToolTipText("查看导入过的历史数据文件，所有导入过的文件以列表形式展示出来");
		btnViewImpRec.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
//				JDesktopPane deskPane = new JDesktopPane();
//				getContentPane().add(deskPane);
//
//				// javax.swing.JInternalFrame inf =new
//				// JInternalFrame("xxxxxxxxxxxx", true, true, true);
//				FrameViewImpRec frame = new FrameViewImpRec("文件导入列表", instance);
//				// frame.setLocation( 20,20);
//				// frame.setSize(200,200);
//				frame.setVisible(true);
//				deskPane.add(frame);
//				frame.setVisible(true);
				 JPanel panel = new JPanel();
				 panel.setLayout(new BorderLayout());
				String sql = "select id as 序号,filename as 文件名,fullpath as 路径,time as 导入时间 from t_import_files";

			JTable tableList;

				tableList = SQLiteHelper.getInstance("db").queryTable(sql);


//				panel.add(tableList, BorderLayout.CENTER);
				tableList.setVisible(true);
				// tableList.setRowHeight(50);
				panel.add(new JScrollPane(tableList), BorderLayout.CENTER);
				tableList.setFillsViewportHeight(true);
				
				tabPane.addTab("数据导入记录",panel);
		 
//				for(int i=0;i<tabPane.getTabCount();i++){
					tabPane.setTabComponentAt(tabPane.getTabCount()-1,
				                 new ButtonTabComponent(tabPane));
//				}
				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// 将定义的工具按钮添加至工具栏
		toolBar.add(btnRefreshDB); // 刷新数据
		toolBar.add(btnImpExcel);		// 导入excel
		 toolBar.add(btnViewImpRec); // 查看导入记录

		toolBar.addSeparator();

		tabPane.addTab("销售记录",  panelTable );
//		tabPane.addTab("销售记录",  new javax.swing.JLabel() );
	for(int i=0;i<tabPane.getTabCount();i++){
		tabPane.setTabComponentAt(i,
	                 new ButtonTabComponent(tabPane));
	}
		
		statusBar = new StatusBar();
 
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
