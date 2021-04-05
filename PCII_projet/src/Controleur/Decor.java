package Controleur;

import Vue.Affichage;

/**
 * Classe décor qui s'occupe de mettre a jour les positions des étoiles dans le décor
 * @author Fedy
 *
 */

public class Decor extends Thread {
	private int time_to_sleep = 200;
	private Affichage panel;

	public Decor(Affichage p) {
		super("Decor");
		panel = p;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				panel.updateStars();  
				Thread.sleep(time_to_sleep);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
