/**
 * 
 */
package listener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

/**
 * @author qiaojianhui
 *
 */
public class ImportExcelListener implements ActionListener {

	private JTabbedPane parentTab;
	public ImportExcelListener(JTabbedPane tab){
		parentTab=tab;
	}
 
	@Override
	public void actionPerformed(ActionEvent e) {
	//	setCursor(java.awt.Cursor.WAIT_CURSOR);
		//	panelTable.removeAll();
		// TODO Auto-generated method stub
		Vector colum = new Vector();
		Vector rows = new Vector();
		//statusBar.setStatusInfo("准备导入excel文件数据……");
		try {

			utilities.PoiExcelHelper helper = new utilities.PoiXlsxHelper();
			JFileChooser dlg = new JFileChooser();
			dlg.setDialogTitle("请选择Excel文件");
			int result = dlg.showOpenDialog(null); // 打开"打开文件"对话框
			// int result = dlg.showSaveDialog(this); // 打"开保存文件"对话框
			File file = null;
			if (result == JFileChooser.APPROVE_OPTION) {
				file = dlg.getSelectedFile();
			} else {
			parentTab.repaint();
				return;
			}
			if (file == null)
				return;

			// 获取文件的md5值
			utilities.SQLiteHelper sqlHelper = utilities.SQLiteHelper.getInstance("db");
			String md5 = utilities.FileIdentifier.getMD5(file);
			String sql = "select count(*) from t_import_files where md5='" + md5 + "'";
			java.sql.ResultSet rs = sqlHelper.excuteQuery(sql);
			int r = rs.getInt(1);
			if (r > 0) {
				JOptionPane.showMessageDialog(null, "文件'" + file.getAbsolutePath() + "'中的数据已经导入过，不允许重复导入。");
			//	statusBar.setStatusInfo("中止导入……");
				return;
			}

			if (file.getName().endsWith(".xls"))
				helper = new utilities.PoiXlsHelper();

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
				//statusBar.setStatusInfo("正在导入excel数据……" ,100*(int)p);
				parentTab.repaint();
			}

			// 将md5值存入数据库
			sql = "insert into T_IMPORT_FILES (FILENAME,FULLPATH,MD5,TIME) VALUES('" + file.getName() + "','"
					+ file.getAbsolutePath() + "','" + md5 + "',datetime('now','localtime'))";
			sqlHelper.executeUpdate(sql);

			// 显示内容
			parentTab.removeAll();
			JTable table = new JTable(rows, colum);

			parentTab.setLayout(new BorderLayout());
			parentTab.add(table, BorderLayout.CENTER);
			table.setVisible(true);
			table.setRowHeight(20);

			parentTab.add(new JScrollPane(table), BorderLayout.CENTER);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			//setCursor(java.awt.Cursor.DEFAULT_CURSOR);

			//statusBar.setStatusInfo("完成导入。",100);

		}
	}
}
