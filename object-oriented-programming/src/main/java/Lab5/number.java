package Lab5;

public abstract class number {
    public abstract void set(String value);
    public abstract void set(double value);
    public abstract number add(number value);
    public abstract number subtract(number value);
    public abstract number multiply(number value);
    public abstract number multiply(double value);
    public abstract number divide(number value);
    public abstract number square();
    public abstract number sqrt();
    public abstract number negative();
    public abstract boolean equals(double value);
    public abstract boolean moreThan(double value);
    public abstract boolean lessThan(double value);
    public abstract String toString();
}
