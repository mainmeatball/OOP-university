package Lab1;

import java.util.Scanner;

public class TApplication {

    TPolinom polinom = new TPolinom();

    public TApplication() {
    }

    public void exec() {
        Scanner scanner = new Scanner(System.in);
        int ch;
        boolean run = true;
        while (run) {
            ch = menu();
            switch (ch) {
                case 1:
                    System.out.println("Представлен полином вида Ах^2 + Bx + C = 0");
                    System.out.print("Введите коэффициент А = ");
                    polinom.a.set(scanner.nextLine());
                    System.out.print("Введите коэффициент B = ");
                    polinom.b.set(scanner.nextLine());
                    System.out.print("Введите коэффициент C = ");
                    polinom.c.set(scanner.nextLine());
                    break;
                case 2:
                    int roots = polinom.roots();
                    System.out.printf("Полином вида " + polinom + " имеет %d корней\n", roots);
                    if (roots == 0) {
                        break;
                    }
                    System.out.println(roots == 1 ? "Корень = " + polinom.root1 : "Корень1 = " + polinom.root1 + "; Корень2 = " + polinom.root2);
                    break;
                case 3:
                    System.out.print("Введите значение аргумента х = ");
                    polinom.x.set(scanner.nextLine());
                    System.out.println(polinom.value());
                    break;
                case 4:
                    run = false;
                    break;
                default:
                    break;
            }
        }
    }

    public int menu() {
        System.out.println("1. Ввести коэффициенты полинома");
        System.out.println("2. Посчитать корни полинома");
        System.out.println("3. Указать значение аргумента");
        System.out.println("4. Выйти из программы");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
