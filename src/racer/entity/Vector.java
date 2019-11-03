package racer.entity;

public class Vector {

	private double x, y;
    private double angle, radius;
    static byte Cartesian = 0, Polar = 1;

    Vector(double scalar1, double scalar2, byte coordianteSystem) {
        this.angle = 0;
        this.y = 0;
        this.x = 0;
        this.radius = 0;
        if (coordianteSystem == Cartesian) {
            this.x = scalar1;
            this.y = scalar2;
            refreshPolar();
        } else {
            this.radius = scalar1;
            this.angle = scalar2;
            refreshCartesian();
        }

    }

	double getX() {
        return x;
    }


    double getY() {
        return y;
    }


    private void refreshPolar() {
        radius = Math.sqrt(x * x + y * y);
        angle = Math.atan2(y, x);
    }

    private void refreshCartesian() {
        x = radius * Math.cos(angle);
        y = radius * Math.sin(angle);
    }

}
