package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Beneficiary;
import dao.BeneficiaryDAO;
import dao.UserBeneficiaryMappingDAO;

/**
 * Servlet implementation class AddBeneficiaryServlet
 */
@WebServlet("/addBeneficiary")
public class AddBeneficiaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BeneficiaryDAO beneficiaryDAO;
    private UserBeneficiaryMappingDAO userBeneficiaryMappingDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    
    public void init() {
    	userBeneficiaryMappingDAO = new UserBeneficiaryMappingDAO();
    	beneficiaryDAO = new BeneficiaryDAO();
	}
    
    public AddBeneficiaryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("addBeneficiary.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String beneficiaryName = request.getParameter("name");
		String aliasName = request.getParameter("aliasName");
		int accountNumber = Integer.parseInt(request.getParameter("accountNumber"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		int transferLimit = Integer.parseInt(request.getParameter("transferLimit"));
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("addBeneficiary.jsp");
		try {
			Beneficiary beneficiary = new Beneficiary();
			beneficiary.setBeneficiaryName(beneficiaryName);
			beneficiary.setAccountNumber(accountNumber);
			beneficiary.setAliasName(aliasName);
			beneficiary.setTransferLimit(transferLimit);
			if(beneficiaryDAO.checkUserBeneficiary(userId, accountNumber) > 0) {
				request.setAttribute("status", "repeat");
				requestDispatcher.forward(request, response);
				return;
			}
			int beneficiaryId = beneficiaryDAO.createBeneficiary(beneficiary);
			System.out.println("The beneficiary id is: "+beneficiaryId);
			if(beneficiaryId > 0) {
				if(userBeneficiaryMappingDAO.createUserBeneficiaryMapping(userId, beneficiaryId) > 0) {
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