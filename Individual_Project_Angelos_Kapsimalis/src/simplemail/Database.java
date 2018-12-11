package simplemail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database
{
	public static final String driver = "jdbc:mysql://" ;
	public static final String host = "localhost" ;
	public static final String port = "3306" ;
	public static final String DB = "simple_message" ;
	public static final String user = "root" ;
	public static final String password = "admin" ; 
	public static final String noSsl = "?useSSL=false" ; 
	
	
	static Connection connection ;
	DriverManager dm ;
	Statement stm ;
	
	public Connection connectToDatabase()
	{
		try 
		{
			connection = DriverManager.getConnection(driver + host + ":" + port + "/" + DB+ noSsl, user, password) ;
			return connection ;
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
			System.out.println("something wrong with DB!");
			return null ;
		}
	}
	
	
	public ResultSet returnResultSet (String query)
	{
		try
		{
			Statement stmt = connection.createStatement() ;
			ResultSet rs = stmt.executeQuery(query);
			return rs ;	
		}
		catch (SQLException e) 
		{	
			e.printStackTrace();
			return null ;
		
		}
	}
		

	public int executeQuery(String sql)
	{
		try 
		{
			stm = connection.createStatement() ;
			return stm.executeUpdate(sql) ;
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			System.out.println("Defintely you did something wrong with executeQuery!");
			return -1;
		}
	}
	
	public boolean checkifUsernameExists(String username)
	{
		boolean flag = true;
		try
		{	
			String aSql = "SELECT username, COUNT(username) FROM users \r\n" + 
							"WHERE username = '" + username + "' ;" ;
				
			Database db = new Database() ;
			connection = db.connectToDatabase() ;
			ResultSet rs = db.returnResultSet(aSql) ;
			int Count = 0 ;
				
			while (rs.next()) 
			{
				Count = rs.getInt("COUNT(username)");
			}
			if (Count == 1)							// checking if user exists
			{
				return flag ;
			}
			else
			{
				return !flag;
			}
		}
		catch (SQLException e)
		{	
			e.printStackTrace();
			return false ;
		}
	}
}
