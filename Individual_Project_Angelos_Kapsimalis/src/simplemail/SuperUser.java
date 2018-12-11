package simplemail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Formatter;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class SuperUser extends GoldUser 
{
	// CONSTRUCTORS ========================================================================================
	public SuperUser() {}
	
	public SuperUser(String username, String password, String usertype, String status)
	{
		super(username, password, usertype, status) ;
	}
	
	final static JDialog dialog = new JDialog();
	
	// METHODS ============================================================================================
	// METHOD FOR CHECKING ALL USERS =====================
	public void checkAllUsers(BronzeUser user) 
	{
		try
		{	
			String aSql = "SELECT * FROM users ;" ;
				
			Database db = new Database() ;
			Connection connection = db.connectToDatabase() ;
			ResultSet rs = db.returnResultSet(aSql) ;
			formatOutput2();		
			
			while (rs.next()) 
			{
				int ID = rs.getInt("user_id") ;
				String Username = rs.getString("username");
				String Type = rs.getString("user_type");
				String Status = rs.getString("status") ;
				
				Formatter f4 = new Formatter();
				f4.format("|%-5s|%-30s|%-10s|%-10s|", ID , Username ,Type,Status);
				System.out.println(f4);
			}
				Formatter f3 = new Formatter();
				f3.format("|%-5s|%-30s|%-10s|%-10s|", "_____", "______________________________" ,"__________","__________");
				System.out.println(f3);	
				
				connection.close();
		}
		catch (SQLException e)
		{	
			e.printStackTrace();
			System.out.println("Can't check all users");
		}
	}
	
	// METHOD FOR CHECKING A SPECIFIC USER =====================
	public void checkUser(BronzeUser user) 
	{
		dialog.setAlwaysOnTop(true);
		boolean flag = true;
		while(flag) 
		{
		String Name=null;
		while(Name==null)
		{
			Name = JOptionPane.showInputDialog(dialog,"Enter the name of the user you want to check");
		}
		String editedName = Name.replaceAll("[\"']", "`");
		
		Database db = new Database() ;
		
		if (db.checkifUsernameExists(editedName))
		{
			flag=false;
			try
			{
			String aSql = "SELECT * FROM users\r\n" + 
						"WHERE username = '" + editedName + "' ;" ;
			
			Connection connection = db.connectToDatabase() ;
			ResultSet rs = db.returnResultSet(aSql) ;
					
			rs.next();
			int ID = rs.getInt("user_id") ;
			String Username = rs.getString("username");
			String Type = rs.getString("user_type");
			String Status = rs.getString("status");
		
			ImageIcon userInfoIcon = new ImageIcon(SuperUser.class.getResource("/images/user_info.jpeg"));
			JOptionPane.showMessageDialog(dialog, "User ID : " + ID + "\nUsername : " + Username +
					"\nUser access : " + Type + "\nStatus : " + Status,  null, JOptionPane.PLAIN_MESSAGE,userInfoIcon);	
			
			connection.close() ;
			}
			catch (Exception e)
			{	
				e.printStackTrace();
				System.out.println("Can't check user");
			}
		}
		else
		{	ImageIcon errorIcon = new ImageIcon(SuperUser.class.getResource("/images/error_stop.png"));
			JOptionPane.showMessageDialog(dialog, "INVALID USER NAME", null, JOptionPane.WARNING_MESSAGE,errorIcon);
			flag=true;
		}
	}
	}
	
	// METHOD TO DEACTIVATE A USER(BAN) =====================
	public void deactivateUser(BronzeUser user) 
	{
		dialog.setAlwaysOnTop(true);
		boolean flag = true;
		while(flag) 
		{
		try
		{
			String Name=null;
			while(Name==null || Name.equals("admin"))
			{
				Name = JOptionPane.showInputDialog(dialog,"Enter the name of the user you want to ban / deactivate");
			}
			String editedName = Name.replaceAll("[\"']", "`");
			Database db = new Database() ;
			Connection connection = db.connectToDatabase() ;
			
			if (db.checkifUsernameExists(editedName))
			{
				flag= false;
				String aSql = "UPDATE users\r\n" + 
							"SET status = 'INACTIVE'\r\n" + 
							"WHERE username = '" + editedName + "' ;" ;
	
				db.executeQuery(aSql) ;
				ImageIcon banUserIcon = new ImageIcon(SuperUser.class.getResource("/images/ban_user.png"));
				JOptionPane.showMessageDialog(dialog, "User " + editedName + " is now banned / deactivated" , null, JOptionPane.PLAIN_MESSAGE,banUserIcon);
			} 	
			else
			{
				ImageIcon errorIcon = new ImageIcon(SuperUser.class.getResource("/images/error_stop.png"));
				JOptionPane.showMessageDialog(dialog, "Invalid Username", null, JOptionPane.WARNING_MESSAGE,errorIcon);
				flag=true;
			}
			connection.close(); 
		}
		catch (SQLException e)
		{	
			e.printStackTrace();
			System.out.println("Can't delete");
		}
		}
	}
	
	// METHOD FOR REACTIVATING A BANNED USER =====================
	public void reactivateUser(BronzeUser user) 
	{
		dialog.setAlwaysOnTop(true);
		boolean flag = true;
		while(flag) 
		{
		try
		{
			String Name=null;
			while(Name==null || Name.equals("admin"))
			{
				Name = JOptionPane.showInputDialog(dialog,"Enter the name of the user you want to reactivate");
			}
			String editedName = Name.replaceAll("[\"']", "`");
			Database db = new Database() ;
			Connection connection = db.connectToDatabase() ;
			
			if (db.checkifUsernameExists(editedName))
			{
				flag= false;
				String aSql = "UPDATE users\r\n" + 
							"SET status = 'ACTIVE'\r\n" + 
							"WHERE username = '" + editedName + "' ;" ;
	
				db.executeQuery(aSql) ;
				ImageIcon reactivateUserIcon = new ImageIcon(SuperUser.class.getResource("/images/reactivate_user.jpeg"));
				JOptionPane.showMessageDialog(dialog, "USER " + editedName + " IS NOW ACTIVE AGAIN" , null, JOptionPane.PLAIN_MESSAGE,reactivateUserIcon);
			} 	
			else
			{
				ImageIcon errorIcon = new ImageIcon(SuperUser.class.getResource("/images/error_stop.png"));
				JOptionPane.showMessageDialog(dialog, "INVALID USER NAME", "DEACTIVATION FAILED", JOptionPane.WARNING_MESSAGE,errorIcon);
				flag=true;
			}
			connection.close(); 
		}
		catch (SQLException e)
		{	
			e.printStackTrace();
			System.out.println("Can't delete");
		}
		}
	}
	
	// METHOD FOR PERMANENTLY DELETING A USER AND HIS MESSAGES =====================
	public void deleteUser(BronzeUser user) 
	{
		dialog.setAlwaysOnTop(true);
		boolean flag = true;
		while(flag) 
		{
			try
			{	String Name = null;
				while(Name==null || Name.equals("admin"))
				{
					Name = JOptionPane.showInputDialog(dialog,"Enter the name of the user you want to delete");
				}
					String editedName = Name.replaceAll("[\"']", "`");
					Database db = new Database() ;
					Connection connection = db.connectToDatabase() ;
				
					if (db.checkifUsernameExists(editedName))
					{
						flag=false;
						String aSql = "DELETE FROM users\r\n" + 
								"WHERE username = '" + editedName + "' ;" ;
			
						db.executeQuery(aSql) ;
						ImageIcon deletedUserIcon = new ImageIcon(SuperUser.class.getResource("/images/user_delete.png"));
						JOptionPane.showMessageDialog(dialog, "User " + editedName + " has been deleted along with transacted messages" , null, JOptionPane.PLAIN_MESSAGE,deletedUserIcon);
					} 	
					else
					{	
						ImageIcon errorIcon = new ImageIcon(SuperUser.class.getResource("/images/error_stop.png"));
						JOptionPane.showMessageDialog(dialog, "Invalid username", null, JOptionPane.WARNING_MESSAGE,errorIcon);
					}
					connection.close(); 
				}
				catch (SQLException e)
				{	
					e.printStackTrace();
					System.out.println("Can't delete");
				}
			}
		}	
	
	// METHOD FOR CREATING A NEW USER =====================
	public void createUser(BronzeUser user) 
	{
		dialog.setAlwaysOnTop(true);
		boolean flag = true;
		while(flag) 
		{
			try
			{
				Database db = new Database() ;
				Connection connection = db.connectToDatabase() ;
				
				String Name=null;
				while(Name==null || Name.length()>25)
				{
					Name = JOptionPane.showInputDialog(dialog,"Set a unique username for the new user\n(Maximum 25 characters)");
				}
				String editedName = Name.replaceAll("[\"']", "`");
				
				if (!db.checkifUsernameExists(editedName))
				{
					flag = false;
					String Password=null;
					while(Password==null || Password.length()>15)
					{
						Password = JOptionPane.showInputDialog(dialog,"Set a password for the new user\n(Maximum 15 characters)");
					}
					String editedPassword = Password.replaceAll("[\"']", "`");
					
					boolean flag1= true;
					while(flag1)
					{
						String Type = null;
						while(Type==null) 
						{
							Type = JOptionPane.showInputDialog(dialog,"Select the usertype / access of the new user"
							+ "\n[1] : Bronze user\n[2] : Silver user\n[3] : Gold user");
						}
						switch(Type)
						{
							case "1" :
								String bSql = "INSERT INTO users (user_id, username, password, user_type, status)\r\n" + 
										"VALUES (null, '" + editedName + "','" + editedPassword + "', 'bronze', 'ACTIVE') ;" ;
								db.executeQuery(bSql) ;
								flag1 = false;
								break;
							case "2" :
								String sSql = "INSERT INTO users (user_id, username, password, user_type, status)\r\n" + 
										"VALUES (null, '" + editedName + "','" + editedPassword + "', 'silver', 'ACTIVE') ;" ;
								db.executeQuery(sSql) ;
								flag1 = false;
								break;
							case "3" :
								String gSql = "INSERT INTO users (user_id, username, password, user_type, status)\r\n" + 
										"VALUES (null, '" + editedName + "','" + editedPassword + "', 'gold', 'ACTIVE') ;" ;
								db.executeQuery(gSql) ;
								flag1 = false;
								break;
							default :
								flag1 = true;
						}
					}
					ImageIcon createUserIcon = new ImageIcon(SuperUser.class.getResource("/images/create_user.png"));
					JOptionPane.showMessageDialog(dialog, "User " + Name + " was created" , null, JOptionPane.PLAIN_MESSAGE,createUserIcon);
					connection.close();
					break;
				}
			}
			catch (SQLException e)
			{	
				e.printStackTrace();
				System.out.println("Can't create user");
			}
		}
	}
	
	

	// METHOD FOR EDITING USER TYPE =====================
	public void editUser(BronzeUser user) 
	{
		dialog.setAlwaysOnTop(true);
		boolean flag=true;
		while(flag)
		{
			try
			{
			String Name=null;
			while(Name==null || Name.equals("admin"))
			{
				Name = JOptionPane.showInputDialog(dialog,"Enter the name of the user you want to edit");
			}
				String editedName = Name.replaceAll("[\"']", "`");
				Database db = new Database() ;
				Connection connection = db.connectToDatabase() ;
				
				if (db.checkifUsernameExists(editedName))
				{
					flag= false;
						String Type = null;
						while(Type==null || !(Type.equals("1")) && !(Type.equals("2")) && !(Type.equals("3"))) 
						{	
							Type = JOptionPane.showInputDialog(dialog,"Select the new usertype / access"
							+ "\n[1] : Bronze user\n[2 : Silver user\n[3] : Gold user");
						}
							switch(Type)
							{
								case "1" :
									String bSql = "UPDATE users\r\n"  + 
													"SET username = '" +editedName + "', user_type = 'bronze'\r\n" + 
													"WHERE username = '" + editedName + "' ;" ;
									db.executeQuery(bSql) ;
									break;
								case "2" :
									String sSql = "UPDATE users\r\n"  + 
											"SET username = '" +editedName + "', user_type = 'silver'\r\n" + 
											"WHERE username = '" + editedName + "' ;" ;
									db.executeQuery(sSql) ;
									break;
								case "3" :
									String gSql = "UPDATE users\r\n"  + 
											"SET username = '" +editedName + "', user_type = 'gold'\r\n" + 
											"WHERE username = '" + editedName + "' ;" ;
									db.executeQuery(gSql) ;
									break;
								default :
									ImageIcon errorIcon = new ImageIcon(SuperUser.class.getResource("/images/error_stop.png"));
									JOptionPane.showMessageDialog(dialog, "Invalid input", null, JOptionPane.WARNING_MESSAGE,errorIcon);
									break;
							}
							ImageIcon editUserIcon = new ImageIcon(SuperUser.class.getResource("/images/user_edit.png"));
							JOptionPane.showMessageDialog(dialog, "User " + Name + " is now edited" , null, JOptionPane.PLAIN_MESSAGE,editUserIcon);	
							connection.close(); 
					}
					else
					{
						ImageIcon errorIcon = new ImageIcon(SuperUser.class.getResource("/images/error_stop.png"));
						JOptionPane.showMessageDialog(dialog, "Invalid username", null, JOptionPane.WARNING_MESSAGE,errorIcon);
					}
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					System.out.println("Can't create user");
				}
			}	
		}
		
			
					
	public void formatOutput2()
	{
		Formatter f1 = new Formatter();
		Formatter f2 = new Formatter();
		Formatter f3 = new Formatter();
		
		f1.format("%-5s%-30s%-10s%-10s", " _____", " ______________________________" ," __________"," __________");
		f2.format("|%-5s|%-30s|%-10s|%-10s|", "ID" , "USERNAME" ,"USERTYPE","STATUS");
		f3.format("|%-5s|%-30s|%-10s|%-10s|", "_____", "______________________________" ,"__________","__________");
		
		System.out.println(f1);
		System.out.println(f2);
		System.out.println(f3);	
	}


}

