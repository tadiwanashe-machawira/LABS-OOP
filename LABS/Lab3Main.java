import java.util.*;

public class Lab3Main {

    static class Student {
        private String name;
        private int marks;

        Student(String name, int marks) {
            this.name = name;
            setMarks(marks);
        }

        void setMarks(int marks) {
            if (marks < 0 || marks > 100) {
                throw new IllegalArgumentException(
                    "Marks must be between 0 and 100"
                );
            }

            this.marks = marks;
        }

        int getMarks() {
            return marks;
        }

        String getGrade() {
            if (marks >= 80) return "A";
            if (marks >= 65) return "B";
            if (marks >= 50) return "C";
            return "F";
        }

        public String toString() {
            return name + ": " + marks + " (" + getGrade() + ")";
        }
    }

    static class BankAccount {
        private double balance;
        private final List<String> transactions = new ArrayList<>();

        BankAccount(double balance) {
            if (balance < 0) {
                throw new IllegalArgumentException(
                    "Balance cannot be negative"
                );
            }

            this.balance = balance;
        }

        void deposit(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException(
                    "Amount must be positive"
                );
            }

            balance += amount;
            transactions.add("Deposit: " + amount);
        }

        boolean withdraw(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException(
                    "Amount must be positive"
                );
            }

            if (amount > balance) {
                return false;
            }

            balance -= amount;
            transactions.add("Withdrawal: " + amount);
            return true;
        }

        List<String> getTransactions() {
            return Collections.unmodifiableList(
                new ArrayList<>(transactions)
            );
        }

        double getBalance() {
            return balance;
        }
    }

    static final class ImmutablePerson {
        private final String name;
        private final int age;

        ImmutablePerson(String name, int age) {
            this.name = Objects.requireNonNull(name);

            if (age < 0) {
                throw new IllegalArgumentException(
                    "Age cannot be negative"
                );
            }

            this.age = age;
        }

        String getName() {
            return name;
        }

        int getAge() {
            return age;
        }

        public String toString() {
            return name + ", age " + age;
        }
    }

    static class Temperature {
        private final double kelvin;

        Temperature(double kelvin) {
            if (kelvin < 0) {
                throw new IllegalArgumentException(
                    "Temperature cannot be below absolute zero"
                );
            }

            this.kelvin = kelvin;
        }

        double getCelsius() {
            return kelvin - 273.15;
        }

        double getFahrenheit() {
            return getCelsius() * 9 / 5 + 32;
        }
    }

    public static void main(String[] args) {

        Student student = new Student("Tariro", 82);
        System.out.println(student);

        try {
            student.setMarks(120);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid marks rejected.");
        }

        BankAccount account = new BankAccount(100);
        account.deposit(25);
        account.withdraw(10);

        System.out.println("Balance: " + account.getBalance());
        System.out.println("Transactions: " + account.getTransactions());

        try {
            account.getTransactions().clear();
        } catch (UnsupportedOperationException e) {
            System.out.println(
                "Transaction history cannot be changed externally."
            );
        }

        ImmutablePerson person = new ImmutablePerson("Farai", 20);
        System.out.println(person);

        Temperature temperature = new Temperature(300);

        System.out.printf(
            "300 K = %.2f C, %.2f F%n",
            temperature.getCelsius(),
            temperature.getFahrenheit()
        );
    }
}