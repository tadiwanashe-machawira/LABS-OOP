import java.util.*;

public class Lab7Main {

    static class InvalidAgeException extends Exception {
        InvalidAgeException(String message) {
            super(message);
        }
    }

    static class InsufficientFundsException extends Exception {
        InsufficientFundsException(String message) {
            super(message);
        }
    }

    static void validateAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 120) {
            throw new InvalidAgeException(
                "Age must be from 0 to 120"
            );
        }
    }

    static int getElement(int[] values, int index) {
        try {
            return values[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid array index: " + index);
            return -1;
        }
    }

    static class BankAccount {
        double balance;

        BankAccount(double balance) {
            this.balance = balance;
        }

        void withdraw(double amount)
                throws InsufficientFundsException {

            if (amount <= 0) {
                throw new IllegalArgumentException(
                    "Amount must be positive"
                );
            }

            if (amount > balance) {
                throw new InsufficientFundsException(
                    "Insufficient funds"
                );
            }

            balance -= amount;
        }
    }

    static int tryFinallyExample() {
        try {
            System.out.println("Inside try");
            return 1;
        } finally {
            System.out.println(
                "Finally executes before return completes"
            );
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter two integers to divide: ");

        try {
            int a = sc.nextInt();
            int b = sc.nextInt();

            System.out.println("Result: " + (a / b));

        } catch (InputMismatchException e) {
            System.out.println("Please enter integers only.");

        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero.");
        }

        int[] numbers = {10, 20, 30};

        System.out.println("Valid element: " + getElement(numbers, 1));
        getElement(numbers, 9);

        try {
            validateAge(150);
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
        }

        BankAccount account = new BankAccount(50);

        try {
            account.withdraw(70);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Returned value: " + tryFinallyExample());

        sc.close();
    }
}