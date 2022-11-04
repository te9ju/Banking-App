package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.GiftCardTransaction;

public class GiftCardTransactionDAO {
	public int createGiftCardTransaction(GiftCardTransaction giftCardTransaction) throws ClassNotFoundException {
        String INSERT_GIFT_SQL = "INSERT INTO giftCardTransactions" +
            " (fromAccountNumber, toUserName, amount, purpose, transactionDate) VALUES " +
            " (?, ?, ?, ?, now());";
        int result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GIFT_SQL)) {
            preparedStatement.setString(1, giftCardTransaction.getFromAccountNumber());
            preparedStatement.setString(2, giftCardTransaction.getToUserName());
            preparedStatement.setInt(3, giftCardTransaction.getAmount());
            preparedStatement.setString(4, giftCardTransaction.getPurpose());
            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
	
	public GiftCardTransaction getOneGiftCardTransaction(int id) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from giftCardTransactions where id = ?")) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			GiftCardTransaction giftCardTransaction = new GiftCardTransaction();
			while(rs.next()) {
				giftCardTransaction.setToUserName(rs.getString("toUserName"));
				giftCardTransaction.setPurpose(rs.getString("purpose"));
				giftCardTransaction.setFromAccountNumber(rs.getString("fromAccountNumber"));
				giftCardTransaction.setId(rs.getInt("id"));
				giftCardTransaction.setAmount(rs.getInt("amount"));
				giftCardTransaction.setTransactionDate(rs.getString("transactionDate"));
			}
			return giftCardTransaction;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public ArrayList<GiftCardTransaction> getAllGiftCardTransactionsForAccount(int accountNumber) throws ClassNotFoundException {
		ArrayList<GiftCardTransaction> giftCardTransactions = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from giftCardTransactions where fromAccountNumber = ?")) {
			preparedStatement.setInt(1, accountNumber);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				GiftCardTransaction giftCardTransaction = new GiftCardTransaction();
				giftCardTransaction.setPurpose(rs.getString("purpose"));
				giftCardTransaction.setId(rs.getInt("id"));
				giftCardTransaction.setFromAccountNumber(rs.getString("fromAccountNumber"));
				giftCardTransaction.setToUserName(rs.getString("toUserName"));
				giftCardTransaction.setAmount(rs.getInt("amount"));
				giftCardTransaction.setTransactionDate(rs.getString("transactionDate"));
				giftCardTransactions.add(giftCardTransaction);
			}
			System.out.println("Returning gift card transactions");
			return giftCardTransactions;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		System.out.println("Returning gift card transactions = null");
		return null;
	}
	
	public ArrayList<GiftCardTransaction> getAllFilteredGiftCardTransactionsForAccount(String fromAccountNumber, String fromDate, String toDate) throws ClassNotFoundException {
		ArrayList<GiftCardTransaction> giftCardTransactions = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from giftCardTransactions where (fromAccountNumber = '"+fromAccountNumber+"') and (date(transactionDate) >= '"+fromDate+"' and date(transactionDate) <= '"+toDate+"')")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				GiftCardTransaction giftCardTransaction = new GiftCardTransaction();
				giftCardTransaction.setPurpose(rs.getString("purpose"));
				giftCardTransaction.setId(rs.getInt("id"));
				giftCardTransaction.setFromAccountNumber(rs.getString("fromAccountNumber"));
				giftCardTransaction.setToUserName(rs.getString("toUserName"));
				giftCardTransaction.setAmount(rs.getInt("amount"));
				giftCardTransaction.setTransactionDate(rs.getString("transactionDate"));
				giftCardTransactions.add(giftCardTransaction);
			}
			return giftCardTransactions;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public ArrayList<GiftCardTransaction> getAllGiftCardTransactions() throws ClassNotFoundException {
		ArrayList<GiftCardTransaction> giftCardTransactions = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from giftCardTransactions")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				GiftCardTransaction  giftCardTransaction = new GiftCardTransaction();
				giftCardTransaction.setPurpose(rs.getString("purpose"));
				giftCardTransaction.setId(rs.getInt("id"));
				giftCardTransaction.setFromAccountNumber(rs.getString("fromAccountNumber"));
				giftCardTransaction.setToUserName(rs.getString("toUserName"));
				giftCardTransaction.setAmount(rs.getInt("amount"));
				giftCardTransaction.setTransactionDate(rs.getString("transactionDate"));
				giftCardTransactions.add(giftCardTransaction);
			}
			return giftCardTransactions;
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
