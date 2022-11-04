package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Account;

public class AccountDAO {
	public int createAccount(Account account) throws ClassNotFoundException {
        String INSERT_ACCOUNT_SQL = "INSERT INTO accounts" +
            " (accountNumber, accountBalance, accountType) VALUES " +
            " (?, ?, ?);";
        int result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT_SQL)) {
            preparedStatement.setString(1, String.valueOf(account.getAccountNumber()));
            preparedStatement.setInt(2, account.getAccountBalance());
            preparedStatement.setString(3, account.getAccountType());
            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
	
	public int checkAccountNumber(int accountNumber) throws ClassNotFoundException {
		String CHECK_ACCOUNT_NUMBER_SQL = "SELECT * FROM accounts WHERE accountNumber = '"+accountNumber+"'";
		int result = 0;
		Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_ACCOUNT_NUMBER_SQL)) {
            ResultSet rs  = preparedStatement.executeQuery();
            if(rs.next()) {
            	return 1;
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
	}
	
	public Account getOneAccount(int userId) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from accounts join userAccountMapping on userAccountMapping.accountId = accounts.id and ? = userAccountMapping.userId")) {
			preparedStatement.setInt(1, userId);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			Account account = new Account();
			while(rs.next()) {
				account.setAccountType(rs.getString("accountType"));
				account.setAccountBalance(rs.getInt("accountBalance"));
				account.setAccountNumber(Integer.parseInt(rs.getString("accountNumber")));
				return account;
			}
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public ArrayList<Account> getAllAccounts(int userId) throws ClassNotFoundException {
		ArrayList<Account> accounts = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from accounts join userAccountMapping on userAccountMapping.accountId = accounts.id and ? = userAccountMapping.userId")) {
			preparedStatement.setInt(1, userId);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Account account = new Account();
				account.setAccountType(rs.getString("accountType"));
				account.setAccountBalance(rs.getInt("accountBalance"));
				account.setAccountNumber(Integer.parseInt(rs.getString("accountNumber")));
				accounts.add(account);
			}
			return accounts;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public int updateAccountBalance(int accountNumber, int balance) throws ClassNotFoundException {
		int result = 0;
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("update accounts set accountBalance = '"+balance+"' where accountNumber = '"+accountNumber+"'")) {
			System.out.println(preparedStatement);
			result = preparedStatement.executeUpdate();
			return result;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return result;
	}
	
	public Account getOneAccountUsingAccountNumber(int accountNumber) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from accounts where accountNumber = '"+accountNumber+"'")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			Account account = new Account();
			while(rs.next()) {
				account.setAccountType(rs.getString("accountType"));
				account.setAccountBalance(rs.getInt("accountBalance"));
				account.setAccountNumber(accountNumber);
			}
			return account;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public int getAccountIdUsingAccountNumber(int accountNumber) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select id from accounts where accountNumber = '"+accountNumber+"'")) {
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
