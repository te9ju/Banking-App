package dao;

import java.sql.*;
import java.util.ArrayList;

import bean.User;

public class UserDAO {
	public int registerUser(User user) throws ClassNotFoundException {
        String INSERT_USER_SQL = "INSERT INTO users" +
            " (firstName, lastName, address, phoneNumber, username, password, addedDate) VALUES " +
            " (?, ?, ?, ?, ?, ?, now());";

        int result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, String.valueOf(user.getPhoneNumber()));
            preparedStatement.setString(5, user.getUserName());
            preparedStatement.setString(6, user.getPassword());
            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
	
	public int registerAdmin(User user) throws ClassNotFoundException {
        String INSERT_USER_SQL = "INSERT INTO users" +
            " (firstName, lastName, address, phoneNumber, username, password, isAdmin, addedDate, isApproved) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?, now(), 1);";

        int result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, String.valueOf(user.getPhoneNumber()));
            preparedStatement.setString(5, user.getUserName());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setInt(7, user.getIsAdmin());
            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
	public int updateUser(User user, String updatedBy) throws ClassNotFoundException{
		int result = 0;
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("update users set firstName = '"+user.getFirstName()+"', lastName = '"+user.getLastName()+"', address = '"+user.getAddress()+"', phoneNumber = '"+user.getPhoneNumber()+"', updatedBy = '"+updatedBy+"', updatedDate = now() where id = '"+user.getId()+"'")) {
			System.out.println(preparedStatement);
			result = preparedStatement.executeUpdate();
			return result;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return result;
	}
	
	public int approveUser(int id) throws ClassNotFoundException{
		int result = 0;
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("update users set isApproved = '1' where id = '"+id+"'")) {
			System.out.println(preparedStatement);
			result = preparedStatement.executeUpdate();
			return result;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return result;
	}
	
	public User getOneUser(String username) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from users where userName = ?")) {
			preparedStatement.setString(1, username);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			User user = new User();
			while(rs.next()) {
				user.setIsApproved(rs.getInt("isApproved"));
				user.setId(rs.getInt("id"));
				user.setFirstName(rs.getString("firstName"));
				user.setAddress(rs.getString("address"));
				user.setLastName(rs.getString("lastName"));
				user.setPassword(rs.getString("password"));
				user.setPhoneNumber(Long.parseLong(rs.getString("phoneNumber")));
				user.setAddedDate(rs.getString("addedDate"));
				user.setIsAdmin(rs.getInt("isAdmin"));
				user.setUserName(username);
			}
			return user;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public boolean validate(User user) throws ClassNotFoundException {
		boolean status = false;
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from users where userName = ? and password = ? and isAdmin = '"+user.getIsAdmin()+"'")) {
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassword());
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			status = rs.next();
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return status;
	}
	
	public boolean checkPhoneNumber(String phoneNumber) throws ClassNotFoundException {
		String CHECK_PHONE_NUMBER_SQL = "SELECT * FROM users WHERE phoneNumber = '"+phoneNumber+"'";
		Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_PHONE_NUMBER_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
            	return true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
	}
	
	public boolean checkUserName(String userName) throws ClassNotFoundException {
		String CHECK_USERNAME_SQL = "SELECT * FROM users WHERE userName = '"+userName+"'";
		Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_USERNAME_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
            	return true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
	}
	
	public boolean checkPhoneNumberEdit(String phoneNumber, int id) throws ClassNotFoundException {
		String CHECK_PHONE_NUMBER_SQL = "SELECT * FROM users WHERE phoneNumber = '"+phoneNumber+"' and id = '"+id+"'";
		Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_PHONE_NUMBER_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
            	return false;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return true;
	}
	
	public boolean checkUserNameEdit(String userName, int id) throws ClassNotFoundException {
		String CHECK_USERNAME_SQL = "SELECT * FROM users WHERE userName = '"+userName+"' and id = '"+id+"'";
		Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_USERNAME_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
            	return false;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return true;
	}
	
	public ArrayList<User> getAllUsers() throws ClassNotFoundException {
		ArrayList<User> users = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from users where isAdmin = '0'")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setIsApproved(rs.getInt("isApproved"));
				user.setId(rs.getInt("id"));
				user.setAddedDate(rs.getString("addedDate"));
				user.setAddress(rs.getString("address"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setPhoneNumber(Long.valueOf(rs.getString("phoneNumber")));
				user.setUserName(rs.getString("userName"));
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public ArrayList<User> getSearchedUsers(String searchQuery) throws ClassNotFoundException {
		ArrayList<User> users = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from users where isAdmin = '0' and firstName LIKE '%"+searchQuery+"%' or lastName LIKE '%"+searchQuery+"%' or userName LIKE '%"+searchQuery+"%'")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setIsApproved(rs.getInt("isApproved"));
				user.setId(rs.getInt("id"));
				user.setAddedDate(rs.getString("addedDate"));
				user.setAddress(rs.getString("address"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setPhoneNumber(Long.valueOf(rs.getString("phoneNumber")));
				user.setUserName(rs.getString("userName"));
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public ArrayList<User> getAllUnapprovedUsers() throws ClassNotFoundException {
		ArrayList<User> users = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from users where isAdmin = '0' and isApproved = '0'")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setIsApproved(rs.getInt("isApproved"));
				user.setId(rs.getInt("id"));
				user.setAddedDate(rs.getString("addedDate"));
				user.setAddress(rs.getString("address"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setPhoneNumber(Long.valueOf(rs.getString("phoneNumber")));
				user.setUserName(rs.getString("userName"));
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return null;
	}
	
	public ArrayList<User> getAllAdmins() throws ClassNotFoundException {
		ArrayList<User> users = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bankingApp2?useSSL=false", "root", "tej280402");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from users where isAdmin = '1'")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setIsApproved(rs.getInt("isApproved"));
				user.setId(rs.getInt("id"));
				user.setAddedDate(rs.getString("addedDate"));
				user.setAddress(rs.getString("address"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setPhoneNumber(Long.valueOf(rs.getString("phoneNumber")));
				user.setUserName(rs.getString("userName"));
				users.add(user);
			}
			return users;
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
