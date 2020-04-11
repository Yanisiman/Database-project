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
import java.util.Vector;

import javax.swing.JTable;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TablesGUI extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> tableComboBox = new JComboBox<String>();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable table = new JTable();
	
	private String[] columnNames = new String[] {};
	private DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);

	private BusinessFacade bl;
	private TablesGUI self;
	
	

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
		gbl_contentPane.columnWidths = new int[]{89, 161, 137, 189, 202, 89, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		gbc_scrollPane.gridy = 6;
		contentPane.add(scrollPane, gbc_scrollPane);
		scrollPane.setViewportView(table);
		
		table.setDefaultEditor(Object.class, null);
		table.setModel(tableModel);
		
		Vector<String> tables = bl.displayTables();
		tableComboBox.setModel(new DefaultComboBoxModel<String>(tables));
		displayTable(tables.firstElement());
		
	}
	
	private void displayTable(String table) {
		Vector<String> attributes =  bl.displayAttributeTable(table);
		tableModel.setDataVector(null, attributes);
		Vector<Vector<String>> t = bl.table(table);
		for (Vector<String> e : t) {
			tableModel.addRow(e);
		}
		
	}

}
