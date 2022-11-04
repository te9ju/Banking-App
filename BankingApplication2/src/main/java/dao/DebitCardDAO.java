package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.DebitCard;

public class DebitCardDAO {
	public int createDebitCard(DebitCard card) throws ClassNotFoundException {
        String INSERT_CARD_SQL = "INSERT INTO debitCards" +
            " (cardNumber, cvv, associatedAccountNumber) VALUES " +
            " (?, ?, ?);";
        int result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CARD_SQL)) {
            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setInt(2, card.getCvv());
            preparedStatement.setInt(3, card.getAssociatedAccountNumber());
            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
	
	public ArrayList<DebitCard> getAllDebitCards(int accountNumber) throws ClassNotFoundException {
		ArrayList<DebitCard> debitCards = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from debitCards where associatedAccountNumber = '"+accountNumber+"'")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				DebitCard debitCard = new DebitCard();
				debitCard.setIsApproved(rs.getInt("isApproved"));
				debitCard.setCardNumber(rs.getString("cardNumber"));
				debitCard.setCvv(rs.getInt("cvv"));
				debitCard.setAssociatedAccountNumber(Integer.parseInt(rs.getString("associatedAccountNumber")));
				debitCards.add(debitCard);
			}
			return debitCards;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public DebitCard getDebitCardUsingCardNumber(String cardNumber) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from debitCards where cardNumber = '"+cardNumber+"'")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			DebitCard debitCard = new DebitCard();
			while(rs.next()) {
				debitCard.setAssociatedAccountNumber(Integer.valueOf(rs.getString("associatedAccountNumber")));
				debitCard.setCardNumber(cardNumber);
				debitCard.setCvv(rs.getInt("cvv"));
				debitCard.setIsApproved(rs.getInt("isApproved"));
				return debitCard;
			}
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public int checkDebitCardNumber(String cardNumber) throws ClassNotFoundException {
		String CHECK_CARD_NUMBER_SQL = "SELECT * FROM debitCards WHERE cardNumber = '"+cardNumber+"'";
		int result = 0;
		Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_CARD_NUMBER_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            result = rs.getRow();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
	}
	
	public int checkCVV(int cvv) throws ClassNotFoundException {
		String CHECK_CVV_NUMBER_SQL = "SELECT * FROM debitCards WHERE cvv = '"+cvv+"'";
		int result = 0;
		Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_CVV_NUMBER_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            result = rs.getRow();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
	}
	
	public int getDebitCardIdUsingCardNumber(String cardNumber) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select id from debitCards where cardNumber = '"+cardNumber+"'")) {
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
	
	public String getUserRequestingDebitCardApproval(String cardNumber) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select users.userName from debitCards join accounts on accounts.accountNumber = debitCards.associatedAccountNumber join userAccountMapping on userAccountMapping.accountId = accounts.id join users on users.id = userAccountMapping.userId where debitCards.cardNumber = '"+cardNumber+"'")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				return rs.getString("userName");
			}
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return "";
	}
	
	public ArrayList<DebitCard> getAllUnapprovedDebitCards() throws ClassNotFoundException {
		ArrayList<DebitCard> debitCards = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from debitCards where isApproved = '0'")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				DebitCard debitCard = new DebitCard();
				debitCard.setIsApproved(rs.getInt("isApproved"));
				debitCard.setCvv(rs.getInt("cvv"));
				debitCard.setCardNumber(rs.getString("cardNumber"));
				debitCard.setAssociatedAccountNumber(Integer.parseInt(rs.getString("associatedAccountNumber")));
				debitCards.add(debitCard);
			}
			return debitCards;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public int approveDebitCard(String cardNumber) throws ClassNotFoundException{
		int result = 0;
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("update debitCards set isApproved = '1' where cardNumber = '"+cardNumber+"'")) {
			System.out.println(preparedStatement);
			result = preparedStatement.executeUpdate();
			return result;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return result;
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
