<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Transfer</title></head>
<body>
  <h2>Transfer Money</h2>
  <form action="TransferServlet" method="post">
    <input name="fromAccountId" type="number" placeholder="From Account ID" required><br><br>
    <input name="toAccountId" type="number" placeholder="To Account ID" required><br><br>
    <input name="amount" type="number" step="0.01" placeholder="Amount" required><br><br>
    <button type="submit">Transfer</button>
  </form>
  <a href="index.jsp">Back to Home</a>
</body>
</html>
