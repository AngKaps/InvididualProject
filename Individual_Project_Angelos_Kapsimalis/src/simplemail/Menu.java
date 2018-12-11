package simplemail;

import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Menu 
{
	final static JDialog dialog = new JDialog();
	
	public void mainMenu(BronzeUser user)
	{
		String Type = user.getUsertype();
		switch(Type)
		{
		case "bronze":
			bronzeSubMenu(user);
			break ;
		case "silver":
			silverSubMenu((SilverUser)user);
			break ;
		case "gold":
			goldSubMenu((GoldUser)user);
			break ;
		case "super":	
			superSubMenu((SuperUser)user);
			break ;
		}
	}
	
	
	public void bronzeSubMenu(BronzeUser user)
	{
		dialog.setAlwaysOnTop(true); 
		boolean flag = true ;
		while (flag)	
		{
			String choice = null;
		while(choice==null)
		{		
		choice = JOptionPane.showInputDialog(dialog,"     = = = PRESS = = =\n[1] : Check inbox\n[2] : Check sent messages\n"
				+ "[3] : Send message\n[4] : Change password\n[5] : Log out") ;
		}
			switch(choice)
			
			{
				
				case "1" :
					clearScreen();
					user.checkInbox(user);
					String choice1 = null;
					while(choice1==null) 
					{
					choice1 = JOptionPane.showInputDialog(dialog,"     = = = PRESS = = =\n[1] : Read messages\n[2] : Log out\n"
							+ "[Anything else] : Go back");
					}
					switch(choice1)
					{
						case "1":
							user.readInbox(user);
							break ;
						case "2" : 
							user.logOut(user);
						default: 
							break;
					}
					
					break ;
				case "2" :
					clearScreen();
					user.checkSentMessages(user);
					String choice2 = null;
					while(choice2==null) 
					{
						choice2 = JOptionPane.showInputDialog(dialog,"     = = = PRESS = = =\n[1] : Read messages\n[2] : Log out\n" 
								+ "[Anything else] : Go back");
					}
					switch(choice2) 
					{
						case "1":
							user.readSentMessages(user);
							break ;
						case "2" : 
							user.logOut(user);
						default: 
							break;
					}
					break ;
				case "3" :
					user.sendMessage(user);
					break ;
				case "4" :
					user.changePassword(user);
					break;
				case "5" :
					user.logOut(user);
					break ;
				default :
				
			}
		}
	}
	
	
	
	
	
	public void silverSubMenu(SilverUser user)
	{
		dialog.setAlwaysOnTop(true); 
		boolean flag = true ;
		while (flag)	
		{
			String choice = null;
			while(choice==null) 
			{
				choice = JOptionPane.showInputDialog(dialog,"     = = = PRESS = = =\n[1] : Check inbox\n[2] : Check sent messages\n"
					+ "[3] : Send message\n[4] : Change password\n[5] : Log out") ;
	
				
				switch(choice)
				{
					case "1" :
						user.checkInbox(user);
						String choice1 = null;
						while(choice1==null) 
						{
						choice1 = JOptionPane.showInputDialog(dialog,"     = = = PRESS = = =\n[1] : Read messages\n"
								+ "[2] : Log out\n[Anything else] : Go back");
						}
						switch(choice1)
						{
							case "1":
								user.readInbox(user);
								break ;
							case "2" : 
								user.logOut(user);
								break ;
							default: 
								break;
						}
						break;
					case "2" :
						user.checkSentMessages(user);
						String choice2 = null;
						while(choice2==null) 
						{
							choice2 = JOptionPane.showInputDialog(dialog,"     = = = PRESS = = =\n[1] : Read messages\n[2] : Edit messages\n"
								+ "[3] : Log out\n[Anything else] : Go back");
						}
						switch(choice2) 
						{
							case "1":
								user.readSentMessages(user);
								break ;
							case "2":
								user.editSentMessage(user);
								break;
							case "3":
								user.logOut(user);
							default :
						}
						break ;
					case "3" :
						user.sendMessage(user);
						break ;
					case "4" :
						user.changePassword(user);
						break ;
					case "5" :
						user.logOut(user);
						break;	
					default :
		
				}
			}
		}
	}
	
	
	public void goldSubMenu(GoldUser user)
	{
		dialog.setAlwaysOnTop(true); 
		boolean flag = true ;
		while (flag)	
		{
			String choice = null;
			while(choice==null)
			{
				choice = JOptionPane.showInputDialog(dialog,"     = = = PRESS = = =\n\n[1] : Check inbox\n[2] : Check sent messages\n"
					+ "[3] : Send message\n[4] : Change password\n[5] : Log out") ;
			}
				switch(choice)
				{
					case "1" :
						user.checkInbox(user);
						String choice1 = null;
						while(choice1==null) 
						{
							choice1 = JOptionPane.showInputDialog(dialog,"     = = = PRESS = = =\n[1] : Read messages\n"
								+ "[2] : Log out\n[Anything else] : Go back");
						}
						switch(choice1)
						{
							case "1":
								user.readInbox(user);
								break ;
							case "2":
								user.logOut(user);
								break ;
							default: 		
						}
						break ;
					case "2" :
						user.checkSentMessages(user);
						String choice2 = null;
						while(choice2==null) 
						{
							choice2 = JOptionPane.showInputDialog(dialog,"     = = = PRESS = = =\n[1] : Read messages\n[2] : Edit messages\n"
								+ "[3] : Delete messages\n[4] : Log out\n[Anything else] : Go back");
						}
						switch(choice2) 
						{
							case "1":
								user.readSentMessages(user);
								break ;
							case "2":
								user.editSentMessage(user);
								break;
							case "3":
								user.deleteSentMessage(user);
								break;
							case "4":
								user.logOut(user);
							default :
								
						}
						break ;
					case "3" :
						user.sendMessage(user);
						break ;
					case "4" :
						user.changePassword(user);
						break ;
					case "5" :
						user.logOut(user);
						break;	
					default :
		
				}
			}
		}
				
	
	
	public void superSubMenu(SuperUser user)
	{
		dialog.setAlwaysOnTop(true); 
		boolean flag = true ;
		while (flag)	
		{
		String choice = null;
		while(choice==null)
		{ 
			choice = JOptionPane.showInputDialog(dialog,"     = = = PRESS = = =\n[1] : Check inbox\n[2] : Check sent messages\n"
				+ "[3] : Send message\n[4] : Change password\n[5] : C.R.U.D. users\n[6] : Log out") ;
		}
			switch(choice)
			{
				case "1" :
					user.checkInbox(user);
					String choice1 = null;
					while(choice1==null) 
					{
						choice1 = JOptionPane.showInputDialog(dialog,"     = = = PRESS = = =\n[1] : Read messages\n"
							+ "[2] : Log out\n[Anything else] : Go back");
					}
					switch(choice1)
					{
						case "1":
							user.readInbox(user);
							break ;
						case "2":
							user.logOut(user);
							break ;
						default: 		
					}
					break ;
				case "2" :
					user.checkSentMessages(user);
					String choice2 = null;
					while(choice2==null) 
					{
						choice2 = JOptionPane.showInputDialog(dialog,"     = = = PRESS = = =\n[1] : Read messages\n[2] : Edit messages\n"
							+ "[3] : Delete messages\n[4] : Log out\n[Anything else] : Go back");
					}
					switch(choice2) 
					{
						case "1":
							user.readSentMessages(user);
							break ;
						case "2":
							user.editSentMessage(user);
							break;
						case "3":
							user.deleteSentMessage(user);
							break;
						case "4":
							user.logOut(user);
						default :		
					}
					break ;
				case "3" :
					user.sendMessage(user);
					break ;
				case "4" :
					user.changePassword(user);
					break ;
				case "5" :
					user.checkAllUsers(user);
					String choice3 = null;
					while(choice3==null) 
					{
						choice3 = JOptionPane.showInputDialog(dialog,"     = = = PRESS = = =\n[1] : Create new user\n[2] : Check specific user\n"
							+ "[3] : Update user\n[4] : Ban user\n[5] : Delete user and associated data\n[6] : Reactivate banned user\n"
							+ "[7] : Log out\n[Anything else] : Go back");
					}
					switch(choice3)
					{
						case "1":
							user.createUser(user);
							break ;
						case "2":
							user.checkUser(user);
							break ;
						case "3" : 
							user.editUser(user);
							break ;
						case "4" : 
							user.deactivateUser(user);
							break ;
						case "5" : 
							user.deleteUser(user);
							break ;
						case "6" : 
							user.reactivateUser(user);
							break ;
						case "7" : 
							user.logOut(user);
							break ;
						default : 
		
					}
					break;
				case "6" :
					user.logOut(user);
				default:
			}
					
			}
		}
	
	public static void clearScreen()  {  //only works in CMD
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	}


