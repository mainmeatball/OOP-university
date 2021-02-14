package Lab5;

public class TApplication {

    TPolinom<number> polinom;

    public TApplication() {
    }

    public String exec(String argA, String argB, String argC) {
        if (argA.split(" ").length > 1) {
            polinom = new TPolinom<>(TComplex::new);
        } else {
            polinom = new TPolinom<>(TDouble::new);
        }

        polinom.a.set(argA);
        polinom.b.set(argB);
        polinom.c.set(argC);

        int roots = polinom.roots();

        StringBuilder sb = new StringBuilder();
        sb.append("Данный полином имеет имеет ").append(roots).append(" корней\n");
        switch (roots) {
            case 0:
                return sb.toString();
            case 1:
                return sb.append("Корень = ").append(polinom.root1).toString();
            case 2:
                return sb.append("Корень1 = ").append(polinom.root1).append(";\n Корень2 = ").append(polinom.root2).toString();
            default:
                throw new RuntimeException("Введенные данные неверны.");
        }
    }
}
