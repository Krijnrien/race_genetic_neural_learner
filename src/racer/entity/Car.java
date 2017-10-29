package racer.entity;

import racer.map.Map;
import racer.graphics.Screen;
import racer.graphics.Sprite;

import java.awt.geom.Rectangle2D;

/**
 * Car object
 */
public class Car extends Mobile {
    /**
     * Direction car is facing.
     */
    private double angle;

    /**
     * Construct car with position, angle and map to render on.
     *
     * @param x     Screen X int position.
     * @param y     Screen Y int position.
     * @param angle Direction car faces.
     * @param _map  To render car on.
     */
    public Car(int x, int y, double angle, Map _map) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        map = _map;
    }

    /**
     * Update car.
     */
    public void update() {
    }

    /**
     * Getter "physical" boundaries of car.
     *
     * @return Rectangle shape the size of the car.
     */
    public Rectangle2D getCarBound() {
        return new Rectangle2D.Double(x - Sprite.SIZE / 2, y - Sprite.SIZE / 2, Sprite.SIZE, Sprite.SIZE);
    }

    /**
     * Rendering car on screen
     *
     * @param _screen To render car on.
     */
    public void render(Screen _screen) {
        _screen.renderCar(x, y, angle, Sprite.carSprite);
    }
}
