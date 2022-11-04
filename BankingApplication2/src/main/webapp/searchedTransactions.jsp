<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="bean.Transaction"%>
<%@ page import ="bean.GiftCardTransaction"%>
<%@ page import ="bean.UtilityTransaction"%>
<%@ page import ="bean.User"%>
<%@ page import ="bean.Account"%>
<%@ page import = "dao.AccountDAO"%>
<%@ page import ="dao.TransactionDAO"%>
<%@ page import ="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="tableStyle.css">
<script src="https://kit.fontawesome.com/15b6f152f1.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="userProfileStyles.css">
<meta charset="UTF-8">
<title>TRANSACTIONS</title>
</head>
<body>
<%	User currentUser = (User)(session.getAttribute("user"));
	ArrayList<Account> accounts = new ArrayList<>();
	AccountDAO accountDAO = new AccountDAO();
	accounts = accountDAO.getAllAccounts(currentUser.getId());
	ArrayList<Integer> accountNumbers = new ArrayList<>();
	for(int i = 0; i < accounts.size(); i++){
		accountNumbers.add(accounts.get(i).getAccountNumber());
	}
	ArrayList<GiftCardTransaction> giftCardTransactions = (ArrayList<GiftCardTransaction>) request.getAttribute("searchedGiftCardTransactions");
	ArrayList<UtilityTransaction> utilityTransactions = (ArrayList<UtilityTransaction>) request.getAttribute("searchedUtilityTransactions");
	ArrayList<Transaction> transactions = (ArrayList<Transaction>) request.getAttribute("searchedTransactions");
	if(currentUser.getIsAdmin() == 0){
%>
<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">ABOUT</a></li>  
  <li class="nav-item"><a href="requestCard.jsp">REQUEST CARD</a></li>
  <li class="nav-item"><a href="requestAccount.jsp">REQUEST ACCOUNT</a></li>  
  <li class="nav-item"><a href="transferAmount.jsp">TRANSFER MONEY</a></li>
  <li class="nav-item"><a class="active" href="transactions.jsp">TRANSACTIONS</a></li>
  <li class="nav-item"><a href="addBeneficiary.jsp">ADD BENEFICIARY</a></li>
  <li class="nav-item"><a href="utilityServices.jsp">UTILITY SERVICES</a></li>
  <li class="nav-item"><a href="utilityTransactions.jsp">UTILITY TRANSACTIONS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<%}else if(currentUser.getIsAdmin() == 1){ %>
<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">	ABOUT</a></li>  
  <li class="nav-item"><a class="active" href="allUsers.jsp">VIEW ALL USERS</a></li>  
  <li class="nav-item"><a href="allAccounts.jsp">VIEW ALL ACCOUNTS</a></li>
  <li class="nav-item"><a href="allTransactions.jsp">VIEW ALL TRANSACTIONS</a></li>
  <li class="nav-item"><a href="allApprovals.jsp">VIEW PENDING APPROVALS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<%} %>
<%if(currentUser.getIsAdmin() == 0 && currentUser.getIsApproved() == 0){ %>
<h3 style="text-align: center;">WELCOME <%= currentUser.getFirstName() + " " + currentUser.getLastName() + "!" %>. Please contact bank admin and get your account approved to gain access to features!</h3>
<%}else{ %>
<div class="container">
  <h2>Transaction Details</h2>
</div>
<div class="table-wrapper print-container">
<table id="transactionTable" class="fl-table">
	<thead>
	<tr>
		<th>Transaction ID</th>
        <th>Type</th>
        <th>Amount</th>
        <th>Date and Time of Transaction</th>
        <th>Purpose</th>
    </tr>
    </thead>
    <tbody>
    	<%if(transactions.isEmpty()){ %>
    		<tr>
    			<td colspan="5">No results to show!</td>
    		</tr>
    	<%}else{
    		for(int i = 0; i < transactions.size(); i++){
			%>
			<tr class="item">
				<td><%=transactions.get(i).getId()%></td>
				<%if(accountNumbers.contains(Integer.parseInt(transactions.get(i).getFromAccountNumber())) && !accountNumbers.contains(Integer.parseInt(transactions.get(i).getToAccountNumber()))){%>
				<td>Credit</td>
				<%}else if(!accountNumbers.contains(Integer.parseInt(transactions.get(i).getFromAccountNumber())) && accountNumbers.contains(Integer.parseInt(transactions.get(i).getToAccountNumber()))){%>
				<td>Debit</td>
				<%}else{ %>
				<td>Self Transfer</td>
				<%} %>
				<td>₹<%= transactions.get(i).getAmount()%></td>
				<td><%= transactions.get(i).getTransactionDate()%></td>
				<td><%=transactions.get(i).getPurpose()%></td>
			</tr>
			<%}
    		}%>
    	<%for(int i = 0; i < utilityTransactions.size(); i++){ %>
				<tr class="item">
				<td><%=utilityTransactions.get(i).getId()%></td>
				<td>Credit</td>
				<td>₹<%= utilityTransactions.get(i).getBillAmount()%></td>
				<td><%= utilityTransactions.get(i).getTransactionDate()%></td>
				<td><%=utilityTransactions.get(i).getPurpose()%></td>
			</tr>
		<%} %>
		<%for(int i = 0; i < giftCardTransactions.size(); i++){ %>
				<tr class="item">
				<td><%=giftCardTransactions.get(i).getId()%></td>
				<td>Credit</td>
				<td>₹<%= giftCardTransactions.get(i).getAmount()%></td>
				<td><%= giftCardTransactions.get(i).getTransactionDate()%></td>
				<td><%=giftCardTransactions.get(i).getPurpose()%></td>
			</tr>
		<%} %>
    </tbody>
</table>
</div>
<%if(!transactions.isEmpty()){ %>
<a href="/url/" onclick="window.print(); return false"><i class="fa-solid fa-print submitBtn"></i></a>
<%}
} %>
</body>
</html>