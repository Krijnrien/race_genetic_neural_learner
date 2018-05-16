package racer.entity;

import racer.map.RaceMap;
import racer.graphics.Screen;

/**
 * Object property for all entities.
 */
public abstract class Entity {
    /**
     * Screen position of entity
     */
    protected int x, y;

    /**
     * RaceMap to render entity on.
     */
    protected RaceMap raceMap;

    /**
     * Updating entity
     */
    public void update() {
    }

    /**
     * Rendering entity on screen
     *
     * @param screen To draw entity on.
     */
    public void render(Screen screen) {
    }

    /**
     * @return Entity Y position on screen.
     */
    public int getY() {
        return y;
    }

    /**
     * @param y Entity Y position on screen.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return Entity X position on screen.
     */
    public int getX() {
        return x;
    }

    /**
     * @param x Entity X position on screen.
     */
    public void setX(int x) {
        this.x = x;
    }
}
