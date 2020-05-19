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
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JTable;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class TablesGUI extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> tableComboBox = new JComboBox<String>();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable table = new JTable();
	
	private String[] columnNames = new String[] {};
	private DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);

	private BusinessFacade bl;
	private TablesGUI self;
	private QueriesGUI queriesGUI;
	private final JButton backBtn = new JButton("Go back");
	private final JButton deleteBtn = new JButton("Delete");
	private final JButton updateBtn = new JButton("Update");
	
	

	/**
	 * Create the frame.
	 */
	public TablesGUI(BusinessFacade bl) {
		this.bl = bl;
		this.self = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900,500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{81, 161, 186, 178, 202, 89, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 82, 0, 0, 38, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		GridBagConstraints gbc_tableComboBox = new GridBagConstraints();
		gbc_tableComboBox.gridwidth = 4;
		gbc_tableComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_tableComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_tableComboBox.gridx = 1;
		gbc_tableComboBox.gridy = 2;
		tableComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String table = (String) tableComboBox.getSelectedItem();
				displayTable(table);
			}
		});
		contentPane.add(tableComboBox, gbc_tableComboBox);
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 5;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 5;
		contentPane.add(scrollPane, gbc_scrollPane);
		scrollPane.setViewportView(table);
		
		//table.setDefaultEditor(Object.class, null);
		table.setModel(tableModel);
		
		Vector<String> tables = bl.displayTables();
		tableComboBox.setModel(new DefaultComboBoxModel<String>(tables));
		
		GridBagConstraints gbc_backBtn = new GridBagConstraints();
		gbc_backBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_backBtn.gridwidth = 2;
		gbc_backBtn.insets = new Insets(0, 0, 5, 5);
		gbc_backBtn.gridx = 2;
		gbc_backBtn.gridy = 12;
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				self.setVisible(false);
				queriesGUI.setTablesGUI(self);
				queriesGUI.setVisible(true);
			}
		});
		
		GridBagConstraints gbc_deleteBtn = new GridBagConstraints();
		gbc_deleteBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_deleteBtn.gridwidth = 2;
		gbc_deleteBtn.insets = new Insets(0, 0, 5, 5);
		gbc_deleteBtn.gridx = 2;
		gbc_deleteBtn.gridy = 11;
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String t = (String) tableComboBox.getSelectedItem();
				int r = table.getSelectedRow();
				String attribute = (String) table.getModel().getColumnName(0);
				String value = (String) table.getValueAt(r, 0);
				try {
					Integer.parseInt(value);
				}
				catch (Exception ex) {
					value = "'" + value + "'";
				}
				finally {
					boolean deleted = bl.delete(t, attribute, value);
					if (deleted) displayTable(t);
				}				
			}
		});
		
		GridBagConstraints gbc_updateBtn = new GridBagConstraints();
		gbc_updateBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_updateBtn.gridwidth = 2;
		gbc_updateBtn.insets = new Insets(0, 0, 5, 5);
		gbc_updateBtn.gridx = 2;
		gbc_updateBtn.gridy = 10;
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<String> update = new Vector<String>();
				String tableName = (String) tableComboBox.getSelectedItem();
				int row = table.getRowCount();
				int col = table.getColumnCount();
				for (int i = 0; i < row; i++) {
					for (int j = 0; j < col; j++) {
						if (table.getValueAt(i, j) != null &&
								!((String) table.getValueAt(i, j)).equals("")) {
							String value = (String) table.getValueAt(i, j);
							try {
								Integer.parseInt(value);
							}
							catch (Exception ex) {
								value = "'" + value + "'";
							}
							finally {
								String change = table.getModel().getColumnName(j) + " = " + value;
								update.add(change);
							}
						}
					}		
					bl.update(tableName, update);
					update.clear();
				}
				displayTable(tableName);
			}
		});
		contentPane.add(updateBtn, gbc_updateBtn);
		contentPane.add(deleteBtn, gbc_deleteBtn);
		contentPane.add(backBtn, gbc_backBtn);
		displayTable(tables.firstElement());
		
	}
	
	public void displayTable(String table) {
		if (table.equals(""))
			table = bl.displayTables().firstElement();
		Vector<String> attributes =  bl.displayAttributeTable(table);
		tableModel.setDataVector(null, attributes);
		Vector<Vector<String>> t = bl.table(table);
		for (Vector<String> e : t) {
			tableModel.addRow(e);
		}
		
	}
	
	public void setQueriesGui(QueriesGUI queriesGUI) {
		this.queriesGUI = queriesGUI;
	}

}
