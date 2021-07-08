package BAS;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class login extends JFrame {

	private JPanel contentPane;
	private static JTextField userField;
	private static JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @return 
	 */
	
	@SuppressWarnings("deprecation")
	public void check() {

		String username = userField.getText();
		String password = passwordField.getText();
		
		if(username.equals("admin")&&password.equals("admin")) {
			
			BAS.login.this.hide();
			//main.this.hide();
			adminMain am = new adminMain();
			am.setVisible(true);
			BAS.adminMain.Table_load();
			//JOptionPane.showMessageDialog(null, "Success");
			

		}
		else{
			
			JOptionPane.showMessageDialog(null, "incorrect");

			passwordField.setText("");
			
		}
		
	}
	public login() {
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Admin Login");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel.setBounds(129, 11, 155, 23);
		contentPane.add(lblNewLabel);
		
		userField = new JTextField();
		userField.setHorizontalAlignment(SwingConstants.CENTER);
		userField.setBounds(129, 79, 155, 20);
		contentPane.add(userField);
		userField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					
						check();
				
					
						
					
				}
				
			}
		});

		
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(129, 137, 155, 20);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(170, 98, 79, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setBounds(170, 156, 79, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				check();
				
			}
		});
		btnNewButton.setBounds(160, 198, 89, 23);
		contentPane.add(btnNewButton);
	}
	
}
