package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Account;
import bean.DebitCard;
import bean.UtilityTransaction;
import dao.AccountDAO;
import dao.DebitCardDAO;
import dao.ServicesDAO;
import dao.UtilityTransactionDAO;

/**
 * Servlet implementation class UtilityServiceTransaction
 */
@WebServlet("/utilityServiceTransaction")
public class UtilityServiceTransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilityTransactionDAO utilityTransactionDAO;
	private ServicesDAO servicesDAO;
	private AccountDAO accountDAO;
	private DebitCardDAO debitCardDAO;
    
	public void init() {
		debitCardDAO = new DebitCardDAO();
		utilityTransactionDAO = new UtilityTransactionDAO();
		servicesDAO = new ServicesDAO();
		accountDAO = new AccountDAO();
	}
	/**
     * @see HttpServlet#HttpServlet()
     */
    public UtilityServiceTransactionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("utilityServices.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String purpose = request.getParameter("purpose");
		String paymentMethod = request.getParameter("paymentMethod");
		if(paymentMethod.equals("Debit Card")) {
			String debitCardNumber = request.getParameter("debitCardNumber");
			try {
				DebitCard debitCard = debitCardDAO.getDebitCardUsingCardNumber(debitCardNumber);
				int associatedAccountNumber = debitCard.getAssociatedAccountNumber();
				String serviceConsumerNumber = request.getParameter("serviceConsumerNumber");
				int billAmount = Integer.parseInt(request.getParameter("billAmount"));
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("utilityServices.jsp");
				Account fromAccount = accountDAO.getOneAccountUsingAccountNumber(associatedAccountNumber);
				int updatedFromBalance = fromAccount.getAccountBalance() - billAmount;
				int serviceID = servicesDAO.getServiceId(request.getParameter("serviceName"));
				if(billAmount > fromAccount.getAccountBalance()) {
					request.setAttribute("status", "failure");
					requestDispatcher.forward(request, response);
					return;
				}
				UtilityTransaction utilityTransaction = new UtilityTransaction();
				utilityTransaction.setPurpose(purpose);
				utilityTransaction.setBillAmount(billAmount);
				utilityTransaction.setFromAccountNumber(String.valueOf(associatedAccountNumber));
				utilityTransaction.setServiceConsumerNumber(serviceConsumerNumber);
				utilityTransaction.setServiceId(serviceID);
				if(utilityTransactionDAO.createUtilityTransaction(utilityTransaction) > 0) {
					if(accountDAO.updateAccountBalance(associatedAccountNumber, updatedFromBalance) > 0) {
						request.setAttribute("status", "success");
					}
				}
				else {
					request.setAttribute("status", "failure");
				}
				requestDispatcher.forward(request, response);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		else if(paymentMethod.equals("Transfer From Account")) {
			String fromAccountNumberString = request.getParameter("fromAccountNumber");
			int fromAccountNumber = Integer.parseInt(fromAccountNumberString.replaceAll("[^0-9]", ""));
			String serviceConsumerNumber = request.getParameter("serviceConsumerNumber");
			int billAmount = Integer.parseInt(request.getParameter("billAmount"));
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("utilityServices.jsp");
			try {
				int serviceID = servicesDAO.getServiceId(request.getParameter("serviceName"));
				Account fromAccount = accountDAO.getOneAccountUsingAccountNumber(fromAccountNumber);
				int updatedFromBalance = fromAccount.getAccountBalance() - billAmount;
				if(billAmount > fromAccount.getAccountBalance()) {
					request.setAttribute("status", "failure");
					requestDispatcher.forward(request, response);
					return;
				}
				UtilityTransaction utilityTransaction = new UtilityTransaction();
				utilityTransaction.setPurpose(purpose);
				utilityTransaction.setBillAmount(billAmount);
				utilityTransaction.setFromAccountNumber(String.valueOf(fromAccountNumber));
				utilityTransaction.setServiceConsumerNumber(serviceConsumerNumber);
				utilityTransaction.setServiceId(serviceID);
				if(utilityTransactionDAO.createUtilityTransaction(utilityTransaction) > 0) {
					if(accountDAO.updateAccountBalance(fromAccountNumber, updatedFromBalance) > 0) {
						request.setAttribute("status", "success");
					}
				}
				else {
					request.setAttribute("status", "failure");
				}
				requestDispatcher.forward(request, response);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
