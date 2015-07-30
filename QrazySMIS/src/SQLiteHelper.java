import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;

/**
 * 
 */

/**
 * @author qiaojianhui ����sqlite���ݿ�Ĺ�����
 */
public class SQLiteHelper {
	// ����ʵ������
	public static SQLiteHelper instance;

	private Connection connection;

	private Statement statement;

	/**
	 * ����ʵ��
	 * 
	 * @param dbFile
	 *            ���ݿ��ļ�·��
	 * @return �������ݿ�ʵ��
	 * @throws Exception
	 */
	public static SQLiteHelper getInstance(String dbFile) throws Exception {
		if (instance == null)
			instance = new SQLiteHelper(dbFile);

		return instance;
	}

	/**
	 * ʵ���������õ���ģʽ�����캯��Ϊ˽��
	 * 
	 * @throws Exception
	 *             �����org.sqlite.JDBC�����ڣ�������·�����ԣ�������Ƹ�쳣
	 * @param dbFile
	 *            ���ݿ��ļ�·��
	 */
	private SQLiteHelper(String dbFile) throws Exception {
		// ������
		Class.forName("org.sqlite.JDBC");
		// �������Ӷ���
		connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);

		// ����statement����
		statement = connection.createStatement();
		statement.setQueryTimeout(30); // set timeout to 30 sec.
	}

	/**
	 * ��ȡ���Ӷ���
	 * 
	 * @return ����Connection����
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * ��ȡStatement����
	 * 
	 * @return ����Statement����
	 */
	public Statement getStatement() {
		return statement;
	}

	/**
	 * �ر����ݿ�����
	 */
	public void closeConnection() {
		if (connection != null) {
			try {
				if (!connection.isClosed())
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���´����ݿ����Ӷ���
	 */
	public void openConnection() {

	}

	/**
	 * ִ�в�ѯ�����ؽ����
	 * @param sql ��ѯ���
	 * @return ����ResultSet����  
	 */
	public ResultSet excuteQuery(String sql){
		ResultSet rs=null;
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * ��ȡ��ѯ���
	 * @param sql ��ѯ���
	 * @return �����б��϶���
	 * @throws SQLException �׳�SQLException���͵��쳣
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<ArrayList> getQueryArray(String sql) throws SQLException {
		Vector colum = new Vector();
		Vector rows = new Vector();
		ResultSet rs = excuteQuery(sql);

		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			colum.addElement(rsmd.getColumnName(i));
		}

		while (rs.next()) {
			Vector currow = new Vector();
			for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
				currow.addElement(rs.getString(i));
			}
			rows.addElement(currow);
		}
		return null;

	}

	/**
	 * ִ�����ݿ���²���
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void executeUpdate(String sql) {
		try {
			statement.executeUpdate(sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 *  ���ݲ�ѯ 
	 * @param sql  ��ѯ��sql���
	 * @return ����һ��JTable������������չʾ
	 * <br>sql = "select id as ���,filename as �ļ���,fullpath as ·��,time as ����ʱ�� from t_import_files";</br>
	 */
	public JTable queryTable(String sql){
	 try{  
		 Vector<String> colum = new Vector<String>();
		Vector<Vector<String>> rows = new Vector<Vector<String>>();

		// ��ѯ 
		java.sql.ResultSet rs = excuteQuery(sql);

		java.sql.ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); ++i)
			colum.addElement(rsmd.getColumnName(i));
		while (rs.next()) {
			Vector<String> currow = new Vector<String>();
			for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
				currow.addElement(rs.getString(i));
			}
			rows.addElement(currow);
		}
		return new JTable(rows, colum);
	 }catch(Exception e)
	 {
		 e.printStackTrace();
		 return null;
	 }
	}
}
