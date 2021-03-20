
import java.util.ArrayList;
import java.util.Random;

/*Classe qui represente la piste */
public class Road {
	private Affichage panel;
	public float middlePoint = 0.5f;// le milieu de la piste
	public final float roadWidth = 0.5f;// la largeur de la piste en pourcentage par rapport a la largeur de la fenetre
	public final float clipWidth = roadWidth * 0.15f;// la taille des bordures
	public final int segLength = 5;// taille des segements de la piste
	public int sizeR;
	public ArrayList<Line> lines = new ArrayList<Line>();// stocker les valeurs des lignes qui representes la piste
	public ArrayList<Pair> curvs = new ArrayList<Pair>();
	public int tracksection = 0;
	float offset = 0;
	float curvature = 0 ;
	float checkpointDistance = 500 ;


	// Constructeur de la class road
	public Road() {
		sizeR = 0;
		initCurvs();
	}

	/**
	 * Procedure qui initialise la route
	 */
	private void initLines() {
		int i = 0;
		int cond = 0;
		while (cond < panel.HEIGHT - panel.HEIGHT / 5) {
			Line line = new Line();
			line.y = panel.HEIGHT - (i * segLength);
			cond = i * segLength;
			lines.add(line);
			i++;
		}
		sizeR = lines.size();
	}

	public void findPos() {
		while (tracksection < curvs.size() && offset <= panel.vehicule.distance) {
            offset += curvs.get(tracksection).getScd();
            tracksection++ ; 
		}
	}

	private void initCurvs() {
		
		curvs.add(new Pair(0f,100));
		curvs.add(new Pair(0.01f,100));
		curvs.add(new Pair(0.f,100));
		curvs.add(new Pair(-0.1f,100));

	}

	/**
	 * Fonction pour synchroniser la piste et la vue
	 * 
	 * @param p l'affichage
	 */

	public void initialisePanel(Affichage p) {
		panel = p;
		initLines();
	}

}