package com.sodoku;

import java.util.ArrayList;
import java.util.List;

public class Game implements CellListener {
	private String     name;
	private Level      level;
	private int        emptyCellCount;
	private int        filledCellCount;
	private Cell[][]   chessboard;
	private List<List<Cell>> lines, columns, areas;
	private List<Cell> cells;

	public Game() {
		level = Level.UNKNOWN;
		emptyCellCount=0;
		setFilledCellCount(0);
		setEmptyCellCount(9*9);
		chessboard = new Cell[9][9];

		lines   = new ArrayList<List<Cell>>(9);
		columns = new ArrayList<List<Cell>>(9);
		areas   = new ArrayList<List<Cell>>(9);
		cells   = new ArrayList<Cell>(9);

		for (int x=0; x<9; x++) {
			List<Cell> column = new ArrayList<Cell>(9);
			for(int y=0; y<9; y++) {
				Cell cell = new Cell(x, y, this);
				column.add(cell);
				cell.setCellsOnColumn(column);
				chessboard[x][y] = cell;
				cells.add(cell);
			}
			columns.add(column);
		}
		for(int y=0; y<9; y++) {
			List<Cell> line = new ArrayList<Cell>(9);
			for (int x=0; x<9; x++) {
				Cell cell = getCell(x, y);
				line.add(cell);
				cell.setCellsOnLine(line);
			}
			lines.add(line);
		}
		for(int a=0; a<9; a++) {
			List<Cell> area = new ArrayList<Cell>(9);
			for(int yr=0; yr<3; yr++) {
				for(int xr=0; xr<3; xr++) {
					int x = (a % 3) * 3 + xr;
					int y = (a / 3) * 3 + yr;
					Cell cell = getCell(x,y);
					area.add(cell);
					cell.setCellsOnArea(area);
				}
			}
			areas.add(area);
		}
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public Level getLevel() {
		return level;
	}
	public void setChessboard(Cell[][] chessboard) {
		this.chessboard = chessboard;
	}
	public Cell[][] getChessboard() {
		return chessboard;
	}
	public Cell getCell(int x, int y) {
		return chessboard[x][y];
	}
	public boolean isFinished() {
		return isValid() && (getEmptyCellCount()==0);
	}
	public boolean isValid() {
		List<Integer> foundValues = new ArrayList<Integer>(9);

		// Lines check
		for(List<Cell> line: lines) {
			foundValues.clear();
			for (Cell cell: line) {
				if (cell.isEmpty()) continue;
				Integer i = new Integer(cell.getValue());
				if (foundValues.contains(i)) {
					return false;
				} else {
					foundValues.add(i);
				}
			}	
		}
		// Columns check
		for (List<Cell> column: columns) {
			foundValues.clear();
			for(Cell cell: column) {
				if (cell.isEmpty()) continue;
				Integer i = new Integer(cell.getValue());
				if (foundValues.contains(i)) {
					return false;
				} else {
					foundValues.add(i);
				}
			}
		}
		// Areas check
		for(List<Cell> area: areas) {
			foundValues.clear();
			for(Cell cell: area) {
				if (cell.isEmpty()) continue;
				Integer i = new Integer(cell.getValue());
				if (foundValues.contains(i)) {
					return false;
				} else {
					foundValues.add(i);
				}
			}
		}
		return true;
	}

	public void cellValueCleared(Cell cell) {
		emptyCellCount++;
	}
	public void cellValueInitialized(Cell cell) {
		emptyCellCount--;
		filledCellCount++;
	}
	public void cellValueModified(Cell cell, int previousValue) {
	}

	public void setFilledCellCount(int playedCellCount) {
		this.filledCellCount = playedCellCount;
	}
	public int getFilledCellCount() {
		return filledCellCount;
	}
	public int getEmptyCellCount() {

		return emptyCellCount;
	}
	public void setEmptyCellCount(int emptyCellCount) {
		this.emptyCellCount = emptyCellCount;
	}
	
	public void reset() {
		for(Cell cell: cells) {
			if (!cell.isImmuable() && !cell.isEmpty()) {
				cell.clear();
			}
		}
	}
	
	public boolean solve() {
		boolean solved=false;
		while(!solved) {
			
			int playCount=0;
			// Heuristique 1 : On cherche des cases où une seule valeur est possible
			for(int x=0; x<9; x++) {
				for(int y=0; y<9; y++) {
					Cell cell = getCell(x, y);
					if (cell.isEmpty()) {
						List<Integer> possibleValues = cell.getPossibleValues();
						if (possibleValues.size() == 1) {
							playCount++;
							cell.play(possibleValues.get(0));
						}
					}
				}
			}
			// Heuristique 2 : On cherche des cases comportant une valeur possible non comprises
			//                 dans les cases de son area
			for(int x=0; x<9; x++) {
				for(int y=0; y<9; y++) {
					Cell cell = getCell(x, y);
					if (cell.isEmpty()) {
						List<Integer> cellPossibleValues = cell.getPossibleValues();
						for(Cell areaCell: cell.getCellsOnArea()) {
							if (cell != areaCell) {
								for(Integer value: areaCell.getPossibleValues()) {
									cellPossibleValues.remove(value);
								}
							}
						}
						
						if (cellPossibleValues.size() == 1) {
							playCount++;
							cell.play(cellPossibleValues.get(0));
						}
					}
				}
			}
			
			// Heuristique 3 : On cherche des cases comportant une valeur possible non comprises
			//                 dans les cases de sa colonne
			for(int x=0; x<9; x++) {
				for(int y=0; y<9; y++) {
					Cell cell = getCell(x, y);
					if (cell.isEmpty()) {
						List<Integer> cellPossibleValues = cell.getPossibleValues();
						for(Cell columnCell: cell.getCellsOnColumn()) {
							if (cell != columnCell) {
								for(Integer value: columnCell.getPossibleValues()) {
									cellPossibleValues.remove(value);
								}
							}
						}
						
						if (cellPossibleValues.size() == 1) {
							playCount++;
							cell.play(cellPossibleValues.get(0));
						}
					}
				}
			}

			
			// Heuristique 4 : On cherche des cases comportant une valeur possible non comprises
			//                 dans les cases de sa ligne

			for(int x=0; x<9; x++) {
				for(int y=0; y<9; y++) {
					Cell cell = getCell(x, y);
					if (cell.isEmpty()) {
						List<Integer> cellPossibleValues = cell.getPossibleValues();
						for(Cell lineCell: cell.getCellsOnLine()) {
							if (cell != lineCell) {
								for(Integer value: lineCell.getPossibleValues()) {
									cellPossibleValues.remove(value);
								}
							}
						}
						
						if (cellPossibleValues.size() == 1) {
							playCount++;
							cell.play(cellPossibleValues.get(0));
						}
					}
				}
			}			
		
			
			// Dernier Cas : On va faire des hypothèses sur certaines cases
			
			
			// Où en sommes nous ?
			if (getEmptyCellCount() == 0 ) {
				solved = true;
			}
			if (playCount==0) { 
				break;
			}
		}
		return solved;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getName());
		sb.append("\n");
		sb.append(getLevel());
		sb.append("\n");
		
		int l=0;
		for(List<Cell> line: lines) {
			if (l % 3 == 0) {
				sb.append("\n");
			}
			int c=0;
			for(Cell cell: line) {
				if (cell.isEmpty()) {
					sb.append('x');
				} else {
					sb.append(Integer.toString(cell.getValue()));
				}
				c++;
				if (c>0 && (c%3==0)) {
					sb.append(' ');
				}
			}
			sb.append("\n");
			l++;
		}
		return sb.toString();
	}
}
