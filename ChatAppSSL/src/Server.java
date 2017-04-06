
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;


public class Server {
	

	private ObjectInputStream dataInput;		
	private ObjectOutputStream dataOuput;	
	private SSLSocket SSLsocket;
	private ServerSocket serverSocket;
	private SSLServerSocket sslserversocket;
	private boolean run;
	private static int Port;
	ArrayList<handleClient> activeConnections;
	List<Account> registerdUsers = new ArrayList<Account>();
	

	
	
	public Server(int port){
		this.Port = port;
		activeConnections = new ArrayList<handleClient>();
		registerdUsers.add(new Account("1","1111"));
		registerdUsers.add(new Account("2","2222"));
		registerdUsers.add(new Account("3","3333"));
		registerdUsers.add(new Account("admin", "admin", "admin"));
		
	}
	
	
	public static void main(String[] args) {
	
	}
	
	// each communication will begin with a string communication which will tell the server what user is expecting from server
	// register will begin with a string message "register", login will begin with "login"....
	
	public void start() {
		
		
		System.out.println("Server starting");
		Account acc = new Account("","");
		String whatToDo="";
		run = true;
		
		try{	
			  System.setProperty("javax.net.ssl.keyStore" , "pskey.keystore");
		        System.setProperty("javax.net.ssl.keyStorePassword","123456");
		        SSLServerSocketFactory sslsocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		        sslserversocket = (SSLServerSocket) sslsocketfactory.createServerSocket(Port);   
			while(run){
						
					SSLsocket = (SSLSocket) sslserversocket.accept();
					
					
					if(!run)
						break;
					try{		
						dataOuput = new ObjectOutputStream(SSLsocket.getOutputStream());
						dataInput  = new ObjectInputStream(SSLsocket.getInputStream());	
					
				
						try{
							whatToDo = (String) dataInput.readObject(); // receiving string
						}catch(ClassNotFoundException e){
							System.out.println(e);
						}
						
						
						try{
							acc= (Account) dataInput.readObject(); // receiving account object
						}catch(IOException | ClassNotFoundException e){}
						
				
						if(whatToDo.equals("register")){
							register(acc);
						
						}
						else if(whatToDo.equals("login")){
							boolean login =login(acc); //login is storing info about login, if login was successfull then its true otherwise false
	
							if(login){
								try{
								handleClient t = new handleClient(SSLsocket,dataOuput,dataInput,acc);
								t.acc.setStatus("online");
								activeConnections.add(t);
								t.start();
								}catch(Exception e){
									System.out.println(e);
								}
							}
						}			
					
					}catch(IOException e){
						
					}			
			}
		}catch(IOException e){System.out.println(e);}
	}
	
	
	void register(Account newAcc){
		boolean isNicknameInUse = false;
		
		for(Account a : registerdUsers){
			if(a.getUsername().equals(newAcc.getUsername())){
				isNicknameInUse=true;		
			}		
		}
	
		if(isNicknameInUse){
			try {
				dataOuput.writeObject("nicknameinuse");
			} catch (IOException e) {}
			
		}
		else if(!isNicknameInUse) {
			try {
				dataOuput.writeObject("registersuccess");
			} catch (IOException e) {}
			registerdUsers.add(newAcc);
		}
		close();
	}
	
	boolean login(Account acc){
		boolean accountOnline=false;
		boolean CredencialsAreCorrect=false;
	
		
		for(Account a : registerdUsers){ // for each loop, loop through all registered users 
			if(a.getUsername().equals(acc.getUsername()) && a.getPassword().equals(acc.getPassword() )){ // check if user has registered	
				for(handleClient t : activeConnections) {
					if(t.acc.getUsername().equals(acc.getUsername())) {
						 CredencialsAreCorrect=false;	
						 accountOnline=true;
						 break;
					}
					else{
						CredencialsAreCorrect=true;
						
					}
				}
				if(activeConnections.isEmpty()){
					CredencialsAreCorrect=true;	
				}
				acc.setType(a.getType());
					
			}		
		}
		
		if(CredencialsAreCorrect){
			try {
				if(acc.getType().equals("admin")){
					dataOuput.writeObject("adminloginsuccessfull");	
				}
				else if(acc.getType().equals("standard")){
				dataOuput.writeObject("loginsuccessfull");
				}
				
			} catch (IOException e) {}
			
		}
		else if(accountOnline){
			try {
				dataOuput.writeObject("accinuse");
		
			} catch (IOException e) {}
		}
		else if(!CredencialsAreCorrect) {
			try {
				dataOuput.writeObject("failed");
			} catch (IOException e) {}
		}
		
		
		return CredencialsAreCorrect;
	}
	
	
	public void restart()  {
	System.out.println("Restarting");
		run = false;	
		try {
			new Socket("localhost", Port);
		}
		catch(Exception e) {
		}
		
		try {
			sslserversocket.close();
		} catch (IOException e) {	
			e.printStackTrace();
		}
		
		
		start();
		
		
	}
	
	class handleClient extends Thread {
		Boolean run = true;
		
		Account acc;
		Socket s;
		ObjectOutputStream out;
		ObjectInputStream in;

		public handleClient(SSLSocket socket,ObjectOutputStream dataOuput, ObjectInputStream dataInput, Account acc) {
			s=socket;
			out = dataOuput;
			in = dataInput;	
			this.acc = acc;
			
			 
		}

		public void run(){
			String receiver="";String pmmsg="";String joinmsg="";String msg="";String listclients ="";

			joinmsg = "-"+acc.getUsername() +" has joined chat.\n";
			broadcast(joinmsg);
			
		
				while(run){
			
					String msgtodisplay="";
					try {
						
						msg = (String)in.readObject();
						
					}catch (IOException e) {
						break;				
					}
					catch(ClassNotFoundException e2) {
						break;
					}
				
					switch(msg){
					
						
						
						case "logout":
							run = false;
							msgtodisplay = "-"+acc.getUsername() + " has left the server.(logout)\n";
							break;
						case "clientclosed":
							run = false;
							msgtodisplay = "-"+acc.getUsername() + " has closed the application.(Exit)\n";
							break;
						case "listonlineusers":
							
							listclients="Online users: " + activeConnections.size()+"\n";
							for(int i = 0; i < activeConnections.size(); ++i) {
								handleClient t = activeConnections.get(i);
								listclients = listclients + ""+(i+1)+". " + t.acc.getUsername() +"\n";
							}
							
							break;
						case "pm":
							
							try {						
								receiver = (String)in.readObject();
								pmmsg = (String)in.readObject();
								
								System.out.println(msg+pmmsg + receiver);
												
							} catch (IOException | ClassNotFoundException e) { run = false;}

							break;
							
						case "restart":
							if(acc.getType().equals("admin")){
															
								resetServerThread t = new resetServerThread();
								t.start();
							}
							break;
							
						case "removeclient":
								
								if (acc.getType().equals("admin")){
									String nickname="";
									
									try {
										nickname = (String)in.readObject();
									} catch (ClassNotFoundException e) {
										e.printStackTrace();
									} catch (IOException e) {
										e.printStackTrace();
									}
									
									
									if(removeClient(nickname))
									broadcast("-"+nickname + " was removed from the system.\n");
									
								

								}

							break;
							
						case "online":
							
								if(acc.getStatus().equals("busy")){
									acc.setStatus("online");
									broadcast("-"+acc.getUsername() + " changed his status to "+msg + " status\n");
								}
								
							break;
						case "busy":
							
							if(acc.getStatus().equals("online")){
								acc.setStatus("busy");
								broadcast("-"+acc.getUsername() + " changed his status to "+msg + " status\n");
							}
							
							break;
	
					}
					
					if(msg.equals("logout") || msg.equals("clientclosed")){ // handles logout and client closed
						broadcast(msgtodisplay);
					}
					else if(msg.equals("listonlineusers")){
						writeMsg(listclients);
					}
					else if(msg.equals("pm")){

						for(int i = activeConnections.size(); --i >= 0;) {
							handleClient ct = activeConnections.get(i);
							
							if(ct.acc.getUsername().equals(receiver)){
								
								pmmsg = acc.getUsername() + " :" + pmmsg + "\n";
								
								broadcastpm(new PrivateMessage(pmmsg,receiver, acc.getUsername()));
							}
							
						}
						
					}
					
					else if(msg.equals("removeclient")){
						
					}
					
					else if(msg.equals("online") || msg.equals("busy")){
				
						
					}
					
					
					else if(!msg.equals("listonlineusers") || !msg.equals("logout") || !msg.equals("clientclosed") || !msg.equals("pm") || !msg.equals("restart")||!msg.equals("removeclient")){
						if(acc.getStatus().equals("online")){
							
						msgtodisplay=acc.getUsername() + ": " + msg +"\n";
						broadcast(msgtodisplay);
						}
						
					}
					
	
				}
				
				logout(acc);
				close();
			
		
	
		}
		
		private void close() {
			// try to close the connection
			try {
				if(out != null) out.close();
			}
			catch(Exception e) {}
			try {
				if(in != null) in.close();
			}
			catch(Exception e) {};
			try {
				if(s != null) s.close();
			}
			catch (Exception e) {}
		}
		
		
	

		private void logout(Account a){
			for(int i = 0; i < activeConnections.size(); ++i) {
				handleClient t = activeConnections.get(i);
				if(t.acc.getUsername().equals(a.getUsername())) {
					activeConnections.remove(i);
					return;
				}
			}
		}
		
		
		private void writeMsg(String msg) {
			
			try {
				out.writeObject(msg);
			}catch(Exception e){
				
			}
			
		}
		
	private void writepm(PrivateMessage pm) {
			
			try {
				out.writeObject(pm);
			}catch(Exception e){
				
			}
			
		}
		
	}
	
	class resetServerThread extends Thread {
		public void run(){
		broadcast("----------- Server restart in: 3\n");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		broadcast("----------- Server restart in: 2\n");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		broadcast("----------- Server restart in: 1\n");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		restart();	
		}
	}
	
	private boolean removeClient(String nickname){
		
		boolean clientfound=false;
		Account acctoremove = null;
		for(Account a : registerdUsers){
			if(a.getUsername().equals(nickname)){
				clientfound= true;
				acctoremove = a; 
				break;
				
			}
		}
		
	
			
		if(clientfound){
			for(int i = 0; i < activeConnections.size(); ++i) {
				handleClient t = activeConnections.get(i);	
				if(t.acc.getUsername().equals(nickname)){
					activeConnections.remove(i);
					PrivateMessage a = new PrivateMessage("youhavebeenremoved", t.acc.getUsername() , "system");
					t.writepm(a);
				}	
			}
			
			registerdUsers.remove(acctoremove);
		}
			
		return clientfound;
		
		
	}
	
	private synchronized void broadcastpm (PrivateMessage pm){
		
		for(int i = activeConnections.size(); --i >= 0;) {
			handleClient ct = activeConnections.get(i);
			System.out.println(i);
			if(ct.acc.getUsername().equals(pm.getSender()) || ct.acc.getUsername().equals(pm.getReceiver()) ){		
				if(ct.acc.getStatus().equals("online")){	
					
					ct.writepm(pm);
				}
			}
			
		}
		
	}
	
	
	
	private synchronized void broadcast(String message) {
		for(int i = activeConnections.size(); --i >= 0;) {
			handleClient ct = activeConnections.get(i);
			if(ct.acc.getStatus().equals("online")){			
				ct.writeMsg(message);
			}
			
		}
	}
	

	private void close() {
		// try to close the connection
		try {
			if(dataOuput != null) dataOuput.close();
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
	
}
	
