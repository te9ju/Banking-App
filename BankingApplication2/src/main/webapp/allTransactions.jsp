<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="bean.Transaction"%>
<%@ page import ="bean.UtilityTransaction"%>
<%@ page import ="bean.User"%>
<%@ page import ="dao.TransactionDAO"%>
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
<title>ALL TRANSACTIONS</title>
</head>
<body>
<%
	User currentUser = (User)(session.getAttribute("user"));
	UtilityTransactionDAO utilityTransactionDAO = new UtilityTransactionDAO();
	TransactionDAO transactionDAO = new TransactionDAO();
	ArrayList<Transaction> transactions = transactionDAO.getAllTransactions();
	ArrayList<UtilityTransaction> utilityTransactions = utilityTransactionDAO.getAllUtilityTransactions();
%>
<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">	ABOUT</a></li>  
  <li class="nav-item"><a href="allUsers.jsp">VIEW ALL USERS</a></li>  
  <li class="nav-item"><a href="allAccounts.jsp">VIEW ALL ACCOUNTS</a></li>
  <li class="nav-item"><a class="active" href="allTransactions.jsp">VIEW ALL TRANSACTIONS</a></li>
  <li class="nav-item"><a href="allApprovals.jsp">VIEW PENDING APPROVALS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<div class="container">
<h2>Transaction Details</h2>
</div>
<div class="table-wrapper">
<table id="transactionTable" class="fl-table sortable">
	<thead>
	<tr>
		<th>No.</th>
		<th>From Account No.</th>
        <th>To Account No.</th>
        <th>Amount</th>
        <th>Date and Time</th>
    </tr>
    </thead>
    <tbody>
    	<%for(int i = 0; i < transactions.size(); i++){
			%>
			<tr class="item">
				<td><%=i+1 %></td>
				<td><%=transactions.get(i).getFromAccountNumber()%></td>
				<td><%= transactions.get(i).getToAccountNumber()%></td>
				<td><%= transactions.get(i).getAmount()%></td>
				<td><%= transactions.get(i).getTransactionDate()%></td>
			</tr>
			<%} %>
    </tbody>
</table>
</div>
<div class="container">
<h2>Utility Transaction Details</h2>
</div>
<div class="table-wrapper">
<table id="transactionTable" class="fl-table">
	<thead>
	<tr>
		<th>No.</th>
		<th>From Account No.</th>
        <th>Service Consumer No.</th>
        <th>Bill Amount</th>
        <th>Date</th>
    </tr>
    </thead>
    <tbody>
    	<%for(int i = 0; i < utilityTransactions.size(); i++){
			%>
			<tr>
				<td><%=i+1 %></td>
				<td><%=utilityTransactions.get(i).getFromAccountNumber()%></td>
				<td><%= utilityTransactions.get(i).getServiceConsumerNumber()%></td>
				<td><%= utilityTransactions.get(i).getBillAmount()%></td>
				<td><%= utilityTransactions.get(i).getTransactionDate()%></td>
			</tr>
			<%} %>
    </tbody>
</table>
</div>
</body>
</html>