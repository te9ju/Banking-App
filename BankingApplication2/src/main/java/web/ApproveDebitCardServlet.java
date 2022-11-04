package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DebitCardDAO;

/**
 * Servlet implementation class ApproveDebitCardServlet
 */
@WebServlet("/approveDebitCard")
public class ApproveDebitCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DebitCardDAO debitCardDAO;
	
	public void init() {
		debitCardDAO = new DebitCardDAO();
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApproveDebitCardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String cardNumber = request.getParameter("cardNumber");
		System.out.println(cardNumber);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("allApprovals.jsp");
		try {
			if(debitCardDAO.approveDebitCard(cardNumber) > 0) {
				request.setAttribute("status", "success");
			}
			requestDispatcher.forward(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
