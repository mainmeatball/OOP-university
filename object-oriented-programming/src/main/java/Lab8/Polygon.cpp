#include "Polygon.h"
#include "Point.h"
#include <string>
#include <cmath>

using namespace std;

Polygon::Polygon(int n, double* sides, int* angles) {
    this->n = n;
    Point* points = new Point[n];
    points[0] = {0, 0};
    points[1] = {-sides[0], 0};
    int realAngle = angles[0];
    for (int i = 2; i < n; i++) {
        if (i > 2) {
            realAngle = (angles[i-2] + realAngle) - 180;
        }
        points[i] = {
                points[i-1].x + (sides[i-1] * cos(toRadians(realAngle))),
                points[i-1].y + (sides[i-1] * sin(toRadians(realAngle)))};
    }
    double lastSide = sqrt((points[n - 1].x * points[n - 1].x) +
            (points[n - 1].y * points[n - 1].y));
    this->sides = sides;
    this->angles = angles;
    double sum = 0;
    for (int i = 0; i < n - 1; i++) {
        sum += sides[i];
    }
    this->perimeter = sum + lastSide;
    double _area = 0.0;
    int j = n - 1;
    for (int i = 0; i < n; i++) {
        _area += (points[j].x + points[i].x) * (points[j].y - points[i].y);
        j = i;
    }
    this->area = abs(_area / 2.0);
}

double Polygon::getArea() {
    return area;
}

double Polygon::toRadians(int angle) {
    return angle * M_PI / 180.0; ;
}

double Polygon::getPerimeter() {
    return this->perimeter;
}

bool Polygon::onSegment(Point p, Point q, Point r) {
    return q.x <= max(p.x, r.x) && q.x >= min(p.x, r.x) &&
           q.y <= max(p.y, r.y) && q.y >= min(p.y, r.y);
}

bool Polygon::doIntersect(Point p1, Point q1, Point p2, Point q2) {
    int o1 = orientation(p1, q1, p2);
    int o2 = orientation(p1, q1, q2);
    int o3 = orientation(p2, q2, p1);
    int o4 = orientation(p2, q2, q1);

    // General case
    if (o1 != o2 && o3 != o4)
        return true;

    // Special Cases
    // p1, q1 and p2 are colinear and p2 lies on segment p1q1
    if (o1 == 0 && onSegment(p1, p2, q1)) return true;

    // p1, q1 and q2 are colinear and q2 lies on segment p1q1
    if (o2 == 0 && onSegment(p1, q2, q1)) return true;

    // p2, q2 and p1 are colinear and p1 lies on segment p2q2
    if (o3 == 0 && onSegment(p2, p1, q2)) return true;

    // p2, q2 and q1 are colinear and q1 lies on segment p2q2
    if (o4 == 0 && onSegment(p2, q1, q2)) return true;

    return false; // Doesn't fall in any of the above cases
}

int Polygon::orientation(Point p, Point q, Point r) {
    double val = (q.y - p.y) * (r.x - q.x) -
                 (q.x - p.x) * (r.y - q.y);
    if (val == 0) return 0;  // colinear
    return (val > 0)? 1: 2; // clock or counterclock wise
}

bool Polygon::isValid(int n, double *sides, int* angles) {
    Point* points = new Point[n];
    points[0] = {0, 0};
    points[1] = {-sides[0], 0};
    int realAngle = angles[0];
    for (int i = 2; i < n; i++) {
        if (i > 2) {
            realAngle = (angles[i-2] + realAngle) - 180;
        }
        points[i] = {
                points[i-1].x + (sides[i-1] * cos(toRadians(realAngle))),
                points[i-1].y + (sides[i-1] * sin(toRadians(realAngle)))};
    }
    for (int i = 0; i < n-1; i++) {
        for (int j = i+2; j < n-1; j++) {
            if (doIntersect(points[i],points[i+1],points[j],points[j+1])) {
                return false;
            }
        }
    }
    return true;
}


Triangle::Triangle(double a, double b, int angle) :
    Polygon(3, new double[2]{a,b}, &angle){}

Quadrangle::Quadrangle(double a, double b, double c, int A, int B) :
    Polygon(4, new double[3]{a,b,c}, new int[2]{A, B}){}

Parallelogram::Parallelogram(double a, double b, int angle) :
    Quadrangle(a,b,a,angle,180-angle){}

Rectangle::Rectangle(double a, double b) : Parallelogram(a,b,90){

}

Rhombus::Rhombus(double a, int angle) : Parallelogram(a, a, angle){

}

Square::Square(double a) : Parallelogram(a,a,90), Rectangle(a,a), Rhombus(a, 90){

}
