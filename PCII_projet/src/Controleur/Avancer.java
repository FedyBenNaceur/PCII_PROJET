package Controleur;

import Vue.Affichage;
import Vue.Affichage.STATE;

public class Avancer extends Thread {
	private int time_to_sleep = 50;
	private Affichage panel;

	public Avancer(Affichage p) {
		super("Avancer");
		panel = p;
	}

	@Override
	public void run() {
		try {
			while (!panel.vehicule.checkEndGame()||panel.vehicule.starting) {
				if (panel.state == STATE.GAME) {
					if (panel.vehicule.upSpeed < 50) {
						if (panel.vehicule.speed + panel.vehicule.acceleration <= panel.vehicule.maxSpeed) {
							panel.vehicule.speed += panel.vehicule.acceleration;
						} else {
							panel.vehicule.speed = panel.vehicule.maxSpeed;
						}
					}
					panel.vehicule.distance += panel.vehicule.speed;
					panel.vehicule.checkCross();
				}
				panel.repaint();
				Thread.sleep(time_to_sleep);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
