import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.io.File;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7018583495088202767L;
	private final JPanel panelTable = new JPanel();

	public MainFrame(String title) {
		setTitle(title);

		JMenuBar menuBar = new JMenuBar();

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mnOpen = new JMenuItem("Open");
		mnOpen.setAccelerator(KeyStroke.getKeyStroke("control O"));
		mnFile.add(mnOpen);

		JMenuItem mnSave = new JMenuItem("Save");
		mnSave.setAccelerator(KeyStroke.getKeyStroke("control S"));
		mnFile.add(mnSave);
		mnFile.addSeparator();
		JMenuItem mnExit = new JMenuItem("Exit");
		mnExit.setAccelerator(KeyStroke.getKeyStroke("control Q"));
		mnFile.add(mnExit);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem menuItem = new JMenuItem("New menu item");
		mnEdit.add(menuItem);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mnItemHelpAbout = new JMenuItem("关于");
		mnItemHelpAbout.setAccelerator(KeyStroke.getKeyStroke("control A"));
		mnHelp.add(mnItemHelpAbout);

		JMenuItem mnItemHelpHelp = new JMenuItem("帮助");
		mnItemHelpHelp.setAccelerator(KeyStroke.getKeyStroke("F1"));
		// mnItemHelpHelp.addMouseListener(new MouseAdapter(){
		// @Override
		// public void mouseClicked(MouseEvent arg0) {
		//// JOptionPane.showConfirmDialog(null, "测试帮助");
		// JFileChooser f=new JFileChooser();
		// f.showSaveDialog(null);
		// }
		// });
		mnItemHelpHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "测试帮助");
			}
		});
		mnHelp.add(mnItemHelpHelp);

		this.setJMenuBar(menuBar);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setRollover(true);
		toolBar.setBounds(20, 60, 154, -30);
		getContentPane().add(toolBar, BorderLayout.NORTH);

		JButton btnImpExcel = new JButton("导入Excel");
		btnImpExcel.setToolTipText("打开Excel文件，显示表单内容并存入到数据库中");
		btnImpExcel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setCursor(java.awt.Cursor.WAIT_CURSOR);
				panelTable.removeAll();
				// TODO Auto-generated method stub
				Vector colum = new Vector();
				Vector rows = new Vector();

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
					if (r > 0){
					   JOptionPane.showMessageDialog(null, "文件'"+file.getAbsolutePath()+"'中的数据已经导入过，不允许重复导入。");
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
				}
			}
		});
		
		JButton btnRefreshDB = new JButton("\u5237\u65B0\u6570\u636E");
		btnRefreshDB.setToolTipText("刷新数据列表");
		btnRefreshDB.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				String sql="SELECT no as 序号,BILLNO as 单据编号,BILLDATE as 单据日期,client as 往来单位,medicinename as 药品名称," +
								 "MEDICINESCALE as 药品规格,MEDICINECERNO as 批号,MEDICINEVALIDITY as 有效期,SALESNUMBER as 销售数量," + 
								 " SALESPRICE as 销售单价,SALESAMOUNT as 销售金额, EMPLOYEE as 经办人员 " +
								 " FROM t_salesrecord_ee";
		
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
		
		toolBar.add(btnRefreshDB);
		toolBar.add(btnImpExcel);
		
		JButton btnViewImpRec = new JButton("\u6587\u4EF6\u5BFC\u5165\u8BB0\u5F55");
		btnViewImpRec.setToolTipText("查看导入过的历史数据文件，所有导入过的文件以列表形式展示出来");
		btnViewImpRec.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JDesktopPane deskPane=new JDesktopPane( );
				getContentPane().	add(deskPane);
				
//				javax.swing.JInternalFrame inf =new JInternalFrame("xxxxxxxxxxxx", true, true, true);
				FrameViewImpRec frame = new FrameViewImpRec("文件导入列表");
//				frame.setLocation( 20,20);
//				frame.setSize(200,200); 
				frame.setVisible(true);
				deskPane.add(frame);
				frame.setVisible(true);
 
			}
		});
		toolBar.add(btnViewImpRec);

		toolBar.addSeparator();

		getContentPane().add(panelTable, BorderLayout.CENTER);
	}
}
