import java.util.Random;
import java.lang.Math;

public class LineSegment {
	Point pointA;
	Point pointB;
	
	public LineSegment (Point A, Point B) {
		this.pointA = A;
		this.pointB = B;
	}
	
	public LineSegment (int x1, int y1, int x2, int y2) {
		this.pointA = new Point(x1, y1);
		this.pointB = new Point(x2, y2);
	}
	
	/**
	 * Erzeugt eine Strecke, indem die Koordinaten der Endpunkte 
	 * aus dem Intervall 0 bis max - 1 gewählt werden.
	 * 
	 * @param int max
	 */
	public LineSegment (int max) {
		Random rn = new Random();
		int x1 = rn.nextInt(0, max-1);
		int y1 = rn.nextInt(0, max-1);
		
		int x2 = rn.nextInt(0, max-1);
		int y2 = rn.nextInt(0, max-1);
		
		this.pointA = new Point(x1, y1);
		this.pointB = new Point(x2, y2);
	}
	
	/**
	 * Hier sollen die Koordinaten eines Endpunktes 
	 * im Intervall 0 bis max - 1 zufällig gewählt werden. 
	 * Der zweite Endpunkt soll nun auch zufällig im Intervall 
	 * 0 bis max - 1 gewählt sein, jedoch mit Abstand length zum ersten Endpunkt.
	 * 
	 * @param int max
	 * @param int length
	 */
	public LineSegment (int max, int length) {
		Random rn = new Random();
		int x = rn.nextInt(0, max-1);
		int y = rn.nextInt(0, max-1);
		
		double angle, u = -1, v = -1; 
		while ( ((u < 0) || (u > max-1)) || ((v < 0) || (v > max-1)) ) {
			angle = rn.nextInt(360) * Math.PI / 180;
			u = x + length * Math.cos(angle);
			v = y + length * Math.sin(angle);
		}
		
		this.pointA = new Point(x, y);
		this.pointB = new Point((int) u, (int) v);
	}
	
	public void setA (Point A) {
		this.pointA = A;
	}
	
	public void setB (Point B) {
		this.pointB = B;
	}
	
	public Point getA () {
		return this.pointA;
	}
	
	public Point getB () {
		return this.pointB;
	}
	
	public String toString() {
		String output = "(" + pointA.x() + "," + pointA.y() + ") -- (" + pointB.x() + "," + pointB.y() + ")";
		return output;
	}
	
	/**
	 * Erzeugt n horizontale, parallele Strecken, beginnend bei der Strecke 
	 * (0,0) -- (distance,0)und endend mit der Strecke 
	 * (0,distance) -- (distance,distance),mit Abstand distance / (n - 1) für n > 1.
	 * 
	 * @param int distance
	 * @param int n
	 * @return Array von Geraden oder null, wenn n <= 1
	 */
	public static LineSegment[] spawnParallel(int distance, int n) {
		int abstand;
		if ( n > 1 ) {
			LineSegment[] lines = new LineSegment[n];
			for (int i = 0; i < n; i++) {
				abstand = (distance / (n-1)) * i;
				System.out.println(abstand);
				lines[i] = new LineSegment(0, abstand, distance, abstand);
			}
			return lines;
		}
		return null;
	}
	
	/**
	 * True zurückgibt, wenn this einen Schnittpunkt mit der horizontalen Geraden hat, 
	 * welche durch l repräsentiert wird.
	 * 
	 * @param LineSegment l
	 * @return boolean
	 */
	public boolean intersectHorizontal(LineSegment l) {
		// Because l is horizontal, we pay attention to the y-coordinates
		int y1 = l.pointA.y();
		int y2 = l.pointB.y();
		
		// Check if l isn't horizontal
		if ( y1 != y2 ) return false;
		
		// To see if the horizontal line intersects, we need the projection of the y coordinates
		int y3 = this.pointA.y();
		int y4 = this.pointB.y();
		if ( ((y3 <= y1) && (y4 >= y1)) || ((y3 >= y1) && (y4 <= y1)) ) return true;
		else return false;
	}
	
	/**
	 * True zurückgibt, wenn this mit mindestens einer der horizontalen Geraden 
	 * aus parallel einen Schnittpunkt hat.
	 * 
	 * @param LineSegment[] parallel
	 * @return boolean
	 */
	public boolean intersectHorizontal(LineSegment[] parallel) {
		int[] y1 = new int[parallel.length];
		int[] y2 = new int[parallel.length];
		int y3 = this.pointA.y();
		int y4 = this.pointB.y();
		for (int i = 0; i < parallel.length; i++) {
			y1[i] = parallel[i].pointA.y();
			y2[i] = parallel[i].pointB.y();
			// Returns false if any of the lines is not horizontal
			if ( y1[i] != y2[i] ) return false;
			
			if ( ((y3 <= y1[i]) && (y4 >= y1[i])) || ((y3 >= y1[i]) && (y4 <= y1[i])) ) return true;
		}
		return false;
	}
	
	/**
	 * Berechnet die Anzahl m der Strecken, die mit mindestens einer der horizontalen 
	 * Geraden aus parallel einen Schnittpunkt haben.
	 * 
	 * @param LineSegment[] parallel
	 * @param LineSegment[] random
	 * @return double
	 */
	public static double computeValue(LineSegment[] parallel, LineSegment[] random) {
		int n = random.length;
		int m = 0;
		
		for (int i = 0; i < n; i++) {
			if ( random[i].intersectHorizontal(parallel) ) {
				m++;
			}
		}
		
		if ( m == 0 ) return 0.0;
		else return 2*n/m;
	}
	
}
