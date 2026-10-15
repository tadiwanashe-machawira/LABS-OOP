public class Lab5Main {

    static class Printer {
        void print(int x) {
            System.out.println("int: " + x);
        }

        void print(double x) {
            System.out.println("double: " + x);
        }

        void print(String x) {
            System.out.println("String: " + x);
        }

        void print(int[] x) {
            System.out.print("Array: ");

            for (int n : x) {
                System.out.print(n + " ");
            }

            System.out.println();
        }
    }

    static class Animal {
        String type = "Animal";

        void sound() {
            System.out.println("Some sound");
        }
    }

    static class Cat extends Animal {
        @Override
        void sound() {
            System.out.println("Meow");
        }
    }

    static class Cow extends Animal {
        @Override
        void sound() {
            System.out.println("Moo");
        }
    }

    static class Duck extends Animal {
        @Override
        void sound() {
            System.out.println("Quack");
        }
    }

    static class Dog extends Animal {
        String type = "Dog";

        @Override
        void sound() {
            System.out.println("Woof");
        }
    }

    static class Employee {
        String name;
        double salary;

        Employee(String name, double salary) {
            this.name = name;
            this.salary = salary;
        }

        double pay() {
            return salary;
        }
    }

    static class Intern extends Employee {
        Intern(String name, double salary) {
            super(name, salary);
        }

        @Override
        double pay() {
            return salary * 0.8;
        }
    }

    interface Payment {
        void process();
    }

    static class CardPayment implements Payment {
        public void process() {
            System.out.println("Card payment processed");
        }
    }

    static class MobileMoneyPayment implements Payment {
        public void process() {
            System.out.println("Mobile money processed");
        }
    }

    static class PaymentProcessor {
        void pay(Payment payment) {
            payment.process();
        }
    }

    public static void main(String[] args) {

        Printer printer = new Printer();

        printer.print(4);
        printer.print(2.5);
        printer.print("Java");
        printer.print(new int[]{1, 2, 3});

        Animal[] animals = {
            new Cat(),
            new Cow(),
            new Duck()
        };

        for (Animal animal : animals) {
            animal.sound();
        }

        Employee[] staff = {
            new Employee("Tendai", 1000),
            new Intern("Kuda", 500)
        };

        double payroll = 0;

        for (Employee employee : staff) {
            payroll += employee.pay();
        }

        System.out.println("Payroll: " + payroll);

        Animal animal = new Dog();
        animal.sound();

        System.out.println("Field through Animal reference: " + animal.type);

        PaymentProcessor processor = new PaymentProcessor();

        processor.pay(new CardPayment());
        processor.pay(new MobileMoneyPayment());
    }
}