<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="bean.UtilityTransaction"%>
<%@ page import ="bean.User"%>
<%@ page import ="bean.Account"%>
<%@ page import = "dao.AccountDAO"%>
<%@ page import ="dao.UtilityTransactionDAO"%>
<%@ page import ="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="tableStyle.css">
<script src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>
<script src="https://kit.fontawesome.com/15b6f152f1.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="userProfileStyles.css">
<meta charset="UTF-8">
<title>UTILITY TRANSACTIONS</title>
</head>
<body>
<%
	User currentUser = (User)(session.getAttribute("user"));
	UtilityTransactionDAO utilityTransactionDAO = new UtilityTransactionDAO();
	AccountDAO accountDAO = new AccountDAO();
	ArrayList<Account> accounts = accountDAO.getAllAccounts(currentUser.getId());
	ArrayList<UtilityTransaction> utilityTransactions = new ArrayList<>();
	for(int i = 0; i < accounts.size(); i++){
		utilityTransactions.addAll(utilityTransactionDAO.getAllUtilityTransactionsForAccount(accounts.get(i).getAccountNumber()));
	}
%>
<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">ABOUT</a></li>  
  <li class="nav-item"><a href="requestCard.jsp">REQUEST CARD</a></li>
  <li class="nav-item"><a href="requestAccount.jsp">REQUEST ACCOUNT</a></li>  
  <li class="nav-item"><a href="transferAmount.jsp">TRANSFER MONEY</a></li>
  <li class="nav-item"><a href="transactions.jsp">TRANSACTIONS</a></li>
  <li class="nav-item"><a href="addBeneficiary.jsp">ADD BENEFICIARY</a></li>
  <li class="nav-item"><a href="utilityServices.jsp">UTILITY SERVICES</a></li>
  <li class="nav-item"><a class="active" href="utilityTransactions.jsp">UTILITY TRANSACTIONS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<%if(currentUser.getIsApproved() == 0){ %>
<h3 style="text-align: center;">WELCOME <%= currentUser.getFirstName() + " " + currentUser.getLastName() + "!" %>. Please contact bank admin and get your account approved to gain access to features!</h3>
<%}else{ %>
<!--
<div class="container">
  <h2>Transaction Details</h2>
  <ul class="cards">
  <%for(int i = 0; i < utilityTransactions.size(); i++){
	%>
    <li class="card">
      <div>
        <h3 class="card-title">Transaction <%=i+1 %></h3>
        <div class="card-content">
          <p>From Account Number: <%= utilityTransactions.get(i).getFromAccountNumber()%></p>
          <p>To Account Number: <%= utilityTransactions.get(i).getServiceConsumerNumber()%></p>
          <p>Amount: ₹<%= utilityTransactions.get(i).getBillAmount()%></p>
          <p>Date: <%= utilityTransactions.get(i).getTransactionDate()%></p>
        </div>
      </div>
    </li>
    <%} %>
  </ul>
</div>
-->
<div class="container">
  <h2>Utility Transaction Details</h2>
</div>
<div class="table-wrapper print-container">
<table id="transactionTable" class="fl-table sortable">
	<thead>
	<tr>
		<th>No.</th>
        <th>From Account No.</th>
        <th>Service Consumer Number</th>
        <th>Bill Amount</th>
        <th>Date and Time of Transaction</th>
    </tr>
    </thead>
    <tbody>
    	<%for(int i = 0; i < utilityTransactions.size(); i++){
			%>
			<tr class="item">
				<td><%=i+1 %></td>
				<td><%=utilityTransactions.get(i).getFromAccountNumber()%></td>
				<td><%= utilityTransactions.get(i).getServiceConsumerNumber()%></td>
				<td>₹<%= utilityTransactions.get(i).getBillAmount()%></td>
				<td><%= utilityTransactions.get(i).getTransactionDate()%></td>
			</tr>
			<%} %>
    </tbody>
</table>
</div>
<a href="/url/" onclick="window.print(); return false"><i class="fa-solid fa-print submitBtn"></i></a>
<%} %>
</body>
</html>