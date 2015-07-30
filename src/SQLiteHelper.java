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
 * @author qiaojianhui 访问sqlite数据库的工具类
 */
public class SQLiteHelper {
	// 定义实例对象
	public static SQLiteHelper instance;

	private Connection connection;

	private Statement statement;

	/**
	 * 返回实例
	 * 
	 * @param dbFile
	 *            数据库文件路径
	 * @return 返回数据库实例
	 * @throws Exception
	 */
	public static SQLiteHelper getInstance(String dbFile) throws Exception {
		if (instance == null)
			instance = new SQLiteHelper(dbFile);

		return instance;
	}

	/**
	 * 实例化，采用单例模式，构造函数为私有
	 * 
	 * @throws Exception
	 *             如果类org.sqlite.JDBC不存在，或引用路径不对，对外招聘异常
	 * @param dbFile
	 *            数据库文件路径
	 */
	private SQLiteHelper(String dbFile) throws Exception {
		// 引用类
		Class.forName("org.sqlite.JDBC");
		// 创建连接对象
		connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);

		// 创建statement对象
		statement = connection.createStatement();
		statement.setQueryTimeout(30); // set timeout to 30 sec.
	}

	/**
	 * 获取连接对象
	 * 
	 * @return 返回Connection对象
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * 获取Statement对象
	 * 
	 * @return 返回Statement对象
	 */
	public Statement getStatement() {
		return statement;
	}

	/**
	 * 关闭数据库连接
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
	 * 重新打开数据库连接对象
	 */
	public void openConnection() {

	}

	/**
	 * 执行查询，返回结果集
	 * @param sql 查询语句
	 * @return 返回ResultSet对象  
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
	 * 获取查询结果
	 * @param sql 查询语句
	 * @return 返回列表集合对象
	 * @throws SQLException 抛出SQLException类型的异常
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
	 * 执行数据库更新操作
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
	 *  数据查询 
	 * @param sql  查询的sql语句
	 * @return 返回一个JTable对象，用于数据展示
	 * <br>sql = "select id as 序号,filename as 文件名,fullpath as 路径,time as 导入时间 from t_import_files";</br>
	 */
	public JTable queryTable(String sql){
	 try{  
		 Vector<String> colum = new Vector<String>();
		Vector<Vector<String>> rows = new Vector<Vector<String>>();

		// 查询 
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
