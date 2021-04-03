
import javax.swing.Timer;
import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		Road r = new Road();
		Car c = new Car();
		Affichage panel = new Affichage(r,c);
		r.initialisePanel(panel);
		c.initPanel(panel);
		Timer timer = new Timer(30, c);
	    timer.setInitialDelay(0);
	    timer.start();   
		(new Avancer(panel)).start();
		JFrame fenetre = new JFrame("jeu de course");
		fenetre.setFocusTraversalKeysEnabled(false);
		fenetre.setFocusable(true);
		fenetre.setLocationRelativeTo(null);
	    fenetre.setLocation(0, 0);
		fenetre.addKeyListener(c);
		fenetre.add(panel);
		fenetre.pack();
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.pack();
		fenetre.setResizable(false);
	}
}
