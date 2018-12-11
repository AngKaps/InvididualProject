package simplemail;


import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class SimpleMessageMain {

	final static JDialog dialog = new JDialog();
	
	public static BronzeUser loginUser()
	{
		dialog.setAlwaysOnTop(true); 
		ImageIcon welcomeIcon = new ImageIcon(SimpleMessageMain.class.getResource("/images/hello.png"));
		JOptionPane.showMessageDialog(dialog, "Hello and welcome to mySimpleMessage Application!\n\n"
				+ "                     Press ok to login...",null,JOptionPane.PLAIN_MESSAGE,welcomeIcon);
		BronzeUser user = null ;
		Database db = new Database() ;
		Connection connection = db.connectToDatabase() ;
		
		boolean flag = true;
		while(flag)
		{
			String username = null;
			String password = null;
			while(username==null || password==null)
			{
				username = JOptionPane.showInputDialog(dialog,"Enter your Username") ;
				password = JOptionPane.showInputDialog(dialog,"Enter your Password") ;
			}
			String editedUsername = username.replaceAll("[\"']", "`");
			String editedPassword = password.replaceAll("[\"']", "`");
			
			try
			{
				String aSql = "SELECT *,  COUNT(username) FROM users \r\n" + 
						"WHERE username = '" + editedUsername + "' AND password = '" + editedPassword + "' AND status = 'ACTIVE' ;";
		
				ResultSet rs = db.returnResultSet(aSql) ;
				
				String Username = "" ;
				String Password = "" ;
				String Type = "" ;
				String Status = "";
				int Count = 0 ;
				
				while (rs.next()) 
				{
					Username = rs.getString("username");
					Password = rs.getString("password");
					Type = rs.getString("user_type");
					Status = rs.getString("status") ;
					Count = rs.getInt("COUNT(username)") ;	
				}	
					if (Count == 1)
					{
						switch(Type)
						{
						case "bronze":
							ImageIcon bronzeIcon = new ImageIcon(SimpleMessageMain.class.getResource("/images/bronze_user.jpg"));
							JOptionPane.showMessageDialog(dialog, "Welcome " + Username + "!", null, JOptionPane.QUESTION_MESSAGE, bronzeIcon);
							user = new BronzeUser(Username, Password, Type, Status) ;
							flag = false;
							break;
						case "silver":
							ImageIcon silverIcon = new ImageIcon(SimpleMessageMain.class.getResource("/images/silver_user.jpg"));
							JOptionPane.showMessageDialog(dialog, "Welcome " + Username + "!", null, JOptionPane.QUESTION_MESSAGE, silverIcon);
							user = new SilverUser(Username, Password, Type, Status) ;
							flag = false;
							break;
						case "gold":
							ImageIcon goldIcon = new ImageIcon(SimpleMessageMain.class.getResource("/images/gold_user.jpg"));
							JOptionPane.showMessageDialog(dialog, "Welcome " + Username + "!", null, JOptionPane.QUESTION_MESSAGE, goldIcon);
							user = new GoldUser(Username, Password, Type, Status) ;
							flag = false;
							break;
						case "super":
							ImageIcon superIcon = new ImageIcon(SimpleMessageMain.class.getResource("/images/super_user.png"));
							JOptionPane.showMessageDialog(dialog, "Welcome " + Username + "!", null, JOptionPane.QUESTION_MESSAGE, superIcon);
							user = new SuperUser(Username, Password, Type, Status) ;
							flag = false;
							break;
						}	
					}
					else
					{	
						ImageIcon errorIcon = new ImageIcon(SimpleMessageMain.class.getResource("/images/error_stop.png"));
						JOptionPane.showMessageDialog(dialog, "Incorrect Username or Password\n\nPlease try again",null, JOptionPane.ERROR_MESSAGE,errorIcon);
					}	
			}
			catch (SQLException e)
			{	
			//e.printStackTrace();
			System.out.println("There is a problem with login");
			}
				}
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return user;
		
	
	}
	public static void main(String[] args) 
	{
		UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 30));
		UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 20));
		BronzeUser user = loginUser();
		Menu m = new Menu();
		m.mainMenu(user);
		
	}	
}

