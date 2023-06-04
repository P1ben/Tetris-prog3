package leaderboard;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Leaderboard implements Serializable {
	
	List<NameScore> lista = new LinkedList<NameScore>();
	
	class NameScore implements Serializable {
		public String name;
		public int score;
	}
	
	public void add(String name, int score) {
		NameScore a = new NameScore();
		a.name = name;
		a.score = score;
		lista.add(a);
		Collections.sort(lista, new Comparator<NameScore>() {
		    @Override
		    public int compare(NameScore lhs, NameScore rhs) {
		        return lhs.score > rhs.score ? -1 : (lhs.score < rhs.score) ? 1 : 0;
		    }
		});
		while(lista.size() > 10) {
			lista.remove(10);
		}
	}
	public int len() {
		return lista.size();
	}
	public List<Integer> getScores() {
		List<Integer> b = new ArrayList<Integer>();
		for(NameScore a : lista) {
			b.add(a.score);
		}
		return b;
	}
	public List<JPanel> getAllPanels(int width) {
		List<JPanel> panels = new ArrayList<JPanel>();
		int pos = 1;
		for(NameScore a : lista) {
			JPanel temp = new JPanel(null);
			
			temp.setBounds(0, 0, width, 30);
			
			JLabel left_text = new JLabel(String.valueOf(pos), SwingConstants.CENTER);
			JLabel center_text = new JLabel(a.name, SwingConstants.CENTER);
			JLabel right_text = new JLabel(String.valueOf(a.score), SwingConstants.CENTER);
			
			left_text.setBounds(0, 0, (width / 10) * 2, 30);
			center_text.setBounds((width / 10) * 2, 0, (width / 10) * 5, 30);
			right_text.setBounds((width / 10) * 7, 0, (width / 10) * 3, 30);
			
			//temp.setLayout(new BoxLayout(temp, BoxLayout.LINE_AXIS));
			temp.add(left_text);
			temp.add(center_text);
			temp.add(right_text);
			pos++;
			panels.add(temp);
		}
		return panels;
	}
};
