package racer.block;

import racer.graphics.Screen;
import racer.graphics.Sprite;

/**
 * Create and render wall block based on Block class.
 */
public class WallBlock extends Block {

    public WallBlock(Sprite sprite, int size) {
        super(sprite, size);
    }

    /**
     * Construct track block
     *
     * @param x      pixel position of block.
     * @param y      pixel position of block.
     * @param screen Screen object to draw block on.
     */
    public void render(int x, int y, Screen screen) {
        screen.renderBlock(x, y, this);
    }

    /**
     * Determine whether block is solid or not for collision detection.
     *
     * @return Block solid (has collision) or not by boolean.
     */
    public boolean solid() {
        return true;
    }
}
