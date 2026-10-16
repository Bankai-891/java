
import java.io.*;
import java.util.Scanner;

class Account {
    String name;
    double balance;

    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Enter a valid amount.");
            return;
        }

        balance += amount;
        save();
        System.out.println("Amount deposited successfully!");
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Enter a valid amount.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            save();
            System.out.println("Withdrawal successful!");
        }
    }

    public void save() {
        try (PrintWriter writer =
                 new PrintWriter(new FileWriter("account.txt"))) {

            writer.println(name);
            writer.println(balance);

        } catch (IOException e) {
            System.out.println("Error saving account.");
        }
    }

    public static Account load() {
        File file = new File("account.txt");

        if (file.exists()) {
            try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

                String name = reader.readLine();
                String balanceText = reader.readLine();

                if (name != null && balanceText != null) {
                    return new Account(
                        name, Double.parseDouble(balanceText)
                    );
                }

            } catch (IOException | NumberFormatException e) {
                System.out.println("Error reading account file.");
            }
        }

        return null;
    }
}


public class bank {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Account account = Account.load();

        if (account == null) {
            System.out.print("Enter account holder name: ");
            String name = sc.nextLine();

            System.out.print("Enter initial balance: ");
            double balance = sc.nextDouble();

            if (balance < 0) {
                System.out.println("Balance cannot be negative.");
                return;
            }

            account = new Account(name, balance);
            account.save();
        }

        while (true) {
            System.out.println("\n--- BANK MENU ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Name: " + account.name);
                    System.out.println("Balance: " + account.balance);
                    break;

                case 2:
                    System.out.print("Enter deposit amount: ");
                    account.deposit(sc.nextDouble());
                    break;

                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    account.withdraw(sc.nextDouble());
                    break;

                case 4:
                    System.out.println("Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
    

