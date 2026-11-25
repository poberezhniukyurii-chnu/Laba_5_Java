package records;

/** Звичайний дріб: first — чисельник, second — знаменник (> 0), завжди нескоротний.
 *  Природний порядок (Comparable) — за величиною дробу. */
public class Fraction extends Pair implements Comparable<Fraction> {

    public Fraction(long numerator, long denominator) {
        super(numerator, denominator);
        if (denominator == 0) throw new ArithmeticException("знаменник дорівнює нулю");
        if (second < 0) {
            first = -first;
            second = -second;
        }
        long g = gcd(Math.abs(first), second);
        first /= g;
        second /= g;
    }

    /** Дріб з рядка "3/4". */
    public Fraction(String s) {
        this(Long.parseLong(s.split("/")[0].trim()),
             s.contains("/") ? Long.parseLong(s.split("/")[1].trim()) : 1);
    }

    private static long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a == 0 ? 1 : a;
    }

    @Override
    public Pair add(Pair other) {
        checkSameType(other);
        Fraction f = (Fraction) other;
        return new Fraction(first * f.second + f.first * second, second * f.second);
    }

    @Override
    public Pair subtract(Pair other) {
        checkSameType(other);
        Fraction f = (Fraction) other;
        return new Fraction(first * f.second - f.first * second, second * f.second);
    }

    @Override
    public Pair multiply(int k) {
        return new Fraction(first * k, second);
    }

    @Override
    public Pair divide(int k) {
        if (k == 0) throw new ArithmeticException("ділення на нуль");
        return new Fraction(first, second * k);
    }

    /** Додатковий метод підкласу: множення двох дробів. */
    public Fraction multiply(Fraction f) {
        return new Fraction(first * f.first, second * f.second);
    }

    /** a/b < c/d  ⇔  a·d < c·b (знаменники додатні), тож порівнюємо без дійсних чисел. */
    @Override
    public int compareTo(Fraction other) {
        return Long.compare(first * other.second, other.first * second);
    }

    @Override
    public double value() {
        return (double) first / second;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Fraction)) return false;
        Fraction f = (Fraction) obj;
        return first == f.first && second == f.second;
    }

    @Override
    public int hashCode() {
        return 31 * Long.hashCode(first) + Long.hashCode(second);
    }

    @Override
    public String toString() {
        return second == 1 ? String.valueOf(first) : first + "/" + second;
    }
}
