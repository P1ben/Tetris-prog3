package windows;

import static org.junit.Assert.*;

import javax.swing.JButton;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import board.Board;

public class GameWindowTest {

	GameWindow gw;
	@Before
	public void setUp() {
		gw = new GameWindow();
	}
	@Test
	public void testPauseButtonVisibility() {
		JButton[] buttons = gw.getAllButtons();
		boolean save_btn_0 = buttons[1].isVisible();
		buttons[0].doClick();
		boolean save_btn_1 = buttons[1].isVisible();
		Assert.assertNotEquals(save_btn_0, save_btn_1);
	}
	@Test
	public void testSaveLoadBoard() {
		JButton[] buttons = gw.getAllButtons();
		buttons[0].doClick();
		buttons[1].doClick();
		Board a = gw.getBoard();
		buttons[2].doClick();
		Board b = gw.getBoard();
		Assert.assertNotSame(a, b);
	}
	/*
	@Test
	public void testEndGameBoardDelete() {
		JButton[] buttons = gw.getAllButtons();
		Board a = gw.getBoard();
		for(int i = 0; i < 30 && gw.getBoard() != null; i++) {
			a.control_move_place();
			gw.tick_listener.actionPerformed(null);
		}
		Board b = gw.getBoard();
		Assert.assertNull(b);
	}*/

}
