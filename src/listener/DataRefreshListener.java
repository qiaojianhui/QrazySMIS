/**
 * 
 */
package listener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import utilities.SQLiteHelper;

/**
 * @author qiaojianhui
 *
 */
public class DataRefreshListener implements ActionListener {

	private JTabbedPane parentTab;
	private JPanel panelTable;
	public DataRefreshListener(JTabbedPane tab, JPanel panel){
		parentTab=tab;
		panelTable=panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String sql = "SELECT no as ���,BILLNO as ���ݱ��,BILLDATE as ��������,client as ������λ,medicinename as ҩƷ����,"
					+ "MEDICINESCALE as ҩƷ���,MEDICINECERNO as ����,MEDICINEVALIDITY as ��Ч��,SALESNUMBER as ��������,"
					+ " SALESPRICE as ���۵���,SALESAMOUNT as ���۽��, EMPLOYEE as ������Ա " + " FROM t_salesrecord_ee";

			JTable table = SQLiteHelper.getInstance("db").queryTable(sql);
			 
			panelTable.setLayout(new BorderLayout());
//			panel.add(table, BorderLayout.CENTER);
			table.setVisible(true);
			table.setRowHeight(20);

		//	panel.removeAll();
			panelTable.add(new JScrollPane(table), BorderLayout.CENTER);
		  
			parentTab.setSelectedIndex(0);
			parentTab.repaint();
			

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
