import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SavePointExample {
	private static final String INSERT_SQL = "INSERT INTO employee " + "(EMP_ID, NAME, DOB) VALUES (?,?,?)";

	public static void main(String[] args) {
		String jdbcUrl = "jdbc:mysql://dif-mysql.ehu.es:3306/DBDI05";
		String username = "DBDI05";
		String password = "zwV4Y9Mk";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);) {

			// Disable auto commit mode
			conn.setAutoCommit(false);

			try (PreparedStatement insertStmt = conn.prepareStatement(INSERT_SQL);) {

				// Insert 1st record
				insertStmt.setInt(1, 1);
				insertStmt.setString(2, "Michael");
				insertStmt.setDate(3, new Date(dateFormat.parse("1995-07-01").getTime()));
				insertStmt.executeUpdate();

				// Insert 2st record
				insertStmt.setInt(1, 2);
				insertStmt.setString(2, "Sunil");
				insertStmt.setDate(3, new Date(dateFormat.parse("1988-03-22").getTime()));
				insertStmt.executeUpdate();

				// Insert 3st record
				insertStmt.setInt(1, 3);
				insertStmt.setString(2, "Mike");
				insertStmt.setDate(3, new Date(dateFormat.parse("1980-05-12").getTime()));
				insertStmt.executeUpdate();

				// Create Savepoint
				Savepoint savepoint = conn.setSavepoint();

				// Insert 4st record
				insertStmt.setInt(1, 4);
				insertStmt.setString(2, "Manish");
				insertStmt.setDate(3, new Date(dateFormat.parse("1992-01-21").getTime()));
				insertStmt.executeUpdate();

				// Insert 5st record
				insertStmt.setInt(1, 5);
				insertStmt.setString(2, "Albert");
				insertStmt.setDate(3, new Date(dateFormat.parse("1972-07-05").getTime()));
				insertStmt.executeUpdate();

				// Rollback to savepoint
				conn.rollback(savepoint);

				// Commit statement
				conn.commit();

				System.out.println("Transaction is commited successfully.");
			} catch (SQLException | ParseException e) {
				e.printStackTrace();
				if (conn != null) {
					try {
						// Roll back transaction
						System.out.println("Transaction is being rolled back.");
						conn.rollback();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
