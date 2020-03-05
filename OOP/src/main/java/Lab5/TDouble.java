package Lab5;

public class TDouble extends number {

    public double value;

    public TDouble() {
        this.value = 0.0;
    }

    public TDouble(double value) {
        this.value = value;
    }

    public TDouble(String value) {
        this.value = Double.parseDouble(value);
    }

    public void set(double value) {
        this.value = value;
    };

    public void set(String value) {
        this.value = Double.parseDouble(value);
    }

    public TDouble add(double value) {
        return new TDouble(this.value + value);
    }

    @Override
    public TDouble add(number value) {
        return ((TDouble) value).add(this.value);
    }

    public TDouble subtract(TDouble value) {
        return new TDouble(this.value - value.value);
    }

    @Override
    public TDouble subtract(number value) {
        return this.subtract((TDouble) value);
    }

    public TDouble multiply(double value) {
        return new TDouble(this.value * value);
    }

    @Override
    public TDouble multiply(number value) {
        return ((TDouble) value).multiply(this.value);
    }

    public TDouble divide(TDouble value) {
        return new TDouble(this.value / value.value);
    }

    @Override
    public TDouble divide(number value) {
        return this.divide((TDouble) value);
    }

    @Override
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
