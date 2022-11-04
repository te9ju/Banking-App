package web;

import java.io.IOException;
import bean.User;
import dao.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EditUserProfile
 */
@WebServlet("/editProfile")
public class EditUserProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
    
	public void init() {
		userDAO = new UserDAO();
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("editProfile.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int isAdmin = Integer.parseInt(request.getParameter("isAdmin"));
		if(isAdmin == 1) {
			String firstName = request.getParameter("firstName");
			int userId = Integer.parseInt(request.getParameter("userId"));
			String lastName = request.getParameter("lastName");
			String address = request.getParameter("address");
			String password = request.getParameter("password");
			String phoneNumber = request.getParameter("phoneNumber");
			String userName = request.getParameter("userName");
			String adminUsername = request.getParameter("adminUsername");
			User user = new User();
			user.setUserName(userName);
			user.setIsAdmin(0);
			user.setId(userId);
			user.setFirstName(firstName);
			user.setPassword(password);
			user.setLastName(lastName);
			user.setPhoneNumber(Long.parseLong(phoneNumber));
			user.setAddress(address);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("adminEditUser.jsp");
			try {
				if(userDAO.checkPhoneNumberEdit(phoneNumber, userId)) {
//					System.out.println("Inside the phone number check if");
					request.setAttribute("phoneNumberCheck", "failure");
					requestDispatcher.forward(request, response);
					return;
				}
				if(userDAO.checkUserNameEdit(userName, userId)) {
					request.setAttribute("userNameCheck", "failure");
					requestDispatcher.forward(request, response);
					return;
				}
				if(userDAO.updateUser(user, adminUsername) > 0) {
					request.setAttribute("status", "success");
				}
				else {
					request.setAttribute("status", "failure");
				}
				requestDispatcher.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(isAdmin == 0) {
			String firstName = request.getParameter("firstName");
			int userId = Integer.parseInt(request.getParameter("userId"));
			String lastName = request.getParameter("lastName");
			String address = request.getParameter("address");
			String password = request.getParameter("password");
			String phoneNumber = request.getParameter("phoneNumber");
			String userName = request.getParameter("userName");
			User user = new User();
			user.setUserName(userName);
			user.setIsAdmin(0);
			user.setId(userId);
			user.setFirstName(firstName);
			user.setPassword(password);
			user.setLastName(lastName);
			user.setPhoneNumber(Long.parseLong(phoneNumber));
			user.setAddress(address);
			user.setIsApproved(1);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("editProfile.jsp");
			try {
				if(userDAO.checkPhoneNumberEdit(phoneNumber, userId)) {
//					System.out.println("Inside the phone number check if");
					request.setAttribute("phoneNumberCheck", "failure");
					requestDispatcher.forward(request, response);
					return;
				}
				if(userDAO.checkUserNameEdit(userName, userId)) {
					request.setAttribute("userNameCheck", "failure");
					requestDispatcher.forward(request, response);
					return;
				}
				if(userDAO.updateUser(user, userName) > 0) {
					HttpSession session = request.getSession(true);
					session.setAttribute("user", user);
					request.setAttribute("status", "success");
				}
				else {
					request.setAttribute("status", "failure");
				}
				requestDispatcher.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
