package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import board.Board;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import enums.Colors;
import enums.Components;
import leaderboard.Leaderboard;
import enums.BoardEvents;
import misc.Coordinate;
import tetris.Tetris;

public class GameWindow extends JPanel {
	
	class tick_listener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
        	BoardEvents ev =  board.updateBoard();
        	if(ev == BoardEvents.ended) {
        		System.out.println("ENd");
        		timer.stop();
        		key_listener_enabled = false;
        		board = null;
        		render_name_input_box();
        	}
        	else if(ev == BoardEvents.placed) {
        		System.out.println("Placed");
        		int old_score = score;
        		score = board.getScore();
        		
        		if(score/10000 > old_score/10000) {
        			System.out.println("---------------------------");
        			if(score <= 100000) currentSpeed = initialSpeed - ((initialSpeed - maxSpeed) / 10) * (score / 10000);
        			else currentSpeed = maxSpeed;
        			timer.setDelay(currentSpeed);
        			timer.restart();
        			System.out.println("/Delay: " + currentSpeed + "/");
        		}
        		
        		score_label_score.setText(String.valueOf(score));
        		timer.restart();
        	}
        	else if(ev == BoardEvents.spawned) {
        		System.out.println("Spawned ");
        		next_pan.switch_component(board.getNextComponent());
        	}
        };
    };
    class save_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
        	System.out.println("MENTÉS");
        	save_game();
        };
    };
    class load_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
        	System.out.println("MENTÉS");
        	load_game();
        	board.resetCurrentComponentPosition();
        	score = board.getScore();
        	score_label_score.setText(String.valueOf(score));
        	if(score <= 100000) currentSpeed = initialSpeed - ((initialSpeed - maxSpeed) / 10) * (score / 10000);
			else currentSpeed = maxSpeed;
			timer.setDelay(currentSpeed);
        	board.repaint();
        };
    };
    class pause_resume_listener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
        	System.out.println("PAUSE");
        	if(pause_resume_btn.getText().equals("Pause")) {
        		save_btn.setVisible(true);
        		load_btn.setVisible(true);
        		back_btn.setVisible(true);
        		timer.stop();
        		pause_resume_btn.setText("Resume");
        		key_listener_enabled = false;
        	}
        	else if(pause_resume_btn.getText().equals("Resume")) {
        		save_btn.setVisible(false);
        		load_btn.setVisible(false);
        		back_btn.setVisible(false);
        		timer.setInitialDelay(500);
        		timer.start();
        		timer.setInitialDelay(0);
        		pause_resume_btn.setText("Pause");
        		key_listener_enabled = true;
        	}
        	board.setFocusable(true);
        };
    };
    class back_listener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
        	//System.out.println("MENTÉS");
        	back_to_menu();
        };
    };
    class score_save_listener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
        	//System.out.println("MENTÉS");
        	if(text_field.getText().length() > 20) {
        		error_label.setText("A megadott név túl hosszú, adj meg egy másikat.");
        	}
        	else if(text_field.getText().equals(""))
        		back_to_menu();
        	else {
        		save_score();
        		back_to_menu();
        	}
        };
    };
    
    private int score = 0;
    private final int initialSpeed = 800;
    private final int maxSpeed = 100;
    private int currentSpeed;
    
    private Timer timer;
    
    private JLabel error_label;
    JTextField text_field;
    
	private Board board;
	private NextComponentPanel next_pan;
	private JLabel score_label_score;
	JButton pause_resume_btn;
	JButton save_btn;
	JButton load_btn;
	JButton back_btn;
	boolean key_listener_enabled = true;
	
	public GameWindow(){
		super();
		this.board = new Board(10, 20);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(410, 651));
		
		JPanel next_component = new JPanel(null);
		
		next_pan = new NextComponentPanel(Components.cross);

		JPanel score_panel = new JPanel(new GridBagLayout());
		JLabel score_label_text = new JLabel("Score: ");
		score_label_score = new JLabel("0");
		JPanel button_panel = new JPanel(new GridBagLayout());
		button_panel.setBounds(300, 160, 110, 490);
		save_btn = new JButton("save");
		load_btn = new JButton("load");
		back_btn = new JButton("back");
	    pause_resume_btn = new JButton("Pause");
		
	    save_btn.setFocusable(false);
	    load_btn.setFocusable(false);
	    back_btn.setFocusable(false);
	    pause_resume_btn.setFocusable(false);
	    
		score_panel.setBounds(0, 0, 410, 50);
		next_pan.setLayout(null);
		next_pan.setBounds(0, 0, 110, 110);
		board.setBounds(0, 50, 300, 600);
		next_component.setBackground(Color.gray);
		
		next_component.setBounds(300, 50, 110, 110);
		
		score_panel.add(score_label_text);
		score_panel.add(score_label_score);
		next_component.add(next_pan);
		
		
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
		
        save_btn.addActionListener(new save_button_listener());
        load_btn.addActionListener(new load_button_listener());
        pause_resume_btn.addActionListener(new pause_resume_listener());
        back_btn.addActionListener(new back_listener());
        
        button_panel.add(pause_resume_btn, gbc);
		button_panel.add(save_btn, gbc);
		button_panel.add(load_btn, gbc);
		button_panel.add(back_btn, gbc);
		save_btn.setVisible(false);
		load_btn.setVisible(false);
		back_btn.setVisible(false);
		
		this.add(button_panel);
		this.add(board);
		this.add(next_component);
		this.add(score_panel);
		
        timer = new Timer(initialSpeed, new tick_listener());
		timer.setInitialDelay(0);
        timer.setRepeats(true);
        timer.start();
        
        setFocusable(true);
        
        this.addKeyListener(new Keychecker());
		
	}
	private void save_game() {
		try {
	         FileOutputStream fileOut = new FileOutputStream("game_save.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(board);
	         out.close();
	         fileOut.close();
	         System.out.printf("Játék elmentve.");
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   
	}
	private void save_score() {
		Leaderboard leaderboard;
		try {
	         	FileInputStream fileIn = new FileInputStream("leaderboard.ser");
	         	ObjectInputStream in = new ObjectInputStream(fileIn);
	         	leaderboard = (Leaderboard) in.readObject();
	         	leaderboard.add(text_field.getText(), score);
    	  		FileOutputStream fileOut = new FileOutputStream("leaderboard.ser");
    	  		ObjectOutputStream out = new ObjectOutputStream(fileOut);
    	  		out.writeObject(leaderboard);
 	        	out.close();
 	        	fileOut.close();
 	        	System.out.printf("Pontszám mentve.");
	         	in.close();
	         	fileIn.close();
	      } catch (IOException e) {
	    	  	try {
	    	  		leaderboard = new Leaderboard();
	    	  		leaderboard.add(text_field.getText(), score);
	    	  		FileOutputStream fileOut = new FileOutputStream("leaderboard.ser");
	    	  		ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    	  		out.writeObject(leaderboard);
	 	        	out.close();
	 	        	fileOut.close();
	 	        	System.out.printf("Pontszám mentve.");
	    	  	} catch (IOException ee) {
	    	  		ee.printStackTrace();
	    	  	}
	      } catch (ClassNotFoundException e) {
	         e.printStackTrace();
	      }
	}
	
	private void render_name_input_box() {
		JPanel c = new JPanel(null);
		c.setBounds(0, 100, 410, 651);
		JPanel label_panel_text = new JPanel();
		label_panel_text.setBounds(0, 25, 410, 25);
		JPanel label_panel_score = new JPanel();
		label_panel_score.setBounds(0, 0, 410, 25);
		JPanel text_field_panel = new JPanel();
		text_field_panel.setBounds(0, 50, 410, 25);
		JPanel button_panel = new JPanel();
		button_panel.setBounds(0, 75, 410, 100);
		JPanel label_panel_error = new JPanel();
		label_panel_error.setBounds(0, 175, 410, 25);
		
		error_label = new JLabel("");
		error_label.setForeground(Color.red);
		
		text_field = new JTextField(20);
		text_field.setMaximumSize(new Dimension(200, 20));
		JButton save_btn = new JButton("Ment");
		
		label_panel_error.add(error_label);
		label_panel_text.add(new JLabel("A pontszám mentéséhez add meg a neved:"));
		label_panel_score.add(new JLabel("Pontszám:   %d".formatted(score)));
		
		button_panel.add(save_btn);
		text_field_panel.add(text_field);
		
		save_btn.addActionListener(new score_save_listener());
		
		c.add(text_field_panel);
		c.add(label_panel_text);
		c.add(button_panel);
		c.add(label_panel_score);
		c.add(label_panel_error);
		this.removeAll();
		this.add(c);
		this.revalidate();
		repaint();
	}
	private void load_game() {
		try {
	         FileInputStream fileIn = new FileInputStream("game_save.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         board = (Board) in.readObject();
	         in.close();
	         fileIn.close();
	         this.add(board);
	         next_pan.switch_component(board.getNextComponent());
	      } catch (IOException e) {
	         e.printStackTrace();
	         return;
	      } catch (ClassNotFoundException e) {
	         e.printStackTrace();
	         return;
	      }
	}
	private void back_to_menu() {
		timer.stop();
    	((Tetris)SwingUtilities.getRoot(this)).back_to_menu();
	}
	public JButton[] getAllButtons() {
		return new JButton[] {pause_resume_btn, save_btn, load_btn, back_btn};
	}
	public Board getBoard() {
		return board;
	}
   class Keychecker extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event) {
        	int k = event.getKeyCode();
        	if(key_listener_enabled) {
        		switch(k) {
	            case KeyEvent.VK_RIGHT:
	            case KeyEvent.VK_D:
	            	board.control_move_right();
	            	break;
	            case KeyEvent.VK_LEFT:
	            case KeyEvent.VK_A:
	            	board.control_move_left();
	            	break;
	            case KeyEvent.VK_SPACE:
	            	board.control_move_place();
	            	timer.restart();
	            	break;
	            case KeyEvent.VK_UP:
	            case KeyEvent.VK_W:
	            	board.control_rotate_left();
	            	break;
	            case KeyEvent.VK_DOWN:
	            case KeyEvent.VK_S:
	            	board.control_rotate_right();
	            	break;
	            }
        	}
            
        }
    }
}
