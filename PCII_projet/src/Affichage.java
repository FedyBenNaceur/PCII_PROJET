 import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/*Classe Affichage qui gère la vue*/
public class Affichage extends JPanel {

	public final int HEIGHT = 1024;// largeur et hauteur correspondent respectivement a la largeur et hauteur de la
									// // Panel
	public final int WIDTH = 1280;
	public Road road;
	public Car vehicule;
	private BufferedImage background;

	/* constructeur de la classe Affichage */
	public Affichage(Road r, Car c) {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		road = r;
		vehicule = c;

		URL urlImg=getClass().getResource("/background.png");
        try {
			background = ImageIO.read(urlImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// drawQuad(g, Color.blue, 0, 0, 0, HEIGHT / 2, WIDTH, HEIGHT / 2, WIDTH, 0);
		drawRoad(g);
		drawBackground(g);
		drawCar(g);

	}

	/**
	 * Methode qui dessine un polygone etant donne les coordonnees des points
	 */
	private void drawQuad(Graphics g, Color c, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		int[] xPoints = { x1, x2, x3, x4 };
		int[] yPoints = { y1, y2, y3, y4 };
		int numPoints = 4;
		g.setColor(c);
		g.fillPolygon(xPoints, yPoints, numPoints);

	}

	/**
	 * Methode qui dessine le vehicule
	 * 
	 * @param g
	 */
	private void drawCar(Graphics g) {
		int carPos = (int) ((WIDTH / 2) + (WIDTH * vehicule.position / 2.f) - 10);
		g.setColor(Color.black);
		g.fillRect(carPos, HEIGHT - 20, 20, 20);
	}

	/**
	 * Methode qui dessine la piste
	 * 
	 * @param g
	 */
	private void drawRoad(Graphics g) {

		ArrayList<Line> tmp = road.getLines();
		int i = 0;
		while (i < road.sizeR - 1) {
			Line l = tmp.get(i);
			float persp = l.project();
			float rWidth =  persp ;
			float cWidth = rWidth * 0.15f;
			rWidth *= 0.90f;
			int leftGrass = (int) ((road.middlePoint - rWidth - cWidth) * WIDTH);
			int leftClip = (int) ((road.middlePoint - rWidth) * WIDTH);
			int rightGrass = (int) ((road.middlePoint + rWidth + cWidth) * WIDTH);
			int rightClip = (int) ((road.middlePoint + rWidth) * WIDTH);

			Line p = tmp.get(i + 1);
			//Color grass = (i / 2) % 2 == 0 ? new Color(16, 200, 16) : new Color(0, 154, 0);
			//Color clip = (i / 2) % 2 == 0 ? new Color(255, 255, 255) : new Color(255, 0, 0);
			Color grass = Math.sin(70.0f *  Math.pow(1.0f - persp,3) + vehicule.distance * 0.1f) > 0.0f ? new Color(16, 200, 16) : new Color(0, 154, 0);
			Color clip = Math.sin(80.0f *  Math.pow(1.0f - persp, 3) + vehicule.distance) > 0.0f ? new Color(255, 255, 255) : new Color(255, 0, 0);
			Color r = Color.gray;
			drawQuad(g, grass, 0, (int) p.y, leftGrass, (int) p.y, leftGrass, (int) l.y, 0, (int) l.y);
			drawQuad(g, clip, leftGrass, (int) p.y, leftGrass, (int) l.y, leftClip, (int) l.y, leftClip, (int) p.y);
			drawQuad(g, clip, rightClip, (int) p.y, rightGrass, (int) p.y, rightGrass, (int) l.y, rightClip, (int) l.y);
			drawQuad(g, grass, rightGrass, (int) p.y, WIDTH, (int) p.y, WIDTH, (int) l.y, rightGrass, (int) l.y);
			drawQuad(g, r, rightClip, (int) p.y, rightClip, (int) l.y, leftClip, (int) l.y, leftClip, (int) p.y);
			i++;
		}
	}

	/**
	 * Methode qui paint l'arriere plan
	 */
	private void drawBackground(Graphics g) {
		g.drawImage(background, 0, 0, WIDTH,HEIGHT / 2, this);
	}

}