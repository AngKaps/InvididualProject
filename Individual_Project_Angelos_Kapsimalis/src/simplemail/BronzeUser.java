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
import java.util.Formatter;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class BronzeUser
{
	// VARIABLES ===========================================================================================
	private String username ;
	private String password ;
	private String usertype ;
	private String status ;
	
	// CONSTRUCTORS ========================================================================================
	public BronzeUser() {}
	
	public BronzeUser(String username, String password, String usertype, String status)
	{
		this.username = username ;
		this.password = password ;
		this.usertype = usertype ;
		this.status = status ;
	}
	
	// SETTERS - GETTERS ===================================================================================
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public String getStatus() {
		return status;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	final static JDialog dialog = new JDialog();
	
	// METHODS ============================================================================================
	// METHODS FOR CHECKING AND READING INBOX =====================
	public void checkInbox(BronzeUser user)
	{ 
		try
		{
			String aSql = "SELECT message_id, sender, receiver, subject, date FROM messages\r\n" + 
							"WHERE receiver = '" + user.getUsername() + "'\r\n" + 
							"ORDER BY DATE asc ;" ;
	
			Database db = new Database() ;
			Connection connection = db.connectToDatabase() ;
			ResultSet rs = db.returnResultSet(aSql) ;
			System.out.println("= = = = = INBOX MESSAGES = = = = =");
			formatOutput();										//formating the upper part of the output
			
			while (rs.next()) 
			{
				int ID = rs.getInt("message_id") ;
				String Sender = rs.getString("sender");
				String Receiver = rs.getString("receiver");
				String Subject = rs.getString("subject");
				String Date = rs.getString("date");
				
				Formatter f4 = new Formatter();
				f4.format("|%-5s|%-30s|%-30s|%-30s|%-30s|", ID , Sender ,Receiver,Subject, Date);
				System.out.println(f4);
			} 	
			Formatter f3 = new Formatter();
			f3.format("|%-5s|%-30s|%-30s|%-30s|%-30s|", "_____" ,"______________________________" ,"______________________________" ,"______________________________" ,"______________________________");
			System.out.println(f3);	
			
			connection.close();
		}
		catch (SQLException e)
		{	
			//e.printStackTrace();
			System.out.println("Can't check inbox");
		}
	}
	
	public void readInbox(BronzeUser user) 
	{
		dialog.setAlwaysOnTop(true);
		try
		{
			String Id = null;
			while(Id==null)
			{
			Id = JOptionPane.showInputDialog(dialog,"Enter the ID of the message you want to read\n(Invalid input will return you to main menu)");
			}
			String editedID = Id.replaceAll("[\"']", "`");
			
			String aSql = "SELECT * , COUNT(message_id) FROM messages\r\n" + 
					"WHERE message_id = '" + editedID + "' AND receiver = '" + user.getUsername() + "' ;";
			
			Database db = new Database() ;
			Connection connection = db.connectToDatabase() ;
			ResultSet rs = db.returnResultSet(aSql) ;
			int Count = 0 ;
					
			rs.next();
			Count = rs.getInt("COUNT(message_id)");
			
			if (Count == 1)											// checking if message exists
			{
				try
				{
					int ID = rs.getInt("message_id") ;
					String Sender = rs.getString("sender");
					String Subject = rs.getString("subject");
					String Date = rs.getString("date");
					String Text = rs.getString("text");
					
					ImageIcon readMessageIcon = new ImageIcon(BronzeUser.class.getResource("/images/message_read.png"));
					JOptionPane.showMessageDialog(dialog, "Message ID : " + ID + "\nSent from : " + Sender +
						"\nSubject : " + Subject + "\nDate Sent : " + Date + "\n\n" + Text, null, JOptionPane.PLAIN_MESSAGE,readMessageIcon);	
					
				}
				catch (Exception e)
				{	
					e.printStackTrace();
					System.out.println("Can't read sent message");
				}
			}
			else
			{
				ImageIcon errorIcon = new ImageIcon(BronzeUser.class.getResource("/images/error_stop.png"));
				JOptionPane.showMessageDialog(dialog, "Invalid message ID", null, JOptionPane.WARNING_MESSAGE,errorIcon);
			}
			connection.close();
		}
		catch (SQLException e)
		{	
			e.printStackTrace();
			System.out.println("Can't check sent messages");
		}
	}

		
	// METHODS FOR CHECKING AND READING SENT MESSAGES =====================	
	public void checkSentMessages(BronzeUser user) 
	{
		try
		{
			String aSql = "SELECT * FROM messages\r\n" + 
							"WHERE sender = '" + user.getUsername() + "' \r\n" + 
							"ORDER BY DATE asc ;" ;
			
			
			
			Database db = new Database() ;
			Connection connection = db.connectToDatabase() ;
			ResultSet rs = db.returnResultSet(aSql) ;
			System.out.println("= = = = = SENT MESSAGES = = = = =");
			formatOutput();												//formating the upper part of the output
			
			while (rs.next()) 
			{
				int ID = rs.getInt("message_id") ;
				String Sender = rs.getString("sender");
				String Date = rs.getString("date");
				String Subject = rs.getString("subject");
				String Receiver = rs.getString("receiver");
		     
				Formatter f4 = new Formatter();
				f4.format("|%-5s|%-30s|%-30s|%-30s|%-30s|", ID , Sender ,Receiver,Subject, Date);
				System.out.println(f4);
			} 	
			Formatter f3 = new Formatter();
			f3.format("|%-5s|%-30s|%-30s|%-30s|%-30s|", "_____", "______________________________" ,"______________________________","______________________________", "______________________________");
			System.out.println(f3);	
			
			connection.close();
		}
		catch (SQLException e)
		{	
			e.printStackTrace();
			System.out.println("Can't check sent messages");
		}
	}
	
	public void readSentMessages(BronzeUser user) 
	{
		dialog.setAlwaysOnTop(true);
		try
		{
			String Id = null;
			while(Id==null)
			{
			Id = JOptionPane.showInputDialog(dialog,"Enter the ID of the message you want to read\n(Invalid input will return you to main menu)");
			}
			String editedID = Id.replaceAll("[\"']", "`");
			
			String aSql = "SELECT * , COUNT(message_id) FROM messages\r\n" + 
							"WHERE message_id = '" + editedID + "' AND sender = '" + user.getUsername() + "' ;" ;
			
			Database db = new Database() ;
			Connection connection = db.connectToDatabase() ;
			ResultSet rs = db.returnResultSet(aSql) ;
			int Count = 0 ;
					
			rs.next();
			Count = rs.getInt("COUNT(message_id)");
			
			if (Count == 1)											// checking if message exists
			{
				try
				{
					int ID = rs.getInt("message_id") ;
					String Receiver = rs.getString("receiver");
					String Subject = rs.getString("subject");
					String Date = rs.getString("date");
					String Text = rs.getString("text");
				
					ImageIcon readMessageIcon = new ImageIcon(BronzeUser.class.getResource("/images/message_read.png"));
					JOptionPane.showMessageDialog(dialog, "Message ID : " + ID + "\nSent to : " + Receiver +
						"\nSubject : " + Subject + "\nDate sent : " + Date + "\n\n" + Text, "READ MESSAGE", JOptionPane.PLAIN_MESSAGE, readMessageIcon);		
				}
				catch (Exception e)
				{	
					e.printStackTrace();
					System.out.println("problem");
				}
			}
			else
			{
				ImageIcon errorIcon = new ImageIcon(BronzeUser.class.getResource("/images/error_stop.png"));
				JOptionPane.showMessageDialog(dialog, "Invalid message ID", null, JOptionPane.ERROR_MESSAGE,errorIcon);
			}
			connection.close();
		}
		catch (SQLException e)
		{	
			e.printStackTrace();
			System.out.println("Can't check sent messages");
		}
	}
	
	
	// METHOD FOR SENDING MESSAGE =====================
	public void sendMessage(BronzeUser user) 
	{
		dialog.setAlwaysOnTop(true);
		boolean flag = true;
		while(flag) 
		{
		try
		{	
			String receiver = null;
			while(receiver==null || receiver.length() >25)
			{
			receiver = JOptionPane.showInputDialog(dialog,"Enter a valid receiver") ;
			}
			String editedReceiver = receiver.replaceAll("[\"']", "`");
			
			String aSql = "SELECT username, COUNT(user_id) FROM users \r\n" + 
							"WHERE username = '" + editedReceiver + "' AND status = 'ACTIVE' ;" ;
				
			Database db = new Database() ;
			Connection connection = db.connectToDatabase() ;
			ResultSet rs = db.returnResultSet(aSql) ;
			int Count = 0 ;
					
			while (rs.next()) 
			{
				Count = rs.getInt("COUNT(user_id)");
			}
			if (Count == 1)							// checking if message is valid
			{
				flag=false;
				try
				{	
					String subject=null;
					while(subject==null || subject.length() > 25)
					{
					subject = JOptionPane.showInputDialog(dialog,"Type the message subject\n(Maximum 25 characters)") ;
					}
					String text=null;
					while(text==null || text.length()>250)
					{
					text = JOptionPane.showInputDialog(dialog,"Type the message you want to send\n(Maximum 250 characters)") ;
					}
					String editedSubject = subject.replaceAll("[\"']", "`");
					String editedText = text.replaceAll("[\"']", "`");
					
					Date date = new Date();
					SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					String bSql = "INSERT INTO messages (message_id, sender, receiver, subject, date, text)\r\n" + 
						"VALUES(null,'"+ user.getUsername() +"','"+ editedReceiver +"','"+ editedSubject +"','"+ dateFormat.format(date) +"','"+ editedText +"');" ;
				
					db.executeQuery(bSql) ;
					
					File logFile = new File("all_message_log.txt");
					try
					{
						logFile.createNewFile();
						Path logFilePath = Paths.get("all_message_log.txt");
						Files.write(logFilePath, Arrays.asList("SENDER : " + user.getUsername() + " \t RECEIVER : " + editedReceiver , 
								"DATE : " + dateFormat.format(date),"SUBJECT : " + editedSubject , "TEXT : " + editedText, ""),
								StandardCharsets.UTF_8, StandardOpenOption.APPEND);
					}
					catch(IOException e) {}
					
					connection.close(); 
					ImageIcon sendMessageIcon = new ImageIcon(BronzeUser.class.getResource("/images/send_message_icon.png"));
					JOptionPane.showMessageDialog(dialog, "Message sent to " + receiver, null, JOptionPane.PLAIN_MESSAGE, sendMessageIcon);
				} 	
				catch (SQLException e)
				{	
					e.printStackTrace();
					System.out.println("Can't send message");
				}
			}
			else
			{
				ImageIcon errorIcon = new ImageIcon(BronzeUser.class.getResource("/images/error_stop.png"));
				JOptionPane.showMessageDialog(dialog, "Invalid receiver name", null, JOptionPane.ERROR_MESSAGE,errorIcon);
				flag = true;
			} 	
			connection.close();
		}
		catch (SQLException e)
		{	
			e.printStackTrace();
			System.out.println("Can't sent messages to to anyone");
		}
		}
	}
		
	// METHOD FOR CHANGING PASSWORD =====================
	public void changePassword(BronzeUser user) 
	{
		dialog.setAlwaysOnTop(true);
		boolean flag = true;
		while(flag) 
		{
		try
		{	
			String currentPassword = null;
			while(currentPassword==null || currentPassword.length() >25)
			{
			currentPassword = JOptionPane.showInputDialog(dialog,"Enter current password");
			}
			String editedCurrentPassword = currentPassword.replaceAll("[\"']", "`");
			String aSql = "SELECT * FROM users\r\n" + 
					"WHERE username = '" + user.getUsername() + "' AND password = '" + editedCurrentPassword + "' ;";
				
			Database db = new Database() ;
			Connection connection = db.connectToDatabase() ;
			ResultSet rs = db.returnResultSet(aSql) ;
			String Password = "" ;
					
			while (rs.next()) 
			{
				Password = rs.getString("password");
			}
			if (currentPassword.equals(Password))								// security question
			{
				flag = false;
				try
				{	
					String newPassword1 = null;
					String newPassword2 = null;
					while(newPassword1==null || newPassword2==null || newPassword1.length()>15 || newPassword2.length()>15)
					{
					newPassword1 = JOptionPane.showInputDialog(dialog,"Enter new password") ;
					newPassword2 = JOptionPane.showInputDialog(dialog,"Enter new password again") ;
					}
					String editedNewPassword1 = newPassword1.replaceAll("[\"']", "`");
					String editedNewPassword2 = newPassword2.replaceAll("[\"']", "`");
					if (editedNewPassword1.equals(editedNewPassword2))
					{
					String bSql = "UPDATE users\r\n" + 
							"SET password = '" + editedNewPassword1 + "'\r\n" + 
							"WHERE username = '" + user.getUsername() +"' ;" ;
				
					db.executeQuery(bSql) ;
					connection.close(); 
					ImageIcon passwordChangeOKIcon = new ImageIcon(BronzeUser.class.getResource("/images/password_change.png"));
					JOptionPane.showMessageDialog(dialog, "Password change was successful" , null, JOptionPane.PLAIN_MESSAGE,passwordChangeOKIcon);
					}
					else
					{
						ImageIcon errorIcon = new ImageIcon(BronzeUser.class.getResource("/images/error_stop.png"));
						JOptionPane.showMessageDialog(dialog, "Passwords don't match", null, JOptionPane.ERROR_MESSAGE,errorIcon);
						flag = true;
					}
				}
				catch (SQLException e)
				{	
					e.printStackTrace();
					System.out.println("Can't change Password");
				}
			}
			else
			{	
				ImageIcon errorIcon = new ImageIcon(BronzeUser.class.getResource("/images/error_stop.png"));
				JOptionPane.showMessageDialog(dialog, "Wrong password", null, JOptionPane.ERROR_MESSAGE,errorIcon);
				flag = true ;
			} 
			connection.close();
		}
		catch (SQLException e)
		{	
			e.printStackTrace();
			System.out.println("Password change failed");
		}
		}
	}
	
	// METHOD TO EXIT PROGRAM =====================
	public void logOut(BronzeUser user)
	{	
		dialog.setAlwaysOnTop(true);
		ImageIcon logoutIcon = new ImageIcon(BronzeUser.class.getResource("/images/bye.png"));
		JOptionPane.showMessageDialog(dialog, "Goodbye " + user.getUsername() + " ...till next time", null, JOptionPane.PLAIN_MESSAGE,logoutIcon);
		System.exit(0);
	}

	
	public void formatOutput()
	{
		Formatter f1 = new Formatter();
		Formatter f2 = new Formatter();
		Formatter f3 = new Formatter();
		
		f1.format("%-5s%-30s%-30s%-30s%-30s", " _____", " ______________________________" ," ______________________________"," ______________________________", " ______________________________");
		f2.format("|%-5s|%-30s|%-30s|%-30s|%-30s|", "ID" , "SENDER" ,"RECEIVER","SUBJECT", "DATE");
		f3.format("|%-5s|%-30s|%-30s|%-30s|%-30s|", "_____", "______________________________" ,"______________________________","______________________________", "______________________________");
			
			System.out.println(f1);
			System.out.println(f2);
			System.out.println(f3);	
	}
}
	
