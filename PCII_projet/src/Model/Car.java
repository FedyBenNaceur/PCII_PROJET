package Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import Vue.Affichage;

/** Classe qui represente le vehicule */
public class Car extends KeyAdapter implements ActionListener {
	public float position = 0.0f;// position du vehicule
	public Affichage panel;// L'affichage
	public float distance = 0f;
	public float speed = 0f;
	public float maxUpSpeed = 246f;
	public float upSpeed = 0f;
	public float upAcceleration = 5f;
	public final float acceleration = 5f;
	public final float decceleration = 1f ;
	public final float maxSpeed = 200f;
	public boolean isUp, isLeft, isRight, isDown;
	public boolean released = true;
	public float score = 0;
	public boolean start = false;
	public long startChrono;
	public long lastLapTime;
	public boolean crossed = true;
	public float travelDistance = 0;

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void initPanel(Affichage a) {
		panel = a;
	}

	public void actionPerformed(ActionEvent e) {

		if (released) {
			if (upSpeed - upAcceleration < 0) {
				upSpeed = 0;
			} else {
				upSpeed -= upAcceleration;
			}
		}
		if (isUp)
			if (upSpeed + upAcceleration <= maxUpSpeed) {
				upSpeed += upAcceleration;
			}
		if (isLeft) {
			position -= speed / 10000;
		}
		if (isRight) {
			position += speed / 10000;
		}
		if (isDown && speed > 0 || upSpeed!=0) {
			if (speed - decceleration < 0) {
				speed = 0;
			} else {
				speed -= decceleration;
			}
		}


		if (!start) {
			startChrono = System.currentTimeMillis() / 1000;
			start = true;
			lastLapTime = 0;
		}
		distance += speed;
		travelDistance += speed;
		score += speed;

		panel.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP) {
			released = false;
		}
		update(code, true);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP) {
			released = true;
		}
		update(code, false);

	}

	private void update(int code, boolean press) {
		if (code == KeyEvent.VK_UP)
			isUp = press;
		else if (code == KeyEvent.VK_LEFT)
			isLeft = press;
		else if (code == KeyEvent.VK_RIGHT)
			isRight = press;
		else if (code == KeyEvent.VK_DOWN)
			isDown = press;
	}

	public void checkCross() {
		if ((panel.road.tracksection - 1) == 0 && !crossed) {
			lastLapTime = (System.currentTimeMillis() / 1000) - startChrono;
			startChrono = System.currentTimeMillis() / 1000;
			crossed = true;
		} else if ((panel.road.tracksection - 1) != 0) {
			crossed = false;
		}
	}

	public boolean checkOffRoad() {
		int carPos = (int) ((panel.WIDTH / 2) + (panel.WIDTH * position / 2.f) - (256 / 2));

		int currentPos = (int) (score / panel.road.segLength);
		double maxY = panel.HEIGHT;
		for (int n = 0; n < panel.drawDistance; n++) {
			Line l = panel.road.lines.get((n + currentPos) % panel.road.sizeR);
			Line p = null;
			if (l.Y > 0 && l.Y < maxY) {
				p = panel.road.lines.get(((n + currentPos) - 1) % panel.road.sizeR);
			} else {
				p = l;
			}

			// System.out.println(carPos+"|||"+(p.X-(p.W * 1.4)));
			if (carPos <= p.X - (p.W * 1.4)) {
				return false;
			}

		}
		return true;
	}

}
