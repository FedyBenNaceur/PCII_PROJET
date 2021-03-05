 import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/*Classe Affichage qui gère la vue*/
public class Affichage extends JPanel {

	public final int HEIGHT = 1024;// largeur et hauteur correspondent respectivement a la largeur et hauteur de la
									// // Panel
	public final int WIDTH = 1280;
	public Road road;
	public Car vehicule;
	private BufferedImage background;
	private BufferedImage car;

	/* constructeur de la classe Affichage */
	public Affichage(Road r, Car c) {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		road = r;
		vehicule = c;
		URL url1Img=getClass().getResource("/background.png");
		URL url2Img=getClass().getResource("/newcar.png");
        try {
			background = ImageIO.read(url1Img);
			car = ImageIO.read(url2Img);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
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
		int carPos = (int) ((WIDTH / 2) + (WIDTH * vehicule.position / 2.f) -50);
		g.drawImage(car, carPos , HEIGHT - 136 ,this );
	}

	/**
	 * Methode qui dessine la piste
	 * 
	 * @param g
	 */
	private void drawRoad(Graphics g) {
        road.findPos();
		ArrayList<Line> tmp = road.getLines();
		float targetCurvature = road.curvs.get(road.tracksection-1).getFst();
		float diff = targetCurvature - road.curvature ;		
		
		int i = 0;
		while (i < road.sizeR - 1) {
			Line l = tmp.get(i);
			float persp = l.project();
			float midddlePoint = road.middlePoint  ;
			
			float rWidth =  persp * 2f ;
			float cWidth = rWidth * 0.20f;
			int leftGrass = (int) ((midddlePoint - rWidth - cWidth) * WIDTH);
			int leftClip = (int) ((midddlePoint - rWidth) * WIDTH);
			int rightGrass = (int) ((midddlePoint + rWidth + cWidth) * WIDTH);
			int rightClip = (int) ((midddlePoint + rWidth) * WIDTH);

			Line p = tmp.get(i + 1);
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
		g.drawImage(background, 0, 0, WIDTH,HEIGHT /5, this);
	}

}