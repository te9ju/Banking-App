<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="bean.User"%>
<%@ page import ="dao.UserDAO"%>
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
<%	UserDAO userDAO = new UserDAO();
	ArrayList<User> users = userDAO.getAllUsers();
	ArrayList<User> admins = userDAO.getAllAdmins();
%>
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
<div class="container">
<h2>User Details</h2>
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
        <th colspan="2">Action</th>
    </tr>
    </thead>
    <tbody>
    	<%for(int i = 0; i < users.size(); i++){
    		if(users.get(i).getIsApproved() == 0){
    			continue;
    		}
			%>
			<tr class="item">
				<td><%=users.get(i).getId() %></td>
				<td><%=users.get(i).getFirstName()%></td>
				<td><%= users.get(i).getLastName()%></td>
				<td><%= users.get(i).getAddress()%></td>
				<td><%= users.get(i).getPhoneNumber()%></td>
				<td><%= users.get(i).getUserName()%></td>
				<td><%= users.get(i).getAddedDate()%></td>
				<td colspan="2"><a href="adminEditUser.jsp?userName=<%=users.get(i).getUserName()%>"><i class="fa-solid fa-pen"></i></a><a href="transactions.jsp?userName=<%=users.get(i).getUserName()%>"><i class="fa-solid fa-up-right-and-down-left-from-center"></i></a></td>
			</tr>
			<%} %>
    </tbody>
</table>
</div>
<div class="container">
<h2>Admin Details</h2>
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
    </tr>
    </thead>
    <tbody>
    	<%for(int i = 0; i < admins.size(); i++){
			%>
			<tr class="item">
				<td><%=admins.get(i).getId()%></td>
				<td><%=admins.get(i).getFirstName()%></td>
				<td><%= admins.get(i).getLastName()%></td>
				<td><%= admins.get(i).getAddress()%></td>
				<td><%= admins.get(i).getPhoneNumber()%></td>
				<td><%= admins.get(i).getUserName()%></td>
				<td><%= admins.get(i).getAddedDate()%></td>
			</tr>
			<%} %>
    </tbody>
</table>
</div>
</body>
</html>