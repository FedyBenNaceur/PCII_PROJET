package Vue;

import javax.swing.Timer;

import Controleur.Avancer;
import Controleur.KeyController;
import Model.Car;
import Model.Road;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		Road r = new Road();
		Car c = new Car();
		Affichage panel = new Affichage(r,c);
		r.initialisePanel(panel);
		c.initPanel(panel);
		KeyController kc = new KeyController(c);
		Timer timer = new Timer(30, kc);
	    timer.setInitialDelay(0);
	    timer.start();   
		(new Avancer(panel)).start();
		JFrame fenetre = new JFrame("jeu de course");
		fenetre.setFocusTraversalKeysEnabled(false);
		fenetre.setFocusable(true);
		fenetre.setLocationRelativeTo(null);
	    fenetre.setLocation(0, 0);
		fenetre.addKeyListener(kc);
		fenetre.add(panel);
		fenetre.pack();
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.pack();
		fenetre.setResizable(false);
	}
}
