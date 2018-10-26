package racer.entity;

import racer.algorithm.NeuralNetwork;
import racer.graphics.block.Block;
import racer.graphics.map.RaceMap;
import racer.graphics.Screen;
import racer.graphics.Sprite;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static java.lang.Double.isInfinite;

/**
 * Car object
 */
public class Car extends Mobile {

    public class Sensor {
        Point2D[] base;
        Point2D[] end;

        Sensor() {
            base = new Point2D[FEELER_COUNT];
            end = new Point2D[FEELER_COUNT];
        }
    }

    static int FEELER_COUNT = 5;

    public enum Sensors {
        left, left_front, front, front_right, right,
    }

    //todo
    private int Id;
    private static float SENSOR_LENGTH = 32.0f;
    private double deltaDistance;
    private Sensor sensor;
    private NeuralNetwork neural;
    private Point2D[] intersections;
    private double[] normalizedIntersectionDepths;

    private int eastIdx = Sensors.left.ordinal();
    private int northEastIdx = Sensors.left_front.ordinal();
    private int northIdx = Sensors.front.ordinal();
    private int northWestIdx = Sensors.front_right.ordinal();
    private int westIdx = Sensors.right.ordinal();

    private static final float MAX_ROTATION_PER_SECOND = 30.0f / 180;

    /**
     * Direction car is facing.
     */
    private double angle;

    /**
     * Construct car with position, angle and raceMap to render on.
     *
     * @param x         Screen X int position.
     * @param y         Screen Y int position.
     * @param angle     Direction car faces.
     * @param _Race_map To render car on.
     */
    Car(int x, int y, double angle, RaceMap _Race_map) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        raceMap = _Race_map;
        sensor = new Sensor();
        buildFeelers();
        detectFeelerIntersection();
    }


    /**
     * Update car.
     */
    public void update() {
        if (!this.collided) {
            buildFeelers();
            detectFeelerIntersection();
            neural.setInput(normalizedIntersectionDepths);
            neural.update();
            double leftForce = neural.getOutput(Manager.NeuralNetOuputs.NN_OUTPUT_LEFT_FORCE.ordinal());
            double rightForce = neural.getOutput(Manager.NeuralNetOuputs.NN_OUTPUT_RIGHT_FORCE.ordinal());
            //System.out.println("left force: " + leftForce + "-right force: " + rightForce);

            // Convert the outputs to a proportion of how much to turn.
            double leftTheta = MAX_ROTATION_PER_SECOND * leftForce;
            double rightTheta = MAX_ROTATION_PER_SECOND * rightForce;
            angle += (leftTheta - rightTheta) * 2;
            double movingX = Math.sin(angle) * 2;
            double movingY = -Math.cos(angle) * 2;

            deltaDistance = Math.sqrt(movingX * movingX + movingY * movingY);
            move((int) movingX, (int) movingY);
        }
    }

    /**
     * this function determines the position of sensors when the car turns
     */
    private void buildFeelers() {

        for (int i = 0; i < FEELER_COUNT; i++) {
            sensor.end[i] = new Point2D.Float();
            sensor.base[i] = new Point2D.Float();
            // All feelers's tails has the same coordinate which is the center
            // of the car.
            sensor.base[i].setLocation(x, y);
        }
        // East feeler's head
        sensor.end[eastIdx].setLocation(
                x + Math.sin(Math.PI - (angle + Math.PI / 2)) * SENSOR_LENGTH,
                y + Math.cos(Math.PI - (angle + Math.PI / 2)) * SENSOR_LENGTH);
        // North East feeler's head
        sensor.end[northEastIdx].setLocation(
                x + Math.sin(Math.PI - (angle + Math.PI / 4)) * SENSOR_LENGTH,
                y + Math.cos(Math.PI - (angle + Math.PI / 4)) * SENSOR_LENGTH);
        // North feeler's head
        sensor.end[northIdx].setLocation(x + Math.sin(Math.PI - angle)
                * SENSOR_LENGTH, y + Math.cos(Math.PI - angle) * SENSOR_LENGTH);
        // North West feeler's head
        sensor.end[northWestIdx].setLocation(
                x + Math.sin(Math.PI - (angle - Math.PI / 4)) * SENSOR_LENGTH,
                y + Math.cos(Math.PI - (angle - Math.PI / 4)) * SENSOR_LENGTH);
        // West feeler's head
        sensor.end[westIdx].setLocation(
                x + Math.sin(Math.PI - (angle - Math.PI / 2)) * SENSOR_LENGTH,
                y + Math.cos(Math.PI - (angle - Math.PI / 2)) * SENSOR_LENGTH);
    }

    /**
     * This function measures the distance from center of the car to the wall.
     */
    private void detectFeelerIntersection() {
        intersections = new Point2D[FEELER_COUNT];
        normalizedIntersectionDepths = new double[FEELER_COUNT];
        for (int k = 0; k < FEELER_COUNT; k++) {
            double xStart = sensor.end[k].getX();
            double xEnd = sensor.base[k].getX();
            double yStart = sensor.end[k].getY();
            double yEnd = sensor.base[k].getY();
            Line2D line = new Line2D.Double();
            line.setLine(sensor.end[k], sensor.base[k]);
            double step = 0.001;
            double slope = (yStart - yEnd) / (xStart - xEnd);
            if (!isInfinite(slope)) {
                for (double i = xStart; i < xEnd; i += step) {
                    double j = slope * (i - xEnd) + yEnd;
                    Block block = raceMap.getBlock((int) (i + Sprite.SIZE / 2)
                            / Sprite.SIZE, (int) (j + Sprite.SIZE / 2)
                            / Sprite.SIZE);
                    if (block != null) {
                        if (block.solid()) {
                            intersections[k] = new Point2D.Float();
                            intersections[k].setLocation(i, j);
                        }
                    }
                }
                for (double i = xStart; i > xEnd; i -= step) {
                    double j = slope * (i - xEnd) + yEnd;
                    Block block = raceMap.getBlock((int) (i + Sprite.SIZE / 2)
                            / Sprite.SIZE, (int) (j + Sprite.SIZE / 2)
                            / Sprite.SIZE);
                    if (block != null) {
                        if (block.solid()) {
                            intersections[k] = new Point2D.Float();
                            intersections[k].setLocation(i, j);
                        }
                    }
                }
            } else {
                for (double j = yStart; j < yEnd; j += step) {
                    Block block = raceMap.getBlock((int) (xStart + Sprite.SIZE / 2)
                            / Sprite.SIZE, (int) (j + Sprite.SIZE / 2)
                            / Sprite.SIZE);
                    if (block != null) {
                        if (block.solid()) {
                            intersections[k] = new Point2D.Float();
                            intersections[k].setLocation(xStart, j);
                        }
                    }
                }
                for (double j = yStart; j > yEnd; j -= step) {
                    Block block = raceMap.getBlock((int) (xStart + Sprite.SIZE / 2)
                            / Sprite.SIZE, (int) (j + Sprite.SIZE / 2)
                            / Sprite.SIZE);
                    if (block != null) {
                        if (block.solid()) {
                            intersections[k] = new Point2D.Float();
                            intersections[k].setLocation(xStart, j);
                        }
                    }
                }
            }
            if (intersections[k] != null) {
                normalizedIntersectionDepths[k] = 1 - (Math.sqrt(Math.pow(x
                        - intersections[k].getX(), 2)
                        + Math.pow(y - intersections[k].getY(), 2)) / SENSOR_LENGTH);
            } else {
                normalizedIntersectionDepths[k] = 0;
            }
        }
    }


    void attach(NeuralNetwork neuralNet) {
        this.neural = neuralNet;
    }

    void clearFailure() {
        collided = false;
    }

    boolean hasFailed() {
        return collided;
    }

    double getDistanceDelta() {
        return deltaDistance;
    }


    /**
     * Getter "physical" boundaries of car.
     *
     * @return Rectangle shape the size of the car.
     */
    Rectangle2D getCarBound() {
        return new Rectangle2D.Double(x - Sprite.SIZE / 2.0, y - Sprite.SIZE / 2.0, Sprite.SIZE, Sprite.SIZE);
    }

    /**
     * Rendering car on screen
     *
     * @param _screen To render car on.
     */
    public void render(Screen _screen) {
        _screen.renderCar(x, y, angle, Sprite.carSprite);
        // Render the car
        // draw collisions by a small circle
        for (int k = 0; k < FEELER_COUNT; k++) {
            if (intersections[k] != null) {
                _screen.renderCircle(intersections[k].getX(),
                        intersections[k].getY(), 1, Color.BLUE);
            }
        }
    }
}
