<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Open Account</title>
    <style>
        body { font-family: Arial; background:#f5f5f5; text-align:center; }
        form { background:white; width:350px; margin:50px auto; padding:20px; border-radius:10px; box-shadow:0 0 10px gray; }
        input, select { width:90%; padding:10px; margin:10px 0; border:1px solid #ccc; border-radius:5px; }
        input[type=submit] { background:#4CAF50; color:white; border:none; cursor:pointer; }
        input[type=submit]:hover { background:#45a049; }
        a { text-decoration:none; color:#007BFF; }
    </style>
</head>
<body>
    <h2>Open New Account</h2>
    <form action="OpenAccountServlet" method="post">
        <input type="number" name="customerId" placeholder="Enter Customer ID" required><br>
        <select name="type" required>
            <option value="">Select Account Type</option>
            <option value="Savings">Savings</option>
            <option value="Current">Current</option>
        </select><br>
        <input type="number" step="0.01" name="initialDeposit" placeholder="Initial Deposit" required><br>
        <input type="submit" value="Open Account">
    </form>
    <br>
    <a href="index.jsp">Back to Home</a>
</body>
</html>
