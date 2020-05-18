package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class DataAccess {

	private String jdbcUrl = "jdbc:mysql://dif-mysql.ehu.es:3306/DBDI05";
	private String username;
	private String password;

	private Connection connection = null;
	private PreparedStatement stmt = null;
	private ResultSet re = null;
	
	private Vector<String> queries = new Vector<String>();
	private Vector<String> queriesExplanation = new Vector<String>();

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
		
		String sql = "SELECT ht.TripTo, ht.DepartureDate "
				+ "FROM hotel_trip as ht "
				+ "INNER JOIN hotel as h on ht.HotelId = h.HotelId "
				+ "WHERE h.hotelcity = 'Donostia'";
		String name = "Trips that use hotels in Donostia";
		
		String sql2 = "SELECT AVG(trip.Numdays * trip.Ppday) "
				+ "FROM trip "
				+ "WHERE trip.CityDeparture = 'Donostia'";
		String name2 = "Average price of the trips that depart " + 
				"from Donostia";
		
		String sql3 = "SELECT c.custname, c.custaddress from customer as c "
				+ "where not exists (select * from hotel_trip_customer as htc "
				+ "inner join hotel as h on htc.HotelId = h.HotelId where c.CustomerId = htc.CustomerId "
				+ "and h.hotelname = 'Amara' and h.hotelcity = 'Donostia')";
		String name3 = "Customers who have never gone on a trip using the hotel Amara in Donostia";
		
		String sql4 = "insert INTO EMPLOYEE VALUES ('Richard', 'K', 'Marini', '653298653', "
					+ "'30/12/1962', '98 Oak Forest, Katy, TX', 'M', 37000,'987654321', 4);"
					+ "Select * from employee";
		String name4 = "Add a new employee";
		
		String sql5 = "delete FROM EMPLOYEE WHERE DNO IN (SELECT DNUMBER"
						+ " FROM DEPARTMENT WHERE DNAME = 'Research');"
						+ "Select * from employee";
		String name5 = "Delete all employees working in the Research department";
		
		String sql6 = "update PROJECT SET PLOCATION = 'Bellaire', DNUM = 4 " 
						+ "WHERE PNUMBER = 10;"
						+ "Select * from project";
		String name6 = "Change the location and controlling department number of project number 10";
		
		String sql7 = "update EMPLOYEE SET SALARY = SALARY * 1.1 "
				 + "WHERE DNO IN (SELECT DNUMBER FROM DEPARTMENT " 
				 + "WHERE DNAME = 'Research');"
				 + "Select * from employee";
		String name7 = "Give all employees in the Research department a 10% raise in salary";
		
		String sql8 = "delete from department where Dnumber = 4"
					+ "Select * from department";
		String name8 = "Delete department number 4";
		
		
		String sql9 = "select ht.TripTo, ht.DepartureDate"
				+ " from hotel_trip as ht inner join hotel as h on ht.HotelId = h.HotelId"
				+ " group by ht.TripTo, ht.DepartureDate"
				+ " having count(distinct h.hotelcity) = 1";
		String name9 = "Retrieve the id of the trips in which all hotels offered (for " + 
				"that trip) are in the same location";
		
		String sql10 = "select c.CustomerId, c.custname, count(*)"
				+ " from hotel_trip_customer as htc inner join customer as c on"
				+ " htc.CustomerId = c.CustomerId where c.CustomerId not in"
				+ " (select htc1.CustomerId from hotel_trip_customer as htc1"
				+ " where htc.CustomerID = htc1.CustomerID and htc1.TripTo <> 'Madrid')"
				+ " group by c.CustomerId, c.custname";
		String name10 = "For each customer who has travelled ONLY to Madrid, print out " + 
				"his/her name and the number of trips to that city.";
		
		String sql11 = "select count(distinct f.restaurname) as N_restaurants"
				+ " from hotel_trip_customer as htc inner join person as p"
				+ " on htc.CustomerId = p.id inner join frequents as f on p.nameid = f.nameid";
		String name11 = "Retrieve the number of different restaurants frequented " + 
				"by people in trips.";
		
		queries.add(sql);
		queries.add(sql2);
		queries.add(sql3);
		queries.add(sql4);
		queries.add(sql5);
		queries.add(sql6);
		queries.add(sql7);
		queries.add(sql8);
		queries.add(sql9);
		queries.add(sql10);
		
		queriesExplanation.add(name);
		queriesExplanation.add(name2);
		queriesExplanation.add(name3);
		queriesExplanation.add(name4);
		queriesExplanation.add(name5);
		queriesExplanation.add(name6);
		queriesExplanation.add(name7);
		queriesExplanation.add(name8);
		queriesExplanation.add(name9);
		queriesExplanation.add(name10);
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
	
	public Vector<String> queries(){
		return queriesExplanation;
	}
	
	public Vector<Vector<String>> table(String table){
		String sql = "SELECT * FROM " + table; 
		return getAllRows(sql, null);
	}
	
	private Vector<Vector<String>> getAllRows(String sql, Vector<String> a){
		try {
			stmt = connection.prepareStatement(sql);
			re = stmt.executeQuery();
			Vector<Vector<String>> attributes = new Vector<Vector<String>>();
			
			ResultSetMetaData rsmd = re.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			
			int j = 0;
			while(re.next()) {
				
				Vector<String> row = new Vector<String>();
				for (int i = 1; i <= columnsNumber; i++) {
					row.add(re.getString(i));
					if (j == 0 && a != null)
						a.add(rsmd.getColumnName(i));
				}
				attributes.add(row);
				j += 1;
			}
			return attributes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void queryChanges(String sql) {
		try {
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Vector<Vector<String>> query(int i, Vector<String> attributes){
		String sql = queries.elementAt(i);
		if (sql.startsWith("insert") || sql.startsWith("update") || sql.startsWith("delete")) {
			String[] splits = sql.split(";");
			queryChanges(splits[0]);
			sql = splits[1];
		}
		return getAllRows(sql, attributes);		
	}
	
	
	public boolean insert(HashMap<String, String> m) {
		String table = m.get("table");
		m.remove("table");
		String sql = "insert into " + table +"(" + String.join(",", m.keySet())
		+ ") VALUES(" + String.join(",", m.values()) + ")";
		
		try {
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	public boolean update() {
		String sql = "Update table set attribute where attribute=";
		try {
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean delete() {
		String sql = "Delete from table where";
		try {
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
