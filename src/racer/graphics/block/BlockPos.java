package racer.graphics.block;

import racer.graphics.Sprite;

/**
 * Converting block position to screen pixel coordinates
 */
public class BlockPos {

    /**
     * X and Y pixel position of block on screen.
     */
    private int x, y;

    /**
     * Construct object properties
     *
     * @param x position of block, not pixels.
     * @param y position of block, not pixels.
     */
    public BlockPos(int x, int y) {
        // Setting block position equal to block size pixels.
        int blockPixels = Sprite.SIZE;
        this.setX(x * blockPixels);
        this.setY(y * blockPixels);
    }

    /**
     * @return Y screen pixel position of block.
     */
    public int getY() {
        return y;
    }


    /**
     * @param y screen pixel position of block.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return X screen pixel position of block.
     */
    public int getX() {
        return x;
    }

    /**
     * @param x screen pixel position of block.
     */
    public void setX(int x) {
        this.x = x;
    }

}
