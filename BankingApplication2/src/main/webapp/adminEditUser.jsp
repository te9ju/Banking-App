<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<%@ page import ="bean.User"%>
<%@ page import ="dao.UserDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Profile</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.0/sweetalert.min.js"></script>
<link rel="stylesheet" href="style.css">
</head>
<body>
<% 	User currentUser = (User)(session.getAttribute("user"));
	String userName = request.getParameter("userName");
	UserDAO userDAO = new UserDAO();
	User editUser = userDAO.getOneUser(userName);%>
 <input type="hidden" id="status" value="<%= request.getAttribute("status") %>">
 <input type="hidden" id="phoneNumberCheck" value="<%=request.getAttribute("phoneNumberCheck")%>">
 <input type="hidden" id="userNameCheck" value="<%=request.getAttribute("userNameCheck")%>">
 <div align="center" class="signUpForm">
  <form action="<%= request.getContextPath() %>/editProfile" method="post" onSubmit="return validateTextFields()" class="form">
   <h1>Edit Profile</h1>
   <div class="inputContainer">
        <input type="text" class="input" name="firstName" id="firstName" placeholder="First Name" value="<%=editUser.getFirstName()%>" required>
        <label for="" class="label">First Name</label>
   </div>
   <div class="inputContainer">
        <input type="text" class="input" name="lastName" id="lastName" placeholder="Last Name" value="<%=editUser.getLastName()%>" required>
        <label for="" class="label">Last Name</label>
   </div>
   <div class="inputContainer">
        <input type="text" class="input" name="address" id="address" placeholder="Address" value="<%=editUser.getAddress()%>" required>
        <label for="" class="label">Address</label>
   </div>
   <div class="inputContainer">
        <input type="text" class="input" name="phoneNumber" id="phoneNumber" placeholder="Contact" value="<%=editUser.getPhoneNumber()%>" required>
        <label for="" class="label">Contact</label>
   </div>
   <input type="hidden" name="password" value="<%=editUser.getPassword()%>">
   <input type="hidden" name="userId" value="<%=editUser.getId()%>">
   <input type="hidden" name="isAdmin" value="1">
   <input type="hidden" name="adminUsername" value="<%=currentUser.getUserName()%>">
   <input type="hidden" name="userName" value="<%=editUser.getUserName() %>">
   <input type="submit" class="submitBtn" value="Save">
   <!--<a href="registerAdmin.jsp" style="text-decoration: none;"><input type="button" class="submitBtn" value="Admin Sign Up"></a>-->
  </form>
 </div>
 
 <script type="text/javascript">
	var status = document.getElementById("status").value;
	var phoneNumberCheck = document.getElementById("phoneNumberCheck").value;
	var userNameCheck = document.getElementById("userNameCheck").value;
	console.log("phoneNumberCheck: "+phoneNumberCheck);
	if(phoneNumberCheck == "failure"){
		swal("Sorry!", "Phone number already exists please try again!", "error");
	}
	if(userNameCheck == "failure"){
		swal("Sorry!", "Username already exists please try again!", "error");
	}
	console.log(status)
	if(status == "success"){
		swal("Congrats", "Profile updated successfully!", "success")
		.then(function(){
			window.location = "allUsers.jsp";
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
		var firstName = document.getElementById("firstName").value;
		var lastName = document.getElementById("lastName").value;
		var phoneNumber = document.getElementById("phoneNumber").value;
		if(specialChars.test(firstName)){
			swal("Sorry!", "First name cannot contain special characters", "error");
			return false;
		}
		if(specialChars.test(lastName)){
			swal("Sorry!", "Last name cannot contain special characters", "error");
			return false;
		}
		if(!onlyNumbers.test(phoneNumber)){
			swal("Sorry!", "Phone number must only have numeric digits", "error");
			return false;
		}
		if(phoneNumber.length != 10){
			swal("Sorry!", "Phone number must be 10 digits", "error");
			return false;
		}
	}
</script>
 
</body>
</html>