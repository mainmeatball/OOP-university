#include <iostream>
#include "Polygon.h"

int menu();

using namespace std;

int main() {
    bool end = false;
    while (!end) {
        int var = menu();
        switch (var) {
            case 1: {
                int n;
                cout << "Введите количество углов многоугольника: ";
                cin >> n;
                double *sides = new double[n - 1];
                int *angles = new int[n - 2];
                cout << "Введите " << n - 1 << " сторон многоугольника по часовой стрелке: ";
                for (int i = 0; i < n - 1; i++) {
                    cin >> sides[i];
                }
                cout << "Введите " << n - 2 << " углов многоугольника по часовой стрелке: ";
                for (int i = 0; i < n - 2; i++) {
                    cin >> angles[i];
                }
                if (!Polygon::isValid(n, sides, angles)) {
                    cout << "Заданный многоугольник пересекает себя, попробуйте ввести заново!" << endl;
                    break;
                }
                Polygon *polygon = new Polygon(n, sides, angles);
                cout << "Периметр многоугольника: " << polygon->getPerimeter() << endl;
                cout << "Площадь многоугольника: " << polygon->getArea() << endl;
                cout << "---------------------------------------------" << endl << endl;
                break;
            }
            case 2: {
                double a, b;
                cout << "Введите две стороны: ";
                cin >> a >> b;
                cout << "Введите угол между ними: ";
                int angle;
                cin >> angle;
                Triangle *triangle = new Triangle(a, b, angle);
                cout << "Периметр треугольника: " << triangle->getPerimeter() << endl;
                cout << "Площадь треугольника: " << triangle->getArea() << endl;
                cout << "---------------------------------------------" << endl << endl;
                break;
            }
            case 3: {
                double a, b, c;
                cout << "Введите три стороны: ";
                cin >> a >> b >> c;
                cout << "Введите два угла между ними: ";
                int ab, bc;
                cin >> ab >> bc;
                Quadrangle* quadrangle = new Quadrangle(a, b, c, ab, bc);
                cout << "Периметр четырехугольника: " << quadrangle->getPerimeter() << endl;
                cout << "Площадь четырехугольника: " << quadrangle->getArea() << endl;
                cout << "---------------------------------------------" << endl << endl;
                break;
            }
            case 4: {
                double a, b;
                cout << "Введите две стороны: ";
                cin >> a >> b;
                cout << "Введите угол между ними: ";
                int ab;
                cin >> ab;
                Parallelogram* parallelogram = new Parallelogram(a, b, ab);
                cout << "Периметр параллелограмма: " << parallelogram->getPerimeter() << endl;
                cout << "Площадь параллелограмма: " << parallelogram->getArea() << endl;
                cout << "---------------------------------------------" << endl << endl;
                break;
            }
            case 5: {
                double a, b;
                cout << "Введите две стороны: ";
                cin >> a >> b;
                Rectangle* rectangle = new Rectangle(a, b);
                cout << "Периметр прямоугольника: " << rectangle->getPerimeter() << endl;
                cout << "Площадь прямоугольника: " << rectangle->getArea() << endl;
                cout << "---------------------------------------------" << endl << endl;
                break;
            }
            case 6: {
                double a;
                int angle;
                cout << "Введите сторону и угол: ";
                cin >> a >> angle;
                Rhombus* rhombus = new Rhombus(a, angle);
                cout << "Периметр ромба: " << rhombus->getPerimeter() << endl;
                cout << "Площадь ромба: " << rhombus->getArea() << endl;
                cout << "---------------------------------------------" << endl << endl;
                break;
            }
            case 7: {
                double a;
                cout << "Введите сторону: ";
                cin >> a;
                Square* square = new Square(a);
                cout << "Периметр квадрата: " << square->getPerimeter() << endl;
                cout << "Площадь квадрата: " << square->getArea() << endl;
                cout << "---------------------------------------------" << endl << endl;
                break;
            }
            case 0: {
                end = true;
                break;
            }
            default:
                break;
        }
    }
    return 0;
}

int menu() {
    int var;
    cout << "Выберите фигуру, которую вы хотите задать: " << endl;
    cout << "1. Многоугольник" << endl;
    cout << "2. Треугольник" << endl;
    cout << "3. Четырехугольник" << endl;
    cout << "4. Параллелограмм" << endl;
    cout << "5. Прямоугольник" << endl;
    cout << "6. Ромб" << endl;
    cout << "7. Квадрат" << endl;
    cout << "0. Выйти из программы" << endl;
    cout << ">>> ";
    cin >> var;
    return var;
}
