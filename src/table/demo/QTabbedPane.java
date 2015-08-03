package table.demo;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;

/**
 * 
 * @ClassName: YiTabbedPane
 * @Description: ��չJTabbedPane,���ӹرա��Ҽ��˵�������ͼ��ʾ����
 *
 */
public class QTabbedPane extends JTabbedPane implements MouseListener {  

	private static final long serialVersionUID = 2985098138273905480L;
	
	/**
	 * ����ͼ���Ŵ�С
	 */
	private double scaleRatio = 0.3d; 
	
	private HashMap<String, Component> maps = new HashMap<String, Component>();  
	
	public QTabbedPane() {
		super();
		addMouseListener(this);
	}

	public QTabbedPane(int tabPlacement, int tabLayoutPolicy) {
		super(tabPlacement, tabLayoutPolicy);
		addMouseListener(this);
	}

	public QTabbedPane(int tabPlacement) {
		super(tabPlacement);
		addMouseListener(this);
	}

	@Override
	public void addTab(String title, Icon icon, Component component, String tip) {
		super.addTab(title, icon, component, tip);
	}

	@Override
	public void addTab(String title, Icon icon, Component component) {
		super.addTab(title, new CloseTabIcon(icon), component);
	}

	@Override
	public void addTab(String title, Component component) {
		addTab(title, null, component);
	}
	
	public void insertTab(String title, Icon icon, Component component, String tip, int index) {  
        tip = "tab" + component.hashCode();  
        maps.put(tip, component);  
        super.insertTab(title, icon, component, tip, index);  
    }  
    public void removeTabAt(int index) {  
        Component component = getComponentAt(index);  
        maps.remove("tab" + component.hashCode());  
        super.removeTabAt(index);  
    }  
    public JToolTip createToolTip() {  
        ThumbnailToolTip tooltip = new ThumbnailToolTip();  
        tooltip.setComponent(this);  
        return tooltip;  
    } 

	@Override
	public void mouseClicked(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)) {
			showPopupMenu(e);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//�ر�ͼ��ֻ��Ӧ���
		if(SwingUtilities.isLeftMouseButton(e)) {
			int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());  
	        if (tabNumber < 0) {
	            return;  
	        }  
	        Rectangle rect = ((CloseTabIcon) getIconAt(tabNumber)).getBounds();  
	        if (rect.contains(e.getX()-rect.getWidth(), e.getY())) {  
	            this.removeTabAt(tabNumber);  
	        }
		} else if(SwingUtilities.isRightMouseButton(e)) {
			showPopupMenu(e);
		}
	}
	
	private void showPopupMenu(final MouseEvent event) {
		
		// �����ǰ�¼����Ҽ��˵��йأ������Ҽ������򵯳��˵�
		if (event.isPopupTrigger()) {
			final int index = ((QTabbedPane)event.getComponent()).getUI().tabForCoordinate(this, event.getX(), event.getY()); 
			final int count = ((QTabbedPane)event.getComponent()).getTabCount(); 
			JPopupMenu pop = new JPopupMenu();
			JMenuItem closeCurrent = new JMenuItem("�رյ�ǰ");
			closeCurrent.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					((QTabbedPane)event.getComponent()).removeTabAt(index);  
				}
			});
			pop.add(closeCurrent);
			
			JMenuItem closeLeft = new JMenuItem("�ر�����ǩ");
			closeLeft.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					for(int j=(index-1);j>=0;j--) {
						((QTabbedPane)event.getComponent()).removeTabAt(j);  
					}
				}
			});
			pop.add(closeLeft);
			
			JMenuItem closeRight = new JMenuItem("�ر��Ҳ��ǩ");
			closeRight.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					for(int j=(count-1);j>index;j--) {
						((QTabbedPane)event.getComponent()).removeTabAt(j);  
					}
				}
			});
			pop.add(closeRight);
			
			pop.show(event.getComponent(), event.getX(), event.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	/**
	 * 
	 * @ClassName: ImageToolTip
	 * @Description: ����ͼ
	 * @author QQ tkts@qq.com 
	 *
	 */
	class ThumbnailToolTip extends JToolTip {  
		private static final long serialVersionUID = -7317621488447910306L;
		public Dimension getPreferredSize() {  
            String tip = getTipText();  
            Component component = maps.get(tip);  
            if (component != null) {  
                return new Dimension((int) (getScaleRatio() * component.getWidth()), (int) (getScaleRatio() * component.getHeight()));  
            } else {  
                return super.getPreferredSize();  
            }  
        }  
        public void paintComponent(Graphics g) {  
            String tip = getTipText();  
            Component component = maps.get(tip);  
            if (component instanceof JComponent) {  
                JComponent jcomponent = (JComponent) component;  
                Graphics2D g2d = (Graphics2D) g;  
                AffineTransform at = g2d.getTransform();  
                g2d.transform(AffineTransform.getScaleInstance(getScaleRatio(), getScaleRatio()));  
                ArrayList<JComponent> dbcomponents = new ArrayList<JComponent>();  
                updateDoubleBuffered(jcomponent, dbcomponents);  
                jcomponent.paint(g);  
                resetDoubleBuffered(dbcomponents);  
                g2d.setTransform(at);  
            }  
        }  
        private void updateDoubleBuffered(JComponent component, ArrayList<JComponent> dbcomponents) {  
            if (component.isDoubleBuffered()) {  
                dbcomponents.add(component);  
                component.setDoubleBuffered(false);  
            }  
            for (int i = 0; i < component.getComponentCount(); i++) {  
                Component c = component.getComponent(i);  
                if (c instanceof JComponent) {  
                    updateDoubleBuffered((JComponent) c, dbcomponents);  
                }  
            }  
        }  
        private void resetDoubleBuffered(ArrayList<JComponent> dbcomponents) {  
            for (JComponent component : dbcomponents) {  
                component.setDoubleBuffered(true);  
            }  
        }  
    }  
    public double getScaleRatio() {  
        return scaleRatio;  
    }  
    public void setScaleRatio(double scaleRatio) {  
        this.scaleRatio = scaleRatio;  
    }  

}
class CloseTabIcon implements Icon {  
    private int x_pos;  
    private int y_pos;  
    private int width;  
    private int height;  
    private Icon fileIcon;  
    public CloseTabIcon(Icon fileIcon) {  
        this.fileIcon = fileIcon;  
        width = 16;  
        height = 16;  
    }  
    public void paintIcon(Component c, Graphics g, int x, int y) {  
        this.x_pos = x;  
        this.y_pos = y;  
        int y_p = y + 2;  
        /*Color col = g.getColor();  
        g.setColor(Color.black);  
        g.drawLine(x + 1, y_p, x + 12, y_p);  
        g.drawLine(x + 1, y_p + 13, x + 12, y_p + 13);  
        g.drawLine(x, y_p + 1, x, y_p + 12);  
        g.drawLine(x + 13, y_p + 1, x + 13, y_p + 12);  
        g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);  
        g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);  
        g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);  
        g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);  
        g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);  
        g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);  
        g.setColor(col);  */
        if (fileIcon != null) {  
            fileIcon.paintIcon(c, g, x + width, y_p);  
        }  
    }  
    public int getIconWidth() {  
        return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);  
    }  
    public int getIconHeight() {  
        return height;  
    }  
    public Rectangle getBounds() {  
        return new Rectangle(x_pos, y_pos, width, height);  
    }  
}  
