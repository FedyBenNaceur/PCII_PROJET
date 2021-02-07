import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**Classe qui represente le vehicule*/
public class Car implements KeyListener {
	public float position = 0f;//position du vehicule
	public Affichage panel;//L'affichage

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	void initPanel(Affichage a) {
		panel = a;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {

		case KeyEvent.VK_LEFT:
			position -= 0.05;
			break;

		case KeyEvent.VK_RIGHT:
			position += 0.05;

			break;
		}
		panel.repaint();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
