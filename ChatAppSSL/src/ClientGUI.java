import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JRadioButton;




public class ClientGUI {
	
	private boolean connected=false;

	
	private JFrame frmChatApplicationClient;
	private JTextField RegisterUsername;
	private JPasswordField RegisterPassword1;
	private JPasswordField RegisterPassword2;
	private JTextField LoginUsername;
	private JPasswordField LoginPassword;
	private JTextField ServerAddress;
	private JTextField ServerPort;
	private JTextField Messagetext;
	private JTextArea ChattextArea;
	private JButton Loginbtn;
	private JPanel Chatpanel;
	JRadioButton onlineradio;
	JRadioButton busyradio;
	public JButton btnLogout;
	private JButton btnListOnlineUsers;
	private JButton btnSendPrivateMessage;
	private JButton btnRegister;
	private JPanel Adminpanel;


	

	/**
	 * Launch the application.
	 */
	
	private String server;
	private int port;
	
	public Client client = new Client(this);
	private JTextField pmtextfield;
	private JTextField pmnickname;
	private JButton Restart;
	private JLabel lblUserRemover;
	private JButton btnRemove;
	private JLabel lblNickname;
	private JTextField nickname;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
					window.frmChatApplicationClient.setVisible(true);

					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientGUI() {
		initialize();
	
		centreWindow(frmChatApplicationClient);
		
		JLabel lblTypeYourPrivate = new JLabel("Type your private message:");
		lblTypeYourPrivate.setFont(new Font("Sitka Text", Font.PLAIN, 11));
		lblTypeYourPrivate.setBounds(10, 379, 158, 21);
		Chatpanel.add(lblTypeYourPrivate);
		
		pmtextfield = new JTextField();
		pmtextfield.setFont(new Font("Sitka Text", Font.PLAIN, 11));
		pmtextfield.setColumns(10);
		pmtextfield.setBounds(178, 379, 296, 20);
		Chatpanel.add(pmtextfield);
		
		JLabel lblSendMessageTo = new JLabel("Send message to:");
		lblSendMessageTo.setFont(new Font("Sitka Text", Font.PLAIN, 11));
		lblSendMessageTo.setBounds(10, 413, 158, 21);
		Chatpanel.add(lblSendMessageTo);
		
		pmnickname = new JTextField();
		pmnickname.setFont(new Font("Sitka Text", Font.PLAIN, 11));
		pmnickname.setColumns(10);
		pmnickname.setBounds(111, 411, 148, 20);
		Chatpanel.add(pmnickname);
		
		
		
		
		btnSendPrivateMessage.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		btnSendPrivateMessage.setBounds(282, 405, 168, 33);
		Chatpanel.add(btnSendPrivateMessage);
		
		
		////
		ChattextArea.setEditable(false);
		Messagetext.setEditable(false);
		pmtextfield.setEditable(false);
		pmnickname.setEditable(false);
		btnSendPrivateMessage.setEnabled(false);
		onlineradio.setEnabled(false);
		busyradio.setEnabled(false);
		btnLogout.setEnabled(false);
		btnListOnlineUsers.setEnabled(false);
		
		Adminpanel = new JPanel();
		Adminpanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		Adminpanel.setBounds(508, 434, 222, 171);
		frmChatApplicationClient.getContentPane().add(Adminpanel);
		Adminpanel.setLayout(null);
		
		
		
		Restart.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		Restart.setBounds(10, 127, 204, 33);
		Adminpanel.add(Restart);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 11, 204, 105);
		Adminpanel.add(panel);
		panel.setLayout(null);
		
		lblUserRemover = new JLabel("User Remover");
		lblUserRemover.setFont(new Font("Sitka Text", Font.PLAIN, 15));
		lblUserRemover.setBounds(10, 11, 139, 14);
		panel.add(lblUserRemover);
		
		
		btnRemove.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		btnRemove.setBounds(26, 61, 168, 33);
		panel.add(btnRemove);
		
		lblNickname = new JLabel("Nickname:");
		lblNickname.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		lblNickname.setBounds(10, 36, 106, 14);
		panel.add(lblNickname);
		
		nickname = new JTextField();
		nickname.setBounds(81, 30, 113, 20);
		panel.add(nickname);
		nickname.setColumns(10);
		Adminpanel.setVisible(false);
		
		
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatApplicationClient = new JFrame();
		frmChatApplicationClient.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
			
				if(connected){
				client.sendMessage("clientclosed");
				client.close();
				}
				
			}
		});
		
		btnRemove = new JButton("Remove!");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String nick = nickname.getText();
				
			
				String user = LoginUsername.getText();
				if((!nick.equals("")) && (!nick.equals(user))){
				
				client.sendMessage("removeclient");
				client.sendMessage(nick);
				}	
				else if(nick.equals(user)){
					JOptionPane.showMessageDialog(new JFrame(), "You can remove yourself from system", "Error",JOptionPane.ERROR_MESSAGE); // display error message

				}
				nickname.setText("");
			}
			
			
		});
		
		Restart = new JButton("Restart");
		Restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				client.sendMessage("restart");
	
				
			}
		});
		
		btnSendPrivateMessage = new JButton("Send private message");
		btnSendPrivateMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String pmtext=pmtextfield.getText();
				String pmnick = pmnickname.getText();
					
				if (pmtext.equals("") || pmnick.equals("")){
					JOptionPane.showMessageDialog(new JFrame(), "Make sure to enter message and receiver", "Private Message Error",JOptionPane.ERROR_MESSAGE); // display error message

				}
				else if(pmnick.equals(LoginUsername.getText())){
					JOptionPane.showMessageDialog(new JFrame(), "You can send private message to your self", "Private Message Error",JOptionPane.ERROR_MESSAGE); // display error message

				}
				else {
					String pmmsg = pmtextfield.getText();
					String receiver = pmnickname.getText();
					client.sendMessage("pm");
					client.sendMessage(receiver);
					client.sendMessage(pmmsg);
			
					pmnickname.setText("");
					pmtextfield.setText("");
				}
			}
		});
		
		frmChatApplicationClient.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmChatApplicationClient.setResizable(false);
		frmChatApplicationClient.getContentPane().setForeground(Color.WHITE);
		frmChatApplicationClient.setTitle("Chat Client");
		frmChatApplicationClient.setBounds(100, 100, 752, 645);
		frmChatApplicationClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatApplicationClient.getContentPane().setLayout(null);
		
		JPanel RegisterPanel = new JPanel();
		RegisterPanel.setBorder(new LineBorder(Color.DARK_GRAY));
		RegisterPanel.setBounds(508, 11, 222, 176);
		frmChatApplicationClient.getContentPane().add(RegisterPanel);
		RegisterPanel.setLayout(null);
			
		btnRegister = new JButton("Register");

		btnRegister.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		btnRegister.setBounds(10, 123, 202, 40);
		RegisterPanel.add(btnRegister);
		
		RegisterUsername = new JTextField();
		RegisterUsername.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		RegisterUsername.setBounds(126, 33, 86, 20);
		RegisterPanel.add(RegisterUsername);
		RegisterUsername.setColumns(10);
		
		RegisterPassword1 = new JPasswordField();
		RegisterPassword1.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		RegisterPassword1.setBounds(126, 64, 86, 20);
		RegisterPanel.add(RegisterPassword1);
		
		JLabel lblLogin = new JLabel("Username:");
		lblLogin.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		lblLogin.setBounds(10, 36, 69, 14);
		RegisterPanel.add(lblLogin);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		lblPassword.setBounds(10, 67, 69, 14);
		RegisterPanel.add(lblPassword);
		
		JLabel lblRegisterToUse = new JLabel("Create new Account");
		lblRegisterToUse.setFont(new Font("Sitka Text", Font.PLAIN, 15));
		lblRegisterToUse.setBounds(10, 11, 169, 14);
		RegisterPanel.add(lblRegisterToUse);
		
		RegisterPassword2 = new JPasswordField();
		RegisterPassword2.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		RegisterPassword2.setBounds(126, 95, 86, 20);
		RegisterPanel.add(RegisterPassword2);
		
		JLabel lblNewLabel = new JLabel("Repeat Password:");
		lblNewLabel.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 98, 106, 14);
		RegisterPanel.add(lblNewLabel);
		
		JPanel Loginpanel = new JPanel();
		Loginpanel.setBorder(new LineBorder(Color.DARK_GRAY));
		Loginpanel.setBounds(10, 11, 187, 134);
		frmChatApplicationClient.getContentPane().add(Loginpanel);
		Loginpanel.setLayout(null);
		
		JLabel lblLogin_1 = new JLabel("Login");
		lblLogin_1.setFont(new Font("Sitka Text", Font.PLAIN, 15));
		lblLogin_1.setBounds(10, 11, 46, 14);
		Loginpanel.add(lblLogin_1);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		lblUsername.setBounds(10, 36, 79, 14);
		Loginpanel.add(lblUsername);
		
		JLabel lblPassword_1 = new JLabel("Password:");
		lblPassword_1.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		lblPassword_1.setBounds(10, 65, 79, 14);
		Loginpanel.add(lblPassword_1);
		
		LoginUsername = new JTextField();
		LoginUsername.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		LoginUsername.setBounds(92, 33, 86, 20);
		Loginpanel.add(LoginUsername);
		LoginUsername.setColumns(10);
		
		LoginPassword = new JPasswordField();
		LoginPassword.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		LoginPassword.setBounds(92, 62, 86, 20);
		Loginpanel.add(LoginPassword);
		
		Loginbtn = new JButton("Login");
			
		Loginbtn.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		Loginbtn.setBounds(10, 90, 168, 33);
		Loginpanel.add(Loginbtn);
		
		
		JPanel ServerInfopanel = new JPanel();
		ServerInfopanel.setBorder(new LineBorder(Color.DARK_GRAY));
		ServerInfopanel.setBounds(207, 11, 291, 134);
		frmChatApplicationClient.getContentPane().add(ServerInfopanel);
		ServerInfopanel.setLayout(null);
		
		JLabel lblServerInfo = new JLabel("Server Info");
		lblServerInfo.setFont(new Font("Sitka Text", Font.PLAIN, 15));
		lblServerInfo.setBounds(10, 11, 139, 14);
		ServerInfopanel.add(lblServerInfo);
		
		JLabel lblServer = new JLabel("Server Address:");
		lblServer.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		lblServer.setBounds(10, 36, 92, 14);
		ServerInfopanel.add(lblServer);
		
		JLabel lblServerPort = new JLabel("Server Port:");
		lblServerPort.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		lblServerPort.setBounds(10, 61, 92, 14);
		ServerInfopanel.add(lblServerPort);
		
		ServerAddress = new JTextField();
		ServerAddress.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		ServerAddress.setText("127.0.0.1");
		ServerAddress.setColumns(10);
		ServerAddress.setBounds(112, 33, 166, 20);
		ServerInfopanel.add(ServerAddress);
		
		ServerPort = new JTextField();
		ServerPort.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		ServerPort.setText("33333");
		ServerPort.setColumns(10);
		ServerPort.setBounds(112, 58, 166, 20);
		ServerInfopanel.add(ServerPort);
		
		Chatpanel = new JPanel();
		Chatpanel.setBorder(new LineBorder(Color.DARK_GRAY));
		Chatpanel.setBounds(10, 156, 488, 449);
		frmChatApplicationClient.getContentPane().add(Chatpanel);
		Chatpanel.setLayout(null);
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 464, 325);
		Chatpanel.add(scrollPane);
		
		ChattextArea = new JTextArea();
		ChattextArea.setFont(new Font("Sitka Text", Font.PLAIN, 13));
		scrollPane.setViewportView(ChattextArea);

		
		JLabel lblTypeYourMessage = new JLabel("Type your message:");
		lblTypeYourMessage.setFont(new Font("Sitka Text", Font.PLAIN, 11));
		lblTypeYourMessage.setBounds(10, 347, 121, 21);
		Chatpanel.add(lblTypeYourMessage);
		
		Messagetext = new JTextField();
		Messagetext.setFont(new Font("Sitka Text", Font.PLAIN, 11));
		Messagetext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = Messagetext.getText();
				if(msg.equals("")){
					
				}
				else{
					client.sendMessage(msg);
					Messagetext.setText("");
				}
			}
		});
		
		
		Messagetext.setBounds(133, 347, 341, 20);
		Chatpanel.add(Messagetext);
		Messagetext.setColumns(10);
		
		
		JPanel Statuspanel = new JPanel();
		Statuspanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		Statuspanel.setBounds(508, 198, 222, 227);
		frmChatApplicationClient.getContentPane().add(Statuspanel);
		Statuspanel.setLayout(null);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Sitka Text", Font.PLAIN, 15));
		lblStatus.setBounds(10, 11, 139, 14);
		Statuspanel.add(lblStatus);
		
		onlineradio = new JRadioButton("Online");
		onlineradio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onlineradio.setSelected(true);
				busyradio.setSelected(false);
	
				client.sendMessage("online");
			
			}
		});
	
		onlineradio.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		onlineradio.setBounds(69, 8, 109, 23);
		Statuspanel.add(onlineradio);
		
		busyradio = new JRadioButton("Busy");
		busyradio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onlineradio.setSelected(false);
				busyradio.setSelected(true);
				client.sendMessage("busy");
			}
		});
		busyradio.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		busyradio.setBounds(69, 32, 109, 23);
		Statuspanel.add(busyradio);
		
		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ServerAddress.setEditable(true);
				ServerPort.setEditable(true);
				RegisterUsername.setEditable(true);
				RegisterPassword1.setEditable(true);	
				RegisterPassword2.setEditable(true);
				btnRegister.setEnabled(true);
				Loginbtn.setEnabled(true);
				LoginUsername.setEditable(true);
				LoginPassword.setEditable(true);
				
				
				Messagetext.setEditable(false);
				pmtextfield.setEditable(false);
				pmnickname.setEditable(false);
				btnSendPrivateMessage.setEnabled(false);
				onlineradio.setEnabled(false);
				busyradio.setEnabled(false);
				btnLogout.setEnabled(false);
				btnListOnlineUsers.setEnabled(false);
	
				
				LoginUsername.setText("");
				LoginPassword.setText("");
				ChattextArea.setText("");	
				
				
				onlineradio.setSelected(false);
				busyradio.setSelected(false);
				
				if(Adminpanel.isVisible()){
					Adminpanel.setVisible(false);
				}
				
				client.sendMessage("logout");
				client.close();
				connected = false;
				
				client.clearPms();
				

			}
		});
		btnLogout.setBounds(10, 156, 168, 33);
		Statuspanel.add(btnLogout);
		btnLogout.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		
		btnListOnlineUsers = new JButton("List online users");
		btnListOnlineUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client.sendMessage("listonlineusers");
				
				
			}
		});
		btnListOnlineUsers.setBounds(10, 83, 168, 33);
		Statuspanel.add(btnListOnlineUsers);
		btnListOnlineUsers.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				server = ServerAddress.getText();
				port = Integer.parseInt(ServerPort.getText());
				
				char[] passwordchar1 = RegisterPassword1.getPassword();
				char[] passwordchar2 = RegisterPassword2.getPassword();
				
				String username = RegisterUsername.getText();
				String password1 = String.valueOf(passwordchar1);
				String password2 = String.valueOf(passwordchar2);
				
		
				if (password1.equals(password2) && password1.length()>= 4){// if passwords are the same then...
					String registerd = client.register(server,port,username,password1);
					if(registerd.equals("registersuccess")){
						JOptionPane.showMessageDialog(new JFrame(), "You have registered successfully\n" + "Your nickname is: " + username +"\n" + "Your password is: " + password1, "Register successful",JOptionPane.INFORMATION_MESSAGE); 
						RegisterUsername.setText("");
						RegisterPassword1.setText("");
						RegisterPassword2.setText("");
						
					}
					else if(registerd.equals("nicknameinuse")){
						JOptionPane.showMessageDialog(new JFrame(), "Error, this nickname is already in use", "Register Error",JOptionPane.ERROR_MESSAGE); // display error message
					}
					
					
				}
				else{// if passwords are different then...
					JOptionPane.showMessageDialog(new JFrame(), "Register Error, Please re-enter passwords", "Register Error",JOptionPane.ERROR_MESSAGE); // display error message
					RegisterPassword1.setText(""); // clear textbox
					RegisterPassword2.setText(""); // clear textbox
				}		
			}
		});
		
		Loginbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				server = ServerAddress.getText();
				port = Integer.parseInt(ServerPort.getText());
				
				String username = LoginUsername.getText();	
				
				char[] passwordchar = LoginPassword.getPassword();
				String password = String.valueOf(passwordchar);
			
				
				
				if(!username.equals("") && password.length() >= 4){ // if correct data is enterd then...
					ChattextArea.setText("");
					
					String outcome = client.login(server,port,username,password);
					if(outcome.equals("loginsuccessfull")){
						
						ServerAddress.setEditable(false);
						ServerPort.setEditable(false);
						RegisterUsername.setEditable(false);
						RegisterPassword1.setEditable(false);	
						RegisterPassword2.setEditable(false);
						btnRegister.setEnabled(false);
						Loginbtn.setEnabled(false);
						LoginUsername.setEditable(false);
						LoginPassword.setEditable(false);
						
				
						Messagetext.setEditable(true);
						pmtextfield.setEditable(true);
						pmnickname.setEditable(true);
						btnSendPrivateMessage.setEnabled(true);
						onlineradio.setEnabled(true);
						busyradio.setEnabled(true);
						btnLogout.setEnabled(true);
						btnListOnlineUsers.setEnabled(true);
				
						
						connected = true;
						
						onlineradio.setSelected(true);
						
						
					}
					
					else if(outcome.equals("adminloginsuccessfull")){
						
						
						ServerAddress.setEditable(false);
						ServerPort.setEditable(false);
						RegisterUsername.setEditable(false);
						RegisterPassword1.setEditable(false);	
						RegisterPassword2.setEditable(false);
						btnRegister.setEnabled(false);
						Loginbtn.setEnabled(false);
						LoginUsername.setEditable(false);
						LoginPassword.setEditable(false);
						
						
						Messagetext.setEditable(true);
						pmtextfield.setEditable(true);
						pmnickname.setEditable(true);
						btnSendPrivateMessage.setEnabled(true);
						onlineradio.setEnabled(true);
						busyradio.setEnabled(true);
						btnLogout.setEnabled(true);
						btnListOnlineUsers.setEnabled(true);
				
						
						Adminpanel.setVisible(true);
						onlineradio.setSelected(true);
						
						connected = true;
						
						
					}
					
					
					
					
	
				}
				else{
					JOptionPane.showMessageDialog(new JFrame(), "Login Error, Wrong username or password", "Login Error",JOptionPane.ERROR_MESSAGE); // display error message

				}
	
			}
		});
	
		
	}
	
	
	

	void displayMessage (String msg){
		ChattextArea.append(msg);
	//	ChattextArea.setCaretPosition(ChattextArea.getText().length() - 1);
	}
	
	public static void centreWindow(Window frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	}
}


