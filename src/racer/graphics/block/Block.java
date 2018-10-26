package racer.graphics.block;

import racer.graphics.Screen;
import racer.graphics.Sprite;

/**
 * Parent class of all blocks
 */
public class Block {
    /**
     * Property sprite to hold sprite
     */
    public Sprite sprite;

    /**
     * Property size to set sprite size.
     */
    public int size;

    /**
     * Green block from sprite sheet
     */
    public static Block greenBlock = new GreenBlock(Sprite.greenSprite, 16);

    /**
     * Wall block from sprite sheet, used as collision to create walls
     */
    public static Block wallBlock = new WallBlock(Sprite.wallSprite, 16);

    /**
     * Empty block as road
     */
    public static Block trackBlock = new TrackBlock(Sprite.trackSprite, 16);

    /**
     * Defining greenColor with colour value to determine greenBlock
     */
    public static final int greenColor = 0xff00ff00;

    /**
     * Defining trackColor with colour value to determine trackBlock
     */
    public static final int trackColor = 0xffFFD800;

    /**
     * Constructor to set sprite and size as object properties.
     *
     * @param sprite
     * @param size
     */
    Block(Sprite sprite, int size) {
        this.sprite = sprite;
        this.size = size;
    }

    /**
     * Rendering of block
     *
     * @param x      pixel position of block.
     * @param y      pixel position of block.
     * @param screen Screen object to draw block on.
     */
    public void render(int x, int y, Screen screen) {

    }

    /**
     * Determine whether block is solid or not for collision detection. Set false by default, override to set for collision.
     *
     * @return Block solid (has collision) or not by boolean.
     */
    public boolean solid() {
        return false;
    }
}
