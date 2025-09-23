<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Deposit Money</title>
    <style>
        body { font-family: Arial; background:#f5f5f5; text-align:center; }
        .form-box { margin:50px auto; width:400px; background:white; padding:20px; border-radius:10px; box-shadow:0 0 10px gray; }
        input { width:90%; padding:8px; margin:10px 0; border:1px solid gray; border-radius:5px; }
        input[type=submit] { background:#4CAF50; color:white; border:none; cursor:pointer; }
        input[type=submit]:hover { background:#45a049; }
    </style>
</head>
<body>
    <div class="form-box">
        <h2>Deposit Money</h2>
        <form action="DepositServlet" method="post">
            Account ID: <input type="number" name="accountId" required><br>
            Amount: <input type="number" step="0.01" name="amount" required><br>
            <input type="submit" value="Deposit">
        </form>
        <br>
        <a href="index.jsp">Back to Home</a>
    </div>
</body>
</html>
