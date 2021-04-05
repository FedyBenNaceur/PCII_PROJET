package Model;

public class Line {
	public double camD = 0.3;
	public double x, y, z;
	public double X, Y, W;
	public double scale;
	public double curve ;
	int width = 1600;
	int height = 768;
	int roadW = 1500;
	public boolean obstacle = false; 
	public double obsX=0 ;
	public boolean hit = false ; 

	public Line() {

	}

	public void project(int camX, int camY, int camZ) {
		scale = camD / (z - camZ);
		X = (1 + scale * (x - camX)) * width / 2;
		Y = (1 - scale * (y - camY)) * height / 2;
		W = scale * roadW * width / 2;
	}

}
