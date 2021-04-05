package Controleur;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Vue.Affichage;
import Vue.Affichage.STATE;

public class MouseInput implements MouseListener {
	Affichage panel ;
	public MouseInput(Affichage p) {
		this.panel =p ;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (mx >= panel.WIDTH/2-50 && mx<=panel.WIDTH/2-50+100) {
			if (my>=350 && my<=400) {
				panel.state = STATE.GAME;
			}
		}
		
		if (mx >= panel.WIDTH/2-50 && mx<=panel.WIDTH/2-50+100) {
			if (my>=550 && my<=600) {
				System.exit(1);
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
