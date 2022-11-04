package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Account;
import bean.Transaction;
import dao.AccountDAO;
import dao.BeneficiaryDAO;
import dao.TransactionDAO;
import dao.UserAccountMappingDAO;
import dao.UserBeneficiaryMappingDAO;

/**
 * Servlet implementation class TransferAmountServlet
 */
@WebServlet("/transferAmount")
public class TransferAmountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserAccountMappingDAO userAccountMappingDAO;
	private AccountDAO accountDAO;
	private TransactionDAO transactionDAO;
	private BeneficiaryDAO beneficiaryDAO;
    private UserBeneficiaryMappingDAO userBeneficiaryMappingDAO;
	
	public void init() {
		userBeneficiaryMappingDAO = new UserBeneficiaryMappingDAO();
    	beneficiaryDAO = new BeneficiaryDAO();
		userAccountMappingDAO = new UserAccountMappingDAO();
		accountDAO = new AccountDAO();
		transactionDAO = new TransactionDAO();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransferAmountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("transferAmount.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String purpose = request.getParameter("purpose");
		int fromUserId = Integer.parseInt(request.getParameter("userId"));
		String toAccountString = request.getParameter("toAccountNumber");
		String fromAccountString = request.getParameter("fromAccountNumber");
		int toAccountNumber = Integer.parseInt(toAccountString.replaceAll("[^0-9]", ""));
		int fromAccountNumber = Integer.parseInt(fromAccountString.replaceAll("[^0-9]", ""));
		int amount = Integer.parseInt(request.getParameter("amount"));
		System.out.println("Amount entered: "+amount);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("transferAmount.jsp");
		try {
			if(fromAccountNumber == toAccountNumber) {
				System.out.println("Inside if check!");
				request.setAttribute("status", "sameAcc");
				requestDispatcher.forward(request, response);
				return;
			}
			Account fromAccount = accountDAO.getOneAccount(fromUserId);
			Account toAccount = accountDAO.getOneAccountUsingAccountNumber(toAccountNumber);
			int beneficiaryId = beneficiaryDAO.getBeneficiaryId(fromUserId, toAccountNumber);
			int updatedFromBalance = fromAccount.getAccountBalance() - amount;
			int updatedToBalance = toAccount.getAccountBalance() + amount;
			System.out.println("userId: "+fromUserId+" beneficiaryId: "+beneficiaryId+" checkUserBeneficiaryMapping: "+userBeneficiaryMappingDAO.checkUserBeneficiaryMapping(fromUserId, beneficiaryId));
			if(amount > beneficiaryDAO.getTransferLimitForBeneficiary(fromUserId, toAccountNumber)) {
				request.setAttribute("status", "limitExceeded");
				requestDispatcher.forward(request, response);
				return;
			}
			if(userBeneficiaryMappingDAO.checkUserBeneficiaryMapping(fromUserId, beneficiaryId) <= 0) {
				request.setAttribute("status", "addBeneficiary");
				requestDispatcher.forward(request, response);
				return;
			}
			if(amount > fromAccount.getAccountBalance()) {
				request.setAttribute("status", "failure");
				requestDispatcher.forward(request, response);
				return;
			}
			if(accountDAO.checkAccountNumber(toAccountNumber) > 0 && userAccountMappingDAO.checkUserAccountMapping(fromUserId, accountDAO.getAccountIdUsingAccountNumber(fromAccountNumber)) > 0) {
				if(accountDAO.updateAccountBalance(fromAccountNumber, updatedFromBalance) > 0 && accountDAO.updateAccountBalance(toAccountNumber, updatedToBalance) > 0) {
					Transaction transaction = new Transaction();
					transaction.setPurpose(purpose);
					transaction.setFromAccountNumber(String.valueOf(fromAccountNumber));
					transaction.setToAccountNumber(String.valueOf(toAccountNumber));
					transaction.setAmount(amount);
					if(transactionDAO.createTransaction(transaction) > 0) {
						request.setAttribute("status", "success");
					}
				}
			}
			else {
				request.setAttribute("status", "failure");
			}
			requestDispatcher.forward(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
