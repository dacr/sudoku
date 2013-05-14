package com.sodoku;

import java.util.List;

import javax.swing.JFrame;

import junit.framework.*;

public class SodokuTest extends TestCase {
	
	// --------------------------------------------------------------------------
	String sampleSimple=
		"Téléstar\n" +
		"EASY\n" +
		"\n" +
		"1xx 83x xx2\n" +
		"57x xx1 xxx\n" +
		"xxx 5x9 x64\n" +
		"\n"+
		"7x4 xx8 59x\n" +
		"xx3 x1x 4xx\n" +
		"x51 4xx 3x6\n" +
		"\n" +
		"36x 7x4 xxx\n" +
		"xxx 6xx x79\n" +
		"8xx x52 xx3\n" +
		"\n";
	String sampleSimpleSoluce=
		"Téléstar\n" +
		"EASY\n" +
		"\n" +
		"149 836 752\n" +
		"576 241 938\n" +
		"238 579 164\n" +
		"\n"+
		"724 368 591\n" +
		"683 915 427\n" +
		"951 427 386\n" +
		"\n" +
		"362 794 815\n" +
		"415 683 279\n" +
		"897 152 643\n" +
		"\n";

	// --------------------------------------------------------------------------

	String sampleMedium=
		"Téléstar\n" +
		"MEDIUM\n" +
		"\n" +
		"x7x 1x3 x6x\n" +
		"x5x xxx x7x\n" +
		"3xx x5x xx1\n" +
		"\n"+
		"5xx 3x4 xx8\n" +
		"4x7 xxx 1x2\n" +
		"9xx 7x2 xx4\n" +
		"\n" +
		"2xx x7x xx3\n" +
		"x3x xxx x4x\n" +
		"x6x 5x9 x2x\n" +
		"\n";
	String sampleMediumSoluce=
		"Téléstar\n" +
		"MEDIUM\n" +
		"\n" +
		"872 193 465\n" +
		"651 248 379\n" +
		"349 657 281\n" +
		"\n"+
		"526 314 798\n" +
		"487 965 132\n" +
		"913 782 654\n" +
		"\n" +
		"298 476 513\n" +
		"735 821 946\n" +
		"164 539 827\n" +
		"\n";
	// --------------------------------------------------------------------------
	String[] games = {
		"1 - Téléstar\n" +
		"DIFFICULT\n" +
		"\n" +
		"xx8 x9x xxx\n" +
		"x7x xxx 28x\n" +
		"x64 1xx 3x9\n" +
		"\n"+
		"xxx 8x5 9xx\n" +
		"5xx xxx xx1\n" +
		"xx9 3x4 xxx\n" +
		"\n" +
		"8x2 xx7 56x\n" +
		"x97 xxx x1x\n" +
		"xxx x6x 7xx\n" +
		"\n"
		,
		"2 - Téléstar\n" +
		"HARD\n" +
		"\n" +
		"x1x xx8 4x7\n" +
		"95x xxx xxx\n" +
		"xx8 x1x xxx\n" +
		"\n"+
		"x82 xxx xxx\n" +
		"7xx 4x6 xx8\n" +
		"xxx xxx 62x\n" +
		"\n" +
		"xxx x5x 7xx\n" +
		"xxx xxx x82\n" +
		"5x3 2xx x1x\n" +
		"\n"
		,
		"3 - Sciences et Avenir - Août 2005\n" +
		"DIFFICULT\n" +
		"\n" +
		"xx5 xx8 xx1\n" +
		"3xx 62x x4x\n" +
		"xxx x5x 7xx\n" +
		"\n"+
		"8xx xx5 1x9\n" +
		"xxx xxx xxx\n" +
		"2x6 3xx xx8\n" +
		"\n" +
		"xx4 x8x xxx\n" +
		"x9x x67 xx3\n" +
		"1xx 4xx 9xx\n" +
		"\n"
		,
		"4 - Sciences et Avenir - Août 2005\n" +
		"DIFFICULT\n" +
		"\n" +
		"x1x 5xx 2xx\n" +
		"x38 xxx 6xx\n" +
		"xxx xx6 xxx\n" +
		"\n"+
		"x8x x5x xxx\n" +
		"92x x3x x67\n" +
		"xxx x4x x9x\n" +
		"\n" +
		"xxx 1xx xxx\n" +
		"xx7 xxx 95x\n" +
		"xx3 xx2 x1x\n" +
		"\n"
		,
		"Téléstar-1507\n" +
		"DIFFICULT\n" +
		"\n" +
		"xx7 238 xxx\n" +
		"x6x 7xx x5x\n" +
		"xxx 4xx xx2\n" +
		"\n"+
		"9xx xxx 867\n" +
		"1xx xxx xx3\n" +
		"648 xxx xx5\n" +
		"\n" +
		"7xx xx3 xxx\n" +
		"x2x xx5 x3x\n" +
		"xxx 174 9xx\n" +
		"\n"
		,
		"Téléstar-1507\n" +
		"HARD\n" +
		"\n" +
		"xxx 89x x2x\n" +
		"xx9 xx5 xx7\n" +
		"x5x xxx 3xx\n" +
		"\n"+
		"x93 5xx 1xx\n" +
		"xxx 1x7 xxx\n" +
		"xx1 xx6 84x\n" +
		"\n" +
		"xx8 xxx x6x\n" +
		"9xx 6xx 4xx\n" +
		"x1x x28 xxx\n" +
		"\n"
	};
	// --------------------------------------------------------------------------
	String sampleEMPTY=
		"EMPTY\n" +
		"HARD\n" +
		"\n" +
		"xxx xxx xxx\n" +
		"xxx xxx xxx\n" +
		"xxx xxx xxx\n" +
		"\n"+
		"xxx xxx xxx\n" +
		"xxx xxx xxx\n" +
		"xxx xxx xxx\n" +
		"\n" +
		"xxx xxx xxx\n" +
		"xxx xxx xxx\n" +
		"xxx xxx xxx\n" +
		"\n";
	// --------------------------------------------------------------------------

	public void testEmptyGame() {
		Game game = new Game();
		assertFalse(game.isFinished());
		assertTrue(game.isValid());
		assertTrue(9*9 == game.getEmptyCellCount());
		assertTrue(0 == game.getFilledCellCount());
		
		game.getCell(0,0).play(3);
		assertTrue(9*9-1 == game.getEmptyCellCount());
		assertTrue(1 == game.getFilledCellCount());
		
	}
	
	public void testSamples() {
		GameGenerator gg = new GameGeneratorBean();
		assertTrue(gg.generateFromString(sampleSimple).isValid());
		assertTrue(gg.generateFromString(sampleSimpleSoluce).isValid());
		assertTrue(gg.generateFromString(sampleSimpleSoluce).isFinished());
		assertTrue(gg.generateFromString(sampleMedium).isValid());
		assertTrue(gg.generateFromString(sampleMediumSoluce).isValid());
		assertTrue(gg.generateFromString(sampleMediumSoluce).isFinished());
		for(String gameDesc: games) {
			Game g = gg.generateFromString(gameDesc);
			assertTrue(g.isValid());
		}
	}
	
	public void testSampleSimple() {
		GameGenerator gg = new GameGeneratorBean();
		Game game = gg.generateFromString(sampleSimple);
		assertTrue(game.isValid());
		assertTrue(game.getCell(8,8).getValue() == 3);
		assertTrue(game.getCell(0,3).getValue() == 7);
		assertTrue(game.getCell(4,4).getValue() == 1);
		int emptyCellCount = game.getEmptyCellCount();

		// Duplicate value on line
		game.getCell(1,0).play(3);
		assertFalse(game.isValid());
		game.getCell(1,0).clear();

		// Duplicate value on column
		game.getCell(4,5).play(3);
		assertFalse(game.isValid());
		game.getCell(4,5).clear();

		// Duplicate value on area
		game.getCell(2,2).play(1);
		assertFalse(game.isValid());
		game.getCell(2,2).clear();

		// The game must stay valid
		assertTrue(game.isValid());
		assertFalse(game.isFinished());
		assertTrue(emptyCellCount == game.getEmptyCellCount());
	}


	public void testPossibleValues() {
		GameGenerator gg = new GameGeneratorBean();
		Game game = gg.generateFromString(sampleSimple);
		List<Integer> values =  game.getCell(1,0).getPossibleValues();
		assertTrue(values.size() == 2);
		assertTrue(values.contains(new Integer(4)));
		assertTrue(values.contains(new Integer(9)));
	}
	
	public void testAutoPlaySimple() {
		GameGenerator gg = new GameGeneratorBean();
		Game game = gg.generateFromString(sampleSimple);
		assertTrue(game.solve());
		assertTrue(game.getEmptyCellCount()==0);
		assertTrue(game.isValid());
	}

	public void testAutoPlayMedium() {
		GameGenerator gg = new GameGeneratorBean();
		Game game = gg.generateFromString(sampleMedium);
		boolean result = game.solve();
		assertTrue("Game not solved", result);
		assertTrue(game.getEmptyCellCount()==0);
		assertTrue(game.isValid());
	}
	
	public void testAutoPlayAllGames() {
		GameGenerator gg = new GameGeneratorBean();
		for(String gameDesc: games) {
			Game game = gg.generateFromString(gameDesc);
			int filledCountBefore = game.getFilledCellCount();
			boolean result = game.solve();
			int filledCountAfter = game.getFilledCellCount();
			String message =String.format("Game not solved : %d cell played\n%s\n", 
											filledCountAfter - filledCountBefore,
											game.toString());
			assertTrue(message, result);
			assertTrue(game.getEmptyCellCount()==0);
			assertTrue(game.isValid());
		}
	}
	
	public void testGUI() throws InterruptedException {
		GameGenerator gg = new GameGeneratorBean();
		Game game = gg.generateFromString(games[1]);
		JFrame f = new GUIFrame(game);
		//Thread.sleep(2000);
		//game.solve();
		while(f.isVisible()) {
			Thread.sleep(1000);
		}
	}
	
	public void dumpPossibleValues(Game game) {
		for(int x=0; x<9; x++) {
			for(int y=0; y<9; y++) {
				Cell cell = game.getCell(x,y);
				List<Integer> pvalues = cell.getPossibleValues();
				StringBuffer pvstr = new StringBuffer();
				for(Integer i: pvalues) {
					pvstr.append(i.toString());
					pvstr.append(" ");
				}
				char isEmpty    = (cell.isEmpty())?'E':'e';
				char isImmuable = (cell.isImmuable())?'I':'i';
				System.out.format("%d-%d %c%c= %s\n", x, y, isEmpty, isImmuable, pvstr);
			}
		}
	}
}
