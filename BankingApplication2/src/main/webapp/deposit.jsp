<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="bean.User"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="style.css">
<script src="https://kit.fontawesome.com/15b6f152f1.js" crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>ABOUT</title>
</head>
<body>
<%User currentUser = (User)(session.getAttribute("user")); %>
<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">	ABOUT</a></li>  
  <li class="nav-item"><a href="requestCard.jsp">REQUEST CREDIT/DEBIT CARD</a></li>
  <li class="nav-item"><a href="requestAccount.jsp">REQUEST ACCOUNT</a></li>  
  <li class="nav-item"><a href="transferAmount.jsp">TRANSFER MONEY</a></li>
  <li class="nav-item"><a class="active" href="deposit.jsp">DEPOSIT MONEY</a></li>
  <li class="nav-item"><a href="transactions.jsp">TRANSACTIONS</a></li>
  <li class="nav-item"><a href="addBeneficiary.jsp">ADD BENEFICIARY</a></li>
  <li class="nav-item"><a href="utilityServices.jsp">UTILITY SERVICES</a></li>
  <li class="nav-item"><a href="utilityTransactions.jsp">UTILITY TRANSACTIONS</a></li>
    <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<%if(currentUser.getIsApproved() == 0){ %>
<h3 style="text-align: center;">WELCOME <%= currentUser.getFirstName() + " " + currentUser.getLastName() + "!" %>. Please contact bank admin and get your account approved to gain access to features!</h3>
<%}else{ %>
<h3>Feature loading.....</h3>
<%}%>
<img src="bank.jpg" class="home-page-image"></img>
</body>
</html>