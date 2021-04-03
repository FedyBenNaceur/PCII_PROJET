package Vue;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controleur.Decor;
import Model.Car;
import Model.Line;
import Model.Road;

/*Classe Affichage qui gère la vue*/
public class Affichage extends JPanel {

	public final int HEIGHT = 768;// largeur et hauteur correspondent respectivement a la largeur et hauteur de la
									// // Panel
	public final int WIDTH = 1600;
	public Road road;
	public Car vehicule;
	private BufferedImage background;
	private BufferedImage carStraight;
	private BufferedImage carLeft;
	private BufferedImage carRight;
	private JLabel stats;
	public int drawDistance = 300;
	public ArrayList<Star> stars = new ArrayList<Star>();

	/* constructeur de la classe Affichage */
	public Affichage(Road r, Car c) {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		road = r;
		vehicule = c;
		this.stats = new JLabel("Speed");
		this.setLayout(null);
		stats.setAlignmentX(TOP_ALIGNMENT);
		stats.setBounds(0, 0, 300, 300);
		this.add(stats);
		URL url1Img = getClass().getResource("/space-1.png");
		URL url2Img = getClass().getResource("/player_starship.png");
		URL url3Img = getClass().getResource("/player_starship.png");
		URL url4Img = getClass().getResource("/player_starship.png");

		try {
			background = ImageIO.read(url1Img);
			carStraight = ImageIO.read(url2Img);
			carLeft = ImageIO.read(url3Img);
			carRight = ImageIO.read(url4Img);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 100; i++) {
			stars.add(new Star());
		}
		(new Decor(this)).start();

	}

	@Override
	public void paint(Graphics g) {

		// g.drawImage(background, 0, 0, WIDTH, dimBack, this);

		super.paint(g);
		g.setColor(Color.BLACK);
	    g.fillRect(0, 0, WIDTH, HEIGHT / 2+20);
		drawRoad(g);
		drawCar(g);
		setSpeed();
		vehicule.checkCross();
		g.translate(WIDTH / 2, HEIGHT / 4);
	    drawStars(g);
		g.translate(0, 0);

	}

	/**
	 * Methode qui dessine le vehicule
	 * 
	 * @param g
	 */
	private void drawCar(Graphics g) {
		int carPos = (int) ((WIDTH / 2) + (WIDTH * vehicule.position / 2.f) - (251 / 2));
		if (vehicule.isUp || vehicule.isDown) {
			g.drawImage(carStraight, carPos, (int) (HEIGHT - 136 - vehicule.upSpeed), this);
		} else if (vehicule.isLeft) {
			g.drawImage(carLeft, carPos, (int) (HEIGHT - 136 - vehicule.upSpeed), this);
		} else if (vehicule.isRight) {
			g.drawImage(carRight, carPos, (int) (HEIGHT - 136 - vehicule.upSpeed), this);
		} else {
			g.drawImage(carStraight, carPos, (int) (HEIGHT - 136 - vehicule.upSpeed), this);
		}

	}

	/**
	 * Methode qui dessine la piste
	 * 
	 * @param g
	 */

	private void drawRoad(Graphics g) {
		road.findPos();
	    int currentPos = road.findSegmentIndex(vehicule.score);
		double x = 0, dx = 0;
		double maxY = HEIGHT;
		int camH = 2500;
		if(currentPos + drawDistance >1500) {
			currentPos = 0 ;
			vehicule.score = 0 ;
		}
		for (int n = 0; n < drawDistance; n++) {
			Line l = road.lines.get((currentPos + n) );
			l.project((int) (vehicule.position - (int) x), camH, (int) vehicule.score);
			x += dx;
			dx += l.curve;
			Line p = null;
			if (l.Y > 0 && l.Y < maxY  ) {
				p = road.lines.get((currentPos + n - 1));
			} else {
				p = l;
			}
			maxY = l.Y;
			float persp = (float) (l.z / (HEIGHT / 0.2f));
			Color grass = Math.sin(90.0f * (1.0f - persp) + vehicule.distance * 0.01f) > 0.0f ? new Color(51, 135, 255)
					: new Color(0, 0, 204);
			Color clip = Math.sin(80.0f * (1.0f - persp) + vehicule.distance * 0.1f) > 0.0f ? new Color(255, 255, 255)
					: Color.black;
			Color r = road.tracksection - 1 == 0 ? Color.WHITE : Color.blue;
			drawQwad(g, grass, 0, (int) p.Y, WIDTH, 0, (int) l.Y, WIDTH);
			drawQwad(g, clip, (int) p.X, (int) p.Y, (int) (p.W * 1.5), (int) l.X, (int) l.Y, (int) (l.W * 1.5));
			drawQwad(g, r, (int) p.X, (int) p.Y, (int) (p.W * 1.4), (int) l.X, (int) l.Y, (int) (l.W * 1.4));
            
		}

	}

	private void setSpeed() {
		boolean check = vehicule.checkOffRoad();
		this.stats.setText("<html>Speed:" + new DecimalFormat("##.##").format(this.vehicule.speed) + "<br>" + "Score:"
				+ new DecimalFormat("##").format(this.vehicule.score) + "<br>" + "Lap Time:" + this.vehicule.lastLapTime
				+ "<br>" + "distance:" + new DecimalFormat("##").format(this.vehicule.distance) + check + "</html>");
	}

	void drawQwad(Graphics g, Color c, int x1, int y1, int w1, int x2, int y2, int w2) {
		int[] xPoints = { x1 - w1, x2 - w2, x2 + w2, x1 + w1 };
		int[] yPoints = { y1, y2, y2, y1 };
		int n = 4;
		g.setColor(c);
		g.fillPolygon(xPoints, yPoints, n);
	}

	void drawStars(Graphics g) {
		for (Star s : stars) {
			s.show(g);
		}

	}

	public void updateStars() {
		for (Star s : stars) {
			s.update();
		}
	}

	class Star {
		double x;
		double y;
		double z;
		double pz;

		Star() {
			x = randRange(-WIDTH, WIDTH);
			y = randRange(-HEIGHT / 2, HEIGHT / 2);
			z = WIDTH;
			pz = z;
		}

		void update() {
			z = z - vehicule.speed ;
			if (z < 500) {
				z = WIDTH;
				pz = z;
			}
		}

		void show(Graphics g) {
			g.setColor(Color.WHITE);
			double sx = map(x / z, 0, 1, 0, WIDTH);
			double sy = map(y / z, 0, 1, 0, HEIGHT / 2);
			double r = map(z, 0, WIDTH, 16, 0);
			double px = map(x / pz, 0, 1, 0, WIDTH);
			;
			double py = map(y / pz, 0, 1, 0, HEIGHT / 2);

			g.fillOval((int) sx, (int) sy, (int) r, (int) r);
			g.drawLine((int) px, (int) py, (int) sx, (int) sy);
		}

		double map(double value, double start1, double stop1, double start2, double stop2) {
			return (value - start1) / (stop1 - start1) * (stop2 - start2) + start2;
		}

		private float randRange(float min, float max) {
			return min + (float) Math.random() * (max - min);
		}
	}

}