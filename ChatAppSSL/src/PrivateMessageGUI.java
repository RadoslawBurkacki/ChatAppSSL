import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PrivateMessageGUI {

	private JFrame frame;
	private JTextField textField;
	private JScrollPane scrollPane_1;
	private JTextArea pmChat;
	private ClientGUI cgui;
	String me="";
	String partner="";
	boolean isActive=false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//PrivateMessageGUI window = new PrivateMessageGUI("","");
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PrivateMessageGUI(String me, String partner,ClientGUI cgui) {
		initialize();
		this.me = me;
		this.partner = partner;
		this.cgui=cgui;
		frame.setTitle(partner);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				frame.setVisible(false);
			}
		});
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setForeground(Color.WHITE);
		frame.setBounds(100, 100, 320, 389);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		

		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 11, 284, 328);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Send message");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String pmmsg = textField.getText();
				
				if (pmmsg.equals("")){
					
				}
				
				else if(cgui.busyradio.isSelected()){
					
				}else{
					
				
				cgui.client.sendMessage("pm");
				cgui.client.sendMessage(partner);
				cgui.client.sendMessage(pmmsg);
		
				textField.setText("");
				}
				
				
			}
		});
		btnNewButton.setBounds(127, 269, 147, 48);
		panel.add(btnNewButton);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String pmmsg = textField.getText();
				
				if (pmmsg.equals("")){
					
				}
				
				else if(cgui.busyradio.isSelected()){
					
				}
				else{
					
				
				cgui.client.sendMessage("pm");
				cgui.client.sendMessage(partner);
				cgui.client.sendMessage(pmmsg);
		
				textField.setText("");
				}
				
			}
		});
		textField.setBounds(117, 238, 157, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 46, 264, 181);
		panel.add(scrollPane_1);
		
		pmChat = new JTextArea();
		pmChat.setFont(new Font("Sitka Text", Font.PLAIN, 13));
		pmChat.setEditable(false);
		scrollPane_1.setViewportView(pmChat);
		
		JLabel label = new JLabel("Type your message:");
		label.setFont(new Font("Sitka Text", Font.PLAIN, 11));
		label.setBounds(10, 239, 137, 20);
		panel.add(label);
		
		centreWindow(frame);
	}
	
	public static void centreWindow(Window frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	}
	
	void displayMessage (String msg){
	pmChat.append(msg);
	pmChat.setCaretPosition(pmChat.getText().length() - 1);
	}
	
	boolean isOpen(){
		return frame.isActive();
	
	}
	
	void setVisible(){
		frame.setVisible(true);
	}
	void setInVisible(){
		frame.setVisible(false);
	}

	public void setTitle(String title) {
		frame.setTitle(title);
		
	}
}

