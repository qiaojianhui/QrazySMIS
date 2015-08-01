import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

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
	public FrameViewImpRec(String title,MainFrame parentFrame) {
		super(title, true, true, true, true);
		
//		setClosable(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
addInternalFrameListener(new InternalFrameListener() {
	
	@Override
	public void internalFrameOpened(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Opened");
	}
	
	@Override
	public void internalFrameIconified(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("internalFrameIconified");
	}
	
	@Override
	public void internalFrameDeiconified(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void internalFrameDeactivated(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("internalFrameDeactivated");
	}
	
	@Override
	public void internalFrameClosing(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
//		parentFrame.minimumSize();
		parentFrame.repaint();
		hide();
	}
	
	@Override
	public void internalFrameClosed(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("internalFrameClosed");
	}
	
	@Override
	public void internalFrameActivated(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("internalFrameActivated");
	}
}); 

		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		// setTitle(title);

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
