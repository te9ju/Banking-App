package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.GiftCard;

public class GiftCardDAO {
	public int createGiftCard(GiftCard card) throws ClassNotFoundException {
        String INSERT_CARD_SQL = "INSERT INTO giftCards" +
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
	
	public ArrayList<GiftCard> getAllGiftCards(int userId) throws ClassNotFoundException {
		ArrayList<GiftCard> giftCards = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from giftCards join userGiftCardMapping on userGiftCardMapping.giftCardId = giftCards.id and ? = userGiftCardMapping.userId")) {
			preparedStatement.setInt(1, userId);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				GiftCard giftCard = new GiftCard();
				giftCard.setCardLimit(rs.getInt("cardLimit"));
				giftCard.setCardNumber(rs.getString("cardNumber"));
				giftCard.setCvv(rs.getInt("cvv"));
				giftCards.add(giftCard);
			}
			return giftCards;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public int checkGiftCardNumber(String cardNumber) throws ClassNotFoundException {
		String CHECK_CARD_NUMBER_SQL = "SELECT * FROM giftCards WHERE cardNumber = '"+cardNumber+"'";
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
		String CHECK_CVV_NUMBER_SQL = "SELECT * FROM giftCards WHERE cvv = '"+cvv+"'";
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
						.prepareStatement("select id from giftCards where cardNumber = '"+cardNumber+"'")) {
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
