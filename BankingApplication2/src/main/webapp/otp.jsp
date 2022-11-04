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
  <form action="<%= request.getContextPath() %>/otp" method="post" class="form">
   <h1>OTP</h1>
   <div class="inputContainer">
        <input type="text" class="input" name="otp" placeholder="OTP" required>
        <label for="" class="label">OTP</label>
   </div>
   <input type="hidden" name="isAdmin" value="user">
   <p id="timeLeft" class="blink"></p>
   <input type="submit" class="submitBtn" value="Submit OTP">
  </form>
 </div>
 
 <script>
 	var timeLeft = 120;
 	var elem = document.getElementById('timeLeft');
 	var timerId = setInterval(countdown, 1000);

 	function countdown() {
     	if (timeLeft == -1) {
         	clearTimeout(timerId);
         	doSomething();
     	} else {
         	elem.innerHTML = timeLeft + ' seconds remaining';
         	timeLeft--;
     	}
 	}
	function doSomething() {
     	swal("Sorry!", "Time limit for OTP exceeded!", "error")
     	.then(function(){
		    document.location.href="/BankingApplication/logOut";
		});
	}
 </script>
 <script type="text/javascript">
	var status = document.getElementById("status").value;
	console.log(status)
	if(status == "failure"){
		swal("Sorry!", "Wrong OTP!", "error")
		.then(function(){
		    document.location.href="/BankingApplication/logOut";
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
 
</body>
</html>