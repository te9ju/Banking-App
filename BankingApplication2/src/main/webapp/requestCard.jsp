<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import ="bean.User"%>
<%@ page import ="bean.Account"%>
<%@ page import ="bean.Beneficiary"%>
<%@ page import ="dao.AccountDAO"%>
<%@ page import ="dao.BeneficiaryDAO"%>
<%@ page import ="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>REQUEST CARD</title>
<link rel="stylesheet" href="style.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.0/sweetalert.min.js"></script>
<script src="https://kit.fontawesome.com/15b6f152f1.js" crossorigin="anonymous"></script>
</head>
<body>
<% User currentUser = (User)(session.getAttribute("user"));
   BeneficiaryDAO beneficiaryDAO = new BeneficiaryDAO();
   AccountDAO accountDAO = new AccountDAO();
   ArrayList<Account> accounts = accountDAO.getAllAccounts(currentUser.getId());
   ArrayList<Beneficiary> beneficiaries = beneficiaryDAO.getAllBeneficiariesForUser(currentUser.getId());
%>
<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">ABOUT</a></li>  
  <li class="nav-item"><a class="active" href="requestCard.jsp">REQUEST CARD</a></li>  
  <li class="nav-item"><a href="requestAccount.jsp">REQUEST ACCOUNT</a></li>
  <li class="nav-item"><a href="transferAmount.jsp">TRANSFER MONEY</a></li>
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
<input type="hidden" id="status" value="<%= request.getAttribute("status") %>">
 <div align="center" class="signUpForm">
  <form action="<%= request.getContextPath()%>/requestCard" method="post" class="form">
   <h1>Request Card</h1>
   <div class="inputContainer">
        <select class="input" id="cardType" name="cardType" onChange="displayCardInputs()">
        	<option>Credit Card</option>
        	<option>Debit Card</option>
        	<option>Gift Card</option>
        </select>
        <label for="" class="label">Select your card type *</label>
   </div>
   <div id="debitCardDiv" style="visibility: hidden;">
   		<div class="inputContainer">
        <select class="input" id="accountNumber" name="accountNumber">
        <%for(int i = 0; i < accounts.size(); i++){ %>
        	<option><%=accounts.get(i).getAccountType()%> Account - <%=accounts.get(i).getAccountNumber()%></option>
        <%} %>
        </select>
        <label for="" class="label">Account *</label>
   		</div>
   </div>
   <div id="giftCardDiv" style="visibility: hidden;">
   	<div class="inputContainer">
   		<select class="input" id="fromAccountNumber" name="fromAccountNumber">
	        <%for(int i = 0; i < accounts.size(); i++){ %>
	        	<option><%=accounts.get(i).getAccountType()%> Account - <%=accounts.get(i).getAccountNumber()%></option>
	        <%} %>
        </select>
        <label for="" class="label">From account *</label>
    </div>    
	   <div class="inputContainer">
	        <input type="text" class="input" id="toUserName" name="toUserName" >
	        <label for="" class="label">To username *</label>
	   </div>
	   <div class="inputContainer">
	        <input type="number" min="0" class="input" id="amount" name="amount" placeholder="0" >
	        <label for="" class="label">Amount *</label>
	   </div>
	   <div class="inputContainer">
	        <input type="text" class="input" id="purpose" name="purpose">
	        <label for="" class="label">Purpose *</label>
	   </div>
   </div>
   <p>Fields marked with * are mandatory.</p>
   <div id="giftCardDetailsDiv" style="visibility: hidden;">
	   <p class="giftCardDetails">•The card will get added to the user but they cannot use it as of now. That feature is cooking and it'll be ready soon!</p>
	   <p class="giftCardDetails">•You cannot withdraw cash using a Prepaid Gift Card.</p>
	   <p class="giftCardDetails">•The amount loaded in the Prepaid Gift Card is limited. The Bank limits it to INR 10,000.</p>
	   <p class="giftCardDetails">•The Prepaid Gift Card is a single-load card, cannot be reloaded again.</p>
   </div>
   <input type="hidden" name="userId" value="<%= currentUser.getId() %>">
   <input type="submit" class="submitBtn" value="Request"> 	
  </form>
 </div>
<%} %>

<script>
	function displayCardInputs(){
		var x = document.getElementById("giftCardDiv");
		var w = document.getElementById("debitCardDiv");
		var y = document.getElementById("cardType").value;
		var z = document.getElementById("giftCardDetailsDiv");
		console.log(y);
		if(y == "Debit Card"){
			w.style.visibility = "visible";
		}
		else{
			w.style.visibility = "hidden";
		}
		if(y == "Gift Card"){
			x.style.visibility = "visible";
			z.style.visibility = "visible";
		}
		else{
			x.style.visibility = "hidden";
			z.style.visibility = "hidden";
		}
	}
</script>
 
 <script type="text/javascript">
	var status = document.getElementById("status").value;
	console.log(status)
	if(status == "failure"){
		swal("Sorry!", "Not able to allot card!", "error");
	}
	else if(status == "success"){
		swal("Congrats!", "Card alloted to you successfully! Once approved by bank admin, the card will be visible in your profile page!", "success")
		.then(function(){
			window.location = "userProfile.jsp";
		});
	}
	else if(status == "invalid"){
		swal("Sorry!", "Not a valid username!", "error");
	}
	else if(status == "insufficientBalance"){
		swal("Sorry!", "Insufficient Balance", "error");
	}
	else if(status == "amountExceeds"){
		swal("Sorry!", "Maximum gift card amount is ₹10,000 only.", "error");
	}
	else if(status == "pending"){
		swal("Sorry!", "Sorry! You already have a credit card that is yet to be approved. Please contact bank admin before applying another.", "error");
	}
</script>
</body>
</html>