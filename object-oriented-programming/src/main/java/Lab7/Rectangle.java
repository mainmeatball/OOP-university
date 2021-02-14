package Lab7;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends SymmetricShape {
    public Rectangle(int a, int b) {
        super(a, b);
    }

    @Override
    public boolean contains(double x, double y) {
        double xMin = this.centerX - this.width / 2.;
        double xMax = this.centerX + this.width / 2.;
        double yMin = this.centerY - this.height / 2.;
        double yMax = this.centerY + this.height / 2.;
        return x > xMin && x < xMax && y > yMin && y < yMax;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.strokeRect(this.centerX - this.width / 2., this.centerY - this.height / 2., this.width, this.height);
    }
}
