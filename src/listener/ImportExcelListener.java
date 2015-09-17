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
		//statusBar.setStatusInfo("׼������excel�ļ����ݡ���");
		try {

			utilities.PoiExcelHelper helper = new utilities.PoiXlsxHelper();
			JFileChooser dlg = new JFileChooser();
			dlg.setDialogTitle("��ѡ��Excel�ļ�");
			int result = dlg.showOpenDialog(null); // ��"���ļ�"�Ի���
			// int result = dlg.showSaveDialog(this); // ��"�������ļ�"�Ի���
			File file = null;
			if (result == JFileChooser.APPROVE_OPTION) {
				file = dlg.getSelectedFile();
			} else {
			parentTab.repaint();
				return;
			}
			if (file == null)
				return;

			// ��ȡ�ļ���md5ֵ
			utilities.SQLiteHelper sqlHelper = utilities.SQLiteHelper.getInstance("db");
			String md5 = utilities.FileIdentifier.getMD5(file);
			String sql = "select count(*) from t_import_files where md5='" + md5 + "'";
			java.sql.ResultSet rs = sqlHelper.excuteQuery(sql);
			int r = rs.getInt(1);
			if (r > 0) {
				JOptionPane.showMessageDialog(null, "�ļ�'" + file.getAbsolutePath() + "'�е������Ѿ���������������ظ����롣");
			//	statusBar.setStatusInfo("��ֹ���롭��");
				return;
			}

			if (file.getName().endsWith(".xls"))
				helper = new utilities.PoiXlsHelper();

			// ��ȡexcel�ļ�����
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
				//statusBar.setStatusInfo("���ڵ���excel���ݡ���" ,100*(int)p);
				parentTab.repaint();
			}

			// ��md5ֵ�������ݿ�
			sql = "insert into T_IMPORT_FILES (FILENAME,FULLPATH,MD5,TIME) VALUES('" + file.getName() + "','"
					+ file.getAbsolutePath() + "','" + md5 + "',datetime('now','localtime'))";
			sqlHelper.executeUpdate(sql);

			// ��ʾ����
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

			//statusBar.setStatusInfo("��ɵ��롣",100);

		}
	}
}
