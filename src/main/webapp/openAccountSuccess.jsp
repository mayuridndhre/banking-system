<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Account Created</title></head>
<body>
  <h2>Account Successfully Created!</h2>
  <c:choose>
    <c:when test="${not empty account}">
      <p>Account ID: <b>${account.id}</b></p>
      <p>Customer ID: <b>${account.customerId}</b></p>
      <p>Type: <b>${account.type}</b></p>
      <p>Balance: <b>${account.balance}</b></p>
    </c:when>
    <c:otherwise>
      <p>No account information available.</p>
    </c:otherwise>
  </c:choose>
  <a href="index.jsp">Back to Home</a>
</body>
</html>
