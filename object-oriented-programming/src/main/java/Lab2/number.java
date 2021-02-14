package Lab2;

public class number {

    TComplex value = new TComplex();

    public number() {
        this.value.set(0.0d);
    }

    public number(double value) {
        this.value.set(value);
    }

    public number(TComplex value) {
        this.value = value;
    }

    public void set(String value) {
        this.value.set(value);
    };

    public number add(number value) {
        return new number(this.value.add(value.value));
    }

    public number subtract(number value) {
        return new number(this.value.subtract(value.value));
    }

    public number multiply(number value) {
        return new number(this.value.multiply(value.value));
    }

    public number multiply(double value) {
        return new number(this.value.multiply(value));
    }

    public number divide(number value) {
        return new number(this.value.divide(value.value));
    }

    public number square() {
        return new number(this.value.multiply(this.value));
    }

    public number sqrt() {
        return new number(this.value.sqrt());
    }

    public number negative() {
        return new number(this.value.negative());
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
