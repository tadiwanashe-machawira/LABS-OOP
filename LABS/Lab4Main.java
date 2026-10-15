import java.util.*;

public class Lab4Main {

    static class Person {
        protected final String name;
        protected final int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        void display() {
            System.out.println(name + ", age " + age);
        }
    }

    static class Student extends Person {
        private final String course;

        Student(String name, int age, String course) {
            super(name, age);
            this.course = course;
        }

        @Override
        void display() {
            super.display();
            System.out.println("Course: " + course);
        }
    }

    static class Teacher extends Person {
        private final String subject;

        Teacher(String name, int age, String subject) {
            super(name, age);
            this.subject = subject;
        }

        @Override
        void display() {
            super.display();
            System.out.println("Subject: " + subject);
        }
    }

    static class Vehicle {
        Vehicle() {
            System.out.println("Vehicle constructor");
        }
    }

    static class Car extends Vehicle {
        Car() {
            System.out.println("Car constructor");
        }
    }

    static class ElectricCar extends Car {
        ElectricCar() {
            System.out.println("ElectricCar constructor");
        }
    }

    static class Animal {
        final String name;

        Animal(String name) {
            this.name = name;
        }
    }

    static class Dog extends Animal {
        Dog(String name) {
            super(name);
        }
    }

    static class SafeStack<T> {
        private final List<T> items = new ArrayList<>();

        void push(T item) {
            items.add(item);
        }

        T pop() {
            if (items.isEmpty()) {
                throw new EmptyStackException();
            }

            return items.remove(items.size() - 1);
        }

        boolean isEmpty() {
            return items.isEmpty();
        }
    }

    public static void main(String[] args) {

        System.out.println("Person subclasses:");

        new Student("Rudo", 21, "Computing").display();
        new Teacher("Mr Moyo", 40, "Java").display();

        System.out.println("\nConstructor order:");
        new ElectricCar();

        Dog dog = new Dog("Bingo");
        System.out.println("Dog: " + dog.name);

        SafeStack<Integer> stack = new SafeStack<>();
        stack.push(10);
        stack.push(20);

        System.out.println("Stack pop: " + stack.pop());
    }
}
