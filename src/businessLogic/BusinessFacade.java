package businessLogic;

import java.util.Vector;

import database.DataAccess;

public class BusinessFacade {
	DataAccess db;

	public BusinessFacade(String username, String password) {
		db = new DataAccess(username, password);
	}
	
	public boolean login(String username, String password) {
		db.connection();
		boolean login = db.login(username, password);
		db.close();
		return login;
	}
	
	public Vector<String> displayTables() {
		db.connection();
		Vector<String> tables = db.displayTables();
		db.close();
		return tables;
	}
	
	public Vector<String> displayAttributeTable(String table) {
		db.connection();
		Vector<String> attributes = db.displayAttributeTable(table);
		db.close();
		return attributes;
	}
	
	public Vector<Vector<String>> table(String table){
		db.connection();
		Vector<Vector<String>> table_ = db.table(table);
		db.close();
		return table_;
	}
	
	
}
