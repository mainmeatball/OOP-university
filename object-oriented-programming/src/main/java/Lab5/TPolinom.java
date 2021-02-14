package Lab5;

import java.util.function.Supplier;

public class TPolinom<T extends number> {

    T a, b, c;
    T root1, root2;
    T x;

    public TPolinom(Supplier<? extends T> ctor) {
        this.a = ctor.get();
        this.b = ctor.get();
        this.c = ctor.get();
        this.root1 = ctor.get();
        this.root2 = ctor.get();
        this.x = ctor.get();
    }

    public T value() {
        number n = (a.multiply(x.square()).add(b.multiply(x)).add(c));
        if (n != null) {
            return (T)n;
        }
        return null;
    }

    public int roots() {
        root1.set(0);
        root2.set(0);
        T d = (T)b.square().subtract(a.multiply(c).multiply(4));
        if (d.equals(0)) {
            root1 = root2 = (T)b.negative().divide(a.multiply(2));
            return 1;
        } else if (d.moreThan(0)) {
            root1 = (T)(b.negative().add(d.sqrt())).divide(a.multiply(2));
            root2 = (T)(b.negative().subtract(d.sqrt())).divide(a.multiply(2));
            return 2;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(a).append("x^2");
        if (b.lessThan(0)) {
            sb.append(" - ").append(b.negative()).append('x');
        } else {
            if (!b.equals(0)) {
                sb.append(" + ").append(b).append('x');
            }
        }
        if (c.lessThan(0)) {
            sb.append(" - ").append(c.negative()).append(" = 0");
        } else {
            if (!c.equals(0)) {
                sb.append(" + ").append(c).append(" = 0");
            }
        }
        return sb.toString();
    }
}
