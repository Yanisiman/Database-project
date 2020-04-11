package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BusinessFacade;
import database.DataAccess;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField loginField;
	private JLabel passwordLbl;
	private JPasswordField passwordField;
	private BusinessFacade bl;
	private LoginGUI self;

	/**
	 * Create the frame.
	 */
	public LoginGUI(BusinessFacade bl) {
		this.bl = bl;
		this.self = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{45, 338, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 32, 0, 32, 30, 35, 30, 41, 45, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel title = new JLabel("MY COMPANY");
		GridBagConstraints gbc_title = new GridBagConstraints();
		gbc_title.insets = new Insets(0, 0, 5, 5);
		gbc_title.gridx = 1;
		gbc_title.gridy = 1;
		contentPane.add(title, gbc_title);
		
		JLabel usernameLbl = new JLabel("Username :");
		GridBagConstraints gbc_usernameLbl = new GridBagConstraints();
		gbc_usernameLbl.anchor = GridBagConstraints.WEST;
		gbc_usernameLbl.insets = new Insets(0, 0, 5, 5);
		gbc_usernameLbl.gridx = 1;
		gbc_usernameLbl.gridy = 3;
		contentPane.add(usernameLbl, gbc_usernameLbl);
		
		loginField = new JTextField();
		GridBagConstraints gbc_loginField = new GridBagConstraints();
		gbc_loginField.insets = new Insets(0, 0, 5, 5);
		gbc_loginField.fill = GridBagConstraints.BOTH;
		gbc_loginField.gridx = 1;
		gbc_loginField.gridy = 4;
		contentPane.add(loginField, gbc_loginField);
		loginField.setColumns(10);
		
		passwordLbl = new JLabel("Password :");
		GridBagConstraints gbc_passwordLbl = new GridBagConstraints();
		gbc_passwordLbl.anchor = GridBagConstraints.WEST;
		gbc_passwordLbl.insets = new Insets(0, 0, 5, 5);
		gbc_passwordLbl.gridx = 1;
		gbc_passwordLbl.gridy = 5;
		contentPane.add(passwordLbl, gbc_passwordLbl);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.BOTH;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 6;
		contentPane.add(passwordField, gbc_passwordField);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = loginField.getText();
				String password = passwordField.getText();
				if (username.equals("")) return;
				if (password.equals("")) password = null;
				if (bl.login(username, password)) {
					self.setVisible(false);
					QueriesGUI queriesGUI = new QueriesGUI(bl);
					queriesGUI.setVisible(true);
				}
				else
					System.err.println("Invalid input");
			}
		});
		GridBagConstraints gbc_loginBtn = new GridBagConstraints();
		gbc_loginBtn.insets = new Insets(0, 0, 5, 5);
		gbc_loginBtn.gridx = 1;
		gbc_loginBtn.gridy = 8;
		contentPane.add(loginBtn, gbc_loginBtn);
	}

}
