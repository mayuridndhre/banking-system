package servlet;

import service.BankService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {
    private BankService bankService;

    @Override
    public void init() throws ServletException {
        bankService = new BankService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int accountId = Integer.parseInt(req.getParameter("accountId"));
            double amount = Double.parseDouble(req.getParameter("amount"));
            String desc = req.getParameter("desc");

            bankService.withdraw(accountId, amount, desc);

            req.setAttribute("message", "✅ Withdraw successful! ₹" + amount + " from Account " + accountId);
            req.getRequestDispatcher("withdrawSuccess.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
