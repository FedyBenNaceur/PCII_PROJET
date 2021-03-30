
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/** Classe qui represente le vehicule */
public class Car extends KeyAdapter implements ActionListener {
	public float position = 0.0f;// position du vehicule
	public Affichage panel;// L'affichage
	public float distance = 0f;
	public float speed = 0f;
	private final float acceleration = 0.5f;
	private final float maxSpeed = 25f;
	public boolean isUp, isLeft, isRight, isDown;
	public boolean released = true;
	public float score = 0;
	public boolean start = false;
	public long startChrono;
	public long lastLapTime ;
	public boolean crossed = true ; 

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	void initPanel(Affichage a) {
		panel = a;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (released) {
			if (speed - acceleration < 0) {
				speed = 0;
			} else {
				speed -= acceleration;
			}
			distance += speed;
		}
		if (isUp)
			if (speed <= maxSpeed) {
				speed += acceleration;
			}
		if (!start) {
			startChrono = System.currentTimeMillis()/1000;
			start = true;
			lastLapTime = 0 ;
		}
		distance += speed;
		score += speed;
		if (isLeft) {
			position -= speed / 1000;
		}
		if (isRight) {
			position += speed / 1000;
		}
		if (isDown && speed > 0) {
			if (speed - acceleration < 0) {
				speed = 0;
			} else {
				speed -= acceleration;
			}
		}

	

		panel.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		released = false;
		int code = e.getKeyCode();
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
		if ((panel.road.tracksection-1)== 0 && !crossed) {
			lastLapTime = (System.currentTimeMillis()/1000) - startChrono;
			startChrono = System.currentTimeMillis()/1000;
			crossed = true ; 
		}else if((panel.road.tracksection-1) != 0) {
			crossed = false ;
		}
	}

}
