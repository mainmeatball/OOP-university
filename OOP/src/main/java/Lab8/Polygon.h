#ifndef Polygon_h
#define Polygon_h

#include "cmath"
#include <iostream>
#include "Point.h"

class Polygon {
public:
    Polygon(int, double*, int*);
    double getArea();
    double getPerimeter();
    static bool isValid(int, double*, int*);

protected:
    int n;
    double* sides;
    int* angles;

    double perimeter;
    double area;

    static double toRadians(int angle);

private:
    static bool onSegment(Point,Point,Point);
    static int orientation(Point,Point,Point);
    static bool doIntersect(Point,Point,Point,Point);
};

class Triangle : public Polygon {
public:
    Triangle(double, double, int);
};

class Quadrangle : public Polygon {
public:
    Quadrangle(double,double,double,int,int);
};

class Parallelogram : public Quadrangle {
public:
    Parallelogram(double,double,int);
};

class Rectangle : public virtual Parallelogram {
public:
    Rectangle(double, double);
};

class Rhombus : public virtual Parallelogram {
public:
    Rhombus(double, int);
};

class Square : public Rectangle, Rhombus {
public:
    Square(double);
};
#endif // Polygon_h