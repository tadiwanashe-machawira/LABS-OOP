import java.time.Year;

public class Lab1Main {

    static class Car {
        String brand;
        int year;
        double mileage;

        Car(String brand, int year, double mileage) {
            this.brand = brand;
            this.year = year;
            this.mileage = mileage;
        }

        void display() {
            System.out.println("Brand: " + brand);
            System.out.println("Year: " + year);
            System.out.println("Mileage: " + mileage + " km");
        }

        boolean isAntique() {
            return Year.now().getValue() - year > 25;
        }
    }

    static class Rectangle {
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

    static class Address {
        String city;

        Address(String city) {
            this.city = city;
        }
    }

    static class Person {
        String name;
        Address address;

        Person(String name, Address address) {
            this.name = name;
            this.address = address;
        }
    }

    public static void main(String[] args) {

        Car[] cars = {
            new Car("Toyota", 2000, 150000),
            new Car("Honda", 2020, 45000),
            new Car("Mazda", 1995, 210000)
        };

        System.out.println("CARS");

        for (Car car : cars) {
            car.display();
            System.out.println("Antique: " + car.isAntique());
            System.out.println();
        }

        Rectangle[] rectangles = {
            new Rectangle(2, 3),
            new Rectangle(4, 5),
            new Rectangle(6, 2),
            new Rectangle(3, 7),
            new Rectangle(5, 5)
        };

        Rectangle largest = rectangles[0];

        System.out.println("RECTANGLES");

        for (Rectangle r : rectangles) {
            System.out.println("Area: " + r.area());
            System.out.println("Perimeter: " + r.perimeter());

            if (r.area() > largest.area()) {
                largest = r;
            }
        }

        System.out.println("Largest area: " + largest.area());

        Rectangle r1 = new Rectangle(4, 8);
        Rectangle r2 = r1;

        r2.width = 99;

        System.out.println("r1 width: " + r1.width);

        Person p = new Person("Tariro", new Address("Harare"));

        System.out.println(p.name + " lives in " + p.address.city);

        Person noAddress = new Person("Farai", null);

        if (noAddress.address == null) {
            System.out.println(noAddress.name + " has no address.");
        }
    }
}