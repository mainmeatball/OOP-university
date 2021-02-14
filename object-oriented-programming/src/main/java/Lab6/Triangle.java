package Lab6;

import java.util.Arrays;

public class Triangle {

    double a, b, c;
    double A, B, C;
    double x, y;

    public Triangle(double a, double b, double c) {
        double[] d = {a, b, c};
        if (a != b) {
            Arrays.sort(d);
        }

        System.out.println("---------------------");
        System.out.println("Сторона а = " + a);
        System.out.println("Сторона b = " + b);
        System.out.println("Сторона c = " + c);
        System.out.println("---------------------");

        this.a = d[0] * 50;
        this.b = d[1] * 50;
        this.c = d[2] * 50;
        this.A = Math.acos((this.b * this.b + this.c * this.c - this.a * this.a) / (2 * this.b * this.c));
        this.B = Math.acos((this.a*this.a + this.c*this.c - this.b*this.b) / (2*this.a*this.c));
        this.C = Math.acos((this.a*this.a + this.b*this.b - this.c*this.c) / (2*this.a*this.b));
    }

    public void setPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double[] getXPoints() {
        return calculateXPoints();
    }

    public double[] getYPoints() {
        return calculateYPoints();
    }

    private double[] calculateXPoints() {
        return new double[] {
                this.x,
                this.x + (a * (Math.cos(C + A))),
                this.x + (b * Math.cos(A)),
        };
    }

    private double[] calculateYPoints() {
        return new double[] {
                this.y,
                this.y + (a * (Math.sin(C + A))),
                this.y + (b * Math.sin(A)),
        };
    }

    public static boolean isCorrect(double a, double b, double c) {
        return ((a < b + c) && (b < a + c) && (c < a + b));
    }
}
