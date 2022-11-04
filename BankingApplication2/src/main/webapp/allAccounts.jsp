<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="bean.User"%>
<%@ page import ="dao.UserDAO"%>
<%@ page import ="bean.Account"%>
<%@ page import ="dao.AccountDAO"%>
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
<title>ALL ACCOUNTS</title>
</head>
<body>
<%	AccountDAO accountDAO = new AccountDAO();
	UserDAO userDAO = new UserDAO();
	ArrayList<User> users = userDAO.getAllUsers();
	ArrayList<ArrayList<Account>> allAccounts = new ArrayList<>();
	for(int i = 0; i < users.size(); i++){
		System.out.println(users.get(i).getId());
		allAccounts.add(accountDAO.getAllAccounts(users.get(i).getId()));
	}
%>
<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">	ABOUT</a></li>  
  <li class="nav-item"><a href="allUsers.jsp">VIEW ALL USERS</a></li>  
  <li class="nav-item"><a class="active" href="allAccounts.jsp">VIEW ALL ACCOUNTS</a></li>
  <li class="nav-item"><a href="allTransactions.jsp">VIEW ALL TRANSACTIONS</a></li>
  <li class="nav-item"><a href="allApprovals.jsp">VIEW PENDING APPROVALS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<div align="center" class="signUpForm">
	<form action="<%= request.getContextPath() %>/searchUser" method="post" class="searchForm">
		<div class="inputContainer">
        	<input type="search" class="input" id="search" name="search" placeholder="Search">
        	<label for="" class="label">Search</label>
		</div>
	</form>
</div>
<% for(int i = 0; i < users.size(); i++){
	if(users.get(i).getIsApproved() == 0){
		continue;
	}
	%>
	<div class="container">
	<h2>User <%=users.get(i).getFirstName()%> <%=users.get(i).getLastName()%>'s Account Details</h2>
	</div>
	<div class="table-wrapper">
	<table id="transactionTable" class="fl-table sortable">
		<thead>
		<tr>
			<th>No.</th>
			<th>Account No.</th>
	        <th>Balance</th>
	        <th>Account Type</th>
	    </tr>
	    </thead>
	    <tbody>
	    	<%for(int j = 0; j < allAccounts.get(i).size(); j++){
				%>
				<tr class="item">
					<td><%=j+1 %></td>
					<td><%=allAccounts.get(i).get(j).getAccountNumber()%></td>
					<td><%= allAccounts.get(i).get(j).getAccountBalance()%></td>
					<td><%= allAccounts.get(i).get(j).getAccountType()%></td>
				</tr>
				<%} %>
	    </tbody>
	</table>
	</div>
<%}%>
</body>
</html>