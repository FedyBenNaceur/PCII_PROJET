
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/** Classe qui represente le vehicule */
public class Car   extends KeyAdapter implements ActionListener {
	public float position = 0.0f;// position du vehicule
	public Affichage panel;// L'affichage
	public float distance = 0f;
	private final float speed = 10f;
	private boolean isUp, isLeft, isRight;
	
	/*public Car () {
		isUp =false ;
		isLeft = false ; 
		isRight = false;
	}*/

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	void initPanel(Affichage a) {
		panel = a;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if (isUp)
			distance += speed;
		if (isLeft)
			position -= speed /50;
		if (isRight)
			position += speed / 50;
		

		panel.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();
	
		update(code,true);
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();		
		update(code,false);

	}

	
	
    private void update(int code, boolean press) {
        if (code == KeyEvent.VK_UP) isUp = press;
        else if (code == KeyEvent.VK_LEFT) isLeft = press;
        else if (code == KeyEvent.VK_RIGHT) isRight = press;
    }
}


