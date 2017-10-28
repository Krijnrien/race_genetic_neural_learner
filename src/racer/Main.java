package racer;

import racer.graphics.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * RaceLearner starting point.
 * Program set off by main, initializing canvas in screen.
 * Start runnable with infinite loop of rendering screen, canvas, by pixels.
 */
public class Main extends Canvas implements Runnable {
    /**
     * Width of screen
     */
    private static int width = 1200;

    /**
     * Height of screen of 9/16 aspect ratio depending on width size.
     */
    private static int height = width * 9 / 16;

    /**
     * Pre-defining due to main non-static call from constructor.
     */
    private Thread thread;

    /**
     * Pre-defining due to main non-static call from constructor.
     */
    private JFrame frame;

    /**
     * Sets runnable infinite while loop updating the screen.
     */
    private boolean running = false;

    /**
     * Pre-defining due to main non-static call from constructor.
     */
    private Screen screen;


    /**
     * Setup Canvas GUI
     *
     * @param args none
     */
    public static void main(String[] args) {
        // Initializing on constructor as non-static methods cannot be called from static main.
        Main car = new Main();
        // Setting title of window
        car.frame.setTitle("Race learner");
        car.frame.add(car);
        // Resize to preferred size to fit components
        car.frame.pack();
        // Set default close action, closing on exit.
        car.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Set frame to middle of screen
        car.frame.setLocationRelativeTo(null);
        // Set frame visible
        car.frame.setVisible(true);
        car.start();
    }

    /**
     * Using constructor to call non-static methods
     */
    private Main() {
        // Define size of screen
        Dimension size = new Dimension(width, height);
        // Setting preferred size of screen
        setPreferredSize(size);
        // Initializing screen with set width and height
        screen = new Screen(width, height);
        // Setting frame as new JFrame
        frame = new JFrame();
    }

    /**
     * Loop runnable and set ups and frames as title
     */
    @Override
    public void run() {
        // Set focus to screen as program starts running
        requestFocus();
        // Infinite loop to continuously update screen. Main program functionality starts here.
        while (running) {
            render();
        }
        // If running is set elsewhere to false, call stop to join threads gracefully.
        stop();
    }

    /**
     * Updating screen, canvas, from previous pixel set and drawing over it with new elements
     */
    private void render() {
        // Set canvas buffer
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }
        // Set graphics as 2D
        Graphics2D graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
        // Set graphics2D as graphic on screen
        screen.setGraphic(graphics2D);

        // Pre-define BufferedImage with width and height of screen and set RGB as type.
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // Integer list of pixels
        int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        // Copy previous pixel set
        System.arraycopy(screen.getPixels(), 0, pixels, 0, pixels.length);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        screen.dispose();
        bufferStrategy.show();
    }

    /**
     * Create new thread and send Main (this) as parameter. Set running as true for infinite runnable while loop.
     */
    private synchronized void start() {
        // Set new thread of application.
        thread = new Thread(this, "Race learner");
        // Start runnable inifinite while loop updating screen.
        running = true;
        // Start newly set thread.
        thread.start();
    }

    /**
     * Set running boolean false to stop infinite runnable while loop.
     */
    private synchronized void stop() {
        // Stop runnable while loop by setting running boolean to false.
        running = false;
        try {
            // Join threads for safe stop
            thread.join();
        } catch (InterruptedException e) {
            //TODO Handle error
            // No need for proper error handling YET as stopping the loop means stopping the program (for now)
            e.printStackTrace();
        }
    }
}
