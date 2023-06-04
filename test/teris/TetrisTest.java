package teris;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tetris.Tetris;

public class TetrisTest {

	Tetris tetris;
	
	@Before
	public void setUp() {
		tetris = new Tetris();
	}
	@Test
	public void testStartGame() {
		int tetris_y_1 = tetris.getSize().height;
		tetris.getStartGameButton().doClick();
		int tetris_y_2 = tetris.getSize().height;
		Assert.assertNotEquals(tetris_y_1, tetris_y_2);
	}
	@Test
	public void testLeadreBoardGame() {
		int tetris_y_1 = tetris.getSize().height;
		tetris.getLeaderboardButton().doClick();
		int tetris_y_2 = tetris.getSize().height;
		Assert.assertEquals(tetris_y_1, tetris_y_2);
	}
	

}
