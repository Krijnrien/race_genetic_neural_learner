package racer.graphics;

/**
 * Individual sprites from sheet. Sizes are fixed to 16 pixels.
 */
public class Sprite {
    /**
     * Sprite default set size in pixels.
     */
    public static final int SIZE = 16;

    /**
     * Sprite size in binary. 2^4
     */
    public static final int SIZE_2BASED = 4;

    /**
     * Position of sprite in sheet.
     */
    private int x, y;

    /**
     * List sprite pixels.
     */
    private int[] pixels;

    /**
     * Predefine sprite sheet.
     */
    private SpriteSheet spriteSheet;

    /**
     * Pre-loading each type of sprite
     */
    public static Sprite greenSprite = new Sprite(0, 0, SpriteSheet.spriteSheet);
    public static Sprite wallSprite = new Sprite(2, 0, SpriteSheet.spriteSheet);
    public static Sprite trackSprite = new Sprite(0xE6FFA3);

    /**
     * Construct sprite from X & Y position on sheet.
     *
     * @param x           pixels to start defining a sprite.
     * @param y           pixels to start defining a sprite.
     * @param spriteSheet sheet to draw sprites from.
     */
    private Sprite(int x, int y, SpriteSheet spriteSheet) {
        pixels = new int[SIZE * SIZE];
        this.x = x * SIZE;
        this.y = y * SIZE;
        this.spriteSheet = spriteSheet;
        load();
    }

    /**
     * Construct new sprite just by colour
     *
     * @param colour Int to colour fill sprite.
     */
    private Sprite(int colour) {
        pixels = new int[SIZE * SIZE];
        setColour(colour);
    }


    /**
     * Setting colour for sprite pixels list.
     *
     * @param colour Int to set pixel colour.
     */
    private void setColour(int colour) {
        for (int i = 0; i < SIZE * SIZE; i++) {
            pixels[i] = colour;
        }

    }

    /**
     * Load sheet into pixels int list.
     */
    private void load() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                pixels[j + i * SIZE] = spriteSheet.pixels[(j + this.x) + (i + this.y) * spriteSheet.getSize()];
            }
        }
    }

    /**
     * @return list of pixels.
     */
    int[] getPixels() {
        return pixels;
    }

}
