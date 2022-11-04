package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GiftedCardMappingDAO {
	public int createGiftedCardDetail(int userId, int cardId) throws ClassNotFoundException {
        String INSERT_GIFTED_CARD_DETAIL_SQL = "INSERT INTO giftedCardMapping" +
            " (giftedByUserId, giftCardId) VALUES " +
            " (?, ?);";
        int result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GIFTED_CARD_DETAIL_SQL)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, cardId);
            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
	
	public String getUserGifting(int giftCardId) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select users.userName from users join giftedCardMapping on giftedCardMapping.giftedByUserId = users.id where giftCardId = '"+giftCardId+"'")) {
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
