package Controleur;

import Vue.Affichage;

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
