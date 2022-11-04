package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Transaction;

public class TransactionDAO {
	public int createTransaction(Transaction transaction) throws ClassNotFoundException {
        String INSERT_ACCOUNT_SQL = "INSERT INTO transactions" +
            " (fromAccountNumber, toAccountNumber, amount, purpose, transactionDate) VALUES " +
            " (?, ?, ?, ?, now());";
        int result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT_SQL)) {
            preparedStatement.setString(1, transaction.getFromAccountNumber());
            preparedStatement.setString(2, transaction.getToAccountNumber());
            preparedStatement.setInt(3, transaction.getAmount());
            preparedStatement.setString(4, transaction.getPurpose());
            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
	
	public Transaction getOneTransaction(int id) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from transactions where id = ?")) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			Transaction transaction = new Transaction();
			while(rs.next()) {
				transaction.setPurpose(rs.getString("purpose"));
				transaction.setFromAccountNumber(rs.getString("fromAccountNumber"));
				transaction.setId(rs.getInt("id"));
				transaction.setToAccountNumber(rs.getString("toAccountNumber"));
				transaction.setAmount(rs.getInt("amount"));
				transaction.setTransactionDate(rs.getString("transactionDate"));
			}
			return transaction;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public ArrayList<Transaction> getAllTransactionsForAccount(int accountNumber) throws ClassNotFoundException {
		ArrayList<Transaction> transactions = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from transactions where fromAccountNumber = ? or toAccountNumber = ?")) {
			preparedStatement.setInt(1, accountNumber);
			preparedStatement.setInt(2, accountNumber);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Transaction transaction = new Transaction();
				transaction.setPurpose(rs.getString("purpose"));
				transaction.setId(rs.getInt("id"));
				transaction.setFromAccountNumber(rs.getString("fromAccountNumber"));
				transaction.setToAccountNumber(rs.getString("toAccountNumber"));
				transaction.setAmount(rs.getInt("amount"));
				transaction.setTransactionDate(rs.getString("transactionDate"));
				transactions.add(transaction);
			}
			return transactions;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public ArrayList<Transaction> getAllFilteredTransactionsForAccount(String fromAccountNumber, String fromDate, String toDate) throws ClassNotFoundException {
		ArrayList<Transaction> transactions = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from transactions where (fromAccountNumber = '"+fromAccountNumber+"' or toAccountNumber = '"+fromAccountNumber+"') and (date(transactionDate) >= '"+fromDate+"' and date(transactionDate) <= '"+toDate+"')")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Transaction transaction = new Transaction();
				transaction.setPurpose(rs.getString("purpose"));
				transaction.setId(rs.getInt("id"));
				transaction.setFromAccountNumber(rs.getString("fromAccountNumber"));
				transaction.setToAccountNumber(rs.getString("toAccountNumber"));
				transaction.setAmount(rs.getInt("amount"));
				transaction.setTransactionDate(rs.getString("transactionDate"));
				transactions.add(transaction);
			}
			return transactions;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public ArrayList<Transaction> getAllTransactions() throws ClassNotFoundException {
		ArrayList<Transaction> transactions = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from transactions")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Transaction transaction = new Transaction();
				transaction.setPurpose(rs.getString("purpose"));
				transaction.setId(rs.getInt("id"));
				transaction.setFromAccountNumber(rs.getString("fromAccountNumber"));
				transaction.setToAccountNumber(rs.getString("toAccountNumber"));
				transaction.setAmount(rs.getInt("amount"));
				transaction.setTransactionDate(rs.getString("transactionDate"));
				transactions.add(transaction);
			}
			return transactions;
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
