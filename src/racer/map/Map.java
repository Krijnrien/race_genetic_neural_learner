package racer.map;

import racer.graphics.Screen;
import racer.graphics.Sprite;
import racer.block.Block;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Map for racer. Loading (image and blocks), updating and rendering of racer.map for racer.
 */
public class Map {
    /**
     * Width and height of screen
     */
    private int width, height;

    /**
     * List of pixels from width * height
     */
    private int[] pixels;

    /**
     * Pass string to racer.map loading method
     *
     * @param path relative directory path to racer.map image.
     */
    public Map(String path) {
        // Passing to method for coding functionality naming clarity
        loadMap(path);
    }

    /**
     * Load racer.map
     *
     * @param path relative directory path to racer.map image.
     */
    private void loadMap(String path) {
        try {
            // Load image from given path
            BufferedImage image = ImageIO.read(Map.class.getResource(path));
            // Get image width, also used later for rendering.
            width = image.getWidth();
            // Get image height, also used later for rendering.
            height = image.getHeight();
            // Get pixel list of width times height.
            pixels = new int[width * height];
            // Get all RGB values of image into pixels list.
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            //TODO Handle error, display error as pop-up & recover.
            e.printStackTrace();
        }
    }

    /**
     * Rendering racer.map from image to screen. Converts block position to screen pixels and reverse.
     * Spooky magic method that applies magic to numbers I copied.
     *
     * @param xScroll: the xOffset of screen
     * @param yScroll: the yOffset of screen
     */
    public void render(int xScroll, int yScroll, Screen screen) {
        screen.setOffset(xScroll, yScroll);
        // Converting coordinates into pixels it has to display on canvas. Scrollable maps should only display the visible pixels.
        int xMostLeft = xScroll >> Sprite.SIZE_2BASED;
        int xMostRight = (xScroll + screen.getWidth() + Sprite.SIZE) >> Sprite.SIZE_2BASED;
        int yMostTop = yScroll >> Sprite.SIZE_2BASED;
        int yMostBottom = (yScroll + screen.getHeight() + Sprite.SIZE) >> Sprite.SIZE_2BASED;
        for (int y = yMostTop; y < yMostBottom; y++) {
            for (int x = xMostLeft; x < xMostRight; x++) {
                if (x < 0 || y < 0 || x >= width || y >= height) {
                    Block.trackBlock.render(x << Sprite.SIZE_2BASED, y << Sprite.SIZE_2BASED, screen);
                    // Draw racer.map only once, so continue.
                    continue;
                }
                getBlock(x, y).render(x << Sprite.SIZE_2BASED, y << Sprite.SIZE_2BASED, screen);
            }
        }
    }

    /**
     * @param x: xCoordinate
     * @param y: yCoordinate
     */
    private Block getBlock(int x, int y) {
        int index = x + y * width;
        if (index >= 0 && index < pixels.length) {
            switch (pixels[index]) {
                case Block.greenColor:
                    // Return a green block as found color is green (defined in block class).
                    return Block.greenBlock;
                case Block.trackColor:
                    // Return a wall block as found color is "wall" (defined in block class).
                    return Block.wallBlock;
            }
        }
        return Block.trackBlock;
    }
}
