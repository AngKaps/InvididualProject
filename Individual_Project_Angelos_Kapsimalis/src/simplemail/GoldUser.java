package simplemail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class GoldUser extends SilverUser
{
	// CONSTRUCTORS ========================================================================================
	public GoldUser() {}
	
	public GoldUser(String username, String password, String usertype, String status)
	{
		super(username, password, usertype, status) ;
	}
	
	final static JDialog dialog = new JDialog();
	
	// METHODS =============================================================================================
	public void deleteSentMessage(BronzeUser user) 
	{
		dialog.setAlwaysOnTop(true); 
		try
		{	String Id=null;
			while(Id==null)
			{
			Id = JOptionPane.showInputDialog(dialog,"Enter the ID of the message you want to delete\n(Invalid input will return you to main menu)");
			}
			String editedID = Id.replaceAll("[\"']", "`");
			
			String aSql = "SELECT * , COUNT(message_id) FROM messages\r\n" + 
					"WHERE message_id = '" + editedID + "' AND sender = '" + user.getUsername() + "' ;";
				
			Database db = new Database() ;
			Connection connection = db.connectToDatabase() ;
			ResultSet rs = db.returnResultSet(aSql) ;
			int Count = 0 ;
					
			while (rs.next()) 
			{
				Count = rs.getInt("COUNT(message_id)");
			}
			if (Count == 1)								// checking if id is valid
			{
				try
				{
					String bSql = "DELETE FROM messages\r\n" + 
							"WHERE message_id = '" + editedID + "' ;" ;
				
					db.executeQuery(bSql) ;
					connection.close(); 
					ImageIcon deleteMessageIcon = new ImageIcon(GoldUser.class.getResource("/images/delete_message.png"));
					JOptionPane.showMessageDialog(dialog, "Your message has been deleted" , null, JOptionPane.PLAIN_MESSAGE,deleteMessageIcon);
				} 	
				catch (SQLException e)
				{	
					e.printStackTrace();
					System.out.println("Can't delete  sent message");
				}
			}
			else
			{	ImageIcon errorIDIcon = new ImageIcon(GoldUser.class.getResource("/images/error_stop.png"));
				JOptionPane.showMessageDialog(dialog, "WRONG MESSAGE ID", "ERROR, CAN'T DELETE MESSAGE", JOptionPane.WARNING_MESSAGE,errorIDIcon);
			} 	
			connection.close();
		}
		catch (SQLException e)
		{	
			e.printStackTrace();
			System.out.println("Can't delete messages");
		}
	}
}
