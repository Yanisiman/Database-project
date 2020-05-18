package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import businessLogic.BusinessFacade;
import java.awt.GridBagLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTable;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class CustomGUI extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> tableComboBox = new JComboBox<String>();
	private final JScrollPane scrollPane = new JScrollPane();
	private String[] columnNames = new String[] {};
	private final JTable table = new JTable();
	
	
	private DefaultTableModel tableModel = new DefaultTableModel(null, columnNames) {
	
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 1;
        }		
	};

	private BusinessFacade bl;
	private CustomGUI self;
	private QueriesGUI queriesGUI;
	private final JButton backBtn = new JButton("Go back");
	private final JButton insertBtn = new JButton("Insert");
	
	

	/**
	 * Create the frame.
	 */
	public CustomGUI(BusinessFacade bl) {
		this.bl = bl;
		this.self = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900,500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{81, 161, 186, 178, 202, 89, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 82, 0, 38, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
				//queriesGUI.setTablesGUI(self);
				queriesGUI.setVisible(true);
			}
		});
		
		GridBagConstraints gbc_insertBtn = new GridBagConstraints();
		gbc_insertBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_insertBtn.gridwidth = 2;
		gbc_insertBtn.insets = new Insets(0, 0, 5, 5);
		gbc_insertBtn.gridx = 2;
		gbc_insertBtn.gridy = 11;
		insertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HashMap<String, String> insert = new HashMap<String, String>();
				String tableName = (String) tableComboBox.getSelectedItem();
				insert.put("table", tableName);
				for (int i = 0; i < table.getRowCount(); i++) {
					if (!((String) table.getValueAt(i, 1)).equals(""))
						insert.put((String) table.getValueAt(i, 0), (String) table.getValueAt(i, 1));
				}
				bl.insert(insert);					
			}
		});
		contentPane.add(insertBtn, gbc_insertBtn);
		contentPane.add(backBtn, gbc_backBtn);
		displayTable(tables.firstElement());
		
	}
	
	private void displayTable(String table) {
		Vector<String> attributes =  bl.displayAttributeTable(table);
		Vector<String> t = new Vector<String>();
		t.add("Attributes");
		t.add("Values");
		tableModel.setDataVector(null, t);
		for (String e : attributes) {
			Vector<String> row = new Vector<String>();
			row.add(e);
			row.add("");
			tableModel.addRow(row);
		}
		
	}
	
	public void setQueriesGui(QueriesGUI queriesGUI) {
		this.queriesGUI = queriesGUI;
	}

}
