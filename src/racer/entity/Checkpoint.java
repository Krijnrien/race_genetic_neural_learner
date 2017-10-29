package racer.entity;

import racer.graphics.Screen;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Checkpoints on map to track progress.
 */
public class Checkpoint {
    /**
     * First Point2D position for checkpoint.
     */
    private Point2D a;

    /**
     * Second Point2D position for checkpoint.
     */
    private Point2D b;

    /**
     * Boolean to draw and count checkpoint or not.
     */
    private boolean isActive;

    /**
     * Constructor setting checkpoint on map position and set to active.
     *
     * @param a        First Point2D position.
     * @param b        Second Point2D position.
     * @param isActive Boolean to draw and count checkpoint or not.
     */
    Checkpoint(Point2D a, Point2D b, boolean isActive) {
        this.setA(a);
        this.setB(b);
        this.setActive(isActive);
    }

    /**
     * Getter checkpoint is active.
     *
     * @return boolean if checkpoint is active or not.
     */
    boolean isActive() {
        return isActive;
    }

    /**
     * Setter if checkpoint is active or not.
     *
     * @param isActive boolean if checkpoint is active or not.
     */
    void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Setter first checkpoint point.
     *
     * @param a Checkpoint point
     */
    private void setA(Point2D a) {
        this.a = a;
    }

    /**
     * Getter first checkpoint point.
     *
     * @param b checkpoint point
     */
    private void setB(Point2D b) {
        this.b = b;
    }

    /**
     * Render checkpoint line from A to B.
     *
     * @param _screen To render on.
     */
    public void render(Screen _screen) {
        Color color = Color.BLUE;
        _screen.renderLine(a.getX(), a.getY(), b.getX(), b.getY(), color);
    }

    /**
     * Check if rectangle (car) intersects with checkpoint.
     *
     * @param _rectangle2D To intersect with
     * @return Boolean intersects or not
     */
    boolean checkIntersect(Rectangle2D _rectangle2D) {
        double x1 = a.getX();
        double y1 = a.getY();
        double x2 = b.getX();
        double y2 = b.getY();
        Line2D line = new Line2D.Double(x1, y1, x2, y2);
        return line.intersects(_rectangle2D);
    }
}
