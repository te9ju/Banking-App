package web;

import java.io.IOException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Account;
import dao.AccountDAO;
import dao.UserAccountMappingDAO;

/**
 * Servlet implementation class RequestAccountServlet
 */
@WebServlet("/requestAccount")
public class RequestAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AccountDAO accountDAO;
	private UserAccountMappingDAO userAccountMappingDAO;
    
	public void init() {
		userAccountMappingDAO = new UserAccountMappingDAO();
		accountDAO = new AccountDAO();
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("requestAccount.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		String accountType = request.getParameter("accountType");
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("requestAccount.jsp");
		try {
			int rowCount = 1, randAccountNumber = 100000000;
			Random random = new Random();
			while(rowCount != 0) {
				randAccountNumber = 100000000 + random.nextInt(900000000);
				rowCount = accountDAO.checkAccountNumber(randAccountNumber);
			}
			Account account = new Account();
			account.setAccountType(accountType);
			account.setAccountBalance(0);
			account.setAccountNumber(randAccountNumber);
			int rowCount2 = accountDAO.createAccount(account);
			if(rowCount2 > 0) {
				if(userAccountMappingDAO.createUserAccountMapping(userId, accountDAO.getAccountIdUsingAccountNumber(randAccountNumber)) > 0){
					request.setAttribute("status", "success");
				}
			}
			else {
				request.setAttribute("status", "failure");
			}
			requestDispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
