import java.text.DecimalFormat;

public class Triple<A extends Number, B extends Number, C extends Number> implements Comparable<Triple> {
    A one;
    B two;
    C three;

    public Triple(A one, B two, C three) {
        this.one = one;
        this.two = two;
        this.three = three;
    }

    double maxProductTwist() {
        double a = one.doubleValue();
        double b = two.doubleValue();
        double c = three.doubleValue();
        return Math.max(
                a * b + c,
                Math.max(a + b * c, a * c + b)
        );
    }

    public void setOne(A one) {
        this.one = one;
    }

    public void setTwo(B two) {
        this.two = two;
    }

    public void setThree(C three) {
        this.three = three;
    }

    public A getOne() {
        return one;
    }

    public B getTwo() {
        return two;
    }

    public C getThree() {
        return three;
    }

    @Override
    public int compareTo(Triple triple) {
        return Double.compare(triple.maxProductTwist(), this.maxProductTwist());
    }

    @Override
    public String toString() {
        DecimalFormat format = new DecimalFormat("0.0#");
        return String.format(
                "Triple => [%s, %s, %s] has max twist: %s",
                format.format(one.doubleValue()),
                format.format(two.doubleValue()),
                format.format(three.doubleValue()),
                format.format(maxProductTwist())
        );
    }
}
