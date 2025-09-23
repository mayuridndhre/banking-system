package servlet;

import service.BankService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/TransferServlet")
public class TransferServlet extends HttpServlet {
    private BankService bankService;

    @Override
    public void init() throws ServletException {
        bankService = new BankService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int fromId = Integer.parseInt(req.getParameter("fromAccountId"));
            int toId = Integer.parseInt(req.getParameter("toAccountId"));
            double amount = Double.parseDouble(req.getParameter("amount"));

            bankService.transfer(fromId, toId, amount);

            req.setAttribute("message", "✅ Transfer successful! ₹" + amount + " from " + fromId + " to " + toId);
            req.getRequestDispatcher("transferSuccess.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
