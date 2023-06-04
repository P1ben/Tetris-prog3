package board;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import component.Component;

public class BoardTest {
	
	Board board;
	
	@Before
	public void setUp() {
		board = new Board(10, 20);
	}
	
	@Test
	public void testNextComponent() {
		Assert.assertNotNull(board.getNextComponent());
	}
	@Test
	public void testGetScore() {
		Assert.assertEquals(board.getScore(), 0);
	}
	@Test
	public void testComponentStart() {
		Assert.assertNull(board.getCurrComponent());
	}
	@Test
	public void testComponentAfterUpdate() {
		board.updateBoard();
		Assert.assertNotNull(board.getCurrComponent());
	}
	@Test
	public void testComponentAfterLeftMove() {
		int pos1;
		int pos2;
		board.updateBoard();
		pos1 = board.getCurrComponent().getCoords()[0].getX();
		board.control_move_left();
		pos2 = board.getCurrComponent().getCoords()[0].getX();
		Assert.assertEquals(pos1, pos2 + 1);
	}
	@Test
	public void testComponentAfterRightMove() {
		int pos1;
		int pos2;
		board.updateBoard();
		pos1 = board.getCurrComponent().getCoords()[0].getX();
		board.control_move_right();
		pos2 = board.getCurrComponent().getCoords()[0].getX();
		Assert.assertEquals(pos1, pos2 - 1);
	}
	@Test
	public void testComponentAfterPlaceMove() {
		int pos1;
		int pos2;
		board.updateBoard();
		pos1 = board.getCurrComponent().getCoords()[0].getY();
		board.control_move_place();
		pos2 = board.getCurrComponent().getCoords()[0].getY();
		Assert.assertNotEquals(pos1, pos2);
	}
	@Test
	public void testComponentAfterSpinRightMove() {
		int pos1;
		int pos2;
		board.updateBoard();
		pos1 = board.getCurrComponent().getCoords()[0].getY();
		board.control_rotate_right();
		pos2 = board.getCurrComponent().getCoords()[0].getY();
		Assert.assertEquals(pos1, pos2);
	}
	@Test
	public void testComponentAfterSpinLeftMove() {
		int pos1;
		int pos2;
		board.updateBoard();
		pos1 = board.getCurrComponent().getCoords()[0].getY();
		board.control_rotate_left();
		pos2 = board.getCurrComponent().getCoords()[0].getY();
		Assert.assertEquals(pos1, pos2);
	}
	@Test
	public void testComponentAutoFall() {
		int pos1;
		int pos2;
		board.updateBoard();
		pos1 = board.getCurrComponent().getCoords()[0].getY();
		board.updateBoard();
		pos2 = board.getCurrComponent().getCoords()[0].getY();
		Assert.assertNotEquals(pos1, pos2);
	}
	@Test
	public void testComponentPlace() {
		Component a;
		Component b;
		board.updateBoard();
		a = board.getCurrComponent();
		for(int i = 0; i < 30; i++) {
			board.updateBoard();
		}
		b = board.getCurrComponent();
		Assert.assertNotSame(a, b);
	}

}
