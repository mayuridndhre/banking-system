<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Transactions</title></head>
<body>
  <h2>Transactions for Account ${accountId}</h2>
  <c:choose>
    <c:when test="${empty transactions}">
      <p>No transactions found.</p>
    </c:when>
    <c:otherwise>
      <table border="1" cellpadding="6" cellspacing="0">
        <tr><th>ID</th><th>Type</th><th>Amount</th><th>Description</th><th>Date</th></tr>
        <c:forEach var="t" items="${transactions}">
          <tr>
            <td>${t.id}</td>
            <td>${t.type}</td>
            <td>${t.amount}</td>
            <td>${t.description}</td>
            <td>${t.date}</td>
          </tr>
        </c:forEach>
      </table>
    </c:otherwise>
  </c:choose>
  <a href="index.jsp">Back to Home</a>
</body>
</html>
