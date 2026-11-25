package task2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.Collator;
import java.util.*;

/**
 * ЛР5, завдання 2 (варіант 11): ввести рядки з файлу, записати їх у список ArrayList
 * і відсортувати методом sort() класу Collections.
 * Побережнюк Ю.В., група 241б.
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введіть назву файлу з рядками: ");
        String fileName = in.nextLine().trim();

        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = r.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не знайдено: " + fileName);
            return;
        } catch (IOException e) {
            System.out.println("Помилка читання: " + e.getMessage());
            return;
        }

        System.out.println("Рядки з файлу (" + lines.size() + "):");
        lines.forEach(s -> System.out.println("  " + s));

        Collections.sort(lines);                  // природний порядок String (коди Unicode)
        System.out.println("Collections.sort(lines):");
        lines.forEach(s -> System.out.println("  " + s));

        // за українською абеткою
        Collections.sort(lines, Collator.getInstance(new Locale("uk", "UA")));
        System.out.println("Collections.sort(lines, Collator uk_UA) — за абеткою:");
        lines.forEach(s -> System.out.println("  " + s));

        Collections.sort(lines, Comparator.comparingInt(String::length)
                .thenComparing(Comparator.naturalOrder()));
        System.out.println("Collections.sort(lines, за довжиною рядка):");
        lines.forEach(s -> System.out.printf("  %2d  %s%n", s.length(), s));

        Collections.sort(lines, Collections.reverseOrder());
        System.out.println("Collections.sort(lines, Collections.reverseOrder()):");
        lines.forEach(s -> System.out.println("  " + s));
    }
}
