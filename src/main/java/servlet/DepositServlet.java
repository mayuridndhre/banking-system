package servlet;

import service.BankService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {

    private BankService bankService;

    @Override
    public void init() throws ServletException {
        bankService = new BankService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            double amount = Double.parseDouble(request.getParameter("amount"));

            bankService.deposit(accountId, amount, "Deposit via JSP form");

            request.setAttribute("message",
                    "✅ Deposit Successful! ₹" + amount + " added to Account ID: " + accountId);

            request.getRequestDispatcher("depositSuccess.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Deposit failed: " + e.getMessage());
        }
    }
}
