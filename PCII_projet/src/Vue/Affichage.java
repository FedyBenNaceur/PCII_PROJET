package Vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controleur.Decor;
import Controleur.MouseInput;
import Model.Car;
import Model.Line;
import Model.Menu;
import Model.Road;

/**
 * Classe Affichage qui gère la vue
 * 
 * @author Fedy
 *
 */
public class Affichage extends JPanel {

	private static final long serialVersionUID = 1L;
	public final int HEIGHT = 768;// largeur et hauteur correspondent respectivement a la largeur et hauteur de la
									// // Panel
	public final int WIDTH = 1600;
	public Road road;
	public Car vehicule;
	// les resources utilisés
	private BufferedImage background;
	private BufferedImage shipStraight;
	private BufferedImage shipLeft;
	private BufferedImage shipRight;
	private BufferedImage shipRight_boost;
	private BufferedImage shipLeft_boost;
	private BufferedImage shipStraight_boost;
	private BufferedImage obstacle;
	private BufferedImage earth;
	private BufferedImage moon;

	public int drawDistance = 300;// Percise combien de segment affiché a partir du premier
	public ArrayList<Star> stars = new ArrayList<Star>();// Decor

	public enum STATE {
		MENU, GAME, LOST;
	};// represente l'etat du jeu

	public STATE state = STATE.MENU;
	public Menu menu;
	public boolean endGame = false;// vaut vrai si la partie est perdue faux sinon

	/* constructeur de la classe Affichage */
	public Affichage(Road r, Car c) {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		road = r;
		vehicule = c;
		this.setLayout(null);
		URL url1Img = getClass().getResource("/BackGround.png");
		URL url2Img = getClass().getResource("/player_starship.png");
		URL url3Img = getClass().getResource("/starship-left.png");
		URL url4Img = getClass().getResource("/starship-right.png");
		URL url5Img = getClass().getResource("/starship-right-boost.png");
		URL url6Img = getClass().getResource("/starship-left-boost.png");
		URL url7Img = getClass().getResource("/starship-boost.png");
		URL url8Img = getClass().getResource("/obstacle.png");
		URL url9Img = getClass().getResource("/earth.png");
		URL url0Img = getClass().getResource("/moon.png");

		try {
			background = ImageIO.read(url1Img);
			shipStraight = ImageIO.read(url2Img);
			shipLeft = ImageIO.read(url3Img);
			shipRight = ImageIO.read(url4Img);
			shipRight_boost = ImageIO.read(url5Img);
			shipLeft_boost = ImageIO.read(url6Img);
			shipStraight_boost = ImageIO.read(url7Img);
			obstacle = ImageIO.read(url8Img);
			earth = ImageIO.read(url9Img);
			moon = ImageIO.read(url0Img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// On initialise la liste des étoiles
		for (int i = 0; i < 500; i++) {
			stars.add(new Star());
		}
		(new Decor(this)).start();
		menu = new Menu(this);
		this.addMouseListener(new MouseInput(this));

	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);
		// L'affichage si on est en train de jouer
		if (state == STATE.GAME) {
			g.setColor(Color.BLACK);
			g.drawImage(background, 0, 0, WIDTH, HEIGHT / 2 + 10, this);
			drawRoad(g);
			drawCar(g);
			setTxt(g);
			g.translate(WIDTH / 2, HEIGHT / 5);
			drawStars(g);
			g.translate(0, 0);
		} // L'affichage si on est dans le menu
		else if (state == STATE.MENU) {
			g.drawImage(background, 0, 0, WIDTH, HEIGHT, this);
			menu.render(g);
		} // L'affichage si la partie est perdue
		else {
			g.drawImage(background, 0, 0, WIDTH, HEIGHT, this);
			Font fnt = new Font("arial", Font.BOLD, 50);
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("OUPSIES YOU LOST!!!!!", WIDTH / 2 - 200, 100);
		}

	}

	/**
	 * Methode qui dessine le vehicule
	 * 
	 * @param g
	 */
	private void drawCar(Graphics g) {
		int carPos = (int) ((WIDTH / 2) + (WIDTH * vehicule.position / 2.f) - (251 / 2));
		// si le vehicule est en train de voler
		if (vehicule.upSpeed != 0) {
			if (vehicule.isUp || vehicule.isDown) {
				g.drawImage(shipStraight, carPos, (int) (HEIGHT - 136 - vehicule.upSpeed), this);
			} else if (vehicule.isLeft) {
				g.drawImage(shipLeft, carPos, (int) (HEIGHT - 136 - vehicule.upSpeed), this);
			} else if (vehicule.isRight) {
				g.drawImage(shipRight, carPos, (int) (HEIGHT - 136 - vehicule.upSpeed), this);
			} else {
				g.drawImage(shipStraight, carPos, (int) (HEIGHT - 136 - vehicule.upSpeed), this);
			}
		} // Si le vehicule est sur la piste
		else {
			if (vehicule.isUp || vehicule.isDown) {
				g.drawImage(shipStraight_boost, carPos, (int) (HEIGHT - 136 - vehicule.upSpeed), this);
			} else if (vehicule.isLeft) {
				g.drawImage(shipLeft_boost, carPos, (int) (HEIGHT - 136 - vehicule.upSpeed), this);
			} else if (vehicule.isRight) {
				g.drawImage(shipRight_boost, carPos, (int) (HEIGHT - 136 - vehicule.upSpeed), this);
			} else {
				g.drawImage(shipStraight_boost, carPos, (int) (HEIGHT - 136 - vehicule.upSpeed), this);
			}
		}

	}

	/**
	 * Methode qui dessine la piste
	 * 
	 * @param g
	 */

	private void drawRoad(Graphics g) {
		road.findPos();
		int currentPos = road.findSegmentIndex(vehicule.travelDistance);
		double x = 0, dx = 0;
		double maxY = HEIGHT;
		int camH = 2500 + (int) road.lines.get(currentPos).y;
		if (currentPos + drawDistance > 1500) {
			currentPos = 0;
			vehicule.travelDistance = 0;
			resetHit();
			road.chuffleRoad();
		}
		int pX = 0;
		for (int n = 0; n < drawDistance; n++) {
			Line l = road.lines.get((currentPos + n));
			l.project((int) (vehicule.position - (int) x), camH, (int) vehicule.travelDistance);
			x += dx;
			dx += l.curve;
			Line p = null;
			if (l.Y > 0 && l.Y < maxY) {
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
			drawQwad(g, r, (int) p.X, (int) p.Y, (int) (p.W * 0.7), (int) l.X, (int) l.Y, (int) (l.W * 0.7));
			if (p.obstacle && n < 50 && !p.hit) {
				int size = (int) (100);
				g.drawImage(obstacle, (int) (p.X + p.obsX), (int) p.Y, size, size, this);
				if (vehicule.checkCollision((int) (p.X + p.obsX), (int) p.Y, size, size)) {
					p.hit = true;
				}
			}
			pX = (int) p.X;
		}
		drawPlanet(g, pX);

	}

	private void resetHit() {
		for (Line l : road.lines) {
			l.hit = false;
		}
	}

	/**
	 * Procedure qui permet d'afficher les informations du joueur
	 * 
	 * @param g
	 */
	private void setTxt(Graphics g) {
		g.setColor(Color.MAGENTA);
		g.setFont(new Font("TimesRoman", Font.PLAIN + 1, (WIDTH + HEIGHT) / 100));
		g.drawString("Score : " + new DecimalFormat("##.##").format(this.vehicule.score), 20, 40);
		g.drawString("Last Lap Time : " + vehicule.lastLapTime, WIDTH - (WIDTH + HEIGHT) / 10, 40);
		g.drawString("Remaining Time : " + vehicule.remainingTime, WIDTH - (WIDTH + HEIGHT) / 10, 60);
		g.drawString(new DecimalFormat("##.##").format(this.vehicule.speed) + " km/h", 20,
				HEIGHT - (WIDTH + HEIGHT) / 30);
	}

	/**
	 * Methode qui dessine un polygone
	 * 
	 * @param g
	 * @param c  couleur du polygon
	 * @param x1 l'abscisse du premier point du milieu
	 * @param y1 l'ordonné du premier point
	 * @param w1 la moitié de la largeur du polygon
	 * @param x2 l'abscisse du premier point du milieu
	 * @param y2 l'ordonné du premier point
	 * @param w2 la moitié de la largeur du polygon
	 */
	void drawQwad(Graphics g, Color c, int x1, int y1, int w1, int x2, int y2, int w2) {
		int[] xPoints = { x1 - w1, x2 - w2, x2 + w2, x1 + w1 };
		int[] yPoints = { y1, y2, y2, y1 };
		int n = 4;
		g.setColor(c);
		g.fillPolygon(xPoints, yPoints, n);
	}

	/**
	 * Dessine les planetes en decor
	 * @param g
	 * @param x l'abscisse de la première image 
	 */
	void drawPlanet(Graphics g, int x) {
		g.drawImage(earth, (int) (x), 0, 100, 100, this);
		g.drawImage(moon, (int) (WIDTH - x), 100, 100, 100, this);
	}
    
	/**
	 * Methode qui affiche les étoiles dans le decor
	 * @param g
	 */
	void drawStars(Graphics g) {
		for (Star s : stars) {
			s.show(g);
		}

	}
    
	/**
	 * Methode qui met a jour les etoiles
	 */
	public void updateStars() {
		for (Star s : stars) {
			s.update();
		}
	}

	class Star {
		double x;//Coordonées de l'étoile
		double y;
		double z;
	

		/**
		 * Constructeur de l'etoile
		 */
		Star() {
			//place d'une manière aléatoire l'etoile
			x = randRange(-WIDTH, WIDTH);
			y = randRange(-HEIGHT / 2, HEIGHT / 2);
			z = WIDTH;
		}

		/**
		 * Met a jour la postion de l'etoile
		 */
		void update() {
			z = z - vehicule.speed;
			if (z < 500) {
				z = WIDTH;
			}
		}
       
		/**
		 * Procedure qui affiche l'etoile
		 */
		void show(Graphics g) {
			g.setColor(Color.WHITE);
		    //On normalise les coordonnées des etoiles 
			double sx = map(x / z, 0, 1, 0, WIDTH);
			double sy = map(y / z, 0, 1, 0, HEIGHT / 2);
			//Plus l'etoile est proche plus elle est grande
			double r = map(z, 0, WIDTH, 16, 0);
			g.fillOval((int) sx, (int) sy, (int) r, (int) r);
		}

		double map(double value, double start1, double stop1, double start2, double stop2) {
			return (value - start1) / (stop1 - start1) * (stop2 - start2) + start2;
		}

		private float randRange(float min, float max) {
			return min + (float) Math.random() * (max - min);
		}
	}

}