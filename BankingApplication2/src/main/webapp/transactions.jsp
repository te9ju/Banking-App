<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>
<%@ page import ="bean.UtilityTransaction"%>
<%@ page import ="bean.Transaction"%>
<%@ page import ="bean.User"%>
<%@ page import ="bean.Account"%>
<%@ page import ="dao.AccountDAO"%>
<%@ page import ="bean.GiftCardTransaction"%>
<%@ page import ="dao.GiftCardTransactionDAO"%>
<%@ page import ="dao.UserDAO"%>
<%@ page import ="dao.TransactionDAO"%>
<%@ page import ="dao.UtilityTransactionDAO"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.util.Map"%>
<%@ page import ="java.util.HashMap"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="tableStyle.css">
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
<script src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>
<script src="https://kit.fontawesome.com/15b6f152f1.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="userProfileStyles.css">
<meta charset="UTF-8">
<title>TRANSACTIONS</title>
</head>
<body>
<%	Gson gsonObj = new Gson();
	Map<Object,Object> map = null;
	List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
	User currentUser = (User)(session.getAttribute("user"));
	GiftCardTransactionDAO giftCardTransactionDAO = new GiftCardTransactionDAO();
	UtilityTransactionDAO utilityTransactionDAO = new UtilityTransactionDAO();
	User adminViewUser = new User();
	String dataPoints = "";
	ArrayList<GiftCardTransaction> account1GiftCardTransactions = new ArrayList<>();
	ArrayList<GiftCardTransaction> account2GiftCardTransactions = new ArrayList<>();
	ArrayList<UtilityTransaction> account1UtilityTransactions = new ArrayList<>();
	ArrayList<UtilityTransaction> account2UtilityTransactions = new ArrayList<>();
	ArrayList<Transaction> account1Transactions = new ArrayList<>();
	ArrayList<Transaction> account2Transactions = new ArrayList<>();
	ArrayList<Integer> accountNumbers = new ArrayList<>();
	ArrayList<Account> accounts = new ArrayList<>();
	if(currentUser.getIsAdmin() == 1){
		String userName = request.getParameter("userName");
		UserDAO userDAO = new UserDAO();
		adminViewUser = userDAO.getOneUser(userName);
		TransactionDAO transactionDAO = new TransactionDAO();
		AccountDAO accountDAO = new AccountDAO();
		accounts = accountDAO.getAllAccounts(adminViewUser.getId());
		for(int i = 0; i < accounts.size(); i++){
			accountNumbers.add(accounts.get(i).getAccountNumber());
		}
		account1GiftCardTransactions.addAll(giftCardTransactionDAO.getAllGiftCardTransactionsForAccount(accounts.get(0).getAccountNumber()));
		account2GiftCardTransactions.addAll(giftCardTransactionDAO.getAllGiftCardTransactionsForAccount(accounts.get(1).getAccountNumber()));
		account1Transactions.addAll(transactionDAO.getAllTransactionsForAccount(accounts.get(0).getAccountNumber()));
		account2Transactions.addAll(transactionDAO.getAllTransactionsForAccount(accounts.get(1).getAccountNumber()));
		account1UtilityTransactions.addAll(utilityTransactionDAO.getAllUtilityTransactionsForAccount(accounts.get(0).getAccountNumber()));
		account2UtilityTransactions.addAll(utilityTransactionDAO.getAllUtilityTransactionsForAccount(accounts.get(1).getAccountNumber()));
	}
	else{
		TransactionDAO transactionDAO = new TransactionDAO();
		AccountDAO accountDAO = new AccountDAO();
		accounts = accountDAO.getAllAccounts(currentUser.getId());
		for(int i = 0; i < accounts.size(); i++){
			accountNumbers.add(accounts.get(i).getAccountNumber());
		}
		account1GiftCardTransactions.addAll(giftCardTransactionDAO.getAllGiftCardTransactionsForAccount(accounts.get(0).getAccountNumber()));
		account2GiftCardTransactions.addAll(giftCardTransactionDAO.getAllGiftCardTransactionsForAccount(accounts.get(1).getAccountNumber()));
		account1Transactions.addAll(transactionDAO.getAllTransactionsForAccount(accounts.get(0).getAccountNumber()));
		account2Transactions.addAll(transactionDAO.getAllTransactionsForAccount(accounts.get(1).getAccountNumber()));
		account1UtilityTransactions.addAll(utilityTransactionDAO.getAllUtilityTransactionsForAccount(accounts.get(0).getAccountNumber()));
		account2UtilityTransactions.addAll(utilityTransactionDAO.getAllUtilityTransactionsForAccount(accounts.get(1).getAccountNumber()));
		int totalNumberOfTransactions = account1GiftCardTransactions.size()+account1Transactions.size()+account1UtilityTransactions.size()+account2GiftCardTransactions.size()+account2Transactions.size()+account2UtilityTransactions.size();
		map = new HashMap<Object,Object>(); map.put("label", "Account to Account Transactions"); map.put("y", 100 * (float) (account1Transactions.size()+account2Transactions.size())/totalNumberOfTransactions); map.put("exploded", true); list.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "Transactions Towards Gifting Cards"); map.put("y", 100 * (float) (account1GiftCardTransactions.size()+account2GiftCardTransactions.size())/totalNumberOfTransactions); list.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "Utility Service Transactions"); map.put("y", 100 * (float) (account1UtilityTransactions.size()+account2UtilityTransactions.size())/totalNumberOfTransactions); list.add(map);
		dataPoints = gsonObj.toJson(list);
	}
	if(currentUser.getIsAdmin() == 1){
%>
<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">	ABOUT</a></li>  
  <li class="nav-item"><a class="active" href="allUsers.jsp">VIEW ALL USERS</a></li>  
  <li class="nav-item"><a href="allAccounts.jsp">VIEW ALL ACCOUNTS</a></li>
  <li class="nav-item"><a href="allTransactions.jsp">VIEW ALL TRANSACTIONS</a></li>
  <li class="nav-item"><a href="allApprovals.jsp">VIEW PENDING APPROVALS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<div class="container">
  <h2>Filter Transactions</h2>
</div>
<div align="center" class="signUpForm">
	<form action="<%= request.getContextPath() %>/searchTransactions" method="post" class="searchForm" onSubmit="return validateDate()">
		<div class="inputContainer">
	        <select class="input" id="acountNumber" name="accountNumber">
	        <%for(int i = 0; i < accounts.size(); i++){ %>
	        	<option><%=accounts.get(i).getAccountType()%> Account - <%=accounts.get(i).getAccountNumber()%></option>
	        <%} %>
	        </select>
	        <label for="" class="label">From account *</label>
   		</div>
		<div class="inputContainer">
        	<input type="date" class="input" id="fromDate" name="fromDate" placeholder="From Date">
        	<label for="" class="label">From Date</label>
		</div>
		<div class="inputContainer">
        	<input type="date" class="input" id="toDate" name="toDate" placeholder="To Date">
        	<label for="" class="label">To Date</label>
		</div>
		<input type="hidden" name="userId" value="<%=currentUser.getId() %>">
		<input type="submit" class="submitBtn" value="Submit">
	</form>
</div>
<div class="container">
  <h2><%=accounts.get(0).getAccountType()%> Account <%=accounts.get(0).getAccountNumber() %> Transaction Details</h2>
</div>
<div class="table-wrapper print-container">
<table id="transactionTable" class="fl-table sortable">
	<thead>
	<tr>
		<th>Transaction ID</th>
        <th>Type</th>
        <th>Amount</th>
        <th>Date and Time of Transaction</th>
        <th>Purpose</th>
    </tr>
    </thead>
    <tbody>
    	<%for(int i = 0; i < account1Transactions.size(); i++){
			%>
			<tr class="item">
				<td><%=account1Transactions.get(i).getId()%></td>
				<%if(accountNumbers.contains(Integer.parseInt(account1Transactions.get(i).getFromAccountNumber())) && !accountNumbers.contains(Integer.parseInt(account1Transactions.get(i).getToAccountNumber()))){%>
				<td>Credit</td>
				<%}else if(!accountNumbers.contains(Integer.parseInt(account1Transactions.get(i).getFromAccountNumber())) && accountNumbers.contains(Integer.parseInt(account1Transactions.get(i).getToAccountNumber()))){%>
				<td>Debit</td>
				<%}else{ %>
				<td>Self Transfer</td>
				<%} %>
				<td>₹<%= account1Transactions.get(i).getAmount()%></td>
				<td><%= account1Transactions.get(i).getTransactionDate()%></td>
				<td><%=account1Transactions.get(i).getPurpose()%></td>
			</tr>
			<%} %>
		<%for(int i = 0; i < account1UtilityTransactions.size(); i++){ %>
				<tr class="item">
				<td><%=account1UtilityTransactions.get(i).getId()%></td>
				<td>Credit</td>
				<td>₹<%= account1UtilityTransactions.get(i).getBillAmount()%></td>
				<td><%= account1UtilityTransactions.get(i).getTransactionDate()%></td>
				<td><%=account1UtilityTransactions.get(i).getPurpose()%></td>
			</tr>
		<%} %>
		<%for(int i = 0; i < account1GiftCardTransactions.size(); i++){ %>
				<tr class="item">
				<td><%=account1GiftCardTransactions.get(i).getId()%></td>
				<td>Credit</td>
				<td>₹<%= account1GiftCardTransactions.get(i).getAmount()%></td>
				<td><%= account1GiftCardTransactions.get(i).getTransactionDate()%></td>
				<td><%=account1GiftCardTransactions.get(i).getPurpose()%></td>
			</tr>
		<%} %>	
    </tbody>
</table>
</div>
<div class="container">
  <h2><%=accounts.get(1).getAccountType()%> Account <%=accounts.get(1).getAccountNumber() %> Transaction Details</h2>
</div>
<div class="table-wrapper print-container">
<table id="transactionTable" class="fl-table sortable">
	<thead>
	<tr>
		<th>Transaction ID</th>
        <th>Type</th>
        <th>Amount</th>
        <th>Date and Time of Transaction</th>
        <th>Purpose</th>
    </tr>
    </thead>
    <tbody>
    	<%for(int i = 0; i < account2Transactions.size(); i++){
			%>
			<tr class="item">
				<td><%=account2Transactions.get(i).getId()%></td>
				<%if(accountNumbers.contains(Integer.parseInt(account2Transactions.get(i).getFromAccountNumber())) && !accountNumbers.contains(Integer.parseInt(account2Transactions.get(i).getToAccountNumber()))){%>
				<td>Credit</td>
				<%}else if(!accountNumbers.contains(Integer.parseInt(account2Transactions.get(i).getFromAccountNumber())) && accountNumbers.contains(Integer.parseInt(account2Transactions.get(i).getToAccountNumber()))){%>
				<td>Debit</td>
				<%}else{ %>
				<td>Self Transfer</td>
				<%} %>
				<td>₹<%=account2Transactions.get(i).getAmount()%></td>
				<td><%=account2Transactions.get(i).getTransactionDate()%></td>
				<td><%=account2Transactions.get(i).getPurpose()%></td>
			</tr>
			<%} %>
		<%for(int i = 0; i < account2UtilityTransactions.size(); i++){ %>
				<tr class="item">
				<td><%=account2UtilityTransactions.get(i).getId()%></td>
				<td>Credit</td>
				<td>₹<%= account2UtilityTransactions.get(i).getBillAmount()%></td>
				<td><%= account2UtilityTransactions.get(i).getTransactionDate()%></td>
				<td><%=account2UtilityTransactions.get(i).getPurpose()%></td>
			</tr>
		<%} %>
		<%for(int i = 0; i < account2GiftCardTransactions.size(); i++){ %>
				<tr class="item">
				<td><%=account2GiftCardTransactions.get(i).getId()%></td>
				<td>Credit</td>
				<td>₹<%= account2GiftCardTransactions.get(i).getAmount()%></td>
				<td><%= account2GiftCardTransactions.get(i).getTransactionDate()%></td>
				<td><%=account2GiftCardTransactions.get(i).getPurpose()%></td>
			</tr>
		<%} %>
    </tbody>
</table>
</div>
<a href="/url/" onclick="window.print(); return false"><i class="fa-solid fa-print submitBtn"></i></a>
<%}else if(currentUser.getIsAdmin() == 0){%>
<ul class="nav-list">  
  <li class="nav-item"><a href="home.jsp">HOME</a></li>  
  <li class="nav-item"><a href="about.jsp">ABOUT</a></li>  
  <li class="nav-item"><a href="requestCard.jsp">REQUEST CARD</a></li>
  <li class="nav-item"><a href="requestAccount.jsp">REQUEST ACCOUNT</a></li>  
  <li class="nav-item"><a href="transferAmount.jsp">TRANSFER MONEY</a></li>
  <li class="nav-item"><a class="active" href="transactions.jsp">TRANSACTIONS</a></li>
  <li class="nav-item"><a href="addBeneficiary.jsp">ADD BENEFICIARY</a></li>
  <li class="nav-item"><a href="utilityServices.jsp">UTILITY SERVICES</a></li>
  <li class="nav-item"><a href="utilityTransactions.jsp">UTILITY TRANSACTIONS</a></li>
  <li class="nav-item-right"><a href="<%= request.getContextPath() %>/logOut" onclick="javascript:return confirm('Are you sure you want to log out?');"><i class="fa-solid fa-right-from-bracket"></i></a></li>
  <li class="nav-item-right"><a href="userProfile.jsp"><i class="fa-solid fa-user"></i></a></li>
</ul>
<%} if(currentUser.getIsApproved() == 0){ %>
<h3 style="text-align: center;">WELCOME <%= currentUser.getFirstName() + " " + currentUser.getLastName() + "!" %>. Please contact bank admin and get your account approved to gain access to features!</h3>
<%}else if(currentUser.getIsAdmin() == 0 && currentUser.getIsApproved() == 1){ %>
<div id="chartContainer" style="height: 370px; width: 100%; padding: 25px;"></div>
<div class="container">
  <h2>Filter Transactions</h2>
</div>
<div align="center" class="signUpForm">
	<form action="<%= request.getContextPath() %>/searchTransactions" method="post" class="searchForm" onSubmit="return validateDate()">
		<div class="inputContainer">
	        <select class="input" id="acountNumber" name="accountNumber">
	        <%for(int i = 0; i < accounts.size(); i++){ %>
	        	<option><%=accounts.get(i).getAccountType()%> Account - <%=accounts.get(i).getAccountNumber()%></option>
	        <%} %>
	        </select>
	        <label for="" class="label">From account *</label>
   		</div>
		<div class="inputContainer">
        	<input type="date" class="input" id="fromDate" name="fromDate" placeholder="From Date">
        	<label for="" class="label">From Date</label>
		</div>
		<div class="inputContainer">
        	<input type="date" class="input" id="toDate" name="toDate" placeholder="To Date">
        	<label for="" class="label">To Date</label>
		</div>
		<input type="hidden" name="userId" value="<%=currentUser.getId() %>">
		<input type="submit" class="submitBtn" value="Submit">
	</form>
</div>
<div class="container">
  <h2><%=accounts.get(0).getAccountType()%> Account <%=accounts.get(0).getAccountNumber() %> Transaction Details</h2>
</div>
<div class="table-wrapper print-container">
<table id="transactionTable" class="fl-table sortable">
	<thead>
	<tr>
		<th>Transaction ID</th>
        <th>Type</th>
        <th>Amount</th>
        <th>Date and Time of Transaction</th>
        <th>Purpose</th>
    </tr>
    </thead>
    <tbody>
    	<%for(int i = 0; i < account1Transactions.size(); i++){
			%>
			<tr class="item">
				<td><%=account1Transactions.get(i).getId()%></td>
				<%if(accountNumbers.contains(Integer.parseInt(account1Transactions.get(i).getFromAccountNumber())) && !accountNumbers.contains(Integer.parseInt(account1Transactions.get(i).getToAccountNumber()))){%>
				<td>Credit</td>
				<%}else if(!accountNumbers.contains(Integer.parseInt(account1Transactions.get(i).getFromAccountNumber())) && accountNumbers.contains(Integer.parseInt(account1Transactions.get(i).getToAccountNumber()))){%>
				<td>Debit</td>
				<%}else{ %>
				<td>Self Transfer</td>
				<%} %>
				<td>₹<%= account1Transactions.get(i).getAmount()%></td>
				<td><%= account1Transactions.get(i).getTransactionDate()%></td>
				<td><%=account1Transactions.get(i).getPurpose()%></td>
			</tr>
			<%} %>
		<%for(int i = 0; i < account1UtilityTransactions.size(); i++){ %>
				<tr class="item">
				<td><%=account1UtilityTransactions.get(i).getId()%></td>
				<td>Credit</td>
				<td>₹<%= account1UtilityTransactions.get(i).getBillAmount()%></td>
				<td><%= account1UtilityTransactions.get(i).getTransactionDate()%></td>
				<td><%=account1UtilityTransactions.get(i).getPurpose()%></td>
			</tr>
		<%} %>
		<%for(int i = 0; i < account1GiftCardTransactions.size(); i++){ %>
				<tr class="item">
				<td><%=account1GiftCardTransactions.get(i).getId()%></td>
				<td>Credit</td>
				<td>₹<%= account1GiftCardTransactions.get(i).getAmount()%></td>
				<td><%= account1GiftCardTransactions.get(i).getTransactionDate()%></td>
				<td><%=account1GiftCardTransactions.get(i).getPurpose()%></td>
			</tr>
		<%} %>	
    </tbody>
</table>
</div>
<div class="container">
  <h2><%=accounts.get(1).getAccountType()%> Account <%=accounts.get(1).getAccountNumber() %> Transaction Details</h2>
</div>
<div class="table-wrapper print-container">
<table id="transactionTable" class="fl-table sortable">
	<thead>
	<tr>
		<th>Transaction ID</th>
        <th>Type</th>
        <th>Amount</th>
        <th>Date and Time of Transaction</th>
        <th>Purpose</th>
    </tr>
    </thead>
    <tbody>
    	<%for(int i = 0; i < account2Transactions.size(); i++){
			%>
			<tr class="item">
				<td><%=account2Transactions.get(i).getId()%></td>
				<%if(accountNumbers.contains(Integer.parseInt(account2Transactions.get(i).getFromAccountNumber())) && !accountNumbers.contains(Integer.parseInt(account2Transactions.get(i).getToAccountNumber()))){%>
				<td>Credit</td>
				<%}else if(!accountNumbers.contains(Integer.parseInt(account2Transactions.get(i).getFromAccountNumber())) && accountNumbers.contains(Integer.parseInt(account2Transactions.get(i).getToAccountNumber()))){%>
				<td>Debit</td>
				<%}else{ %>
				<td>Self Transfer</td>
				<%} %>
				<td>₹<%=account2Transactions.get(i).getAmount()%></td>
				<td><%=account2Transactions.get(i).getTransactionDate()%></td>
				<td><%=account2Transactions.get(i).getPurpose()%></td>
			</tr>
			<%} %>
		<%for(int i = 0; i < account2UtilityTransactions.size(); i++){ %>
				<tr class="item">
				<td><%=account2UtilityTransactions.get(i).getId()%></td>
				<td>Credit</td>
				<td>₹<%= account2UtilityTransactions.get(i).getBillAmount()%></td>
				<td><%= account2UtilityTransactions.get(i).getTransactionDate()%></td>
				<td><%=account2UtilityTransactions.get(i).getPurpose()%></td>
			</tr>
		<%} %>
		<%for(int i = 0; i < account2GiftCardTransactions.size(); i++){ %>
				<tr class="item">
				<td><%=account2GiftCardTransactions.get(i).getId()%></td>
				<td>Credit</td>
				<td>₹<%= account2GiftCardTransactions.get(i).getAmount()%></td>
				<td><%= account2GiftCardTransactions.get(i).getTransactionDate()%></td>
				<td><%=account2GiftCardTransactions.get(i).getPurpose()%></td>
			</tr>
		<%} %>
    </tbody>
</table>
</div>
<a href="/url/" onclick="window.print(); return false"><i class="fa-solid fa-print submitBtn"></i></a>
<%} %>

<script type="text/javascript">
window.onload = function() { 
 
var chart = new CanvasJS.Chart("chartContainer", {
	theme: "light1",
	animationEnabled: true,
	exportFileName: "Transactions Chart",
	exportEnabled: true,
	title:{
		text: "Transactions Across All Accounts"
	},
	data: [{
		type: "pie",
		showInLegend: true,
		legendText: "{label}",
		toolTipContent: "{label}: <strong>{y}%</strong>",
		indexLabel: "{label} {y}%",
		dataPoints : <%out.print(dataPoints);%>
	}]
});
chart.render();
 
}
</script>
<script>
	function validateDate(){
		var fromDate = document.getElementById("fromDate").value;
		var toDate = document.getElementById("toDate").value;
		const x = new Date(fromDate);
		const y = new Date(toDate);
		console.log(fromDate);
		console.log(toDate);
		if(fromDate == "" || toDate == ""){
			alert("From or to date cannot be empty!");
			return false;
		}
		if(toDate < fromDate){
			alert("To date cannot be less than from date!");
			return false;
		}
		return true;
	}
</script>
</body>
</html>