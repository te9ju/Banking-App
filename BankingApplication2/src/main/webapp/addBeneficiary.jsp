<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="bean.User"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="style.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.0/sweetalert.min.js"></script>
<script src="https://kit.fontawesome.com/15b6f152f1.js" crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>ADD BENEFICIARY</title>
</head>
<body>
<%User currentUser = (User)(session.getAttribute("user")); %>
<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">	ABOUT</a></li>  
  <li class="nav-item"><a href="requestCard.jsp">REQUEST CARD</a></li>
  <li class="nav-item"><a href="requestAccount.jsp">REQUEST ACCOUNT</a></li>  
  <li class="nav-item"><a href="transferAmount.jsp">TRANSFER MONEY</a></li>
  <li class="nav-item"><a href="transactions.jsp">TRANSACTIONS</a></li>
  <li class="nav-item"><a class="active" href="addBeneficiary.jsp">ADD BENEFICIARY</a></li>
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
  <form action="<%= request.getContextPath() %>/addBeneficiary" onSubmit="return validateTextFields()" method="post" class="form">
   <h1>Transfer Amount</h1>
   <div class="inputContainer">
   		<%if(request.getParameter("beneficiaryFirstName") != null){ %>
        <input type="text" class="input" id="name" name="name" placeholder="Beneficiary Name" value="<%=request.getParameter("beneficiaryFirstName")%>" required>
        <%}else{ %>
        <input type="text" class="input" id="name" name="name" placeholder="Beneficiary Name" required>
        <%} %>
        <label for="" class="label">Beneficiary Name *</label>
   </div>
   <div class="inputContainer">
   		<%if(request.getParameter("beneficiaryLastName") != null){ %>
        <input type="text" class="input" id="aliasName" name="aliasName" placeholder="Alias Name" value="<%=request.getParameter("beneficiaryLastName")%>" required>
        <%}else{ %>
        <input type="text" class="input" id="aliasName" name="aliasName" placeholder="Alias Name" required>
        <%} %>
        <label for="" class="label">Alias Name *</label>
   </div>
   <div class="inputContainer">
   		<%if(request.getParameter("beneficiaryAccNumber") != null){ %>
   		<input type="text" class="input" id="accountNumber" name="accountNumber" placeholder="Account Number" value="<%=request.getParameter("beneficiaryAccNumber")%>" required>
   		<%}else{ %>
        <input type="text" class="input" id="accountNumber" name="accountNumber" placeholder="Account Number" required>
        <%} %>
        <label for="" class="label">Account Number *</label>
   </div>
   <div class="inputContainer">
        <input type="number" min="0" class="input" id="transferLimit" name="transferLimit" placeholder="0" required>
        <label for="" class="label">Transfer Limit *</label>
   </div>
   <p>Fields marked with * are mandatory.</p>
   <input type="hidden" name="userId" value="<%= currentUser.getId() %>">
   <input type="submit" class="submitBtn" value="Add"> 	
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
		swal("Successful!", "Beneficiary added!", "success");
	}
	else if(status == "repeat"){
		swal("Sorry!", "You already have this account added as beneficiary!", "error");
	}
</script>
</body>
</html>