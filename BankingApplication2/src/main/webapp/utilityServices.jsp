<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="bean.User"%>
<%@ page import ="bean.DebitCard"%>
<%@ page import ="dao.DebitCardDAO"%>
<%@ page import ="dao.ServicesDAO"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="bean.Account"%>
<%@ page import ="dao.AccountDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UTILITY SERVICES</title>
<link rel="stylesheet" href="style.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.0/sweetalert.min.js"></script>
<script src="https://kit.fontawesome.com/15b6f152f1.js" crossorigin="anonymous"></script>
</head>
<body>
<%	User currentUser = (User)(session.getAttribute("user"));
 	DebitCardDAO debitCardDAO = new DebitCardDAO();
	ServicesDAO servicesDAO = new ServicesDAO();
	AccountDAO accountDAO = new AccountDAO();
	ArrayList<Account> accounts = accountDAO.getAllAccounts(currentUser.getId());
  	ArrayList<String> services = servicesDAO.getAllServices();
  	ArrayList<DebitCard> debitCards = new ArrayList<>();
  	for(int i = 0; i < accounts.size(); i++){
  		debitCards.addAll(debitCardDAO.getAllDebitCards(accounts.get(i).getAccountNumber()));
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
  <li class="nav-item"><a class="active" href="utilityServices.jsp">UTILITY SERVICES</a></li>
  <li class="nav-item"><a href="utilityTransactions.jsp">UTILITY TRANSACTIONS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<%if(currentUser.getIsApproved() == 0){ %>
<h3 style="text-align: center;">WELCOME <%= currentUser.getFirstName() + " " + currentUser.getLastName() + "!" %>. Please contact bank admin and get your account approved to gain access to features!</h3>
<%}else{ %>
<input type="hidden" id="status" value="<%= request.getAttribute("status") %>">
 <div align="center" class="signUpForm">
  <form action="<%= request.getContextPath() %>/utilityServiceTransaction" method="post" onSubmit="return validateTextFields()" class="form">
   <h1>Pay Utility Services Bills</h1>
   <div class="inputContainer">
        <select class="input" id="paymentMethod" name="paymentMethod" onChange="displayPaymentInputs()">
        	<option>Debit Card</option>
        	<option>Transfer From Account</option>
        </select>
        <label for="" class="label">Select your payment method *</label>
   </div>
   <div id="accountDiv" style="visibility: hidden;">
	   <div class="inputContainer">
	        <select class="input" id="fromAccountNumber" name="fromAccountNumber">
	        <%for(int i = 0; i < accounts.size(); i++){ %>
	        	<option><%=accounts.get(i).getAccountType()%> Account - <%=accounts.get(i).getAccountNumber()%></option>
	        <%} %>
	        </select>
	        <label for="" class="label">From account *</label>
	   </div>
   </div>
   <div id="debitCardDiv" style="visibility: visible;">
		<div class="inputContainer">
	        <select class="input" id="debitCardNumber" name="debitCardNumber">
	        <%for(int i = 0; i < debitCards.size(); i++){ %>
	        	<option><%=debitCards.get(i).getCardNumber()%></option>
	        <%} %>
	        </select>
	        <label for="" class="label">Card Number *</label>
	   </div>
   </div>
   <div class="inputContainer">
        <select class="input" id="serviceName" name="serviceName">
        <%for(int i = 0; i < services.size(); i++){ %>
        	<option><%=services.get(i) %></option>
        <%} %>
        </select>
        <label for="" class="label">Select service *</label>
   </div>
   <div class="inputContainer">
        <input type="text" class="input" id="serviceConsumerNumber" name="serviceConsumerNumber" placeholder="000000000" required>
        <label for="" class="label">Service Consumer Number *</label>
   </div>
   <div class="inputContainer">
        <input type="number" min="0" class="input" id="billAmount" name="billAmount" step="100" placeholder="0" required>
        <label for="" class="label">Bill Amount *</label>
   </div>
   <div class="inputContainer">
        <input type="text" class="input" id="purpose" name="purpose" required>
        <label for="" class="label">Purpose *</label>
   </div>
   <p>Fields marked with * are mandatory.</p>
   <input type="hidden" name="userId" value="<%= currentUser.getId() %>">
   <input type="submit" class="submitBtn" value="Pay Bill"> 	
  </form>
 </div>
<%} %>
 
 
 <script>
	function displayPaymentInputs(){
		var x = document.getElementById("accountDiv");
		var w = document.getElementById("debitCardDiv");
		var y = document.getElementById("paymentMethod").value;
		if(y == "Debit Card"){
			w.style.visibility = "visible";
		}
		else{
			w.style.visibility = "hidden";
		}
		if(y == "Transfer From Account"){
			x.style.visibility = "visible";
		}
		else{
			x.style.visibility = "hidden";
		}
	}
</script>


 <script type="text/javascript">
	var status = document.getElementById("status").value;
	console.log(status)
	if(status == "failure"){
		swal("Sorry!", "Not able to process payment!", "error");
	}
	else if(status == "success"){
		swal("Congrats!", "Bill paid successfully!", "success")
		.then(function(){
			window.location = "utilityTransactions.jsp";
		});
	}
</script>

<script>
function validateTextFields(){
	//passw = 6-10 chars, one upper, one lower, one digit.
	//specialChars = contains special character or no.
	//onlyNumbers = contains only numbers.
	const onlyNumbers = /^[0-9]+$/;
	const passw = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,10}$/;
	const specialChars = /[`!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
	var amount = document.getElementById("billAmount").value;
	if(!onlyNumbers.test(amount)){
		swal("Sorry!", "Bill Amount must only have numeric digits", "error");
		return false;
	}
	
}
</script>

</body>
</html>