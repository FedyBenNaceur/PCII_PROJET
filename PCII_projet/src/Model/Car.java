package Model;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import Vue.Affichage;
import Vue.Affichage.STATE;

/** Classe qui represente le vehicule */
public class Car {
	public float position = 0.0f;// position du vehicule sur la piste
	public Affichage panel;// L'affichage
	public float distance = 0f;// la distance parcourue par rapport au circuit
	public float speed = 0f;// la vitesse du vehicule
	public float maxUpSpeed = 246f;// la vitesse maximale en hauteur
	public float upSpeed = 0f;// la vitesse en hauteur
	public float upAcceleration = 5f;// l'acceleration en hauteur
	public final float acceleration = 5f;// acceleration sur la piste
	public final float decceleration = 0.01f;
	public float maxSpeed = 200f;// la vitesse maximale du vehicule
	public boolean isUp, isLeft, isRight, isDown;// les variables qui identifient quelles touches on été foncées
	public boolean released = true;// vaut faux si la fléche haut est enfoncée vrai sinon
	public float score = 0;// le score du joueur
	public boolean start = false;// teste si on a commencer a jouer ou pas
	public long startChrono;
	public long lastLapTime;// le temps du dernier tour
	public boolean crossed = true;// vrai si on est arrivé au checkpoint
	public float travelDistance = 0;
	public long estimatedCrossTime = 35;// le temps estimé pour chaque tour
	public long remainingTime;// le temps qui reste pour effectuer le tour s'il vau 0 la partie est perdue
	public boolean starting = true;

	/*
	 * Constructeur de la classe
	 */
	public void initPanel(Affichage a) {
		panel = a;
	}

	/**
	 * Procédure qui permet de verifier si est arrivé à un checkpoint
	 */
	public void checkCross() {
		// Si on a franchit le checkpoint on remet les chrono a 0
		if ((panel.road.tracksection - 1) == 0 && !crossed) {
			lastLapTime = (System.currentTimeMillis() / 1000) - startChrono;
			startChrono = System.currentTimeMillis() / 1000;
			remainingTime = estimatedCrossTime;
			// On diminue le temps d'un tour estimé à chaque tours
			if (estimatedCrossTime - 1 > 30) {
				estimatedCrossTime -= 1;
				maxSpeed += 5;
			}
			crossed = true;
			//Si on est pas encore arrivé a un checkpoint on met a jour le temps restant
		} else if ((panel.road.tracksection - 1) != 0) {
			crossed = false;
			remainingTime = estimatedCrossTime - ((System.currentTimeMillis() / 1000 - startChrono));
		}
	}
    
	/**
	 * Teste si le vehicule est en collision avec d'autres objet sur la piste
	 * @param x l'abscisse de l'objet
	 * @param y l'ordonné de l'objet
	 * @param height la hauteur de l'image de l'objet
	 * @param width  la largeur de l'image de l'objet
	 * @return vrai s'il ya un collision faux sinon
	 */
	public boolean checkCollision(int x, int y, int height, int width) {
		int carPos = (int) ((panel.WIDTH / 2) + (panel.WIDTH * panel.vehicule.position / 2.f) - (251 / 2));
		Rectangle r1 = new Rectangle(x, y, height, width);
		Rectangle r2 = new Rectangle(carPos, (int) (panel.HEIGHT - 136 - panel.vehicule.upSpeed), 251, 136);
		//On teste si les images du vehicule et de l'objet s'intersecte ou pas
		if (r1.intersects(r2)) {
			//Si on detecte une collision on pénalise le joueur 
			if (speed - 30 > 0) {
				speed -= 50;
			} else {
				speed = 0;
			}
			return true;
		}
		return false;
	}
    
	
	/**
	 * Verfie si la partie est perdue ou pas
	 * @return Vrai si la partie est perdu faux sinon
	 */
	public boolean checkEndGame() {
		//Si la vitesse ou le temps restant atteigne 0 la partie est perdu
		if ((speed == 0 && !starting) || (remainingTime == 0 && !starting)) {
			panel.state = STATE.LOST;
			panel.repaint();
			return true;
		}
		return false;
	}

}
