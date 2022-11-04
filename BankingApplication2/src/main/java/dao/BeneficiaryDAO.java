package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.Beneficiary;

public class BeneficiaryDAO {
	public int createBeneficiary(Beneficiary beneficiary) throws ClassNotFoundException {
        String INSERT_BENEFICIARY_SQL = "INSERT INTO beneficiaries" +
            " (beneficiaryName, transferLimit, aliasName, accountNumber) VALUES " +
            " (?, ?, ?, ?);";
        int result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BENEFICIARY_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, beneficiary.getBeneficiaryName());
            preparedStatement.setInt(2, beneficiary.getTransferLimit());
            preparedStatement.setString(3, beneficiary.getAliasName());
            preparedStatement.setString(4, String.valueOf(beneficiary.getAccountNumber()));
            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
	
	public int checkUserBeneficiary(int userId, int accountNumber) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from beneficiaries join userBeneficiaryMapping on userBeneficiaryMapping.beneficiaryId = beneficiaries.id and ? = userBeneficiaryMapping.userId and beneficiaries.accountNumber = ?")) {
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, accountNumber);
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
	
	public int getBeneficiaryId(int userId, int accountNumber) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select id from beneficiaries join userBeneficiaryMapping on userBeneficiaryMapping.beneficiaryId = beneficiaries.id and ? = userBeneficiaryMapping.userId and beneficiaries.accountNumber = ?")) {
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, accountNumber);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return -1;
	}
	
	public int getTransferLimitForBeneficiary(int userId, int accountNumber) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select transferLimit from beneficiaries join userBeneficiaryMapping on userBeneficiaryMapping.beneficiaryId = beneficiaries.id and ? = userBeneficiaryMapping.userId and beneficiaries.accountNumber = ?")) {
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, accountNumber);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				return rs.getInt("transferLimit");
			}
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return -1;
	}
	
	public ArrayList<Beneficiary> getAllBeneficiariesForUser(int userId) throws ClassNotFoundException {
		ArrayList<Beneficiary> beneficiaries = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection	
						.prepareStatement("select beneficiaries.beneficiaryName, beneficiaries.transferLimit, beneficiaries.aliasName, beneficiaries.accountNumber from beneficiaries join userBeneficiaryMapping on userBeneficiaryMapping.userId = ? and userBeneficiaryMapping.beneficiaryId = beneficiaries.id")) {
			preparedStatement.setInt(1, userId);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Beneficiary beneficiary = new Beneficiary();
				beneficiary.setAccountNumber(Integer.parseInt(rs.getString("accountNumber")));
				beneficiary.setAliasName(rs.getString("aliasName"));
				beneficiary.setBeneficiaryName(rs.getString("beneficiaryName"));
				beneficiary.setTransferLimit(rs.getInt("transferLimit"));
				beneficiaries.add(beneficiary);
			}
			return beneficiaries;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
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
