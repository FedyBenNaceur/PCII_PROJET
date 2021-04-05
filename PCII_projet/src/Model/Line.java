package Model;

/**
 * Classe Line permet de modeliser les segments de la route
 * @author Fedy
 *
 */
public class Line {
	public double camD = 0.3; //la profendeur de la camera plus elle est grande plus on zoom sur le joueur
	public double x, y, z;//les coordonnées dans le monde
	public double X, Y, W;//les coordonnées à afficher sur l'ecran après projection
	public double scale;
	public double curve ;//la courbure d'une courbe 
	int width = 1600;//les dimensions de l'ecran 
	int height = 768;
	int roadW = 1500;
	public boolean obstacle = false; //vaut vrai si le segment contient un obstacle ou pas
	public double obsX=0 ;// la position de l'obstacle sur la piste
	public boolean hit = false ; //vaut vrai si l'obstacle a ete touché


	/**
	 * Fonction qui prend les coordonnées de la camera en parametre 
	 * et projete dans l"espace 2D .
	 * @param camX 
	 * @param camY  
	 * @param camZ
	 */
	public void project(int camX, int camY, int camZ) {
		scale = camD / (z - camZ);
		X = (1 + scale * (x - camX)) * width / 2;
		Y = (1 - scale * (y - camY)) * height / 2;
		W = scale * roadW * width / 2;
	}

}
