package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;

/**
 * Servlet implementation class LoginUserServlet
 */
@WebServlet("/login")
public class LoginUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    
    public void init() {
		userDAO = new UserDAO();
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("loginUser.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int isAdmin;
		if(request.getParameter("isAdmin").equals("user")) {
			isAdmin = 0;
		}
		else {
			isAdmin = 1;
		}
		if(isAdmin == 0) {
			User user = new User();
			user.setUserName(username);
			user.setPassword(password);
			try {
				String destPage;
				if (userDAO.validate(user)) {
					User currentUser = userDAO.getOneUser(username);
					destPage = "otp.jsp";
					HttpSession session = request.getSession(true);
					session.setAttribute("user", currentUser);
				} else {
					destPage = "loginUser.jsp";
					request.setAttribute("status", "failure");
				}
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
				requestDispatcher.forward(request, response);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		else if(isAdmin == 1) {
			User user = new User();
			user.setUserName(username);
			user.setPassword(password);
			user.setIsAdmin(isAdmin);
			try {
				String destPage;
				if (userDAO.validate(user)) {
					User currentUser = userDAO.getOneUser(username);
					destPage = "home.jsp";
					HttpSession session = request.getSession(true);
					session.setAttribute("user", currentUser);
				} else {
					destPage = "loginAdmin.jsp";
					request.setAttribute("status", "failure");
				}
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
				requestDispatcher.forward(request, response);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}

}
