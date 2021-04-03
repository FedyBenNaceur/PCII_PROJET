import java.util.ArrayList;

/*Classe qui represente la piste */
public class Road {
	private Affichage panel;
	public final int segLength =200;// taille des segements de la piste
	public int sizeR;
	public ArrayList<Line> lines = new ArrayList<Line>();// stocker les valeurs des lignes qui representes la piste
	public ArrayList<Pair> curvs = new ArrayList<Pair>();
	public int tracksection = 0;
	public double trackLength;
	float offset = 0;
	float curvature = 0;
	private float trackDistance;
	public int currentIndex = 1;

	/**
	 * Procedure qui initialise la route
	 */

	private void initLines() {
		int j = 0 ; 
		float c = curvs.get(j).getFst();
		float d = curvs.get(j).getScd();
		float x = 0 ;
		for (int i = 0; i < 1600; i++) {
			Line line = new Line();
			if (i*segLength-x>d && j<curvs.size()-1) {
				j++;
				d = curvs.get(j).getScd();
			    c = curvs.get(j).getFst();
			    x = i*segLength ;
			}
			line.z = i * segLength;		
			line.curve = c ;
			lines.add(line);
		}
		sizeR = lines.size();
		trackLength = segLength * sizeR;

	}

	public void findPos() {
		if (panel.vehicule.distance >= trackDistance) {
			panel.vehicule.distance -= trackDistance;
			tracksection = 0;
			offset = 0;
		}
		while (tracksection < curvs.size() && offset <= panel.vehicule.distance) {
			offset += curvs.get(tracksection).getScd();
			tracksection++;
		}
	}

	private void initCurvs() {

		curvs.add(new Pair(0f, 1000));
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

	public int findSegmentIndex(float z) {
		return (int) (z / segLength) % sizeR;
	}

}