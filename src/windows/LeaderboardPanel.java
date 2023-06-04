package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import leaderboard.Leaderboard;
import tetris.Tetris;



public class LeaderboardPanel extends JPanel {
	Leaderboard leaderboard;
	
	class back_listener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
        	//System.out.println("MENTÉS");
        	back_to_menu();
        };
    };
	
	public LeaderboardPanel(Leaderboard lead){
		leaderboard = lead;
		setLayout(null);
		this.setBounds(0, 0, 400, 600);
		List<JPanel> panels = leaderboard.getAllPanels(340);
		int pos_y = 20;
		for(JPanel p : panels) {
			p.setBounds(30, pos_y, 340, 30);
			this.add(p);
			pos_y += 30;
		}
		JButton back = new JButton("Back");
		back.addActionListener(new back_listener());
		back.setBounds(30, 540, 340, 30);
		this.add(back);
	}
	public void back_to_menu() {
		((Tetris)SwingUtilities.getRoot(this)).back_to_menu();
	}
}
