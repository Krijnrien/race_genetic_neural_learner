package racer.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import racer.block.Block;

/**
 * Rendering all graphics2D to screen.
 */
public class Screen {
    /**
     * Width of screen in pixels
     */
    private int width;

    /**
     * Height of screen in pixels
     */
    private int height;

    /**
     * Integer list of all pixels to render.
     */
    private int[] pixels;

    /**
     * Offset of screen of pixel amount exceeding screen size.
     */
    private int xOffset, yOffset;

    /**
     * Graphics to render.
     */
    private Graphics2D graphics2D;


    /**
     * Constructor setting height and width of screen.
     *
     * @param _width
     * @param _height
     */
    public Screen(int _width, int _height) {
        width = _width;
        height = _height;
        pixels = new int[_width * _height];
    }

    public int[] getPixels() {
        return pixels;
    }

    /**
     * This function renders single Block on screen
     *
     * @param _xPosition x coordinate of Block on screen.
     * @param _yPosition y coordinate of Block on screen.
     * @param _block     the block to be rendered.
     */
    public void renderBlock(int _xPosition, int _yPosition, Block _block) {
        // Subtract block position by screen offset when screen moves
        _xPosition -= xOffset;
        _yPosition -= yOffset;
        for (int yTile = 0; yTile < _block.size; yTile++) {
            int yScreen = yTile + _yPosition;
            for (int xTile = 0; xTile < _block.size; xTile++) {
                int xScreen = xTile + _xPosition;
                // Don't render blocks outside of the screen based on xScreen and yScreen boundaries.
                if (xScreen < -_block.size || xScreen >= width || yScreen < 0 || yScreen >= height) {
                    break;
                }
                if (xScreen < 0) xScreen = 0;
                pixels[xScreen + yScreen * width] = _block.sprite.getPixels()[xTile + yTile * _block.size];
            }
        }
    }

    /**
     * Render line on screen.
     *
     * @param _x     Pixel position of screen.
     * @param _y     Pixel position of screen.
     * @param _x2    Pixel position of screen.
     * @param _y2    Pixel position of screen.
     * @param _color Type of Colour property to draw.
     */
    public void renderLine(double _x, double _y, double _x2, double _y2, Color _color) {
        graphics2D.setColor(_color);
        graphics2D.drawLine(((int) (_x - xOffset + Sprite.SIZE / 2)), ((int) (_y - yOffset + Sprite.SIZE / 2)), ((int) (_x2 - xOffset + Sprite.SIZE / 2)), ((int) (_y2 - yOffset + Sprite.SIZE / 2)));
    }

    /**
     * Render circle on screen
     *
     * @param _x     Pixel position of screen.
     * @param _y     Pixel position of screen.
     * @param _r     Pixel position of screen.
     * @param _color Type of Colour property to draw.
     */
    public void renderCircle(double _x, double _y, double _r, Color _color) {
        graphics2D.setColor(_color);
        graphics2D.drawOval((int) (_x - xOffset - _r + Sprite.SIZE / 2), (int) (_y - _r - yOffset + Sprite.SIZE / 2), (int) (2 * _r), (int) (2 * _r));
    }

    public void setGraphic(Graphics2D _graphics2D) {
        this.graphics2D = _graphics2D;
    }

    public void dispose() {
        graphics2D.dispose();
    }

    /**
     * Getter of screen height in pixels.
     *
     * @return int of height pixels.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setter of screen height in pixels by int.
     *
     * @param _height Amount of pixels for screen height.
     */
    public void setHeight(int _height) {
        this.height = _height;
    }

    /**
     * Getter of screen width in pixels.
     *
     * @return int of width pixels.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Setter of screen width in pixels by int.
     *
     * @param _width Amount of pixels for screen height.
     */
    public void setWidth(int _width) {
        this.width = _width;
    }

    /**
     * Get X & Y pixels offset of screen that's not being rendered.
     *
     * @param _xOffset amount of pixels in int.
     * @param _yOffset amount of pixels in int.
     */
    public void setOffset(int _xOffset, int _yOffset) {
        this.xOffset = _xOffset;
        this.yOffset = _yOffset;
    }

    /**
     * Get X pixels offset of screen that's not being rendered.
     *
     * @return amount of pixels in int.
     */
    public int getXOffset() {
        return xOffset;
    }

    /**
     * Get Y pixels offset of screen that's not being rendered.
     *
     * @return amount of pixels in int.
     */
    public int getYOffset() {
        return yOffset;
    }

}
