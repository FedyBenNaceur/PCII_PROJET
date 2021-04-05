package Model;

import java.util.ArrayList;
import java.util.Random;

import Vue.Affichage;

/*Classe qui represente la piste */
public class Road {
	private Affichage panel;
	public final int segLength = 200;// taille des segements de la piste
	public int sizeR;// la taille du tableau qui contient les segments
	public ArrayList<Line> lines = new ArrayList<Line>();// stocker les valeurs des lignes qui representes la piste
	public ArrayList<Pair> curvs = new ArrayList<Pair>();// Stocke la courbure de la piste ainsi que leur distance et
															// longeur
	public int tracksection = 0;// la section de la route ou on se situe
	public double trackLength;// la taille de la piste
	float offset = 0;// la longeur accumulé de la piste
	private float trackDistance;

	/**
	 * Procedure qui initialise la route
	 */

	private void initLines() {
		int j = 0;
		float c = curvs.get(j).getFst();// la courbure dans la section actuelle
		float d = curvs.get(j).getScd();// la longeur de la section courante
		float x = 0;
		Random r = new Random();
		for (int i = 0; i < 1600; i++) {
			Line line = new Line();
			// Si on est dans la bonne partie de la route on assignes le courbures
			if (i * segLength - x > d && j < curvs.size() - 1) {
				j++;
				d = curvs.get(j).getScd();
				c = curvs.get(j).getFst();
				x = i * segLength;
			}
			// Si on depasse une certaine distance on crée des obstacles par hasards
			if (i > 50) {
				line.obstacle = r.nextInt(50) == 5 ? true : false;
				line.obsX = r.nextDouble() * 50;
			}
			line.z = i * segLength;
			line.curve = c;
			lines.add(line);
		}
		sizeR = lines.size();
		trackLength = segLength * sizeR;

	}

	/**
	 * Met a jour la section courante
	 */
	public void findPos() {
		// Si on a franchit un checkpoint on reset les valeurs
		if (panel.vehicule.distance >= trackDistance) {
			panel.vehicule.distance -= trackDistance;
			tracksection = 0;
			offset = 0;
		}
		// Sinon on parcours les sections en accumulant les distances pour savoir dans
		// quelles section on est
		while (tracksection < curvs.size() && offset <= panel.vehicule.distance) {
			offset += curvs.get(tracksection).getScd();
			tracksection++;
		}
	}

	/**
	 * On initialise un premiere piste
	 */
	private void initCurvs() {

		curvs.add(new Pair(0f, 2000));
		curvs.add(new Pair(0f, 19000));
		curvs.add(new Pair(4f, 40000));
		curvs.add(new Pair(0f, 40000));
		curvs.add(new Pair(-4f, 30000));
		curvs.add(new Pair(0f, 30000));
		curvs.add(new Pair(4f, 30000));
		curvs.add(new Pair(-4f, 30000));
		curvs.add(new Pair(0f, 30000));
		for (Pair p : curvs) {
			trackDistance += p.getScd();
		}

	}

	/**
	 * Fonction pour synchroniser la piste et la vue
	 * 
	 * @param p l'affichage
	 */

	public void initialisePanel(Affichage p) {
		initCurvs();
		panel = p;
		initLines();
	}

	/**
	 * Permet de trouver l'index du segment courant 
	 * @param z : la distance parcourue par le joueur 
	 * @return l'index du segment actuel
	 */
	public int findSegmentIndex(float z) {
		return (int) (z / segLength) % sizeR;
	}
	
	/**
	 * Permet d'assigner a notre piste des nouvelles courbures 
	 */
	public void chuffleRoad() {
		//Par hasard on choisit la courbure de chaque section
		for (Pair p : curvs) {
			int n = (int) (Math.random() * (3 + 1));
			if (n == 0) {
				p.setFst(0);
			} else if (n == 1) {
				p.setFst(4);
			} else {
				p.setFst(-4);
			}
		}
		int j = 0;
		float c = curvs.get(j).getFst();
		float d = curvs.get(j).getScd();
		float x = 0;
		
		//On asssigne les courbures a chaque segment
		for (int i = 0; i < sizeR; i++) {
			if (i * segLength - x > d && j < curvs.size() - 1) {
				j++;
				d = curvs.get(j).getScd();
				c = curvs.get(j).getFst();
				x = i * segLength;
			}
		}

	}

}