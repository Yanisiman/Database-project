package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BusinessFacade;
import java.awt.GridBagLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class QueriesGUI extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> queriesComboBox = new JComboBox<String>();
	private JButton tablesBtn = new JButton("All tables");
	private JButton executeBtn = new JButton("Execute Query");
	
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable table = new JTable();
	private String[] columnNames = new String[] {};
	private DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);
	
	private BusinessFacade bl;
	private TablesGUI tablesGUI = null;
	private QueriesGUI self;
	private CustomGUI custom = null;
	private final JButton closeBtn = new JButton("Close");
	private final JButton customQueriesBtn = new JButton("Custom queries");
	

	/**
	 * Create the frame.
	 */
	public QueriesGUI(BusinessFacade bl) {
		this.bl = bl;
		this.self = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{53, 47, 59, 76, 71, 85, 128, 106, 89, 53, 151, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 58, 84, 54, 84, 59, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		
		tablesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				self.setVisible(false);
				if (self.tablesGUI == null) {
					TablesGUI tablesGUI = new TablesGUI(bl);
					tablesGUI.setVisible(true);
					tablesGUI.setQueriesGui(self);
				}
				else {
					self.tablesGUI.setVisible(true);
					self.tablesGUI.displayTable("");
				}
				
			}
		});
		GridBagConstraints gbc_tablesBtn = new GridBagConstraints();
		gbc_tablesBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_tablesBtn.insets = new Insets(0, 0, 5, 5);
		gbc_tablesBtn.gridx = 10;
		gbc_tablesBtn.gridy = 0;
		contentPane.add(tablesBtn, gbc_tablesBtn);
		
		
		GridBagConstraints gbc_queriesComboBox = new GridBagConstraints();
		gbc_queriesComboBox.gridwidth = 5;
		gbc_queriesComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_queriesComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_queriesComboBox.gridx = 2;
		gbc_queriesComboBox.gridy = 1;
		contentPane.add(queriesComboBox, gbc_queriesComboBox);
		
		
		GridBagConstraints gbc_executeBtn = new GridBagConstraints();
		gbc_executeBtn.insets = new Insets(0, 0, 5, 5);
		gbc_executeBtn.gridx = 7;
		gbc_executeBtn.gridy = 1;
		executeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = queriesComboBox.getSelectedIndex();
				Vector<String> attributes =  new Vector<String>();
				Vector<Vector<String>> t = bl.query(i, attributes);
				tableModel.setDataVector(null, attributes);
				if (t == null)
					return;
				for (Vector<String> r : t) {
					tableModel.addRow(r);
				}
			}
		});
		contentPane.add(executeBtn, gbc_executeBtn);
		
		GridBagConstraints gbc_customQueriesBtn = new GridBagConstraints();
		gbc_customQueriesBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_customQueriesBtn.insets = new Insets(0, 0, 5, 5);
		gbc_customQueriesBtn.gridx = 10;
		gbc_customQueriesBtn.gridy = 1;
		customQueriesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				self.setVisible(false);
				if (self.custom == null) {
					CustomGUI customGUI = new CustomGUI(bl);
					customGUI.setVisible(true);
					customGUI.setQueriesGui(self);
				}
				else {
					self.custom.setVisible(true);
				}
				
			}
		});
		contentPane.add(customQueriesBtn, gbc_customQueriesBtn);
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 5;
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 3;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		scrollPane.setViewportView(table);
		table.setDefaultEditor(Object.class, null);
		table.setModel(tableModel);
		
		Vector<String> queries = bl.queries();
		queriesComboBox.setModel(new DefaultComboBoxModel<String>(queries));
		
		GridBagConstraints gbc_closeBtn = new GridBagConstraints();
		gbc_closeBtn.anchor = GridBagConstraints.WEST;
		gbc_closeBtn.insets = new Insets(0, 0, 5, 5);
		gbc_closeBtn.gridx = 2;
		gbc_closeBtn.gridy = 8;
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bl.close();
			}
		});
		contentPane.add(closeBtn, gbc_closeBtn);
	}
	
	public void setTablesGUI(TablesGUI tablesGUI) {
		this.tablesGUI = tablesGUI;
	}

}
