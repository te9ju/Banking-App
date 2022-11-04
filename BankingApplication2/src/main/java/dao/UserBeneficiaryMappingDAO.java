package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBeneficiaryMappingDAO {
	public int createUserBeneficiaryMapping(int userId, int beneficiaryId) throws ClassNotFoundException {
        String INSERT_USER_BENEFICIARY_MAPPING_SQL = "INSERT INTO userBeneficiaryMapping" +
            " (userId, beneficiaryId) VALUES " +
            " (?, ?);";
        int result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_BENEFICIARY_MAPPING_SQL)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, beneficiaryId);
            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
	
	public int checkUserBeneficiaryMapping(int userId, int beneficiaryId) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from userBeneficiaryMapping where userId = '"+userId+"' and beneficiaryId = '"+beneficiaryId+"'")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				return 1;
			}
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return -1;
	}
	
	private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
