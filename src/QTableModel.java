import javax.swing.table.AbstractTableModel;

public class QTableModel extends AbstractTableModel {

	private Object[][] dataset;
	private String[] columnNames;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5124812040243690275L;

//	/**
//	 * 构造函数
//	 */
//	public QTableModel() {
//
//	}

	/**
	 * 构造函数
	 * 
	 * @param colNames
	 *            列名
	 */
	public QTableModel(String[] colNames) {
		columnNames = colNames;
	}

	/**
	 * 数据集
	 * @param colNames 列名
	 * @param data 数据集合
	 */
	public QTableModel(String[] colNames, Object[][] data) {
		columnNames = colNames;
		dataset = data;
	}

	@Override
	public int getColumnCount() {
		if (columnNames != null)
			return columnNames.length;
		else
			return 0;
	}

	@Override
	public int getRowCount() {
		if (dataset != null)
			return dataset.length;
		else
			return 0;
	}
	
	/**
	 * 返回列名
	 */
	public String getColumnName(int col) {
		if (columnNames != null)
			return columnNames[col];
		else
			return "";
	}
 
	@Override
	public Object getValueAt(int row, int col) {
		if (dataset != null)
			return dataset[row][col];
		else
			return null;
	}
    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) { 
        if (col < 2) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) { 
        dataset[row][col] = value;
        fireTableCellUpdated(row, col); 
    }

}
