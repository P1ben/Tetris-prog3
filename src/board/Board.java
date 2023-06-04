package board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import component.Component;
import enums.BoardEvents;
import enums.Colors;
import enums.Components;
import misc.Coordinate;
import windows.NextComponentPanel;


public class Board  extends JPanel implements Serializable{
	
	public static final Map<Components, Colors> component_colors;
	public static final Map<Colors, Color> color_map;
	public static final Components[] all_components;
	
	static {
		component_colors = new HashMap<Components, Colors>();
		component_colors.put(Components.line,   Colors.turquoise);
    	component_colors.put(Components.L,      Colors.orange);
    	component_colors.put(Components.L_rev,  Colors.blue);
    	component_colors.put(Components.square, Colors.yellow);
    	component_colors.put(Components.cross,  Colors.purple);
    	component_colors.put(Components.Z,      Colors.red);
    	component_colors.put(Components.Z_rev,  Colors.green);
    	
    	color_map = new HashMap<Colors, Color>();
    	color_map.put(Colors.turquoise, new Color(64, 224, 208));
    	color_map.put(Colors.orange,    Color.orange);
    	color_map.put(Colors.blue,      Color.blue);
    	color_map.put(Colors.yellow,    Color.yellow);
    	color_map.put(Colors.purple,    Color.magenta);
    	color_map.put(Colors.red,       Color.red);
    	color_map.put(Colors.green,     Color.green);
    	
    	all_components = new Components[] {
    			Components.line,
    			Components.L,
    			Components.L_rev,
    			Components.square,
    			Components.cross,
    			Components.Z,   
    			Components.Z_rev
    	};
	}
	
	private int startSquareX = 0;
    private int startSquareY = 0;
    private int squareW;
    private int squareH;
    private int board_height;
    private int board_width;
    private Coordinate spawn_pos;
    private Random rand;
    private Colors[][] board;
    private int score = 0;
    
    Component current = null;
    Components next = null;
    
    public Board(int width, int height) {
    	setLayout(null);
    	this.setBackground(Color.GRAY);
    	board_width = width;
    	board_height = height;
    	board = new Colors[width][height];
    	for(int i = 0; i < width; i++) {
    		for(int j = 0; j < height; j++) {
    			board[i][j] = Colors.empty;
    		}
    	}
        int spawn_x = width % 2 == 0 ? width / 2 : width / 2 - 1;
        spawn_pos = new Coordinate(spawn_x, 1);
        setFocusable(true);
        rand = new Random();
        next = all_components[rand.nextInt(7)];
    }
    
    public Component getCurrComponent() {
    	return current;
    }
    
    public Components getNextComponent() {
    	return next;
    }
    public int getScore() {
    	return score;
    }
    public BoardEvents updateBoard() {
    		System.out.println("tick");
            //...Perform a task...
        	if(current == null) {
        		
        		if(!spawnComponent(next)) {
        			System.out.println("Di end;");
        			return BoardEvents.ended;
        		}
        		next = all_components[rand.nextInt(7)];
        		renderCurrentComponent();
        		repaint();
        		return BoardEvents.spawned;
        	}
        	else if(checkCollision(current)) {
        		current = null;
        		removeFullRows();
        		return BoardEvents.placed;
        	}
        	else {
        		eraseCurrentComponent();
        		current.move_down(1);
        		renderCurrentComponent();
        		repaint();
        		System.out.println("%d %d".formatted(current.getCoords()[0].getX(), current.getCoords()[0].getY()));
        	}
        	return BoardEvents.running;
    };
    
    
    private boolean checkCollision(Component comp) {
    	if(comp == null) return false;
    	if(comp.isAtBottomBorder(board_height)) return true;
    	for(Coordinate a : comp.getCoords()) {
    		Coordinate temp = a.Add(new Coordinate(0, 1));
    		if(!comp.isPartOf(temp) && getFieldColor(temp) != Colors.empty) return true;
    	}
    	return false;
    }
    
    private void calculateScore(int rowsDeleted) {
    	int temp = 0;
    	int old_score = score;
    	switch(rowsDeleted) {
    	case 1:
    		temp = 200;
    		break;
    	case 2:
    		temp = 500;
    		break;
    	case 3:
    		temp = 1000;
    		break;
    	case 4:
    		temp = 2000; // Tetris
    		break;
    	default:
    		temp = 0;
    		break;	
    	}
    	score += temp;
    }
    
    private Colors getFieldColor(Coordinate a) throws IndexOutOfBoundsException{
    	if(a.getX() < 0 || a.getY() < 0 || a.getX() > board_width - 1 || a.getY() > board_height - 1 ) throw new IndexOutOfBoundsException("A koordinata a tabla hatarain kivulre esik.");
    	return board[a.getX()][a.getY()];
    }
    
    private boolean spawnComponent(Components a) {
    	if(this.current != null) return false;
    	Component temp = new Component(a, spawn_pos);
    	for(Coordinate c : temp.getCoords()) {
    		if(getFieldColor(c) != Colors.empty) return false;
    	}
    	this.current = temp;
    	return true;
    }
    
    public void resetCurrentComponentPosition() {
    	if(this.current == null) return;
    	Components temp = current.getType();
    	eraseCurrentComponent();
    	current = null;
    	spawnComponent(temp);
    	renderCurrentComponent();
    	repaint();
    }
    
    private void eraseCurrentComponent() {
    	if(current == null) return;
    	Coordinate[] temp = current.getCoords();
    	for(Coordinate a : temp) {
    		int[] arr = a.getAsArray();
    		board[arr[0]][arr[1]] = Colors.empty;
    	}
    }
    
    private void renderCurrentComponent() {
    	if(current == null) return;
    	Coordinate[] temp = current.getCoords();
    	for(Coordinate a : temp) {
    		int[] arr = a.getAsArray();
    		board[arr[0]][arr[1]] = component_colors.get(current.getType());
    	}
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        int x = startSquareX;
        int y = startSquareY;
        Dimension size = getSize();
        System.out.println("%f, %f".formatted(size.getHeight(), size.getWidth()));
        squareH = (int)size.getHeight() / board[0].length;
        squareW = squareH;
        
        //renderCurrentComponent();
        
        for(Colors[] a : board) {
        	for(Colors b : a) {
        		g.drawRect(x,y,squareW,squareH);
        		if(b != Colors.empty) {
        			
        			g.setColor(color_map.get(b));
        			g.fillRect(x + 1,y + 1,squareW - 1,squareH - 1);
        			g.setColor(Color.WHITE);
        		}
        		y += squareW;
        	}
        	y = startSquareY;
        	x += squareH;
        }
        // Varhato esesi pozicio kirajzolasa
        if(current != null) {
        	int h = generate_placement_outline();
        	if(h != 0) {
        		Component temp = current.shadow_move(new Coordinate(0, h));
        		// RGBA alfa csokkentese bitvarazslattal.
        		g.setColor(new Color(color_map.get(component_colors.get(temp.getType())).getRGB() & 0x3FFFFFFF, true));
        		for(Coordinate cord : temp.getCoords()) {
        			g.fillRect(startSquareX + 1 + cord.getX() * squareW, startSquareY + 1 + cord.getY()  * squareH, squareW - 1,squareH - 1);
        		}
        		g.setColor(Color.BLACK);
        	}
        }
    }
    
    private boolean checkIfNewPlaceIsEmpty(Component b) {
    	for(Coordinate a : b.getCoords()) {
    		if(getFieldColor(a) != Colors.empty) return false;
    	}
    	return true;
    }
    private void removeFullRows() {
    	eraseCurrentComponent();
    	int counter = 0;
    	for(int i = 0; i < board_height; i++) {
    		boolean good = true;
    		for(int j = 0; j < board_width; j++) {
    			if(board[j][i] == Colors.empty) {
    				good = false;
    				break;
    			}
    		}
    		if(good) {
    			counter++;
    			deleteRow(i);
    		}
    	}
    	renderCurrentComponent();
    	repaint();
    	calculateScore(counter);
    }
    private boolean checkIfNewPlaceIsInBounds(Component b) {
    	for(Coordinate a : b.getCoords()) {
    		if(a.getX() < 0 || a.getX() > board_width - 1 || a.getY() < 0 || a.getY() > board_height - 1)
    			return false;
    	}
    	return true;
    }
    
    private void deleteRow(int k) {
    	for(int i = 0; i < board_width; i++) {
    		board[i][k] = Colors.empty;
    	}
    	for(int i = k; i > 0; i--) {
    		for(int j = 0; j < board_width; j++) {
    			board[j][i] = board[j][i - 1];
    		}
    	}
    	for(int i = 0; i < board_width; i++) {
    		board[i][0] = Colors.empty;
    	}
    }
    public void control_move_right() {
    	if(current == null) return;
    	for(Coordinate b : current.getCoords()) {
    		if (b.isInSameRowOrColumn(new Coordinate(board_width - 1, -1))) return;
    	}
    	eraseCurrentComponent();
    	if(checkIfNewPlaceIsEmpty(current.shadow_move(new Coordinate(1, 0))))
    		current.move_right(1);
    	renderCurrentComponent();
    	repaint();
    }
    
    public void control_move_left() {
    	if(current == null) return;
    	for(Coordinate b : current.getCoords()) {
    		if (b.isInSameRowOrColumn(new Coordinate(0, -1))) return;
    	}
    	eraseCurrentComponent();
    	if(checkIfNewPlaceIsEmpty(current.shadow_move(new Coordinate(-1, 0))))
    		current.move_left(1);
    	renderCurrentComponent();
    	repaint();
    }
    public void control_rotate_left() {
    	if(current == null || current.getType() == Components.square) return;
    	eraseCurrentComponent();
    	Component temp = current.shadow_rotate_left();
    	if(checkIfNewPlaceIsInBounds(temp) && checkIfNewPlaceIsEmpty(temp))
    		current.rotate_left();
    	renderCurrentComponent();
    	repaint();
    }
    public void control_rotate_right() {
    	if(current == null|| current.getType() == Components.square) return;
    	eraseCurrentComponent();
    	Component temp = current.shadow_rotate_right();
    	if(checkIfNewPlaceIsInBounds(temp) && checkIfNewPlaceIsEmpty(temp))
    		current.rotate_right();
    	renderCurrentComponent();
    	repaint();
    }
    
    public void control_move_place() {
    	if(current == null) return;
    	if(checkCollision(current)) return;
    	int needed = 0;
    	for(int i = 1; i < board_height; i++) {
    		Component temp = current.shadow_move(new Coordinate(0, i));
    		if(checkCollision(temp)) {
    			needed = i;
    			break;
    		}
    	}
    	
    	if(needed == 0) return;
    	eraseCurrentComponent();
    	current.move_down(needed);
    	renderCurrentComponent();
    	repaint();
    }
    
    private int generate_placement_outline() {
    	if(current == null) return 0;
    	if(checkCollision(current)) return 0;
    	int needed = 0;
    	for(int i = 1; i < board_height; i++) {
    		Component temp = current.shadow_move(new Coordinate(0, i));
    		if(checkCollision(temp)) {
    			needed = i;
    			break;
    		}
    	}
    	return needed;
    }
}
