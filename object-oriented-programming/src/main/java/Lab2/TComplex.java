package Lab2;

public class TComplex {

     public double re;
     public double im;

    public TComplex() {
    }

    public TComplex(double re, double im) {
         this.re = re;
         this.im = im;
     }

     public void set(double value) {
         this.re = value;
         this.im = value;
     }

    public void set(String value) {
        String[] input = value.split(" ", 2);
        if (input.length < 2) {
            System.out.println("Введите действительную и мнимую часть комлпексного числа.");
            return;
        }
        this.re = Double.parseDouble(input[0]);
        this.im = Double.parseDouble(input[1]);
    }

    public TComplex add(TComplex value) {
        return new TComplex(this.re + value.re, this.im + value.im);
    }

    public TComplex subtract(TComplex value) {
        return new TComplex(this.re - value.re, this.im - value.im);
    }

    public TComplex multiply(TComplex value) {
        return new TComplex(
                this.re * value.re - this.im - value.im,
                this.re * value.im + this.im - value.re);
    }

    public TComplex multiply(double value) {
        return new TComplex(
                this.re * value,
                this.im * value);
    }

    public TComplex divide(TComplex value) {
        return new TComplex(
                (this.re * value.re + this.im * value.im) / (value.re * value.re + value.im * value.im),
                (this.im * value.re + this.re * value.im) / (value.re * value.re + value.im * value.im)
                );
    }

    public TComplex square() {
        return this.multiply(this);
    }

    public TComplex sqrt() {
        double re = Math.sqrt(this.re * this.re + this.im * this.im);
        double im = Math.atan2(this.im , this.re);
        return new TComplex(Math.sqrt(re) * Math.cos(im/2), Math.sqrt(re)*Math.sin(im/2));
    }

    public TComplex negative() {
        return new TComplex(-this.re, -this.im);
    }

    public boolean equals(double value) {
        return this.re == value && this.im == value;
    }

    public boolean moreThan(double value) {
        return this.re > value && this.im > value;
    }

    public boolean lessThan(double value) {
        return this.re < value && this.im < value;
    }

    @Override
    public String toString() {
        return "{" +
                "re=" + re +
                ", im=" + im +
                '}';
    }
}
