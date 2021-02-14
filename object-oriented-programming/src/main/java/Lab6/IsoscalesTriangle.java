package Lab6;

public class IsoscalesTriangle extends Triangle {

    public IsoscalesTriangle(double a, double angle) {
        super(a, a, a * Math.sqrt(2 - 2 * Math.cos((angle * Math.PI / 180))));
    }

    public IsoscalesTriangle(double a) {
        super(a, a, a);
    }

}
