
public class Line {
	double camD = 0.3;
	double x, y, z;
	double X, Y, W;
	double scale;
	double curve ;
	int width = 1600;
	int height = 768;
	int roadW = 1500;

	public Line() {

	}

	void project(int camX, int camY, int camZ) {
		scale = camD / (z - camZ);
		X = (1 + scale * (x - camX)) * width / 2;
		Y = (1 - scale * (y - camY)) * height / 2;
		W = scale * roadW * width / 2;
	}

}
