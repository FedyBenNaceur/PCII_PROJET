import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	private int dimBack = (int) HEIGHT / 2;
	private JLabel stats;
	int roadW = 2000;
	private int drawDistance = 1500;

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
		URL url1Img = getClass().getResource("/background.png");
		URL url2Img = getClass().getResource("/player_straight.png");
		URL url3Img = getClass().getResource("/player_left.png");
		URL url4Img = getClass().getResource("/player_right.png");

		try {
			background = ImageIO.read(url1Img);
			carStraight = ImageIO.read(url2Img);
			carLeft = ImageIO.read(url3Img);
			carRight = ImageIO.read(url4Img);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void paint(Graphics g) {

		g.drawImage(background, 0, 0, WIDTH, dimBack, this);
		setOpaque(false);
		super.paint(g);
		drawRoad(g);
		setOpaque(true);
		drawCar(g);
		setSpeed();
		vehicule.checkCross();

	}

	/**
	 * Methode qui dessine le vehicule
	 * 
	 * @param g
	 */
	private void drawCar(Graphics g) {
		int carPos = (int) ((WIDTH / 2) + (WIDTH * vehicule.position / 2.f) - (256 / 2));
		if (vehicule.isUp || vehicule.isDown) {
			g.drawImage(carStraight, carPos, HEIGHT - 136, this);
		} else if (vehicule.isLeft) {
			g.drawImage(carLeft, carPos, HEIGHT - 136, this);
		} else if (vehicule.isRight) {
			g.drawImage(carRight, carPos, HEIGHT - 136, this);
		} else {
			g.drawImage(carStraight, carPos, HEIGHT - 136, this);
		}

	}

	/**
	 * Methode qui dessine la piste
	 * 
	 * @param g
	 */

	private void drawRoad(Graphics g) {
		road.findPos();
		int currentPos = (int) (vehicule.score / road.segLength);
		double x = 0, dx = 0;
		double maxY = HEIGHT;
		int camH = 1500;
		for (int n = 0; n < drawDistance; n++) {
			Line l = road.lines.get((n + currentPos) % road.sizeR);
			l.project((int) (vehicule.position - (int) x), camH, (int) vehicule.score);
			Line p = null;
			if (l.Y > 0 && l.Y < maxY) {
				p = road.lines.get(((n + currentPos) - 1) % road.sizeR);
			} else {
				p = l;
			}
			maxY = l.Y;
			float persp = (float) (l.z / (HEIGHT / 0.2f));
			Color grass = Math.sin(60.0f * (1.0f - persp) + vehicule.distance * 0.1f) > 0.0f ? new Color(16, 200, 16)
					: new Color(0, 154, 0);

			Color clip = Math.sin(80.0f * (1.0f - persp) + vehicule.distance * 0.1f) > 0.0f ? new Color(255, 255, 255)
					: new Color(255, 0, 0);

			Color r = road.tracksection - 1 == 0 ? Color.WHITE : Color.GRAY;

			drawQwad(g, grass, 0, (int) p.Y, WIDTH, 0, (int) l.Y, WIDTH);
			drawQwad(g, clip, (int) p.X, (int) p.Y, (int) (p.W * 1.5), (int) l.X, (int) l.Y, (int) (l.W * 1.5));

			drawQwad(g, r, (int) p.X, (int) p.Y, (int) (p.W * 1.4), (int) l.X, (int) l.Y, (int) (l.W * 1.4));

			drawQwad(g, r, (int) p.X, (int) p.Y, (int) (p.W * 0.7), (int) l.X, (int) l.Y, (int) (l.W * 0.7));

		}

	}

	private void setSpeed() {
		this.stats.setText("<html>Speed:" + new DecimalFormat("##.##").format(this.vehicule.speed)+"<br>"
				+ "Score:"
				+ new DecimalFormat("##").format(this.vehicule.score)+"<br>"
						+ "Lap Time:"+this.vehicule.lastLapTime+"<br>"
		+ "distance:"+new DecimalFormat("##").format(this.vehicule.distance)+vehicule.crossed+road.tracksection+"</html>");
	}

	void drawQwad(Graphics g, Color c, int x1, int y1, int w1, int x2, int y2, int w2) {
		int[] xPoints = { x1 - w1, x2 - w2, x2 + w2, x1 + w1 };
		int[] yPoints = { y1, y2, y2, y1 };
		int n = 4;
		g.setColor(c);
		g.fillPolygon(xPoints, yPoints, n);
	}

}