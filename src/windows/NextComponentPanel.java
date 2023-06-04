package windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import board.Board;
import component.Component;
import enums.Colors;
import enums.Components;
import misc.Coordinate;

public class NextComponentPanel extends JPanel{
	Components contained;
	Colors[][] field;
	
	public NextComponentPanel(Components curr){
		super();
		this.setBackground(Color.gray);
		field = new Colors[5][5];
		for(int i = 0; i < 5; i++) {
    		for(int j = 0; j < 5; j++) {
    			field[i][j] = Colors.empty;
    		}
    	}
		switch_component(curr);
	}
	public void switch_component(Components a) {
		this.contained = a;
		field = new Colors[5][5];
		for(int i = 0; i < 5; i++) {
    		for(int j = 0; j < 5; j++) {
    			field[i][j] = Colors.empty;
    		}
    	}
		int[][] pos = Component.component_dims.get(a);
		for(int[] b : pos) {
			field[2 + b[0]][2 + b[1]] = Board.component_colors.get(a);
		}
		repaint();
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
        g.setColor(Color.WHITE);
        int startSquareX = 0;
        int startSquareY = 0;
        
        int x = startSquareX;
        int y = startSquareY;
        Dimension size = getSize();
        System.out.println("%f, %f".formatted(size.getHeight(), size.getWidth()));
        int squareH = (int)size.getHeight() / field[0].length;
        int squareW = squareH;
        g.drawRect(0, 0, (int)size.getWidth() - 1 , (int)size.getHeight() - 1);
        
        //renderCurrentComponent();
        
        for(Colors[] a : field) {
        	for(Colors b : a) {
        		//g.drawRect(x,y,squareW,squareH);
        		if(b != Colors.empty) {
        			
        			g.drawRect(x,y,squareW,squareH);
        			g.setColor(Board.color_map.get(b));
        			g.fillRect(x + 1,y + 1,squareW - 1,squareH - 1);
        			g.setColor(Color.WHITE);
        		}
        		y += squareW;
        	}
        	y = startSquareY;
        	x += squareH;
        }
    }
}
