package servlet;

import model.Account;
import service.BankService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/OpenAccountServlet")
public class OpenAccountServlet extends HttpServlet {

    private BankService bankService;

    @Override
    public void init() throws ServletException {
        bankService = new BankService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            String type = request.getParameter("type");
            double initialDeposit = Double.parseDouble(request.getParameter("initialDeposit"));

            Account acc = bankService.openAccount(customerId, type, initialDeposit);

            request.setAttribute("account", acc);
            request.getRequestDispatcher("openAccountSuccess.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Account opening failed: " + e.getMessage());
        }
    }
}
