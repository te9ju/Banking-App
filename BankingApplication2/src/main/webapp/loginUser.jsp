<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.0/sweetalert.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css">
<link rel="stylesheet" href="style.css">
</head>
<body>
 <input type="hidden" id="status" value="<%= request.getAttribute("status") %>">
 <div align="center" class="signUpForm">
  <form action="<%= request.getContextPath() %>/login" method="post" class="form">
   <h1>User Login Form</h1>
   <div class="inputContainer">
        <input type="text" class="input" name="username" placeholder="Username" required>
        <label for="" class="label">Username</label>
   </div>
   <div class="inputContainer">
        <input type="password" class="input" id="password" name="password" placeholder="Password" required>
        <label for="" class="label">Password</label>
        <span id="togglePassword" onclick="showPassword()" style="position: absolute;right: -85px;top: 10px;"><i class="far fa-eye" style="cursor: pointer;"></i></span>
   </div>
   <input type="hidden" name="isAdmin" value="user">
   <input type="submit" class="submitBtn" value="Login">
  </form>
 </div>
  
 <script type="text/javascript">
	var status = document.getElementById("status").value;
	console.log(status)
	if(status == "failure"){
		swal("Sorry!", "Wrong username or password!", "error");
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
 
</body>
</html>