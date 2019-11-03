package racer.block;

import racer.graphics.Screen;
import racer.graphics.Sprite;

/**
 * Create and render green block based on Block class.
 */
public class GreenBlock extends Block {

    /**
     * Construct green block
     *
     * @param sprite
     * @param size   Amount of pixels.
     */
    public GreenBlock(Sprite sprite, int size) {
        super(sprite, size);
    }

    /**
     * Rendering of green block
     *
     * @param x      pixel position of block.
     * @param y      pixel position of block.
     * @param screen Screen object to draw block on.
     */
    public void render(int x, int y, Screen screen) {
        screen.renderBlock(x, y, this);
    }
}
