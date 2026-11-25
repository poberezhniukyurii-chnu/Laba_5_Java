package records;

/**
 * Абстрактний базовий клас «пара чисел» (варіант 8).
 * Спільні поля — дві цілі складові; конкретний зміст визначають підкласи:
 * Money (гривні, копійки) і Fraction (чисельник, знаменник).
 * Клас із лабораторної роботи №3 (завдання 2), використаний у ЛР5 як основа записів.
 * Побережнюк Ю.В., група 241б.
 */
public abstract class Pair {
    protected long first;
    protected long second;

    protected Pair(long first, long second) {
        this.first = first;
        this.second = second;
    }

    public long getFirst() { return first; }
    public long getSecond() { return second; }

    /** Додавання пари того самого типу. */
    public abstract Pair add(Pair other);

    /** Віднімання пари того самого типу. */
    public abstract Pair subtract(Pair other);

    /** Множення на ціле число. */
    public abstract Pair multiply(int k);

    /** Ділення на ціле число. */
    public abstract Pair divide(int k);

    /** Числове значення пари (для виведення й порівняння). */
    public abstract double value();

    /** Спільна перевірка: операнд має бути того самого класу. */
    protected void checkSameType(Pair other) {
        if (other == null || other.getClass() != getClass()) {
            throw new IllegalArgumentException("операція можлива лише між об'єктами класу "
                    + getClass().getSimpleName());
        }
    }

    /** Звичайний (не абстрактний) метод базового класу. */
    public String describe() {
        return getClass().getSimpleName() + " " + this
                + " (значення " + String.format("%.4f", value()) + ")";
    }
}
