package Controleur;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Model.Car;
import Vue.Affichage.STATE;

/**
 * Classe KeyController qui permet de traiter les "inputs" du clavier
 * 
 * @author Fedy
 *
 */
public class KeyController extends KeyAdapter implements ActionListener {
	private Car c;

	/*
	 * Constructeur de la classe KeyControlleur
	 */
	public KeyController(Car c) {
		this.c = c;
	}

	/*
	 * actionPerformed Methode qui réagit au "input" du clavier notamment la mise a
	 * jour de la position du joueur et la vitesse
	 */
	public void actionPerformed(ActionEvent e) {
		// Si on est en train de jouer
		if (c.panel.state == STATE.GAME) {
			// teste si on a laché la flèche haut et met a jour la vitesse
			if (c.released) {
				if (c.upSpeed - c.upAcceleration < 0) {
					c.upSpeed = 0;
				} else {
					c.upSpeed -= c.upAcceleration;
				}
			}
			// teste si on est en train d'appuyer sur la flèche haut et met a jour la
			// hauteur du joueur
			if (c.isUp)
				if (c.upSpeed + c.upAcceleration <= c.maxUpSpeed) {
					c.upSpeed += c.upAcceleration;
				}
			// teste si on appuie sur la flèche droite ou gauche et met à jour la position
			// du joueur sur la route
			if (c.isLeft) {
				c.position -= c.speed / 10000;
			}
			if (c.isRight) {
				c.position += c.speed / 10000;
			}
			// Teste si on appuie sur la flèches bas et freine le véhicule
			if (c.isDown && c.speed > 0 || c.upSpeed != 0) {
				if (c.speed - c.acceleration < 0) {
					c.speed = 0;
				} else if (c.upSpeed != 0) {
					c.speed -= c.decceleration;
				} else {
					c.speed -= c.acceleration;
				}
			}

			// Si on a commencé de joueur on déclenche le chronomètre
			if (!c.start) {
				c.startChrono = System.currentTimeMillis() / 1000;
				c.start = true;
				c.remainingTime = c.estimatedCrossTime;
				c.lastLapTime = 0;
			}
			// On met a jour la vitesse et la distance parcourue
			c.distance += c.speed;
			c.travelDistance += c.speed;
			c.score += c.speed;

			// On permet a l'utilisateur de prendre de la vitesse au début du jeu
			if (c.score >= 300) {
				c.starting = false;
			}

			c.panel.repaint();
		}
	}

	/*keyPressed 
	 * Methode qui permet de preciser quelle touche a été utiliser
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP) {
			c.released = false;
		}
		update(code, true);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP) {
			c.released = true;
		}
		update(code, false);

	}
    
	/**
	 * Update 
	 * Methode qui permet de mettre à jour les variables de la classe vehicule.
	 * @param code : le code de la touche 
	 * @param press : Vaut vrai si la touche a été enfoncée faux si on a relaché 
	 */
	private void update(int code, boolean press) {
		if (code == KeyEvent.VK_UP)
			c.isUp = press;
		else if (code == KeyEvent.VK_LEFT)
			c.isLeft = press;
		else if (code == KeyEvent.VK_RIGHT)
			c.isRight = press;
		else if (code == KeyEvent.VK_DOWN)
			c.isDown = press;
		else if (code == KeyEvent.VK_P)
			c.panel.state = STATE.MENU;
	}
}
