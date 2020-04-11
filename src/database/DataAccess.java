package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class DataAccess {

	private String jdbcUrl = "jdbc:mysql://dif-mysql.ehu.es:3306/DBDI05";
	private String username;
	private String password;

	private Connection connection = null;
	private PreparedStatement stmt = null;
	private ResultSet re = null;

	public DataAccess(String username, String password) {
		this.username = username;
		this.password = password;
		// Load driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Success loading Driver!");
		} catch (Exception e) {
			System.out.println("Fail loading Driver!");
			e.printStackTrace();
		}
	}

	public void connection() {
		try {
			connection = DriverManager.getConnection(jdbcUrl, username, password);
			System.out.println("Connected to the database");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		// Close everything
		try {
			if (re != null) re.close();
			if (stmt != null) stmt.close();
			if (connection != null) connection.close();
			System.out.println("Database closed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean login(String username, String password) {
		String sql = "Select * from person where nameId = \"" + username + "\" and password = \"" + password + "\"";
		try {
			stmt = connection.prepareStatement(sql);
			return (stmt.executeQuery(sql).next());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Vector<String> displayTables() {
		String sql = "SHOW TABLES";
		try {
			stmt = connection.prepareStatement(sql);
			re = stmt.executeQuery();
			Vector<String> tables = new Vector<String>();
			while(re.next()) {
				tables.add(re.getString(1));
			}
			return tables;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Vector<String> displayAttributeTable(String table) {
		String sql = "DESCRIBE " + table;
		try {
			stmt = connection.prepareStatement(sql);
			re = stmt.executeQuery();
			Vector<String> attributes = new Vector<String>();
			while(re.next()) {
				attributes.add(re.getString(1));
			}
			return attributes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Vector<Vector<String>> table(String table){
		String sql = "SELECT * FROM " + table; 
		try {
			stmt = connection.prepareStatement(sql);
			re = stmt.executeQuery();
			Vector<Vector<String>> attributes = new Vector<Vector<String>>();
			
			ResultSetMetaData rsmd = re.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			
			while(re.next()) {
				Vector<String> row = new Vector<String>();
				for (int i = 1; i <= columnsNumber; i++) {
					row.add(re.getString(i));
				}
				attributes.add(row);
			}
			return attributes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}