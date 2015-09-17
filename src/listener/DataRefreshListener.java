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
			String sql = "SELECT no as 序号,BILLNO as 单据编号,BILLDATE as 单据日期,client as 往来单位,medicinename as 药品名称,"
					+ "MEDICINESCALE as 药品规格,MEDICINECERNO as 批号,MEDICINEVALIDITY as 有效期,SALESNUMBER as 销售数量,"
					+ " SALESPRICE as 销售单价,SALESAMOUNT as 销售金额, EMPLOYEE as 经办人员 " + " FROM t_salesrecord_ee";

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
