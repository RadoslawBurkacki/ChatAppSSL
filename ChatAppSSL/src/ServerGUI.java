import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Font;


public class ServerGUI {

	private JFrame frmChatServer;
	private JTextField PortTextField;
	private Server server;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI window = new ServerGUI();
					window.frmChatServer.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatServer = new JFrame();
		frmChatServer.setResizable(false);
		frmChatServer.setTitle("Chat Server");
		frmChatServer.setBounds(100, 100, 250, 102);
		frmChatServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatServer.getContentPane().setLayout(null);
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				
				PortTextField.setEnabled(false);
	
				btnStart.setEnabled(false);
				
				
				
				server = new Server(Integer.parseInt(PortTextField.getText()));
				//server.start();
				new ServerRunning().start();
			
				
				
				
				
				
			}
		});
		btnStart.setBounds(10, 11, 98, 42);
		frmChatServer.getContentPane().add(btnStart);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		lblPort.setBounds(118, 25, 46, 14);
		frmChatServer.getContentPane().add(lblPort);
		
		PortTextField = new JTextField();
		PortTextField.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		PortTextField.setText("33333");
		PortTextField.setBounds(146, 22, 86, 20);
		frmChatServer.getContentPane().add(PortTextField);
		PortTextField.setColumns(10);
	}
	
	class ServerRunning extends Thread {
		public void run() {
			server.start();        
			
			
		}
	}
}
