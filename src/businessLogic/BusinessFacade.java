package businessLogic;

import java.util.HashMap;
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
		//db.close();
		return login;
	}

	public Vector<String> displayTables() {
		//db.connection();
		Vector<String> tables = db.displayTables();
		//db.close();
		return tables;
	}

	public Vector<String> displayAttributeTable(String table) {
		//db.connection();
		Vector<String> attributes = db.displayAttributeTable(table);
		//db.close();
		return attributes;
	}

	public Vector<Vector<String>> table(String table) {
		//db.connection();
		Vector<Vector<String>> table_ = db.table(table);
		//db.close();
		return table_;
	}

	public Vector<String> queries() {
		return db.queries();
	}

	public Vector<Vector<String>> query(int i, Vector<String> attributes) {
		//db.connection();
		Vector<Vector<String>> q = db.query(i, attributes);
		//db.close();
		return q;
	}
	
	public boolean insert(HashMap<String, String> m) {
		boolean valid = db.insert(m);
		return valid;
	}
	
	public boolean update(String table, Vector<String> m) {
		boolean valid = db.update(table, m);
		return valid;
	}
	
	public boolean delete(String table, String attribute, String value) {
		boolean valid = db.delete(table, attribute, value);
		return valid;
	}
	
	public void close() {
		db.close();
	}

}
