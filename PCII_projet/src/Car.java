
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
	public float speed = 0f;
	private final float acceleration = 0.5f ; 
	private final float maxSpeed = 10f ; 
	private boolean isUp, isLeft, isRight;
	private boolean stopped = true ; 
	private boolean released = true ;
 

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	void initPanel(Affichage a) {
		panel = a;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if (!released && speed<=maxSpeed) {
			speed += acceleration;
		}else if(released && speed-acceleration>=0){
			speed -= acceleration ;
		}		
		if (isUp||stopped)
			distance += speed;
		if (isLeft)
			position -= speed /50;
		if (isRight)
			position += speed / 50;
		panel.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
        released  = false ; 
        stopped = false ; 
		int code = e.getKeyCode();
		update(code,true);
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		stopped = true ;
		released = true ;
		int code = e.getKeyCode();		
		update(code,false);

	}

	
	
    private void update(int code, boolean press) {
        if (code == KeyEvent.VK_UP) isUp = press;
        else if (code == KeyEvent.VK_LEFT) isLeft = press;
        else if (code == KeyEvent.VK_RIGHT) isRight = press;
    }
}


