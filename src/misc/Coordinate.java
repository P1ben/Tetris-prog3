package misc;

import java.io.Serializable;

public class Coordinate  implements Serializable {
	private int x;
	private int y;
	
	public Coordinate() {
		x = 0;
		y = 0;
	}
	public Coordinate(int a, int b) {
		x = a;
		y = b;
	}
	public Coordinate(int[] array) {
		x = array[0];
		y = array[1];
	}
	public Coordinate Add(Coordinate b) {
		return new Coordinate(this.x + b.x, this.y + b.y);
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int[] getAsArray() {
		return new int[] {x, y};
	}
	public boolean isInSameRowOrColumn(Coordinate b) {
		if(x == b.x || y == b.y) return true;
		return false;
	}
	public boolean equals(Coordinate b) {
		if(x == b.x && y == b.y) return true;
		return false;
	}
}
