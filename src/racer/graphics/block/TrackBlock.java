package racer.graphics.block;

import racer.graphics.Screen;
import racer.graphics.Sprite;

/**
 * Create and render track block based on Block class.
 */
public class TrackBlock extends Block {

    /**
     * Construct track block
     *
     * @param sprite
     * @param size   Amount of pixels.
     */
    public TrackBlock(Sprite sprite, int size) {
        super(sprite, size);
    }

    /**
     * Rendering of track block
     *
     * @param x      pixel position of block.
     * @param y      pixel position of block.
     * @param screen Screen object to draw block on.
     */
    public void render(int x, int y, Screen screen) {
        screen.renderBlock(x, y, this);
    }
}
