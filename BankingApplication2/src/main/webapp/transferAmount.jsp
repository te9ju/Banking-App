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
<title>TRANSFER AMOUNT</title>
<link rel="stylesheet" href="style.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.0/sweetalert.min.js"></script>
<script src="https://kit.fontawesome.com/15b6f152f1.js" crossorigin="anonymous"></script>
</head>
<body>
<% 	AccountDAO accountDAO = new AccountDAO();
	BeneficiaryDAO beneficiaryDAO = new BeneficiaryDAO();
	User currentUser = (User)(session.getAttribute("user"));
	ArrayList<Account> accounts = accountDAO.getAllAccounts(currentUser.getId());
	ArrayList<Beneficiary> beneficiaries = beneficiaryDAO.getAllBeneficiariesForUser(currentUser.getId());
	for(int i = 0; i < beneficiaries.size(); i++){
		System.out.println("Acc: "+beneficiaries.get(i).getAccountNumber()+" Name: "+beneficiaries.get(i).getBeneficiaryName());
	}
%>
<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">ABOUT</a></li>  
  <li class="nav-item"><a href="requestCard.jsp">REQUEST CARD</a></li>  
  <li class="nav-item"><a href="requestAccount.jsp">REQUEST ACCOUNT</a></li>
  <li class="nav-item"><a class="active" href="transferAmount.jsp">TRANSFER MONEY</a></li>
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
  <form action="<%= request.getContextPath() %>/transferAmount" onSubmit="return validateTextFields()" method="post" class="form">
   <h1>Transfer Amount</h1>
   <div class="inputContainer">
        <select class="input" id="fromAccountNumber" name="fromAccountNumber">
        <%for(int i = 0; i < accounts.size(); i++){%>
        	<option><%=accounts.get(i).getAccountType()%> Account - <%=accounts.get(i).getAccountNumber()%></option>
        <%} %>
        </select>
        <label for="" class="label">From account *</label>
   </div>
   <div class="inputContainer">
        <select class="input" id="toAccountNumber" name="toAccountNumber">
        <%for(int i = 0; i < beneficiaries.size(); i++){ %>
        	<option><%=beneficiaries.get(i).getBeneficiaryName() %> - <%=beneficiaries.get(i).getAccountNumber()%></option>
        <%} %>
        </select>
        <label for="" class="label">To account *</label>
   </div>
   <div class="inputContainer">
        <input type="number" min="0" class="input" id="amount" name="amount" placeholder="0" required>
        <label for="" class="label">Amount *</label>
   </div>
   <div class="inputContainer">
        <input type="text" class="input" id="purpose" name="purpose" required>
        <label for="" class="label">Purpose *</label>
   </div>
   <p>Fields marked with * are mandatory.</p>
   <%for(int i = 0; i < accounts.size(); i++){%>
		<p style="text-align: center;">Your <%=accounts.get(i).getAccountType() %> account XXXXXX<%=String.valueOf(accounts.get(i).getAccountNumber()).substring(6)%> balance is: ₹<%= accounts.get(i).getAccountBalance() %></p>
	<%}%>
   <input type="hidden" name="userId" value="<%= currentUser.getId() %>">
   <input type="submit" class="submitBtn" value="Transfer"> 	
  </form>
 </div>
 <%} %>
 
 <script type="text/javascript">
	var status = document.getElementById("status").value;
	console.log(status)
	if(status == "failure"){
		swal("Sorry!", "The request could not be processed please try again!", "error");
	}
	else if(status == "success"){
		swal("Successful!", "Money transfer successful!", "success")
		.then(function(){
			window.location = "transactions.jsp";
		});
	}
	else if(status == "addBeneficiary"){
		swal("Sorry!", "You need to add this account as a beneficiary first!", "error");
	}
	else if(status == 'limitExceeded'){
		swal("Sorry!", "Amount exceeds transfer limit for the specified beneficiary!", "error");
	}
	else if(status == 'sameAcc'){
		swal("Sorry!", "You cannot transfer amount to the same account!", "error");
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
		var accountNumber = document.getElementById("accountNumber").value;;
		var amount = document.getElementById("amount").value;
		if(!onlyNumbers.test(accountNumber)){
			swal("Sorry!", "Account number must only have numeric digits", "error");
			return false;
		}
		if(accountNumber.length != 9){
			swal("Sorry!", "Account number must be equal to 9 characters", "error");
			return false;
		}
		if(!onlyNumbers.test(amount)){
			swal("Sorry!", "Amount must only have numeric digits", "error");
			return false;
		}
		
	}
</script>

</body>
</html>