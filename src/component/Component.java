package component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import enums.Components;
import misc.Coordinate;

public class Component  implements Serializable{
	Components type;
	Coordinate[] coords;
	
	public static final Map<Components, int[][]> component_dims;
	static {
		component_dims = new HashMap<Components, int[][]>();
		component_dims.put(Components.line,   new int[][] {{0,  0}, {-2,  0}, {-1,  0}, {1,  0}});
		component_dims.put(Components.L,      new int[][] {{0,  0}, {-1,  0}, { 1,  0}, {1, -1}});
		component_dims.put(Components.L_rev,  new int[][] {{0,  0}, {-1, -1}, {-1,  0}, {1,  0}});
		component_dims.put(Components.square, new int[][] {{0,  0}, {-1, -1}, {-1,  0}, {0, -1}});
		component_dims.put(Components.cross,  new int[][] {{0,  0}, {-1,  0}, { 0, -1}, {1,  0}});
		component_dims.put(Components.Z,      new int[][] {{0,  0}, {-1, -1}, { 0, -1}, {1,  0}});
		component_dims.put(Components.Z_rev,  new int[][] {{0,  0}, {-1,  0}, { 0, -1}, {1, -1}});
	}
	
	
	public Component(Components t, Coordinate spwn_coords) {
		this.type = t;
		this.coords = new Coordinate[4];
		for(int i = 0; i < 4; i++) {
			this.coords[i] = spwn_coords.Add(new Coordinate(component_dims.get(t)[i]));
		}
	}
	
	public Component(Component b) {
		this.type = b.type;
		this.coords = new Coordinate[4];
		for(int i = 0; i < 4; i++) {
			this.coords[i] = new Coordinate(b.coords[i].getAsArray());
		}
	}
	public Coordinate[] getCoords() {
		return coords;
	}
	public Components getType() {
		return type;
	}
	public void move_down(int n) {
		for(int i = 0; i < 4; i++) {
			coords[i] = coords[i].Add(new Coordinate(0, n));
		}
	}
	public Component shadow_move(Coordinate b) {
		Component temp = new Component(this);
		for(int i = 0; i < 4; i++) {
			temp.coords[i] = temp.coords[i].Add(b);
		}
		return temp;
	}
	
	public void rotate_left() {
		Coordinate abs = coords[0];
		for(int i = 0; i < 4; i++) {
			coords[i] = coords[i].Add(new Coordinate(-abs.getX(), -abs.getY()));//.Add(new Coordinate(coords[i].getY(), -coords[i].getX());
			coords[i] = new Coordinate(coords[i].getY(), -coords[i].getX());
			coords[i] = coords[i].Add(abs);
		}
	}
	public Component shadow_rotate_left() {
		Component temp = new Component(this);
		Coordinate abs = temp.coords[0];
		for(int i = 0; i < 4; i++) {
			temp.coords[i] = temp.coords[i].Add(new Coordinate(-abs.getX(), -abs.getY()));//.Add(new Coordinate(coords[i].getY(), -coords[i].getX());
			temp.coords[i] = new Coordinate(temp.coords[i].getY(), -temp.coords[i].getX());
			temp.coords[i] = temp.coords[i].Add(abs);
		}
		return temp;
	}
	public void rotate_right() {
		Coordinate abs = coords[0];
		for(int i = 0; i < 4; i++) {
			coords[i] = coords[i].Add(new Coordinate(-abs.getX(), -abs.getY()));//.Add(new Coordinate(coords[i].getY(), -coords[i].getX());
			coords[i] = new Coordinate(-coords[i].getY(), coords[i].getX());
			coords[i] = coords[i].Add(abs);
		}
	}
	public Component shadow_rotate_right() {
		Component temp = new Component(this);
		Coordinate abs = temp.coords[0];
		for(int i = 0; i < 4; i++) {
			temp.coords[i] = temp.coords[i].Add(new Coordinate(-abs.getX(), -abs.getY()));//.Add(new Coordinate(coords[i].getY(), -coords[i].getX());
			temp.coords[i] = new Coordinate(-temp.coords[i].getY(), temp.coords[i].getX());
			temp.coords[i] = temp.coords[i].Add(abs);
		}
		return temp;
	}
	public void move_right(int n) {
		for(int i = 0; i < 4; i++) {
			coords[i] = coords[i].Add(new Coordinate(n, 0));
		}
	}
	public void move_left(int n) {
		for(int i = 0; i < 4; i++) {
			coords[i] = coords[i].Add(new Coordinate(-n, 0));
		}
	}
	public boolean isAtBorder(Coordinate top_left, Coordinate bottom_right) {
		for(Coordinate a : coords) {
			if(a.isInSameRowOrColumn(bottom_right) || a.isInSameRowOrColumn(top_left)) return true;
		}
		return false;
	}
	public boolean isAtBottomBorder(int board_height) {
		for(Coordinate a : coords) {
			if(a.getY() == board_height - 1) return true;
		}
		return false;
	}
	public boolean isPartOf(Coordinate b) {
		for(Coordinate a : coords) {
			if(a.equals(b)) return true;
		}
		return false;
	}
}
