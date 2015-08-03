package table.demo;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TestQTab  extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 198076590749746117L;

	public TestQTab(){
		QTabbedPane qt=new QTabbedPane();
		for(int i=0;i<10;i++){
		qt.addTab("Tab" +  i, new JLabel());
		}
		add(qt);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestQTab frame = new TestQTab();
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	  
	        frame.pack();
	        frame.setVisible(true);
	}

}
