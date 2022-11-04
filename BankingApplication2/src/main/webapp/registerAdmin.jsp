<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Sign Up</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.0/sweetalert.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css">
<link rel="stylesheet" href="style.css">
</head>
<body>
 <input type="hidden" id="status" value="<%= request.getAttribute("status") %>">
  <input type="hidden" id="phoneNumberCheck" value="<%=request.getAttribute("phoneNumberCheck")%>">
 <input type="hidden" id="userNameCheck" value="<%=request.getAttribute("userNameCheck")%>">
 <div align="center" class="signUpForm">
  <form action="<%= request.getContextPath() %>/register" onSubmit="return validateTextFields()" method="post" class="form">
   <h1>Admin Register Form</h1>
   <div class="inputContainer">
        <input type="text" class="input" id="firstName" name="firstName" placeholder="First Name" required>
        <label for="" class="label">First Name *</label>
   </div>
   <div class="inputContainer">
        <input type="text" class="input" id="lastName" name="lastName" placeholder="Last Name" required>
        <label for="" class="label">Last Name *</label>
   </div>
   <div class="inputContainer">
        <input type="text" class="input" id="username" name="username" placeholder="Username" required>
        <label for="" class="label">Username *</label> 
   </div>
   <div class="inputContainer">
        <input type="password" class="input" id="password" name="password" placeholder="Password" required>
        <label for="" class="label">Password *</label>
        <span id="togglePassword" onclick="showPassword()" style="position: absolute;right: -85px;top: 10px;"><i class="far fa-eye" style="cursor: pointer;"></i></span>
   </div>
   <div class="inputContainer">
        <input type="text" class="input" id="address" name="address" placeholder="Address" required>
        <label for="" class="label">Address *</label>
   </div>
   <div class="inputContainer">
        <input type="text" class="input" id="phoneNumber" name="phoneNumber" placeholder="Contact" required>
        <label for="" class="label">Contact *</label>
   </div>
   <p>Fields marked with * are mandatory.</p>
   <input type="hidden" name="isAdmin" value="admin">
   <input type="submit" class="submitBtn" value="Sign up">
   <a href="loginAdmin.jsp" style="text-decoration: none;"><input type="button" class="submitBtn" value="I'm already an admin"></a>
  </form>
 </div>
 
 <script type="text/javascript">
 	var status = document.getElementById("status").value;
	var phoneNumberCheck = document.getElementById("phoneNumberCheck").value;
	var userNameCheck = document.getElementById("userNameCheck").value;
	console.log("phoneNumberCheck: "+phoneNumberCheck);
	console.log("userNameCheck: "+userNameCheck);
	if(phoneNumberCheck == "failure"){
		swal("Sorry!", "Phone number already exists please try again!", "error");
	}
	if(userNameCheck == "failure"){
		swal("Sorry!", "Username already exists please try again!", "error");
	}
	if(status == "success"){
		swal("Congrats", "Account created successfully! You can view your bank account details by logging in and visiting the profile section.", "success")
		.then(function(){
			window.location = "loginUser.jsp";
		});
	}
</script>

<script>
function showPassword(){
	var x = document.getElementById("password");
	if(x.type == "password"){
		x.type = "text";
	}
	else{
		x.type = "password";
	}
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
		var userName = document.getElementById("username").value;
		var password = document.getElementById("password").value;
		var phoneNumber = document.getElementById("phoneNumber").value;
		if(specialChars.test(firstName)){
			swal("Sorry!", "First name cannot contain special characters", "error");
			return false;
		}
		if(specialChars.test(lastName)){
			swal("Sorry!", "Last name cannot contain special characters", "error");
			return false;
		}
		if(specialChars.test(userName)){
			swal("Sorry!", "Username cannot contain special characters", "error");
			return false;
		}
		if(userName.length < 6){
			swal("Sorry!", "Username cannot be less than 6 characters", "error");
			return false;
		}
		if(!passw.test(password)){
			swal("Sorry!", "Password must be 6-10 characters, must have one uppercase, one lowercase and one numeric digit", "error");
			return false;
		}
		if(userName.toUpperCase() == password.toUpperCase()){
			swal("Sorry!", "Username and password cannot be the same!", "error");
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