<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>View Transactions</title></head>
<body>
  <h2>View Transactions</h2>
  <form action="TransactionsServlet" method="post">
    <input name="accountId" type="number" placeholder="Account ID" required>
    <button type="submit">Show</button>
  </form>
  <a href="index.jsp">Back to Home</a>
</body>
</html>
