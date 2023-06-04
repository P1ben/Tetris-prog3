package tetris;

import board.Board;
import enums.Components;
import leaderboard.Leaderboard;
import windows.GameWindow;
import windows.LeaderboardPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Tetris extends JFrame {
	
	JPanel main_menu;
	GameWindow gamePanel;
	JButton start_game_btn;
	JButton exit_game_btn;
	JButton leaderboard_btn;
	Leaderboard leaderboard;
	LeaderboardPanel lbp;
	JLabel error_text;
	
	class exit_game_listener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
        	//System.out.println("MENTÉS");
        	close_window();
        };
    };
    class start_game_listener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
        	//System.out.println("MENTÉS");
        	start_game();
        };
    };
    class leaderboard_listener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
        	//System.out.println("MENTÉS");
        	load_leaderboard();
        	show_leaderboard();
        }
    };
	
	public Tetris(){
		super("Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		//setLayout(null);
		start_game_btn = new JButton("Start Game");
		exit_game_btn = new JButton("Exit Game");
		leaderboard_btn = new JButton("Leaderboard");
		
		JPanel logo_panel = new JPanel();
		JLabel logo = new JLabel("TETRIS");
		
		logo_panel.add(logo, BorderLayout.CENTER);
		logo_panel.setBounds(0, 50, 400, 300);
		
		logo.setFont(new Font("Comic Sans MS", Font.BOLD, 100));
		
		main_menu = new JPanel(null);
		JPanel button_panel = new JPanel(new GridBagLayout());
		
		
		main_menu.setBackground(Color.gray);
		logo_panel.setBackground(Color.gray);
		button_panel.setBackground(Color.gray);
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
		
        error_text = new JLabel("");
        error_text.setForeground(Color.red);
        JPanel error = new JPanel();
        error.setBackground(Color.gray);
        error.add(error_text, BorderLayout.CENTER);
        error.setBounds(0, 550, 400, 50);
        
        start_game_btn.addActionListener(new start_game_listener());
        exit_game_btn.addActionListener(new exit_game_listener());
        start_game_btn.setFocusable(false);
        exit_game_btn.setFocusable(false);
        leaderboard_btn.setFocusable(false);
        
		main_menu.setBounds(0, 0, 400, 600);
		main_menu.setPreferredSize(new Dimension(400, 600));
		button_panel.setBounds(0, 300, 400, 200);
		//this.setBounds(0, 0, 400, 600);
		button_panel.add(start_game_btn, gbc);
		button_panel.add(leaderboard_btn, gbc);
		button_panel.add(exit_game_btn, gbc);
		main_menu.add(button_panel);
		main_menu.add(logo_panel);
		main_menu.add(error);
		
		leaderboard_btn.addActionListener(new leaderboard_listener());
		
		this.setSize(416, 600);
        
        
		//GameWindow gamePanel = new GameWindow();
		this.add(main_menu);
		//this.add(gamePanel);
		this.pack();
	}
	private void start_game() {
		//this.removeAll();
		main_menu.setVisible(false);
		gamePanel = new GameWindow();
		this.add(gamePanel);
		gamePanel.setVisible(true);
		this.pack();
		gamePanel.requestFocus();
	}
	public void back_to_menu() {
		if(gamePanel != null) {
			gamePanel.setVisible(false);
			this.remove(gamePanel);
			main_menu.setVisible(true);
			gamePanel = null;
		}
		if(lbp != null) {
			this.remove(lbp);
			lbp = null;
			leaderboard = null;
			main_menu.setVisible(true);
		}
		
		//this.revalidate();
		this.setSize(416, 630);
		error_text.setText("");
	}
	private void load_leaderboard() {
		try {
			 lbp = null;
			 leaderboard = null;
	         FileInputStream fileIn = new FileInputStream("leaderboard.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         leaderboard = (Leaderboard) in.readObject();
	         in.close();
	         fileIn.close();
	         lbp = new LeaderboardPanel(leaderboard);
	         System.out.println("Beolvasva");
	      } catch (IOException e) {
	         error_text.setText("Nem sikerült a leaderboard beolvasása.");
	         
	      } catch (ClassNotFoundException e) {
	         e.printStackTrace();
	      }
	}
	private void show_leaderboard() {
		if(lbp != null) {
			main_menu.setVisible(false);
			this.add(lbp);
		}
        
	}
	private void close_window() {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public JButton getStartGameButton() {
		return start_game_btn;
	}
	public JButton getLeaderboardButton() {
		return leaderboard_btn;
	}

}
