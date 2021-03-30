import java.util.ArrayList;

/*Classe qui represente la piste */
public class Road {
	private Affichage panel;
	public final int segLength = 500;// taille des segements de la piste
	public int sizeR;
	public ArrayList<Line> lines = new ArrayList<Line>();// stocker les valeurs des lignes qui representes la piste
	public ArrayList<Pair> curvs = new ArrayList<Pair>();
	public int tracksection = 0;
	public double trackLength ;
	float offset = 0;
	float curvature = 0;
	private float trackDistance;

	// Constructeur de la class road
	public Road() {
		sizeR = 0;
		initCurvs();
	}

	/**
	 * Procedure qui initialise la route
	 */
	
	private void initLines() {
		for(int i =0 ; i<2000 ; i++) {
			Line line  = new Line ();
			line.z = i*segLength ; 
			lines.add(line);
		}
		sizeR = lines.size();
		trackLength = segLength * sizeR ;
		
	}

	public void findPos() {
		if (panel.vehicule.distance >= trackDistance) {
			panel.vehicule.distance -= trackDistance;
			tracksection = 0 ; 
			offset = 0 ;
		}
		while (tracksection < curvs.size() && offset <= panel.vehicule.distance) {
			offset += curvs.get(tracksection).getScd();
			tracksection++;
		}
	}

	private void initCurvs() {

		curvs.add(new Pair(0f, 500));
		curvs.add(new Pair(0.01f, 400));
		curvs.add(new Pair(0.f, 500));
		curvs.add(new Pair(-0.1f, 600));

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
		panel = p;
		initLines();
	}
	
	
	public int findSegmentIndex (float z) {
		return (int) (z/segLength)%sizeR;
	}

}