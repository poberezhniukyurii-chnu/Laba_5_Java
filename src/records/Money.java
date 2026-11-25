package records;

/** Грошова сума: first — гривні, second — копійки (0..99); знак зберігається в обох полях.
 *  Природний порядок (Comparable) — за величиною суми. */
public class Money extends Pair implements Comparable<Money> {

    public Money(long hryvnias, long kopecks) {
        super(0, 0);
        setKopecks(hryvnias * 100 + kopecks);
    }

    /** Сума з рядка "125,50" або "125.50". */
    public Money(String s) {
        this(0, Math.round(Double.parseDouble(s.trim().replace(',', '.')) * 100));
    }

    private static Money ofKopecks(long total) {
        return new Money(0, total);
    }

    private void setKopecks(long total) {
        first = total / 100;
        second = total % 100;
    }

    /** Уся сума в копійках. */
    public long toKopecks() {
        return first * 100 + second;
    }

    @Override
    public Pair add(Pair other) {
        checkSameType(other);
        return ofKopecks(toKopecks() + ((Money) other).toKopecks());
    }

    @Override
    public Pair subtract(Pair other) {
        checkSameType(other);
        return ofKopecks(toKopecks() - ((Money) other).toKopecks());
    }

    @Override
    public Pair multiply(int k) {
        return ofKopecks(toKopecks() * k);
    }

    /** Ділення з округленням до копійки. */
    @Override
    public Pair divide(int k) {
        if (k == 0) throw new ArithmeticException("ділення на нуль");
        return ofKopecks(Math.round((double) toKopecks() / k));
    }

    @Override
    public int compareTo(Money other) {
        return Long.compare(toKopecks(), other.toKopecks());
    }

    @Override
    public double value() {
        return toKopecks() / 100.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Money)) return false;
        return toKopecks() == ((Money) obj).toKopecks();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(toKopecks());
    }

    @Override
    public String toString() {
        long total = toKopecks();
        String sign = total < 0 ? "-" : "";
        total = Math.abs(total);
        return String.format("%s%d,%02d грн", sign, total / 100, total % 100);
    }
}
