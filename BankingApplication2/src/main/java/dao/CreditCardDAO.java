package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.CreditCard;

public class CreditCardDAO {
	public int createCreditCard(CreditCard card) throws ClassNotFoundException {
        String INSERT_CARD_SQL = "INSERT INTO creditCards" +
            " (cardNumber, cvv, cardLimit) VALUES " +
            " (?, ?, ?);";
        int result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CARD_SQL)) {
            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setInt(2, card.getCvv());
            preparedStatement.setInt(3, card.getCardLimit());
            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
	
	public ArrayList<CreditCard> getAllCreditCards(int userId) throws ClassNotFoundException {
		ArrayList<CreditCard> creditCards = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from creditCards join userCreditCardMapping on userCreditCardMapping.cardId = creditCards.id and ? = userCreditCardMapping.userId")) {
			preparedStatement.setInt(1, userId);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				CreditCard creditCard = new CreditCard();
				creditCard.setCardLimit(rs.getInt("cardLimit"));
				creditCard.setIsApproved(rs.getInt("isApproved"));
				creditCard.setCardNumber(rs.getString("cardNumber"));
				creditCard.setCvv(rs.getInt("cvv"));
				creditCards.add(creditCard);
			}
			return creditCards;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public int checkCreditCardNumber(String cardNumber) throws ClassNotFoundException {
		String CHECK_CARD_NUMBER_SQL = "SELECT * FROM creditCards WHERE cardNumber = '"+cardNumber+"'";
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
		String CHECK_CVV_NUMBER_SQL = "SELECT * FROM creditCards WHERE cvv = '"+cvv+"'";
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
	
	public int getCardIdUsingCardNumber(String cardNumber) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select id from creditCards where cardNumber = '"+cardNumber+"'")) {
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
	
	public String getUserRequestingCreditCardApproval(String cardNumber) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select users.userName from creditCards join userCreditCardMapping on userCreditCardMapping.cardId = creditCards.id join users on users.id = userCreditCardMapping.userId where cardNumber = '"+cardNumber+"'")) {
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
	
	public ArrayList<CreditCard> getAllUnapprovedCreditCards() throws ClassNotFoundException {
		ArrayList<CreditCard> creditCards = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from creditCards where isApproved = '0'")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				CreditCard creditCard = new CreditCard();
				creditCard.setCardLimit(rs.getInt("cardLimit"));
				creditCard.setIsApproved(rs.getInt("isApproved"));
				creditCard.setCvv(rs.getInt("cvv"));
				creditCard.setCardNumber(rs.getString("cardNumber"));
				creditCards.add(creditCard);
			}
			return creditCards;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public int approveCreditCard(String cardNumber) throws ClassNotFoundException{
		int result = 0;
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("update creditCards set isApproved = '1' where cardNumber = '"+cardNumber+"'")) {
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
