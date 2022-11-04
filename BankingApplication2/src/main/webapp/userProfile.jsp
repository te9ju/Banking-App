<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="bean.User"%>
<%@ page import ="bean.GiftCard"%>
<%@ page import ="bean.Account"%>
<%@ page import ="bean.CreditCard"%>
<%@ page import ="dao.CreditCardDAO"%>
<%@ page import ="dao.GiftedCardMappingDAO"%>
<%@ page import ="bean.DebitCard"%>
<%@ page import ="dao.DebitCardDAO"%>
<%@ page import = "dao.AccountDAO"%>
<%@ page import = "dao.GiftCardDAO"%>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>USER PROFILE</title>
<link rel="stylesheet" href="style.css">
<script src="https://kit.fontawesome.com/15b6f152f1.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="userProfileStyles.css">
</head>
<body>
<%
   User currentUser = (User)(session.getAttribute("user"));
   GiftedCardMappingDAO giftedCardMappingDAO = new GiftedCardMappingDAO();
   GiftCardDAO giftCardDAO = new GiftCardDAO();
   AccountDAO accountDAO = new AccountDAO();
   CreditCardDAO creditCardDAO = new CreditCardDAO();
   DebitCardDAO debitCardDAO = new DebitCardDAO();
   ArrayList<GiftCard> giftCards = giftCardDAO.getAllGiftCards(currentUser.getId());
   ArrayList<Account> accounts = accountDAO.getAllAccounts(currentUser.getId());
   ArrayList<CreditCard> creditCards = creditCardDAO.getAllCreditCards(currentUser.getId());
   ArrayList<DebitCard> debitCards = new ArrayList<>();
   for(int i = 0; i < accounts.size(); i++){
	   debitCards.addAll(debitCardDAO.getAllDebitCards(accounts.get(i).getAccountNumber()));
   }
   if(currentUser.getIsAdmin() == 0 && currentUser.getIsApproved() == 0){
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
  <li class="nav-item"><a href="utilityTransactions.jsp">UTILITY TRANSACTIONS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a class="active" href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<h3 style="text-align: center;">WELCOME <%= currentUser.getFirstName() + " " + currentUser.getLastName() + "!" %>. Please contact bank admin and get your account approved to gain access to features!</h3>
<%}else if(currentUser.getIsAdmin() == 0 && currentUser.getIsApproved() == 1){ %>
<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">ABOUT</a></li>  
  <li class="nav-item"><a href="requestCard.jsp">REQUEST 	CARD</a></li>  
  <li class="nav-item"><a href="requestAccount.jsp">REQUEST ACCOUNT</a></li>
  <li class="nav-item"><a href="transferAmount.jsp">TRANSFER MONEY</a></li>
  <li class="nav-item"><a href="transactions.jsp">TRANSACTIONS</a></li>
  <li class="nav-item"><a href="addBeneficiary.jsp">ADD BENEFICIARY</a></li>
  <li class="nav-item"><a href="utilityServices.jsp">UTILITY SERVICES</a></li>
  <li class="nav-item"><a href="utilityTransactions.jsp">UTILITY TRANSACTIONS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a class="active" href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
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
<div class="container">
	<h2>User Details</h2>
	<div class="card">
		<div>
	        <h3 class="card-title">WELCOME <%= currentUser.getFirstName()%>! <br>Here is your profile details!</h3>
	        <div class="card-content">
	          <p>First Name: <%= currentUser.getFirstName()%></p>
	          <p>Last Name: <%= currentUser.getLastName()%></p>
	          <p>User Name: <%= currentUser.getUserName()%></p>
	          <p>Contact: <%= currentUser.getPhoneNumber()%></p>
	        </div>
	        <div class="card-link-wrapper">
        		<a href="editProfile.jsp" class="card-link">Edit Profile</a>
      		</div>
	      </div>
	</div>
</div>
<%if(currentUser.getIsAdmin() == 0 && currentUser.getIsApproved() == 1	){ %>
<div class="container">
  <h2>Account Details</h2>
  <ul class="cards">
  <%for(int i = 0; i < accounts.size(); i++){
	%>
    <li class="card">
      <div>
        <h3 class="card-title">Account <%=i+1 %></h3>
        <div class="card-content">
          <p>Account Type: <%= accounts.get(i).getAccountType()%></p>
          <p>Account Number: <%= accounts.get(i).getAccountNumber()%></p>
          <p>Account Balance: ₹<%= accounts.get(i).getAccountBalance()%></p>
          <img style="padding-top: 15px;" src="https://api.qrserver.com/v1/create-qr-code/?data=http://tejesh-pt5952:8080/BankingApplication/addBeneficiary.jsp?beneficiaryAccNumber=<%=accounts.get(i).getAccountNumber()%>%26beneficiaryFirstName=<%=currentUser.getFirstName()%>%26beneficiaryLastName=<%=currentUser.getLastName()%>&amp;size=100x100" alt="" title=""/>
          <p>Share this QR code to make adding beneficiary easier.</p>
        </div>
      </div>
    </li>
    <%} %>
  </ul>
</div>
<div class="container">
  <h2>Credit Cards Details</h2>
  <ul class="cards">
  <%for(int i = 0; i < creditCards.size(); i++){
	  if(creditCards.get(i).getIsApproved() == 0){
		  continue;
	  }
	%>
    <li class="card">
      <div>
        <h3 class="card-title">Credit Card <%=i+1 %></h3>
        <div class="card-content">
          <p>Card Number: <%= creditCards.get(i).getCardNumber()%></p>
          <p>Card Limit: <%= creditCards.get(i).getCardLimit()%></p>
        </div>
      </div>
    </li>
    <%} %>
  </ul>
</div>
<div class="container">
  <h2>Debit Cards Details</h2>
  <ul class="cards">
  <%for(int i = 0; i < debitCards.size(); i++){
	  if(debitCards.get(i).getIsApproved() == 0){
		  continue;
	  }
	%>
    <li class="card">
      <div>
        <h3 class="card-title">Debit Card <%=i+1 %></h3>
        <div class="card-content">
          <p>Card Number: <%= debitCards.get(i).getCardNumber()%></p>
          <p>Associated Account Number: <%=debitCards.get(i).getAssociatedAccountNumber()%></p>
        </div>
      </div>
    </li>
    <%} %>
  </ul>
</div>
<div class="container">
  <h2>Gift Cards Details</h2>
  <ul class="cards">
  <%for(int i = 0; i < giftCards.size(); i++){
	%>
    <li class="card">
      <div>
        <h3 class="card-title">Gift Card <%=i+1 %></h3>
        <div class="card-content">
          <p>Card Number: <%= giftCards.get(i).getCardNumber()%></p>
          <p>Card Balance: ₹<%=giftCards.get(i).getCardLimit()%></p>
          <p>Gifted By: <%=giftedCardMappingDAO.getUserGifting(giftCardDAO.getCardIdUsingCardNumber(giftCards.get(i).getCardNumber()))%></p>
        </div>
      </div>
    </li>
    <%} %>
  </ul>
</div>
<%} %>
</body>
</html>