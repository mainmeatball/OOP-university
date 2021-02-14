package Lab4;

public class TPolinom {

    number a, b, c;
    number root1, root2;
    number x;

    public TPolinom() {
        this.a = new number();
        this.b = new number();
        this.c = new number();
        this.root1 = new number();
        this.root2 = new number();
        this.x = new number();
    }

    public number value() {
        return (a.multiply(x.square()).add(b.multiply(x)).add(c));
    }

    public int roots() {
        root1.value.set(0);
        root2.value.set(0);
        number d = a.value.equals(0) ? new number(0) : b.square().subtract(a.multiply(c).multiply(4));
        if (d.value.equals(0)) {
            root1 = root2 = a.value.equals(0) ? c.negative().divide(b) : b.negative().divide(a.multiply(2));
            return 1;
        } else if (d.value.moreThan(0)) {
            root1 = (b.negative().add(d.sqrt())).divide(a.multiply(2));
            root2 = (b.negative().subtract(d.sqrt())).divide(a.multiply(2));
            return 2;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(a).append("x^2");
        if (b.value.lessThan(0)) {
            sb.append(" - ").append(b.negative()).append('x');
        } else {
            if (!b.value.equals(0)) {
                sb.append(" + ").append(b).append('x');
            }
        }
        if (c.value.lessThan(0)) {
            sb.append(" - ").append(c.negative()).append(" = 0");
        } else {
            if (!c.value.equals(0)) {
                sb.append(" + ").append(c).append('x');
            }
        }
        return sb.toString();
    }
}
