package servlet;

import model.Transaction;
import service.BankService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/TransactionsServlet")
public class TransactionsServlet extends HttpServlet {
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
            List<Transaction> list = bankService.getTransactions(accountId);

            req.setAttribute("transactions", list);
            req.setAttribute("accountId", accountId);
            req.getRequestDispatcher("transactionsList.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
