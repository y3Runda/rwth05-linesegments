import java.lang.Math;

record Point(int x, int y) {
	
	/**
	 * Zurückgibt Objekt der Klasse Point 
	 * mit Koordinaten x und y als String “(x,y)”.
	 * 
	 * @return String
	 */
	public String toString() {
		String output = "(" + x + ", " + y + ")";
		return output;
	}
	
	/**
	 * Berechnet die 2-Norm des aktuellen Punktes.
	 * 
	 * @return double
	 */
	public double norm2() {
		return Math.sqrt(x*x + y*y);
	}
}