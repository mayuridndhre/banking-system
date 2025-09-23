<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Withdraw</title></head>
<body>
  <h2>Withdraw Money</h2>
  <form action="WithdrawServlet" method="post">
    <input name="accountId" type="number" placeholder="Account ID" required><br><br>
    <input name="amount" type="number" step="0.01" placeholder="Amount" required><br><br>
    <input name="desc" type="text" placeholder="Description (optional)"><br><br>
    <button type="submit">Withdraw</button>
  </form>
  <a href="index.jsp">Back to Home</a>
</body>
</html>
