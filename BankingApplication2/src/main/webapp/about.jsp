<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="bean.User"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="style.css">
<script src="https://kit.fontawesome.com/15b6f152f1.js" crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>ABOUT</title>
</head>
<body>
<%User currentUser = (User)(session.getAttribute("user"));
  if(currentUser.getIsAdmin() == 0){
  %>
<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>
  <li class="nav-item"><a class="active" href="about.jsp">	ABOUT</a></li>  
  <li class="nav-item"><a href="requestCard.jsp">REQUEST CARD</a></li>
  <li class="nav-item"><a href="requestAccount.jsp">REQUEST ACCOUNT</a></li>  
  <li class="nav-item"><a href="transferAmount.jsp">TRANSFER MONEY</a></li>
  <li class="nav-item"><a href="transactions.jsp">TRANSACTIONS</a></li>
  <li class="nav-item"><a href="addBeneficiary.jsp">ADD BENEFICIARY</a></li>
  <li class="nav-item"><a href="utilityServices.jsp">UTILITY SERVICES</a></li>
  <li class="nav-item"><a href="utilityTransactions.jsp">UTILITY TRANSACTIONS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<%}
  else{%>
  <ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a class="active" href="about.jsp">	ABOUT</a></li>  
  <li class="nav-item"><a href="allUsers.jsp">VIEW ALL USERS</a></li>  
  <li class="nav-item"><a href="allAccounts.jsp">VIEW ALL ACCOUNTS</a></li>
  <li class="nav-item"><a href="allTransactions.jsp">VIEW ALL TRANSACTIONS</a></li>
  <li class="nav-item"><a href="allApprovals.jsp">VIEW PENDING APPROVALS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<%} %>
<h3>This is a bank. Our bank is a very nice bank. This is the about section of our bank. The developer has no idea about what to put here (as of now). So this is just some dummy text.	</h3>
<p>
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ornare sem eleifend urna mollis fringilla. Suspendisse sed lectus sed enim accumsan fringilla quis eget leo. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Fusce ac gravida diam. Nulla congue est vitae lectus accumsan, hendrerit malesuada eros suscipit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Duis vel elit rutrum, pulvinar libero sit amet, suscipit mauris. Integer congue leo et turpis elementum, a scelerisque risus pretium. Cras eu sapien vel tortor vehicula venenatis.
Aliquam dignissim arcu faucibus condimentum viverra. Pellentesque at augue et diam dapibus aliquam vel ac leo. Vivamus quis iaculis eros. Nullam rutrum, orci eu lobortis laoreet, mauris erat mollis mauris, nec gravida lacus nisl sit amet mauris. Aliquam blandit ultrices dolor vel scelerisque. Proin ultrices molestie elit, ac aliquet mauris lobortis sit amet. Donec ac nisl bibendum, finibus neque ac, blandit nisl. Suspendisse porttitor porta mollis. Pellentesque fringilla imperdiet consectetur. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Morbi lacinia ipsum nec enim congue viverra.
Nam id enim nibh. Mauris eleifend sodales tempus. Cras tempor ipsum sed tempor gravida. Interdum et malesuada fames ac ante ipsum primis in faucibus. Donec pellentesque odio quis luctus pretium. Sed a commodo elit. Praesent sagittis sit amet arcu non fermentum. Phasellus eget felis iaculis, pharetra est non, aliquet felis. Nulla ac metus quis diam tristique convallis ut nec urna. Nulla facilisi. Ut semper porttitor orci sit amet malesuada. Nulla facilisi. Cras nec ipsum dignissim est maximus cursus.
Curabitur non elit posuere lacus dictum fermentum. Pellentesque ut scelerisque arcu. Ut at semper leo, id consectetur ex. In gravida viverra consequat. Quisque varius laoreet lectus non vehicula. Maecenas laoreet hendrerit magna a maximus. Maecenas libero leo, ultrices suscipit ornare in, placerat ac sapien. Fusce tristique diam eu odio tincidunt laoreet.
Quisque sed est vel ex porttitor iaculis vel in nisl. Morbi rutrum eros et tortor vulputate, ultrices consequat dolor laoreet. 
</p>
<img src="bank.jpg" class="home-page-image"></img>
</body>
</html>