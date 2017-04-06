import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class Client {
	
	private ObjectInputStream dataInput;		
	private ObjectOutputStream dataOutput;		
	private SSLSocket SSLsocket;
	private ClientGUI clientgui;
	private PrivateMessageGUI pmgui;
	private Account account;
	
	List<PrivateMessageGUI> pmGUIs = new ArrayList<PrivateMessageGUI>();
	
	public Client (ClientGUI clientgui){
		this.clientgui = clientgui;
	}
	

	public static void main(String[] args) {
		
		
	
	}


	public String register(String server, int port, String username, String password1) {
		String outcome="";
		Account newaccount = new Account(username,password1);	
		try {
			 System.setProperty("javax.net.ssl.trustStore" , "hrms.truststore");
		     System.setProperty("javax.net.ssl.trustStorePassword" , "123456");
		     SSLSocketFactory sslsf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		     SSLsocket = (SSLSocket) sslsf.createSocket(server, port);   	
		} 
		catch(Exception ec) {
			JOptionPane.showMessageDialog(new JFrame(), "Register Error, connection error.", "Register Error",JOptionPane.ERROR_MESSAGE); // display error message
		}
		
		try{		
			dataInput  = new ObjectInputStream(SSLsocket.getInputStream());
			dataOutput = new ObjectOutputStream(SSLsocket.getOutputStream());
			
			dataOutput.writeObject("register");
			dataOutput.writeObject(newaccount);
			
			try{
				outcome = (String) dataInput.readObject(); // receiving string
			}catch(ClassNotFoundException e){
				System.out.println(e);
			}

		}
		catch (IOException | java.lang.NullPointerException eIO) {
			System.out.println(eIO);
		}
		close();
		return outcome;
	}
	
	
	public String login(String server, int port, String username, String password) {
		String outcome="";
		account = new Account(username,password);	
		try {
			 System.setProperty("javax.net.ssl.trustStore" , "hrms.truststore");
		     System.setProperty("javax.net.ssl.trustStorePassword" , "123456");
		     SSLSocketFactory sslsf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		     SSLsocket = (SSLSocket) sslsf.createSocket(server, port);   		
		} 
		catch(Exception ec) {
			JOptionPane.showMessageDialog(new JFrame(), "Login Error, connection error.", "Login Error",JOptionPane.ERROR_MESSAGE); // display error message
		}
		
		try{		
			dataInput  = new ObjectInputStream(SSLsocket.getInputStream());
			dataOutput = new ObjectOutputStream(SSLsocket.getOutputStream());
			
			dataOutput.writeObject("login");
			dataOutput.writeObject(account);
			
			try{
				outcome = (String) dataInput.readObject(); // receiving response from server
			}catch(ClassNotFoundException e){
				System.out.println(e);
			}
			System.out.println(outcome);
			if(outcome.equals("loginsuccessfull")){
				
				clientgui.displayMessage("Welcome to chat room, you have connected to: "+server+ " on port: "+port +"\n");
				new ListenFromServer().start();					
			}
			else if(outcome.equals("adminloginsuccessfull")){
				clientgui.displayMessage("Welcome to chat room, you have connected to: "+server+ " on port: "+port +"\n");
				new ListenFromServer().start();	
			}
			else if(outcome.equals("failed")){
				close();
				JOptionPane.showMessageDialog(new JFrame(), "Login Error, Wrong username or password", "Login Error",JOptionPane.ERROR_MESSAGE); // display error message
			}
			else if(outcome.equals("accinuse")){
				close();
				JOptionPane.showMessageDialog(new JFrame(), "Login Error, Account is already online", "Login Error",JOptionPane.ERROR_MESSAGE); // display error message
			}
		}catch (IOException | java.lang.NullPointerException eIO) {
			System.out.println(eIO);
		}
		return outcome;
		
	}
	
	public void clearPms(){
		for(PrivateMessageGUI pm : pmGUIs){
			pm.setInVisible();
		}
		pmGUIs.clear();
	}
	
	public void sendMessage(String msg){
		try{
			dataOutput.writeObject(msg);
		}catch(IOException e){
			System.out.println(e);
		}
	}
	
	

	
		public void close() {
		// try to close the connection
		try {
			if(dataOutput != null) dataOutput.close();
		}
		catch(Exception e) {}
		try {
			if(dataInput != null) dataInput.close();
		}
		catch(Exception e) {};
		try {
			if(SSLsocket != null) SSLsocket.close();
		}
		catch (Exception e) {}
	}

	class ListenFromServer extends Thread {

		public void run() {
			while(true) {
				
				try {
					Object o = dataInput.readObject();
					if(o instanceof PrivateMessage){ // if received object is private message then....
						
					
						PrivateMessage pm = (PrivateMessage) o;
						System.out.println(pm.getMsg());
						
						
						boolean found=false;
					
						
						if(pm.getMsg().equals("youhavebeenremoved") && pm.getSender().equals("system")){
							clientgui.btnLogout.doClick();
							JOptionPane.showMessageDialog(new JFrame(), "Your account was removed from the system and you have been disconnected.\n Bye", "You have been removed",JOptionPane.ERROR_MESSAGE); // display error message					
						
						}
						
						else{
						for(PrivateMessageGUI pmgui : pmGUIs){
							
							
							if((pmgui.me.equals(pm.getSender()) || pmgui.me.equals(pm.getReceiver())) && (pmgui.partner.equals(pm.getSender()) || pmgui.partner.equals(pm.getReceiver()))){
						
								found = true;
								if(!pmgui.isActive){
									pmgui.setVisible();
									pmgui.displayMessage(pm.getMsg());
								}
								else{
									pmgui.displayMessage(pm.getMsg());
									
								}
								
							}
						}
						
						if(!found){

							String secondPerson = null;
							if(account.getUsername().equals(pm.getReceiver())){
								secondPerson=pm.getSender();
							}
							else if(account.getUsername().equals(pm.getSender())){
								secondPerson=pm.getReceiver();
							}
							
							
							
							pmgui = new PrivateMessageGUI(account.getUsername(), secondPerson, clientgui);
							pmGUIs.add(pmgui);
							
							pmgui.setVisible();
							pmgui.displayMessage(pm.getMsg());
						}
						
					
						}
						
						
					}
					else{// global chat
					String msg = (String) o;
					System.out.println(msg);
					clientgui.displayMessage(msg);
					
					if(msg.equals("----------- Server restart in: 1\n")){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						clientgui.btnLogout.doClick();
						}
					}
					

				}
				catch(IOException e) {
					clientgui.displayMessage("Server has close the connection: " + e);
					
					break;
				}
				// can't happen with a String object but need the catch anyhow
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}
