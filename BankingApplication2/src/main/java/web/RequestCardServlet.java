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
import bean.CreditCard;
import bean.DebitCard;
import bean.GiftCard;
import bean.GiftCardTransaction;
import bean.User;
import dao.AccountDAO;
import dao.CreditCardDAO;
import dao.DebitCardDAO;
import dao.GiftCardDAO;
import dao.GiftCardTransactionDAO;
import dao.GiftedCardMappingDAO;
import dao.UserCardMappingDAO;
import dao.UserDAO;
import dao.UserGiftCardMappingDAO;

/**
 * Servlet implementation class CardServlet
 */
@WebServlet("/requestCard")
public class RequestCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CreditCardDAO creditCardDAO;
    private DebitCardDAO debitCardDAO;
    private GiftCardDAO giftCardDAO;
    private GiftedCardMappingDAO giftedCardMappingDAO;
    private UserCardMappingDAO userCardMappingDAO;
    private UserGiftCardMappingDAO userGiftCardMappingDAO;
    private UserDAO userDAO;
    private GiftCardTransactionDAO giftCardTransactionDAO; 
    private AccountDAO accountDAO;
    
    public void init() {
    	giftCardTransactionDAO = new GiftCardTransactionDAO();
    	userGiftCardMappingDAO = new UserGiftCardMappingDAO();
    	giftedCardMappingDAO = new GiftedCardMappingDAO();
    	giftCardDAO = new GiftCardDAO();
    	accountDAO = new AccountDAO();
    	userDAO = new UserDAO();
		creditCardDAO = new CreditCardDAO();
		debitCardDAO = new DebitCardDAO();
		userCardMappingDAO = new UserCardMappingDAO();
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestCardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("requestCard.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cardType = request.getParameter("cardType");
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("requestCard.jsp");
		if(cardType.equals("Gift Card")) {
			String purpose = request.getParameter("purpose");
			String fromAccountString = request.getParameter("fromAccountNumber");
			int fromAccountNumber = Integer.parseInt(fromAccountString.replaceAll("[^0-9]", ""));
			Account fromAccount = new Account();
			try {
				fromAccount = accountDAO.getOneAccountUsingAccountNumber(fromAccountNumber);
			} catch (ClassNotFoundException e3) {
				e3.printStackTrace();
			}
			int amount = Integer.parseInt(request.getParameter("amount"));
			if(amount > 10000) {
				request.setAttribute("status", "amountExceeds");
				requestDispatcher.forward(request, response);
				return;
			}
			int fromUserId = Integer.valueOf(request.getParameter("userId"));
			int updatedFromBalance = fromAccount.getAccountBalance() - amount;
			String toUserName = request.getParameter("toUserName");
			User toUser;
			int toUserId = 0;
			try {
				if(userDAO.getOneUser(toUserName) == null || userDAO.getOneUser(toUserName).getIsAdmin() == 1) {
					request.setAttribute("status", "invalid");
					requestDispatcher.forward(request, response);
					return;
				}
				if(amount > fromAccount.getAccountBalance()) {
					request.setAttribute("status", "insufficientBalance");
					requestDispatcher.forward(request, response);
					return;
				}
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
			}
			try {
				toUser = userDAO.getOneUser(toUserName);
				toUserId = toUser.getId();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			int rowCount = 1, cvv = 111;
			String cardNumber = ""; 
			try {
				while(rowCount != 0) {
					cardNumber = String.valueOf((long) (Math.random() * 10000000000000000L));
					rowCount = giftCardDAO.checkGiftCardNumber(cardNumber);
				}
				rowCount = 1;
				Random random = new Random();
				while(rowCount != 0) {
					cvv = random.nextInt(900) + 100;
					rowCount = giftCardDAO.checkCVV(cvv);
				}
				GiftCard card = new GiftCard();
				card.setCardNumber(cardNumber);
				card.setCvv(cvv);
				card.setCardLimit(amount);
				rowCount = giftCardDAO.createGiftCard(card);
				if(rowCount > 0) {
					if(giftedCardMappingDAO.createGiftedCardDetail(fromUserId, giftCardDAO.getCardIdUsingCardNumber(cardNumber)) > 0 && userGiftCardMappingDAO.createUserGiftCardMapping(toUserId, giftCardDAO.getCardIdUsingCardNumber(cardNumber)) > 0){
						GiftCardTransaction giftCardTransaction = new GiftCardTransaction();
						giftCardTransaction.setAmount(amount);
						giftCardTransaction.setFromAccountNumber(String.valueOf(fromAccountNumber));
						giftCardTransaction.setPurpose(purpose);
						giftCardTransaction.setToUserName(toUserName);
						if(giftCardTransactionDAO.createGiftCardTransaction(giftCardTransaction) > 0 && accountDAO.updateAccountBalance(fromAccountNumber, updatedFromBalance) > 0) {
							request.setAttribute("status", "success");
						}
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
		else if(cardType.equals("Credit Card")) {
			int userId = Integer.parseInt(request.getParameter("userId"));
			int rowCount = 1, cvv = 111;
			String cardNumber = ""; 
			try {
				if(!creditCardDAO.getAllUnapprovedCreditCards().isEmpty()) {
					request.setAttribute("status", "pending");
					requestDispatcher.forward(request, response);
					return;
				}
				while(rowCount != 0) {
					cardNumber = String.valueOf((long) (Math.random() * 10000000000000000L));
					rowCount = creditCardDAO.checkCreditCardNumber(cardNumber);
				}
				rowCount = 1;
				Random random = new Random();
				while(rowCount != 0) {
					cvv = random.nextInt(900) + 100;
					rowCount = creditCardDAO.checkCVV(cvv);
				}
				CreditCard card = new CreditCard();
				card.setCardNumber(cardNumber);
				card.setCvv(cvv);
				rowCount = creditCardDAO.createCreditCard(card);
				if(rowCount > 0) {
					if(userCardMappingDAO.createUserCardMapping(userId, creditCardDAO.getCardIdUsingCardNumber(cardNumber)) > 0){
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
		else if(cardType.equals("Debit Card")) {
			String accountNumberString = request.getParameter("accountNumber");
			int accountNumber = Integer.parseInt(accountNumberString.replaceAll("[^0-9]", ""));
			int rowCount = 1, cvv = 111;
			String cardNumber = ""; 
			try {
				if(!debitCardDAO.getAllUnapprovedDebitCards().isEmpty()) {
					request.setAttribute("status", "pending");
					requestDispatcher.forward(request, response);
					return;
				}
				while(rowCount != 0) {
					cardNumber = String.valueOf((long) (Math.random() * 10000000000000000L));
					rowCount = debitCardDAO.checkDebitCardNumber(cardNumber);
				}
				rowCount = 1;
				Random random = new Random();
				while(rowCount != 0) {
					cvv = random.nextInt(900) + 100;
					rowCount = debitCardDAO.checkCVV(cvv);
				}
				DebitCard card = new DebitCard();
				card.setCardNumber(cardNumber);
				card.setCvv(cvv);
				card.setAssociatedAccountNumber(accountNumber);
				rowCount = debitCardDAO.createDebitCard(card);
				if(rowCount > 0) {
					request.setAttribute("status", "success");
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
}
