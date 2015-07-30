import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteSample
{
  public  ResultSet ExcuteQuery() throws Exception  
  { 
	
    // load the sqlite-JDBC driver using the current class loader
    Class.forName("org.sqlite.JDBC");

    Connection connection = null;
    try{
    
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      statement.executeUpdate("drop table if exists person");
      statement.executeUpdate("create table person (id integer, name string)");
   //   statement.executeUpdate("insert into person values(1, 'leo')");
      //statement.executeUpdate("insert into person values(2, 'yui')");
      for(int i=0;i<50;i++){
    	  statement.executeUpdate("insert into person values("+ i+", 'item"+String.valueOf(i)+"')");
      }
      ResultSet rs = statement.executeQuery("select * from person");
  return rs;
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory", 
      // it probably means no database file is found
      System.err.println(e.getMessage());
      return null;
    }
    finally
    {
//      try
//      {
//        if(connection != null)
//          connection.close();
//      }
//      catch(SQLException e)
//      {
//        // connection close failed.
//        System.err.println(e);
//      }
    }
  }
//  public static void main(String[] args) throws Exception {
//	new  SqliteSample().ExcuteQuery();
//	
//  }
}