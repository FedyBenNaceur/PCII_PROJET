package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Model.Car;
import Vue.Affichage.STATE;

public class KeyController extends KeyAdapter implements ActionListener {
	private Car c;

	public KeyController(Car c) {
		this.c = c;
	}

	public void actionPerformed(ActionEvent e) {
		if (c.panel.state == STATE.GAME) {
			if (c.released) {
				if (c.upSpeed - c.upAcceleration < 0) {
					c.upSpeed = 0;
				} else {
					c.upSpeed -= c.upAcceleration;
				}
			}
			if (c.isUp)
				if (c.upSpeed + c.upAcceleration <= c.maxUpSpeed) {
					c.upSpeed += c.upAcceleration;
				}
			if (c.isLeft) {
				c.position -= c.speed / 10000;
			}
			if (c.isRight) {
				c.position += c.speed / 10000;
			}
			if (c.isDown && c.speed > 0 || c.upSpeed != 0) {
				if (c.speed - c.acceleration < 0) {
					c.speed = 0;
				} else if (c.upSpeed != 0) {
					c.speed -= c.decceleration;
				} else {
					c.speed -= c.acceleration;
				}
			}

			if (!c.start) {
				c.startChrono = System.currentTimeMillis() / 1000;
				c.start = true;
				c.remainingTime = c.estimatedCrossTime;
				c.lastLapTime = 0;
			}
			c.distance += c.speed;
			c.travelDistance += c.speed;
			c.score += c.speed;
			
			if (c.score >= 300) {
				c.starting = false ;
			}

			c.panel.repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP) {
			c.released = false;
		}
		update(code, true);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP) {
			c.released = true;
		}
		update(code, false);

	}

	private void update(int code, boolean press) {
		if (code == KeyEvent.VK_UP)
			c.isUp = press;
		else if (code == KeyEvent.VK_LEFT)
			c.isLeft = press;
		else if (code == KeyEvent.VK_RIGHT)
			c.isRight = press;
		else if (code == KeyEvent.VK_DOWN)
			c.isDown = press;
		else if (code == KeyEvent.VK_P)
			c.panel.state = STATE.MENU ;  
	}
}
