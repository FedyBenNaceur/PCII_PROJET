package Controleur;

/**
 * Classe Avancer : classe qui g�re la route ainsi que l'�tat du jeu et de la vitesse du vehicule 
 */
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
			
			//On teste le jeu n'est pas deja perdu ou bien qu'il commence pour pouvoir mettre a jour les composantes 
			while (!panel.vehicule.checkEndGame()||panel.vehicule.starting) {
				//Si le joueur n'est pas d�ja dans le menu
				if (panel.state == STATE.GAME) {
					//Si le joueur d�passe un certaine hauteur on diminue la vitesse
					if (panel.vehicule.upSpeed < 50) {
						//teste si on depasse pas la vitesse maximale 
						if (panel.vehicule.speed + panel.vehicule.acceleration <= panel.vehicule.maxSpeed) {
							panel.vehicule.speed += panel.vehicule.acceleration;
						} else {
							panel.vehicule.speed = panel.vehicule.maxSpeed;
						}
					}
					//On met � jour la vitesse et on verifie si on a effectu� un tour 
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
