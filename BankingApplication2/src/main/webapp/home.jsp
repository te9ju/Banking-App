<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="bean.User"%>
<%@ page import ="bean.Account"%>
<%@ page import ="dao.AccountDAO"%>
<%@ page import ="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="style.css">
<script src="https://kit.fontawesome.com/15b6f152f1.js" crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>BANKING APPLICATION</title>
</head>
<body>
<% User currentUser = (User)(session.getAttribute("user"));
	AccountDAO accounDAO = new AccountDAO();
	ArrayList<Account> accounts = accounDAO.getAllAccounts(currentUser.getId());
   if(currentUser.getIsAdmin() == 0){%>
<ul class="nav-list">  
  <li class="nav-item"><a class="active" href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">	ABOUT</a></li>  
  <li class="nav-item"><a href="requestCard.jsp">REQUEST CARD</a></li>
  <li class="nav-item"><a href="requestAccount.jsp">REQUEST ACCOUNT</a></li>
  <li class="nav-item"><a href="transferAmount.jsp">TRANSFER MONEY</a></li>
  <li class="nav-item"><a href="transactions.jsp">TRANSACTIONS</a></li>
  <li class="nav-item"><a href="addBeneficiary.jsp">ADD BENEFICIARY</a></li>
  <li class="nav-item"><a href="utilityServices.jsp">UTILITY SERVICES</a></li>
  <li class="nav-item"><a href="utilityTransactions.jsp">UTILITY TRANSACTIONS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
  
</ul>
<%}else if(currentUser.getIsAdmin() == 1){ %>
<ul class="nav-list">  
  <li class="nav-item"><a class="active" href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">	ABOUT</a></li>  
  <li class="nav-item"><a href="allUsers.jsp">VIEW ALL USERS</a></li>  
  <li class="nav-item"><a href="allAccounts.jsp">VIEW ALL ACCOUNTS</a></li>
  <li class="nav-item"><a href="allTransactions.jsp">VIEW ALL TRANSACTIONS</a></li>
  <li class="nav-item"><a href="allApprovals.jsp">VIEW PENDING APPROVALS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<%} %>
<h3 style="text-align: center;">WELCOME <%= currentUser.getFirstName() + " " + currentUser.getLastName() + "!" %></h3>
<div id="balanceDiv" style="display: none;">
<%for(int i = 0; i < accounts.size(); i++){
	%>
<h3 style="text-align: center;">Your <%=accounts.get(i).getAccountType() %> account XXXXXX<%=String.valueOf(accounts.get(i).getAccountNumber()).substring(6)%> balance is: ₹<%= accounts.get(i).getAccountBalance() %></h3>
<%}%>
</div>
<input type="button" onclick="showBalance()" id="balanceBtn" class="balanceBtn" value="Show Balance">
<img src="bank.jpg" class="home-page-image"></img>

<script>
	function showBalance(){
		var btn = document.getElementById("balanceBtn");
		var x = document.getElementById("balanceDiv");
		if(x.style.display === "none"){
			x.style.display = "block";
			balanceBtn.value = "Hide Balance";
		}
		else{
			x.style.display = "none";
			balanceBtn.value = "Show Balance";
		}
	}
</script>

</body>
</html>