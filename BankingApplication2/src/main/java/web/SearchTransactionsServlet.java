package web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.GiftCardTransaction;
import bean.Transaction;
import bean.UtilityTransaction;
import dao.GiftCardTransactionDAO;
import dao.TransactionDAO;
import dao.UtilityTransactionDAO;

/**
 * Servlet implementation class SearchTransactionsServlet
 */
@WebServlet("/searchTransactions")
public class SearchTransactionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private TransactionDAO transactionDAO;
    private GiftCardTransactionDAO giftCardTransactionDAO;
    private UtilityTransactionDAO utilityTransactionDAO;
    
    public void init() {
    	utilityTransactionDAO = new UtilityTransactionDAO();
    	transactionDAO = new TransactionDAO();
    	giftCardTransactionDAO = new GiftCardTransactionDAO();
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchTransactionsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("searchedTransactions.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accountNumberString = request.getParameter("accountNumber");
		int accountNumber = Integer.parseInt(accountNumberString.replaceAll("[^0-9]", ""));
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("searchedTransactions.jsp");
		try {
			ArrayList<GiftCardTransaction> giftCardTransactions = new ArrayList<>();
			ArrayList<UtilityTransaction> utilityTransactions = new ArrayList<>();
			ArrayList<Transaction> transactions = new ArrayList<>();
			giftCardTransactions.addAll(giftCardTransactionDAO.getAllFilteredGiftCardTransactionsForAccount(String.valueOf(accountNumber), fromDate, toDate));
			transactions.addAll(transactionDAO.getAllFilteredTransactionsForAccount(String.valueOf(accountNumber), fromDate, toDate));
			utilityTransactions.addAll(utilityTransactionDAO.getAllFilteredUtilityTransactionsForAccount(String.valueOf(accountNumber), fromDate, toDate));
			request.setAttribute("searchedTransactions", transactions);
			request.setAttribute("searchedUtilityTransactions",utilityTransactions);
			request.setAttribute("searchedGiftCardTransactions", giftCardTransactions);
			requestDispatcher.forward(request, response);
		}  catch(Exception e) {
			e.printStackTrace();
		}
	}
}
