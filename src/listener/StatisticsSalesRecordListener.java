package listener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import utilities.ButtonTabComponent;
import utilities.SQLiteHelper;

/**
 * 
 */

/**
 * @author qiaojianhui
 *
 */
public class StatisticsSalesRecordListener   implements ActionListener {

	private JTabbedPane parentTab;
	public StatisticsSalesRecordListener(JTabbedPane tab){
		parentTab=tab;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) { 
		try {
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			String sql = "select id as 序号,filename as 文件名,fullpath as 路径,time as 导入时间 from t_import_files";

			JTable tableList;

			tableList = SQLiteHelper.getInstance("db").queryTable(sql);

			tableList.setVisible(true);
			// tableList.setRowHeight(50);
			panel.add(new JScrollPane(tableList), BorderLayout.CENTER);
			tableList.setFillsViewportHeight(true);

			parentTab.addTab("数据导入记录",panel);

			//				for(int i=0;i<tabPane.getTabCount();i++){
			parentTab.setTabComponentAt(parentTab.getTabCount()-1,
					new ButtonTabComponent(parentTab));
			//				}
			parentTab.setSelectedIndex(parentTab.getTabCount()-1);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
