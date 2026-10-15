public class Lab2Main {

    static class Circle {
        private final double radius;
        private static int count = 0;

        Circle(double radius) {
            if (radius <= 0) {
                throw new IllegalArgumentException(
                    "Radius must be positive"
                );
            }

            this.radius = radius;
            count++;
        }

        Circle() {
            this(1.0);
        }

        double area() {
            return Math.PI * radius * radius;
        }

        static int getCount() {
            return count;
        }
    }

    static class Employee {
        private static int nextId = 1;

        final int employeeId;
        String name;
        String department;
        double salary;

        Employee(String name, String department, double salary) {
            this.employeeId = nextId++;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        Employee(String name, String department) {
            this(name, department, 0);
        }

        Employee(String name) {
            this(name, "Unassigned", 0);
        }

        Employee(Employee other) {
            this(other.name, other.department, other.salary);
        }

        public String toString() {
            return employeeId + ": " + name + ", "
                    + department + ", $" + salary;
        }
    }

    static class InitOrder {
        static {
            System.out.println("Static block 1");
        }

        static {
            System.out.println("Static block 2");
        }

        {
            System.out.println("Instance block 1");
        }

        {
            System.out.println("Instance block 2");
        }

        InitOrder() {
            System.out.println("Constructor");
        }
    }

    static class Temperature {
        private final double celsius;

        private Temperature(double celsius) {
            if (celsius < -273.15) {
                throw new IllegalArgumentException(
                    "Below absolute zero"
                );
            }

            this.celsius = celsius;
        }

        static Temperature fromCelsius(double c) {
            return new Temperature(c);
        }

        static Temperature fromFahrenheit(double f) {
            return new Temperature((f - 32) * 5 / 9);
        }

        double getCelsius() {
            return celsius;
        }

        double getFahrenheit() {
            return celsius * 9 / 5 + 32;
        }
    }

    public static void main(String[] args) {

        Circle c1 = new Circle(2.5);
        Circle c2 = new Circle();

        System.out.println("Circle 1 area: " + c1.area());
        System.out.println("Circle 2 area: " + c2.area());
        System.out.println("Circles created: " + Circle.getCount());

        Employee e1 = new Employee("Rudo", "IT", 900);
        Employee e2 = new Employee("Tapiwa", "HR");
        Employee e3 = new Employee("Kuda");
        Employee e4 = new Employee(e1);

        System.out.println(e1);
        System.out.println(e2);
        System.out.println(e3);
        System.out.println("Copy: " + e4);

        new InitOrder();

        System.out.println("-- Second object --");
        new InitOrder();

        Temperature t1 = Temperature.fromCelsius(25);
        Temperature t2 = Temperature.fromFahrenheit(68);

        System.out.println("25 C = " + t1.getFahrenheit() + " F");
        System.out.println("68 F = " + t2.getCelsius() + " C");
    }
}