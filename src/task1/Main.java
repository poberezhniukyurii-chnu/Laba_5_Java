package task1;

import records.Fraction;
import records.Money;
import records.Pair;
import records.PairComparator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * ЛР5, завдання 1: керування записами на основі похідних класів з ЛР3 (Money, Fraction).
 * Читання з файлів у ArrayList, сортування Collections.sort() (Comparable), додавання записів
 * з клавіатури, спільний ArrayList<Pair> із сортуванням за Comparator і запис у файл.
 * Побережнюк Ю.В., група 241б.
 */
public class Main {

    /** Узагальнений метод: читає файл і перетворює кожен рядок на об'єкт типу T. */
    static <T> ArrayList<T> readRecords(String fileName, Function<String, T> parser)
            throws IOException {
        ArrayList<T> list = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            String line;
            int no = 0;
            while ((line = r.readLine()) != null) {
                no++;
                if (line.trim().isEmpty()) continue;
                try {
                    list.add(parser.apply(line.trim()));
                } catch (RuntimeException e) {     // NumberFormatException, ArithmeticException
                    System.out.println("  " + fileName + ", рядок " + no + " \"" + line
                            + "\" пропущено: некоректний запис");
                }
            }
        }
        return list;
    }

    static void print(String title, List<?> list) {
        System.out.println(title + " (" + list.size() + "): " + list);
    }

    public static void main(String[] args) {
        String moneyFile = args.length > 0 ? args[0] : "money.txt";
        String fractionFile = args.length > 1 ? args[1] : "fractions.txt";
        String outFile = args.length > 2 ? args[2] : "all_sorted.txt";
        Scanner in = new Scanner(System.in);
        try {
            ArrayList<Money> money = readRecords(moneyFile, Money::new);
            ArrayList<Fraction> fractions = readRecords(fractionFile, Fraction::new);
            print("Суми з файлу " + moneyFile, money);
            print("Дроби з файлу " + fractionFile, fractions);

            Collections.sort(money);          // природний порядок: Money.compareTo
            Collections.sort(fractions);      // природний порядок: Fraction.compareTo
            print("Відсортовані суми", money);
            print("Відсортовані дроби", fractions);

            System.out.print("Введіть нову суму (напр. 75,20): ");
            money.add(new Money(in.nextLine()));
            System.out.print("Введіть новий дріб (напр. 7/8): ");
            fractions.add(new Fraction(in.nextLine()));
            Collections.sort(money);
            Collections.sort(fractions);
            print("Суми після додавання і сортування", money);
            print("Дроби після додавання і сортування", fractions);

            ArrayList<Pair> all = new ArrayList<>();          // колекція на основі абстрактного класу
            all.addAll(money);
            all.addAll(fractions);
            Collections.sort(all, new PairComparator());      // сортування компаратором
            System.out.println("Усі записи, відсортовані PairComparator (за значенням):");
            try (PrintWriter w = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8)))) {
                for (Pair p : all) {
                    String line = String.format("%-9s %-14s %10.4f",
                            p.getClass().getSimpleName(), p, p.value());
                    System.out.println("  " + line);
                    w.println(line);
                }
            }
            System.out.println("Відсортований список записано у файл " + outFile);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не знайдено: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Помилка введення-виведення: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Некоректне введення: " + e.getMessage());
        }
    }
}
