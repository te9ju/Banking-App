<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="bean.User"%>
<%@ page import ="bean.Beneficiary"%>
<%@ page import ="dao.BeneficiaryDAO"%>
<%@ page import ="dao.UserBeneficiaryMappingDAO"%>
<%@ page import ="dao.AccountDAO"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="bean.Account"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>REQUEST ACCOUNT</title>
<link rel="stylesheet" href="style.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.0/sweetalert.min.js"></script>
<script src="https://kit.fontawesome.com/15b6f152f1.js" crossorigin="anonymous"></script>
</head>
<body>
<% UserBeneficiaryMappingDAO userBeneficiaryMappingDAO = new UserBeneficiaryMappingDAO();
   BeneficiaryDAO beneficiaryDAO = new BeneficiaryDAO();
   User currentUser = (User)(session.getAttribute("user"));
   AccountDAO accounDAO = new AccountDAO();
   ArrayList<Account> accounts = accounDAO.getAllAccounts(currentUser.getId());
   if(accounts.size() == 2){
	   for(int i = 0; i < accounts.size(); i++){
		   if(beneficiaryDAO.checkUserBeneficiary(currentUser.getId(), accounts.get(i).getAccountNumber()) > 0) {
			   continue;
		   }
		   Beneficiary beneficiary = new Beneficiary();
		   beneficiary.setBeneficiaryName(currentUser.getFirstName());
 	       beneficiary.setAccountNumber(accounts.get(i).getAccountNumber());
	       beneficiary.setAliasName(currentUser.getFirstName());
	       beneficiary.setTransferLimit(10000);
	       int beneficiaryId = beneficiaryDAO.createBeneficiary(beneficiary);
	       if(beneficiaryId > 0) {
				if(userBeneficiaryMappingDAO.createUserBeneficiaryMapping(currentUser.getId(), beneficiaryId) > 0) {
					System.out.println("Self beneficiary created successfully!");
				}
			}
	   }
   }
%>
<%	if(currentUser.getIsApproved() == 0){%>
	<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">ABOUT</a></li>  
  <li class="nav-item"><a href="requestCard.jsp">REQUEST CARD</a></li>  
  <li class="nav-item"><a class="active" href="requestAccount.jsp">REQUEST ACCOUNT</a></li>
  <li class="nav-item"><a href="transferAmount.jsp">TRANSFER MONEY</a></li>
  <li class="nav-item"><a href="transactions.jsp">TRANSACTIONS</a></li>
  <li class="nav-item"><a href="addBeneficiary.jsp">ADD BENEFICIARY</a></li>
  <li class="nav-item"><a href="utilityServices.jsp">UTILITY SERVICES</a></li>
  <li class="nav-item"><a href="utilityTransactions.jsp">UTILITY TRANSACTIONS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<h3 style="text-align: center;">WELCOME <%= currentUser.getFirstName() + " " + currentUser.getLastName() + "!" %>. Please contact bank admin and get your account approved to gain access to features!</h3>
<%}else if(accounts.size() == 2){
%>
<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">ABOUT</a></li>  
  <li class="nav-item"><a href="requestCard.jsp">REQUEST CARD</a></li>  
  <li class="nav-item"><a class="active" href="requestAccount.jsp">REQUEST ACCOUNT</a></li>
  <li class="nav-item"><a href="transferAmount.jsp">TRANSFER MONEY</a></li>
  <li class="nav-item"><a href="transactions.jsp">TRANSACTIONS</a></li>
  <li class="nav-item"><a href="addBeneficiary.jsp">ADD BENEFICIARY</a></li>
  <li class="nav-item"><a href="utilityServices.jsp">UTILITY SERVICES</a></li>
  <li class="nav-item"><a href="utilityTransactions.jsp">UTILITY TRANSACTIONS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<h3 style="text-align: center;">SORRY! YOU ALREADY HAVE TWO ACCOUNTS! CAN'T CREATE MORE!!</h3>
<%} else{ %>
<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">ABOUT</a></li>  
  <li class="nav-item"><a href="requestCard.jsp">REQUEST CARD</a></li>  
  <li class="nav-item"><a class="active" href="requestAccount.jsp">REQUEST ACCOUNT</a></li>
  <li class="nav-item"><a href="transferAmount.jsp">TRANSFER MONEY</a></li>
  <li class="nav-item"><a href="transactions.jsp">TRANSACTIONS</a></li>
  <li class="nav-item"><a href="addBeneficiary.jsp">ADD BENEFICIARY</a></li>
  <li class="nav-item"><a href="utilityServices.jsp">UTILITY SERVICES</a></li>
  <li class="nav-item"><a href="utilityTransactions.jsp">UTILITY TRANSACTIONS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<input type="hidden" id="status" value="<%= request.getAttribute("status") %>">
 <div align="center" class="signUpForm">
  <form action="<%= request.getContextPath() %>/requestAccount" onSubmit="return validateTextFields()" method="post" class="form">
   <h1>New Card Form</h1>
   <div class="inputContainer">
        <select class="input" id="accountType" name="accountType">
        	<option>Savings</option>
        	<option>Salary</option>
        </select>
        <label for="" class="label">Select your account type *</label>
   </div>
   <p>Fields marked with * are mandatory.</p>
   <input type="hidden" name="userId" value="<%= currentUser.getId() %>">
   <input type="submit" class="submitBtn" value="Request"> 	
  </form>
 </div>
<%} %>

 
 <script type="text/javascript">
	var status = document.getElementById("status").value;
	console.log(status)
	if(status == "failure"){
		swal("Sorry!", "Not able to create an account for you!", "error");
	}
	else if(status == "success"){
		swal("Congrats!", "Account created!", "success");
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
		var accountType = document.getElementById("accountType");
		if(cardType == "Select your account type"){
			swal("Sorry!", "Please select account type", "error");
			return false;
		}
		
	}
</script>
</body>
</html>