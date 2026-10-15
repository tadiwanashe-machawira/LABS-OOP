import java.util.*;

public class Lab6Main {

    abstract static class Shape {
        abstract double area();
        abstract double perimeter();
    }

    static class Circle extends Shape {
        double radius;

        Circle(double radius) {
            this.radius = radius;
        }

        double area() {
            return Math.PI * radius * radius;
        }

        double perimeter() {
            return 2 * Math.PI * radius;
        }
    }

    static class Rectangle extends Shape {
        double width;
        double height;

        Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        double area() {
            return width * height;
        }

        double perimeter() {
            return 2 * (width + height);
        }
    }

    static class Square extends Rectangle {
        Square(double side) {
            super(side, side);
        }
    }

    interface Flyable {
        void fly();
    }

    static class Bird implements Flyable {
        public void fly() {
            System.out.println("Bird flies with wings");
        }
    }

    static class Aeroplane implements Flyable {
        public void fly() {
            System.out.println("Aeroplane flies with engines");
        }
    }

    abstract static class Account {
        double balance;

        Account(double balance) {
            this.balance = balance;
        }

        void deposit(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException(
                    "Amount must be positive"
                );
            }

            balance += amount;
        }

        abstract double monthlyFee();
    }

    static class SavingsAccount extends Account {
        SavingsAccount(double balance) {
            super(balance);
        }

        double monthlyFee() {
            return 0;
        }
    }

    static class CurrentAccount extends Account {
        CurrentAccount(double balance) {
            super(balance);
        }

        double monthlyFee() {
            return 5;
        }
    }

    static class Learner implements Comparable<Learner> {
        String name;
        int marks;

        Learner(String name, int marks) {
            this.name = name;
            this.marks = marks;
        }

        public int compareTo(Learner other) {
            return name.compareTo(other.name);
        }

        public String toString() {
            return name + "(" + marks + ")";
        }
    }

    interface HelloA {
        default void hello() {
            System.out.println("Hello from A");
        }
    }

    interface HelloB {
        default void hello() {
            System.out.println("Hello from B");
        }
    }

    static class Greeter implements HelloA, HelloB {
        public void hello() {
            HelloA.super.hello();
            HelloB.super.hello();
        }
    }

    public static void main(String[] args) {

        Shape[] shapes = {
            new Circle(2),
            new Rectangle(3, 4),
            new Square(5)
        };

        for (Shape shape : shapes) {
            System.out.printf(
                "%s area=%.2f perimeter=%.2f%n",
                shape.getClass().getSimpleName(),
                shape.area(),
                shape.perimeter()
            );
        }

        Flyable[] flyers = {
            new Bird(),
            new Aeroplane()
        };

        for (Flyable flyer : flyers) {
            flyer.fly();
        }

        Account[] accounts = {
            new SavingsAccount(100),
            new CurrentAccount(100)
        };

        for (Account account : accounts) {
            account.deposit(20);
            account.balance -= account.monthlyFee();

            System.out.println(
                account.getClass().getSimpleName()
                + " balance: " + account.balance
            );
        }

        List<Learner> learners = new ArrayList<>(List.of(
            new Learner("Zara", 60),
            new Learner("Ava", 90),
            new Learner("Musa", 75)
        ));

        Collections.sort(learners);
        System.out.println("By name: " + learners);

        learners.sort(
            Comparator.comparingInt((Learner l) -> l.marks).reversed()
        );

        System.out.println("By marks: " + learners);

        new Greeter().hello();
    }
}