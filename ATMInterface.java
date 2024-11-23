import java.util.Scanner;

// BankAccount class
class BankAccount {
    private double balance;
    private String password;

    public BankAccount(double initialBalance, String password) {
        this.balance = initialBalance;
        this.password = password;
    }

    // Getter for balance
    public double getBalance() {
        return balance;
    }

    // Validate password
    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // Change password
    public void changePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password successfully changed.");
    }

    // Deposit method
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Successfully deposited " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Withdraw method
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Successfully withdrew " + amount);
        } else if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
    }
}

// ATM class
class ATM {
    private BankAccount account;

    // Constructor
    public ATM(BankAccount account) {
        this.account = account;
    }

    // Show menu
    public void showMenu() {
        System.out.println("\nPlease select an option from the Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Change Password");
        System.out.println("5. Exit");
    }

    // Check balance
    public void checkBalance(Scanner scanner) {
        if (authenticate(scanner)) {
        System.out.println("Current Balance: " + account.getBalance());
    }
}

    // Deposit with password validation
    public void deposit(double amount, Scanner scanner) {
        if (authenticate(scanner)) {
            account.deposit(amount);
        }
    }

    // Withdraw with password validation
    public void withdraw(double amount, Scanner scanner) {
        if (authenticate(scanner)) {
            account.withdraw(amount);
        }
    }

    // Change password
    public void changePassword(Scanner scanner) {
        boolean status=false;
        int attempts=0;
        System.out.print("Enter previous password: ");
        while(status!=true){
        String currentPassword = scanner.next();
        if (account.validatePassword(currentPassword)) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.next();
            account.changePassword(newPassword);
            status=true;
        } else {
            System.out.println("Incorrect password.Try again");
            status=false;
            attempts++;
            if(attempts==3){
                System.out.println("Did you Forgot password(y/n)?");
                String answer=scanner.next();
                if(answer.equalsIgnoreCase("y")){
                    System.out.println("enter new password");
                    String newPassword = scanner.next();
                    account.changePassword(newPassword);
                    status=true;
                }else{
                    break;
                }
            }
        }
    }
    }

    // Authenticate user
    private boolean authenticate(Scanner scanner) {
        System.out.print("Enter your password: ");
        String inputPassword = scanner.next();
        if (account.validatePassword(inputPassword)) {
            return true;
        } else {
            System.out.println("Incorrect password. Operation denied.");
            return false;
        }
    }
}

// Main class
public class ATMInterface{
    public static void main(String[] args) {
        Scanner sObj = new Scanner(System.in);
        BankAccount userAccount = new BankAccount(500000.0, "0000"); // initial password is 1234
        System.out.println("Welcome to ATM");
        ATM atm = new ATM(userAccount);
        boolean exit = false;
        while (!exit) {
            atm.showMenu();
            int choice = sObj.nextInt();

            switch (choice) {
                case 1:
                    atm.checkBalance(sObj);
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = sObj.nextDouble();
                    atm.deposit(depositAmount, sObj);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = sObj.nextDouble();
                    atm.withdraw(withdrawAmount, sObj);
                    break;
                case 4:
                    atm.changePassword(sObj);
                    break;
                case 5:
                    System.out.println("Exiting... Thank you for using the ATM!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        sObj.close();
    }
}
