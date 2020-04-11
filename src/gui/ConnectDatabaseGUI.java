package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BusinessFacade;

public class ConnectDatabaseGUI extends JFrame {

	private JPanel contentPane;
	private JTextField loginField;
	private JLabel passwordLbl;
	private JPasswordField passwordField;
	private ConnectDatabaseGUI self;

	/**
	 * Create the frame.
	 */
	public ConnectDatabaseGUI() {
		this.self = this;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 45, 338, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 32, 0, 32, 30, 35, 30, 41, 45, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel title = new JLabel("Connect to the database");
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

		JButton connectBtn = new JButton("Connect");
		connectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = loginField.getText();
				String password = passwordField.getText();
				if (username.equals(""))
					return;
				if (password.equals(""))
					password = null;
				self.setVisible(false);
				BusinessFacade bl = new BusinessFacade(username, password);
				LoginGUI loginGUI = new LoginGUI(bl);
				loginGUI.setVisible(true);
			}
		});
		GridBagConstraints gbc_connectBtn = new GridBagConstraints();
		gbc_connectBtn.insets = new Insets(0, 0, 5, 5);
		gbc_connectBtn.gridx = 1;
		gbc_connectBtn.gridy = 8;
		contentPane.add(connectBtn, gbc_connectBtn);
	}

}
