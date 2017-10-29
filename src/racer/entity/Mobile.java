package racer.entity;

import racer.entity.Entity;

/**
 * Mobility functionality for car as extend class.
 */
public abstract class Mobile extends Entity {
    /**
     * If entity collided or not.
     */
    boolean collided = false;


    /**
     * Move entity to given X and Y position on screen.
     *
     * @param _xPosition Screen int X position
     * @param _yPosition Screen int Y position
     */
    void move(int _xPosition, int _yPosition) {
        if (_xPosition != 0 && _yPosition != 0) {
            move(_xPosition, 0);
            move(0, _yPosition);
            return;
        }

        // If moving entity won't not collide then update entity position.
        if (!isCollided(_xPosition, _yPosition)) {
            x += _xPosition;
            y += _yPosition;
        }
    }

    /**
     * Update entity.
     */
    public void update() {

    }

    /**
     * Check if entity collides.
     *
     * @param xPosition Screen X int position.
     * @param yPosition Screen Y int position.
     * @return Boolean if entity collided or not.
     */
    private boolean isCollided(int xPosition, int yPosition) {
        for (int corner = 0; corner < 4; corner++) {
            int xt = ((x + xPosition) + (corner % 2) * 7 + 5) >> 4;
            int yt = ((y + yPosition) + (corner / 2) * 12 + 3) >> 4;
            if (map.getBlock(xt, yt).solid()) {
                collided = true;
            }
        }
        return collided;
    }

}
