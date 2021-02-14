package Lab7;

import javafx.scene.canvas.GraphicsContext;

public class Ellipsis extends SymmetricShape {
    public Ellipsis(int a, int b) {
        super(a, b);
    }

    @Override
    public boolean contains(double x, double y) {
        return (((x - this.centerX)*(x - this.centerX))/((this.width/2.)*(this.width/2.))) + (((y - this.centerY)*(y - this.centerY))/((this.height/2.)*(this.height/2.))) <= 1;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.strokeOval(this.centerX - this.width / 2., this.centerY - this.height / 2., this.width, this.height);
    }
}
