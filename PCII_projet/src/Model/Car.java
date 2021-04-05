package Model;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import Vue.Affichage;

/** Classe qui represente le vehicule */
public class Car {
	public float position = 0.0f;// position du vehicule
	public Affichage panel;// L'affichage
	public float distance = 0f;
	public float speed = 0f;
	public float maxUpSpeed = 246f;
	public float upSpeed = 0f;
	public float upAcceleration = 5f;
	public final float acceleration = 5f;
	public final float decceleration = 0.01f;
	public float maxSpeed = 200f;
	public boolean isUp, isLeft, isRight, isDown;
	public boolean released = true;
	public float score = 0;
	public boolean start = false;
	public long startChrono;
	public long lastLapTime;
	public boolean crossed = true;
	public float travelDistance = 0;
	public long estimatedCrossTime = 45 ;
	public long remainingTime ;

	public void initPanel(Affichage a) {
		panel = a;
	}

	public void checkCross() {
		if ((panel.road.tracksection - 1) == 0 && !crossed) {
			lastLapTime = (System.currentTimeMillis() / 1000) - startChrono;
			startChrono = System.currentTimeMillis() / 1000;
			remainingTime = estimatedCrossTime ;
			if(estimatedCrossTime-1>30) {
				estimatedCrossTime -= 1 ;
				maxSpeed +=5 ;
			}
			crossed = true;
		} else if ((panel.road.tracksection - 1) != 0) {
			crossed = false;
			remainingTime = estimatedCrossTime - ((System.currentTimeMillis()/1000-startChrono));
		}
	}

	

	public boolean checkCollision(int x1, int y, int height, int width) {
		int carPos = (int) ((panel.WIDTH / 2) + (panel.WIDTH * panel.vehicule.position / 2.f) - (251 / 2));
		Rectangle r1 = new Rectangle(x1, y, height, width);
		Rectangle r2 = new Rectangle(carPos, (int) (panel.HEIGHT - 136 - panel.vehicule.upSpeed), 251, 136);
		if (r1.intersects(r2)) {
			if (speed - 30 > 0) {
				speed -= 50;
			} else {
				speed = 0;
			}
			return true ;
		}
		return false ;
	}
	
	
	public boolean checkEndGame () {
		return false;
	}

}
