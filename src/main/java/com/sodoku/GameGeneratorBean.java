package com.sodoku;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameGeneratorBean implements GameGenerator {
	public GameGeneratorBean() {
	}
	
	public Game generateFromString(String gamedesc) {
		Game game = new Game();
		String[] lines = gamedesc.split("\\r?\\n",3);
		game.setName(lines[0]);
		game.setLevel(Level.valueOf(lines[1]));

		Pattern p = Pattern.compile("[0-9xX]{3}");
		Matcher m = p.matcher(lines[2]);
		int x=0, y=0;
		while(m.find()) {
			String sub = m.group();
			for(char c: sub.toCharArray()) {
				if (c!='x' && c!='X') {
					game.getCell(x,y).initialValue(Integer.valueOf(""+c));
				}
				x++;
				if (x % 9 == 0) {
					y++;
					x=0;
				}
			}
		}
		return game;
	}
}
