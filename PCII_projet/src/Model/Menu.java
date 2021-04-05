package Model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Vue.Affichage;

/**
 * Classe menu qui represente le menu du jeu
 * @author Fedy
 *
 */

public class Menu {

	Affichage panel;
	Rectangle playButton;//le bouton play 
	Rectangle helpButton;//le bouton aide 
	Rectangle quitButton ;//le bouton quitter

	/*
	 * Constructeur de la classe Menu
	 */
	public Menu(Affichage p) {
		this.panel = p;
		playButton = new Rectangle(panel.WIDTH / 2 - 50, 350, 100, 50);
		helpButton = new Rectangle(panel.WIDTH / 2 - 50, 450, 100, 50);
		quitButton = new Rectangle(panel.WIDTH / 2 - 50, 550, 100, 50);
	}

	/**
	 * Procedure qui permet d'fficher le menu
	 * @param g
	 */
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Font fnt = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt);
		g.setColor(Color.white);
		g.drawString("SPACE GAME", panel.WIDTH / 2 - 150, 100);
		//Dessine les trois rectangles qui representes les boutouns du menus
		g2d.draw(playButton);
		g2d.draw(quitButton);
		g2d.draw(helpButton);
		Font fnt0 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt0);
		//Dessine le texte dans les boutons
		g.drawString("PLAY", playButton.x+15, playButton.y+30);
		g.drawString("QUIT", quitButton.x+15, quitButton.y+30);
		g.drawString("HELP", helpButton.x+15, helpButton.y+30);
	}
}
