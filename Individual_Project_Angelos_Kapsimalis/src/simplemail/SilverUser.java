package simplemail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class SilverUser extends BronzeUser 
{
	// CONSTRUCTORS ========================================================================================
	public SilverUser() {}
	
	public SilverUser(String username, String password, String usertype, String status)
	{
		super(username, password, usertype, status) ;
	}
	
	final static JDialog dialog = new JDialog();
	
	// METHODS ============================================================================================
	public void editSentMessage(SilverUser user) 
	{
		dialog.setAlwaysOnTop(true); 
		try
		{	String Id = null;
			while(Id==null)
			{
			Id = JOptionPane.showInputDialog(dialog,"Enter the ID of the message you want to edit\n(Invalid input will return you to main menu)");
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
			if (Count == 1)								// checking if input is valid
			{
				try
				{
					String text=null;
					while(text==null || text.length() > 250)
					{
						text = JOptionPane.showInputDialog(dialog,"Type the new edited text") ;
					}
					String editedText = text.replaceAll("[\"']", "`");
					
					Date date = new Date();
					SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					String bSql = "UPDATE messages\r\n" + 
							"SET date = '" + dateFormat.format(date) + "', text = '" + editedText + "'\r\n" + 
							"WHERE message_id = '" + editedID +"' ;" ;
				
					db.executeQuery(bSql) ;
					connection.close(); 
					ImageIcon editMessageIcon = new ImageIcon(SilverUser.class.getResource("/images/message_edit.png"));
					JOptionPane.showMessageDialog(dialog, "Message edited and sent again" , null, JOptionPane.PLAIN_MESSAGE,editMessageIcon);
					
					File logFile = new File("all_message_log.txt");
					try
					{
						logFile.createNewFile();
						Path logFilePath = Paths.get("all_message_log.txt");
						Files.write(logFilePath, Arrays.asList(" Message with ID : " + editedID + " and SENDER : " + user.getUsername() + ""
								+ " was edited on DATE : " + dateFormat.format(date) + "." , "The new text is TEXT : " + editedText, ""),
								StandardCharsets.UTF_8, StandardOpenOption.APPEND);
					}
					catch(IOException e) {}
				} 	
				catch (SQLException e)
				{	
					e.printStackTrace();
					System.out.println("Can't send message");
				}
			}
			else
			{
				ImageIcon errorIDIcon = new ImageIcon(SilverUser.class.getResource("/images/error_stop.png"));
				JOptionPane.showMessageDialog(dialog, "Invalid message ID", null, JOptionPane.ERROR_MESSAGE,errorIDIcon);
			} 	
			connection.close();
		}
		catch (SQLException e)
		{	
			e.printStackTrace();
			System.out.println("Can't edit sent messages");
		}
	}
}
