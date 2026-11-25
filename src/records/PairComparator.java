package records;

import java.util.Comparator;

/**
 * Компаратор для об'єктів абстрактного класу Pair: порівнює числові значення пар,
 * а за однакового значення — назви класів (щоб порядок був однозначним).
 * Лабораторна робота №5, завдання 1. Побережнюк Ю.В., група 241б.
 */
public class PairComparator implements Comparator<Pair> {
    @Override
    public int compare(Pair a, Pair b) {
        int byValue = Double.compare(a.value(), b.value());
        if (byValue != 0) return byValue;
        return a.getClass().getSimpleName().compareTo(b.getClass().getSimpleName());
    }
}
