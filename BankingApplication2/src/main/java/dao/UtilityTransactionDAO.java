package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.UtilityTransaction;

public class UtilityTransactionDAO {
	public int createUtilityTransaction(UtilityTransaction utilitytransaction) throws ClassNotFoundException {
        String INSERT_UTILITY_TRANSACTION_SQL = "INSERT INTO utilityServicesTransactions" +
            " (fromAccountNumber, serviceConsumerNumber, serviceId, billAmount, purpose, transactionDate) VALUES " +
            " (?, ?, ?, ?, ?, now());";
        int result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_UTILITY_TRANSACTION_SQL)) {
            preparedStatement.setString(1, utilitytransaction.getFromAccountNumber());
            preparedStatement.setString(2, utilitytransaction.getServiceConsumerNumber());
            preparedStatement.setInt(3, utilitytransaction.getServiceId());
            preparedStatement.setInt(4, utilitytransaction.getBillAmount());
            preparedStatement.setString(5, utilitytransaction.getPurpose());
            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
	
	public ArrayList<UtilityTransaction> getAllFilteredUtilityTransactionsForAccount(String fromAccountNumber, String fromDate, String toDate) throws ClassNotFoundException {
		ArrayList<UtilityTransaction> utilityTransactions = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from utilityServicesTransactions where (fromAccountNumber = '"+fromAccountNumber+"') and (date(transactionDate) >= '"+fromDate+"' and date(transactionDate) <= '"+toDate+"')")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				UtilityTransaction utilityTransaction = new UtilityTransaction();
				utilityTransaction.setPurpose(rs.getString("purpose"));
				utilityTransaction.setId(rs.getInt("id"));
				utilityTransaction.setServiceConsumerNumber(rs.getString("serviceConsumerNumber"));
				utilityTransaction.setBillAmount(rs.getInt("billAmount"));
				utilityTransaction.setServiceId(rs.getInt("serviceId"));
				utilityTransaction.setFromAccountNumber(rs.getString("fromAccountNumber"));
				utilityTransaction.setTransactionDate(rs.getString("transactionDate"));
				utilityTransactions.add(utilityTransaction);
			}
			return utilityTransactions;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public UtilityTransaction getOneUtilityTransaction(int id) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from utilityServicesTransactions where id = ?")) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			UtilityTransaction utilityTransaction = new UtilityTransaction();
			while(rs.next()) {
				utilityTransaction.setId(rs.getInt("id"));
				utilityTransaction.setPurpose(rs.getString("purpose"));
				utilityTransaction.setFromAccountNumber(rs.getString("fromAccountNumber"));
				utilityTransaction.setServiceConsumerNumber(rs.getString("serviceConsumerNumber"));
				utilityTransaction.setBillAmount(rs.getInt("billAmount"));
				utilityTransaction.setServiceId(rs.getInt("serviceId"));
				utilityTransaction.setTransactionDate(rs.getString("transactionDate"));
			}
			return utilityTransaction;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public ArrayList<UtilityTransaction> getAllUtilityTransactionsForAccount(int accountNumber) throws ClassNotFoundException {
		ArrayList<UtilityTransaction> utilityTransactions = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from utilityServicesTransactions where fromAccountNumber = ?")) {
			preparedStatement.setInt(1, accountNumber);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				UtilityTransaction utilityTransaction = new UtilityTransaction();
				utilityTransaction.setId(rs.getInt("id"));
				utilityTransaction.setPurpose(rs.getString("purpose"));
				utilityTransaction.setServiceId(rs.getInt("serviceId"));
				utilityTransaction.setFromAccountNumber(rs.getString("fromAccountNumber"));
				utilityTransaction.setServiceConsumerNumber(rs.getString("serviceConsumerNumber"));
				utilityTransaction.setBillAmount(rs.getInt("billAmount"));
				utilityTransaction.setTransactionDate(rs.getString("transactionDate"));
				utilityTransactions.add(utilityTransaction);
			}
			return utilityTransactions;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public ArrayList<UtilityTransaction> getAllUtilityTransactions() throws ClassNotFoundException {
		ArrayList<UtilityTransaction> utilityTransactions = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from utilityServicesTransactions")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				UtilityTransaction utilityTransaction = new UtilityTransaction();
				utilityTransaction.setId(rs.getInt("id"));
				utilityTransaction.setPurpose(rs.getString("purpose"));
				utilityTransaction.setServiceId(rs.getInt("serviceId"));
				utilityTransaction.setFromAccountNumber(rs.getString("fromAccountNumber"));
				utilityTransaction.setServiceConsumerNumber(rs.getString("serviceConsumerNumber"));
				utilityTransaction.setBillAmount(rs.getInt("billAmount"));
				utilityTransaction.setTransactionDate(rs.getString("transactionDate"));
				utilityTransactions.add(utilityTransaction);
			}
			return utilityTransactions;
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
