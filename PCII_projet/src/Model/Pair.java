package Model;

/**
 * Classe Pair qui permet de stocker deux float dans un seule structure 
 * @author Fedy
 *
 */
public class Pair {
	private  float p1 ;
	private float p2 ; 
	
	/**
	 * Constructeur de la Classe
	 * @param a le permier float
	 * @param b le deuxieme float
	 */
	public Pair (float a ,float b) {
		p1 = a ;
		p2 = b ;
	}
	
	
	public float getFst() {
		return p1 ; 
	}
	
	public float getScd() {
		return p2 ;
	}
	
	public void setFst(float c) {
		this.p1 = c; 
	}

}
