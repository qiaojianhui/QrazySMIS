import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.sqlite.SQLiteJDBCLoader;

import javax.swing.JTable;

public class FrameViewImpRec extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1860884696474199632L;
	private JPanel contentPane;
	private JTable tableList;
 
	/**
	 * Create the frame.
	 */
	public FrameViewImpRec(String title) {
		super(title,true,true,true,true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
//		setTitle(title);

		try { 
			String sql = "select id as 序号,filename as 文件名,fullpath as 路径,time as 导入时间 from t_import_files";
 
			tableList = SQLiteHelper.getInstance("db").queryTable(sql);
					
			contentPane.add(tableList, BorderLayout.CENTER);
			tableList.setVisible(true);
			// tableList.setRowHeight(50);
			add(new JScrollPane(tableList), BorderLayout.CENTER);
			tableList.setFillsViewportHeight(true);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
