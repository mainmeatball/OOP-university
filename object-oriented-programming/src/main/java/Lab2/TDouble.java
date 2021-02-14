package Lab2;

public class TDouble {

//    TComplex value = new TComplex();
    double value;

    public TDouble() {
        this.value = 0.0d;
    }

    public TDouble(double value) {
        this.value = value;
    }

    public TDouble(TDouble value) {
        this.value = value.value;
    }

    public void set(double value) {
        this.value = value;
    };

    public void set(String value) {
        this.value = Double.parseDouble(value);
    };

    public TDouble add(TDouble value) {
        return new TDouble(this.value + value.value);
    }

    public TDouble subtract(TDouble value) {
        return new TDouble(this.value - value.value);
    }

    public TDouble multiply(TDouble value) {
        return new TDouble(this.value * value.value);
    }

    public TDouble multiply(double value) {
        return new TDouble(this.value * value);
    }

    public TDouble divide(TDouble value) {
        return new TDouble(this.value / value.value);
    }

    public TDouble square() {
        return new TDouble(this.value * this.value);
    }

    public TDouble sqrt() {
        return new TDouble(Math.sqrt(this.value));
    }

    public TDouble negative() {
        return new TDouble(-this.value);
    }

    public boolean equals(double value) {
        return this.value == value;
    }

    public boolean moreThan(double value) {
        return this.value > value;
    }

    public boolean lessThan(double value) { return this.value < value; }

    @Override
    public String toString() {
        return "" + value;
    }
}
