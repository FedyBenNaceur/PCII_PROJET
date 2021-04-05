package Model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Vue.Affichage;

public class Menu {

	Affichage panel;
	Rectangle playButton;
	Rectangle helpButton;
	Rectangle quitButton ;

	public Menu(Affichage p) {
		this.panel = p;
		playButton = new Rectangle(panel.WIDTH / 2 - 50, 350, 100, 50);
		helpButton = new Rectangle(panel.WIDTH / 2 - 50, 450, 100, 50);
		quitButton = new Rectangle(panel.WIDTH / 2 - 50, 550, 100, 50);
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Font fnt = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt);
		g.setColor(Color.white);
		g.drawString("SPACE GAME", panel.WIDTH / 2 - 150, 100);
		g2d.draw(playButton);
		g2d.draw(quitButton);
		g2d.draw(helpButton);
		Font fnt0 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt0);
		g.drawString("PLAY", playButton.x+15, playButton.y+30);
		g.drawString("QUIT", quitButton.x+15, quitButton.y+30);
		g.drawString("HELP", helpButton.x+15, helpButton.y+30);
	}
}
