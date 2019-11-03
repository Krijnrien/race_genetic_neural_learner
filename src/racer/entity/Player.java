package racer.entity;

import racer.graphics.Screen;
import racer.graphics.Sprite;
import racer.map.RaceMap;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Car object
 */
public class Player extends Mobile {

	private int keyPressedCode;
	private boolean keyPressed;
	private double leftForce, rightForce;

	private static final float MAX_ROTATION_PER_SECOND = 30.0f / 180;

	/**
	 * Direction car is facing.
	 */
	private double angle;

	/**
	 * Construct car with position, angle and raceMap to render on.
	 *
	 * @param x         Screen X int position.
	 * @param y         Screen Y int position.
	 * @param angle     Direction car faces.
	 * @param _Race_map To render car on.
	 */
	Player(int x, int y, double angle, RaceMap _Race_map) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		raceMap = _Race_map;
	}


	/**
	 * Update car.
	 */
	void update(JFrame screen) {
		// if (!this.collided) {
		screen.requestFocus();
		screen.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				keyPressed = true;
				keyPressedCode = e.getKeyCode();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				keyPressed = false;
			}
		});

		if(keyPressed) {
			if(keyPressedCode == 37) {
				angle -= (30.0f / 180) * 0.5;
			} else if(keyPressedCode == 39) {
				angle += (30.0f / 180) * 0.5;
			}

		}
		double movingX = x + Math.sin(angle) * 2;
		double movingY = y + Math.cos(angle) * 2;
		//System.out.println(angle + "-" + movingY);
		move((int) movingX, (int) movingY);



		//translateByRadius(0.05);
	}

	private void translateByVector(Vector v) {
		this.x = (int) (x + v.getX());
		this.y = (int) (y + v.getY());
	}

	private void translateByRadius(double d) {
		Vector v = new Vector(d, angle / 180.0 * Math.PI, Vector.Polar);
		translateByVector(v);
	}

	/**
	 * Rendering car on screen
	 *
	 * @param _screen To render car on.
	 */
	public void render(Screen _screen) {
		_screen.renderCar(x, y, angle, Sprite.carSprite);
		// Render the car

	}
}
