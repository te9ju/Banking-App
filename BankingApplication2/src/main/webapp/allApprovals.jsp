<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="bean.User"%>
<%@ page import ="dao.UserDAO"%>
<%@ page import ="bean.CreditCard"%>
<%@ page import ="dao.CreditCardDAO"%>
<%@ page import ="bean.DebitCard"%>
<%@ page import ="dao.DebitCardDAO"%>
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
<title>ALL USERS</title>
</head>
<body>
<%
	CreditCardDAO creditCardDAO = new CreditCardDAO();
	ArrayList<CreditCard> creditCards = creditCardDAO.getAllUnapprovedCreditCards();
	DebitCardDAO debitCardDAO = new DebitCardDAO();
	ArrayList<DebitCard> debitCards = debitCardDAO.getAllUnapprovedDebitCards();
	UserDAO userDAO = new UserDAO();
	ArrayList<User> users = userDAO.getAllUnapprovedUsers();
%>
<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">	ABOUT</a></li>  
  <li class="nav-item"><a href="allUsers.jsp">VIEW ALL USERS</a></li>  
  <li class="nav-item"><a href="allAccounts.jsp">VIEW ALL ACCOUNTS</a></li>
  <li class="nav-item"><a href="allTransactions.jsp">VIEW ALL TRANSACTIONS</a></li>
  <li class="nav-item"><a class="active" href="allApprovals.jsp">VIEW PENDING APPROVALS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<div class="container">
<h2>Pending User Approvals</h2>
</div>
<div class="table-wrapper">
<table id="transactionTable" class="fl-table sortable">
	<thead>
	<tr>
		<th>ID</th>
		<th>First Name</th>
        <th>Last Name</th>
        <th>Address</th>
        <th>Phone Number</th>
        <th>Username</th>
        <th>Added Date</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    	<%	if(users.isEmpty()){%>
    		<tr>
    			<td colspan="8">No pending requests!</td>
    		</tr>
    		<% }else{
    		for(int i = 0; i < users.size(); i++){
			%>
			<tr class="item">
				<td><%=users.get(i).getId() %></td>
				<td><%=users.get(i).getFirstName()%></td>
				<td><%= users.get(i).getLastName()%></td>
				<td><%= users.get(i).getAddress()%></td>
				<td><%= users.get(i).getPhoneNumber()%></td>
				<td><%= users.get(i).getUserName()%></td>
				<td><%= users.get(i).getAddedDate()%></td>
				<td><a href="<%= request.getContextPath() %>/approveUser?id=<%=users.get(i).getId()%>"><i class="fa-solid fa-square-check"></i></a></td>
			</tr>
			<%} 
			}%>
    </tbody>
</table>
</div>
<div class="container">
<h2>Pending Credit Card Approvals</h2>
</div>
<div class="table-wrapper">
<table id="transactionTable" class="fl-table">
	<thead>
	<tr>
		<th>No.</th>
		<th>Card Number</th>
        <th>Requested By</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <%	if(creditCards.isEmpty()){%>
    		<tr>
    			<td colspan="8">No pending requests!</td>
    		</tr>
    		<% }else{
    			for(int i = 0; i < creditCards.size(); i++){
			%>
			<tr>
				<td><%=i+1 %></td>
				<td><%=creditCards.get(i).getCardNumber()%></td>
				<td><%=creditCardDAO.getUserRequestingCreditCardApproval(creditCards.get(i).getCardNumber())%></td>
				<td><a href="<%= request.getContextPath() %>/approveCreditCard?cardNumber=<%=creditCards.get(i).getCardNumber()%>"><i class="fa-solid fa-square-check"></i></a></td>
			</tr>
			<%} 
			}%>
    </tbody>
</table>
</div>
<div class="container">
<h2>Pending Debit Card Approvals</h2>
</div>
<div class="table-wrapper">
<table id="transactionTable" class="fl-table">
	<thead>
	<tr>
		<th>No.</th>
		<th>Card Number</th>
        <th>Requested By</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <%	if(debitCards.isEmpty()){%>
    		<tr>
    			<td colspan="8">No pending requests!</td>
    		</tr>
    		<% }else{
    			for(int i = 0; i < debitCards.size(); i++){
			%>
			<tr>
				<td><%=i+1 %></td>
				<td><%=debitCards.get(i).getCardNumber()%></td>
				<td><%=debitCardDAO.getUserRequestingDebitCardApproval(debitCards.get(i).getCardNumber())%></td>
				<td><a href="<%= request.getContextPath() %>/approveDebitCard?cardNumber=<%=debitCards.get(i).getCardNumber()%>"><i class="fa-solid fa-square-check"></i></a></td>
			</tr>
			<%} 
			}%>
    </tbody>
</table>
</div>
</body>
</html>