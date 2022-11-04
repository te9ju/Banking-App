package web;

import java.io.IOException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bean.User;
import bean.Account;
import bean.DebitCard;
import dao.UserDAO;
import dao.DebitCardDAO;
import dao.AccountDAO;
import dao.UserAccountMappingDAO;

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/register")
public class RegisterUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DebitCardDAO debitCardDAO;
	private UserDAO userDAO;
	private AccountDAO accountDAO;
	private UserAccountMappingDAO userAccountMappingDAO;
	
	public void init() {
		debitCardDAO = new DebitCardDAO();
		userAccountMappingDAO = new UserAccountMappingDAO();
		userDAO = new UserDAO();
		accountDAO = new AccountDAO();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("registerUser.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		String phoneNumber = request.getParameter("phoneNumber");
		String accountType = request.getParameter("accountType");
		int isAdmin;
		if(request.getParameter("isAdmin").equals("user")) {
			isAdmin = 0;
		}
		else {
			isAdmin = 1;
		}
		
		if(isAdmin == 0) {
			User user = new User();
			user.setIsAdmin(isAdmin);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setUserName(username);
			user.setPassword(password);
			user.setPhoneNumber(Long.parseLong(phoneNumber));
			user.setAddress(address);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("registerUser.jsp");
			try {
				if(userDAO.checkPhoneNumber(phoneNumber)) {
					request.setAttribute("phoneNumberCheck", "failure");
					requestDispatcher.forward(request, response);
					return;
				}
				if(userDAO.checkUserName(username)) {
					request.setAttribute("userNameCheck", "failure");
					requestDispatcher.forward(request, response);
					return;
				}
				int rowCount = userDAO.registerUser(user);
				if(rowCount > 0) {
					int id = userDAO.getOneUser(username).getId();
					int rowCount2 = 1, randAccountNumber = 100000000;
					Random random = new Random();
					while(rowCount2 != 0) {
						randAccountNumber = 100000000 + random.nextInt(900000000);
						rowCount2 = accountDAO.checkAccountNumber(randAccountNumber);
					}
					Account account = new Account();
					account.setAccountType(accountType);
					account.setAccountBalance(0);
					account.setAccountNumber(randAccountNumber);
					String cardNumber = "";
					int cvv = 111;
					while(rowCount != 0) {
						cardNumber = String.valueOf((long) (Math.random() * 10000000000000000L));
						rowCount = debitCardDAO.checkDebitCardNumber(cardNumber);
					}
					rowCount = 1;
					while(rowCount != 0) {
						cvv = random.nextInt(900) + 100;
						rowCount = debitCardDAO.checkCVV(cvv);
					}
					int rowCount3 = accountDAO.createAccount(account);
					DebitCard card = new DebitCard();
					card.setCardNumber(cardNumber);
					card.setAssociatedAccountNumber(randAccountNumber);
					card.setCvv(cvv);
					rowCount = debitCardDAO.createDebitCard(card);
					if(rowCount3 > 0 && rowCount > 0) {
						if(userAccountMappingDAO.createUserAccountMapping(id, accountDAO.getAccountIdUsingAccountNumber(randAccountNumber)) > 0){
							request.setAttribute("status", "success");
						}
					}
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
		else if(isAdmin == 1) {
			User user = new User();
			user.setIsAdmin(isAdmin);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setUserName(username);
			user.setPassword(password);
			user.setPhoneNumber(Long.parseLong(phoneNumber));
			user.setAddress(address);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("registerAdmin.jsp");
			try {
				if(userDAO.checkPhoneNumber(phoneNumber)) {
					request.setAttribute("phoneNumberCheck", "failure");
					requestDispatcher.forward(request, response);
					return;
				}
				if(userDAO.checkUserName(username)) {
					request.setAttribute("userNameCheck", "failure");
					requestDispatcher.forward(request, response);
					return;
				}
				int rowCount = userDAO.registerAdmin(user);
				if(rowCount > 0) {
					System.out.println(rowCount);
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