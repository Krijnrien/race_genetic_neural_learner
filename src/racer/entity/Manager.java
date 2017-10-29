package racer.entity;

import racer.map.Map;
import racer.block.BlockPos;
import racer.graphics.Screen;

import java.awt.geom.Point2D;

/**
 * Managing car and other entities.
 */
public class Manager {

    /**
     * Setting blockPos for car
     */
    private static BlockPos blockPos = new BlockPos(15, 35);

    /**
     * Set position for car to be placed.
     */
    private static Point2D position = new Point2D.Double(blockPos.getX() - 12, blockPos.getY() - 12);

    /**
     * Predefine car
     */
    private Car car;

    /**
     * Predefine map
     */
    private Map map;

    /**
     * Initialize map and car
     */
    public Manager() {
        // Load & instantiate new map.
        map = new Map("/map/sprites.png");
        // Initialize car with position and angle.
        car = new Car((int) position.getX(), (int) position.getY(), -Math.PI / 3, map);
    }

    /**
     * Method called from main runnable loop. Constantly update car.
     */
    public void update() {
        car.update();
    }

    /**
     * The map is manipulated by individual pixels.
     *
     * @param screen To render pixel on
     */
    public void renderByPixels(Screen screen) {
        // The car will actually stay at a fixed position, just only screen
        // move. That why we need to set offset for screen when the car "move"
        int xScroll = car.getX() - screen.getWidth() / 2;
        int yScroll = car.getY() - screen.getHeight() / 2;
        map.render(xScroll, yScroll, screen);
    }

    /**
     * Rendering car on screen
     *
     * @param screen To render graphics on
     */
    public void renderByGraphics(Screen screen) {
        car.render(screen);
    }
}
