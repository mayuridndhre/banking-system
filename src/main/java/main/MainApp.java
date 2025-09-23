package main;

import service.BankService;
import dao.CustomerDAO;
import model.Customer;
import model.Account;
import model.Transaction;

import java.util.List;
import java.util.Scanner;
import java.text.DecimalFormat;

public class MainApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankService bankService = new BankService();
        CustomerDAO customerDAO = new CustomerDAO(); // assume exists (createCustomer returns Customer with id)
        DecimalFormat df = new DecimalFormat("#,##0.00");

        while (true) {
            System.out.println("\n===== Mini Banking System =====");
            System.out.println("1. Register Customer");
            System.out.println("2. Open Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. View Transaction History");
            System.out.println("7. Exit");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        System.out.print("Phone: ");
                        String phone = sc.nextLine();
                        System.out.print("Address: ");
                        String address = sc.nextLine();

                        Customer c = new Customer(name, email, phone, address);
                        Customer created = customerDAO.create(c); // assumes create returns Customer with id set
                        System.out.println("Customer registered with id: " + created.getId());
                        break;

                    case 2:
                        System.out.print("Customer ID: ");
                        int custId = Integer.parseInt(sc.nextLine());
                        System.out.print("Account Type (Savings/Current): ");
                        String type = sc.nextLine();
                        System.out.print("Initial deposit: ");
                        double init = Double.parseDouble(sc.nextLine());

                        Account acc = bankService.openAccount(custId, type, init);
                        System.out.println("Account created with id: " + acc.getId() + ", balance: " + df.format(acc.getBalance()));
                        break;

                    case 3:
                        System.out.print("Account ID: ");
                        int acctId = Integer.parseInt(sc.nextLine());
                        System.out.print("Amount to deposit: ");
                        double dep = Double.parseDouble(sc.nextLine());
                        bankService.deposit(acctId, dep, "Deposit via console");
                        System.out.println("Deposit successful.");
                        break;

                    case 4:
                        System.out.print("Account ID: ");
                        int wAcct = Integer.parseInt(sc.nextLine());
                        System.out.print("Amount to withdraw: ");
                        double wd = Double.parseDouble(sc.nextLine());
                        bankService.withdraw(wAcct, wd, "Withdraw via console");
                        System.out.println("Withdrawal successful.");
                        break;

                    case 5:
                        System.out.print("From Account ID: ");
                        int from = Integer.parseInt(sc.nextLine());
                        System.out.print("To Account ID: ");
                        int to = Integer.parseInt(sc.nextLine());
                        System.out.print("Amount: ");
                        double amt = Double.parseDouble(sc.nextLine());
                        bankService.transfer(from, to, amt);
                        System.out.println("Transfer successful.");
                        break;

                    case 6:
                        System.out.print("Account ID: ");
                        int aId = Integer.parseInt(sc.nextLine());
                        List<Transaction> txs = bankService.getTransactions(aId);
                        System.out.println("Transactions for account " + aId + ":");
                        if (txs.isEmpty()) {
                            System.out.println("No transactions yet.");
                        } else {
                            for (Transaction t : txs) {
                                System.out.println(t.getId() + " | " + t.getType() + " | " + df.format(t.getAmount()) + " | " + t.getDate() + " | " + t.getDescription());
                            }
                        }
                        break;

                    case 7:
                        System.out.println("Bye!");
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                // For debugging uncomment next line
                // e.printStackTrace();
            }
        }
    }
}
