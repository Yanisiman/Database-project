import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

	public static void main(String[] args) {
		// Load driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Success loading Driver!");
		} catch (Exception e) {
			System.out.println("Fail loading Driver!");
			e.printStackTrace();
		}

		// Connect to the database
		Connection connection = null;
		String url = "jdbc:mysql://dif-mysql.ehu.es:3306/DBDI05";
		try {
			connection = DriverManager.getConnection(url, "DBDI05", "zwV4Y9Mk");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String sql = "select * from employee";
		Statement stmt = null;
		ResultSet re = null;
		try {
			stmt = connection.createStatement();
			re = stmt.executeQuery(sql);
			while (re.next()) {
				String id = re.getString(1);
				String title = re.getString(2);
				System.out.print("course_id:" + id + ":\n");
				System.out.print("title:" + title + ":\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*
		// Insert example
		String sqlInsert = "insert into course values (?, ?, ?, ?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sqlInsert);
			preparedStatement.setString(1, "999");
			preparedStatement.setString(2, "Databases I");
			preparedStatement.setString(3, "Physics");
			preparedStatement.setInt(4, 6);
			// int rowsAffected = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Update example
		String sqlUpdate = "update course set title=? where course_id=?";
		try {
			preparedStatement = connection.prepareStatement(sqlUpdate);
			// preparedStatement.setString(1, "999");
			preparedStatement.setString(1, "Databases II");
			preparedStatement.setString(2, "999");
			int rowsAffected = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Get Metadata
		ResultSetMetaData rsmd;
		try {
			rsmd = re.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				System.out.print("Column name: " + rsmd.getColumnName(i) + ": ");
				System.out.println("Column type: " + rsmd.getColumnTypeName(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/

		// Close everything
		try {
			re.close();
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
