import java.nio.file.*;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Lab10Main {

    record Student(String name, int marks) {}

    public static void main(String[] args) throws IOException {

        // Exercise 1: Write lines to a file and read them.
        Path notes = Path.of("lab10-lines.txt");

        List<String> lines = List.of(
            "First line",
            "Second line",
            "Java file input/output",
            "Lambda exercise",
            "End of file"
        );

        Files.write(notes, lines);

        System.out.println("Reading lines from file:");

        Files.readAllLines(notes).forEach(System.out::println);

        // Exercise 2: Use a lambda to remove even numbers.
        List<Integer> values = new ArrayList<>(
            List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        );

        Predicate<Integer> isEven = n -> n % 2 == 0;

        values.removeIf(isEven);

        System.out.println("Odd numbers: " + values);

        // Exercise 3: Count lines, words and characters.
        Path textFile = Path.of("lab10-text.txt");

        Files.writeString(
            textFile,
            "Java is useful. Java is object oriented.\nStreams process data."
        );

        String text = Files.readString(textFile);

        long lineCount = Files.lines(textFile).count();

        long wordCount = Arrays.stream(
            text.trim().split("\\s+")
        ).count();

        System.out.println("Lines: " + lineCount);
        System.out.println("Words: " + wordCount);
        System.out.println("Characters: " + text.length());

        // Exercise 4: Calculate the average mark and list
        // students whose marks are above the average.
        List<Student> classList = List.of(
            new Student("Tariro", 80),
            new Student("Farai", 45),
            new Student("Rudo", 90),
            new Student("Kuda", 65)
        );

        double average = classList.stream()
            .mapToInt(Student::marks)
            .average()
            .orElse(0);

        System.out.println("Class average: " + average);
        System.out.println("Students above average:");

        classList.stream()
            .filter(s -> s.marks() > average)
            .map(Student::name)
            .sorted()
            .forEach(System.out::println);

        // Exercise 5: Read a CSV file and group learners by grade.
        Path csv = Path.of("learners.csv");

        Files.write(csv, List.of(
            "name,grade",
            "Tariro,A",
            "Farai,C",
            "Rudo,A",
            "Kuda,B",
            "Musa,C"
        ));

        Map<String, List<String>> groups =
            Files.readAllLines(csv)
                .stream()
                .skip(1)
                .map(row -> row.split(","))
                .collect(Collectors.groupingBy(
                    row -> row[1],
                    Collectors.mapping(
                        row -> row[0],
                        Collectors.toList()
                    )
                ));

        for (String grade : List.of("A", "B", "C")) {
            Path report = Path.of("grade-" + grade + ".txt");

            List<String> learners =
                groups.getOrDefault(grade, List.of());

            Files.write(report, learners);

            System.out.println(
                "Wrote " + report + ": " + learners
            );
        }
    }
}