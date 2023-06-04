package leaderboard;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class LeaderboardTest {

	Leaderboard leaderboard;
	
	@Before
	public void setUp() {
		leaderboard = new Leaderboard();
	}
	@Test
	public void testMaxSize() {
		for(int i = 0; i < 100; i++) {
			leaderboard.add("Béla", 10);
		}
		Assert.assertEquals(leaderboard.len(), 10);
	}
	@Test
	public void testOrderSize() {
		for(int i = 0; i < 100; i++) {
			leaderboard.add("Béla", i);
		}
		List<Integer> list = leaderboard.getScores();
		Assert.assertEquals(list.get(0) + 0, list.get(9) + 9);
	}

}
