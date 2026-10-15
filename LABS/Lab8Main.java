import java.util.*;

public class Lab8Main {

    static class GenericStack<T> {
        private final List<T> items = new ArrayList<>();

        void push(T value) {
            items.add(value);
        }

        T pop() {
            if (items.isEmpty()) {
                throw new EmptyStackException();
            }

            return items.remove(items.size() - 1);
        }

        T peek() {
            if (items.isEmpty()) {
                throw new EmptyStackException();
            }

            return items.get(items.size() - 1);
        }

        boolean isEmpty() {
            return items.isEmpty();
        }
    }

    static <T extends Comparable<T>> void bubbleSort(T[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {

                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {

        List<Integer> numbers = new ArrayList<>(
            List.of(7, 2, 9, 4, 1, 8, 3, 6, 5, 10)
        );

        int sum = 0;

        for (int n : numbers) {
            sum += n;
        }

        System.out.println("Numbers: " + numbers);
        System.out.println("Sum: " + sum);
        System.out.println("Maximum: " + Collections.max(numbers));

        List<Integer> reversed = new ArrayList<>(numbers);
        Collections.reverse(reversed);

        System.out.println("Reverse: " + reversed);

        Set<String> names = new LinkedHashSet<>(
            List.of("Tariro", "Farai", "Tariro", "Rudo")
        );

        System.out.println("Unique names: " + names);

        GenericStack<String> stack = new GenericStack<>();

        stack.push("A");
        stack.push("B");

        System.out.println("Peek: " + stack.peek());
        System.out.println("Pop: " + stack.pop());
        System.out.println("Empty? " + stack.isEmpty());

        String paragraph = "java is fun and java is useful";

        Map<String, Integer> frequency = new HashMap<>();

        for (String word : paragraph.split("\\s+")) {
            frequency.merge(word, 1, Integer::sum);
        }

        frequency.entrySet()
            .stream()
            .sorted(
                Map.Entry.<String, Integer>comparingByValue()
                    .reversed()
                    .thenComparing(Map.Entry.comparingByKey())
            )
            .forEach(System.out::println);

        Integer[] integers = {5, 1, 4, 2, 3};
        String[] words = {"pear", "apple", "orange"};

        bubbleSort(integers);
        bubbleSort(words);

        System.out.println("Sorted integers: "
                + Arrays.toString(integers));

        System.out.println("Sorted words: "
                + Arrays.toString(words));
    }
}