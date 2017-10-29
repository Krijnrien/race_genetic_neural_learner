package racer.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Loading sprite sheet from local relative png.
 */
public class SpriteSheet {
    /**
     * path to image sprite sheet file.
     */
    private String path;

    /**
     * Size of sprite sheet.
     */
    private final int size;

    /**
     * Integer list of all pixels in sprite sheet.
     */
    int[] pixels;


    /**
     * Load sprite sheet from file path.
     */
    static SpriteSheet spriteSheet = new SpriteSheet("/blocks/sprites.png", 256);


    /**
     * Construct sprite sheet from path and size.
     *
     * @param path Directory to sheet file.
     * @param size Integer size of sheet file.
     */
    private SpriteSheet(String path, int size) {
        this.path = path;
        this.size = size;
        pixels = new int[size * size];
        load();
    }

    /**
     * Load images to pixel list.
     */
    private void load() {
        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);
        } catch (IOException e) {
            //TODO Handle error
            e.printStackTrace();
        }
    }

    /**
     * Size property of sheet.
     *
     * @return size of sheet.
     */
    public int getSize() {
        return size;
    }
}
