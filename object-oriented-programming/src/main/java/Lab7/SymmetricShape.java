package Lab7;

import javafx.scene.canvas.GraphicsContext;

public abstract class SymmetricShape {
    int width, height;
    double centerX, centerY;

    public SymmetricShape(int a, int b) {
        this.width = a;
        this.height = b;
    }

    public void setCenter(double x, double y) {
        this.centerX = x;
        this.centerY = y;
    }

    public abstract void draw(GraphicsContext gc);

    public abstract boolean contains(double x, double y);
}
