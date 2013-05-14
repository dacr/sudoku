package com.sodoku;

import java.util.ArrayList;
import java.util.List;

public class Cell {
	private final int      x;
	private final int      y;
	private int      value;    // box current value
	private boolean  empty;    // true if the box has no value
	private boolean  immuable; // true if the value can't be modified
	private List<CellListener> listeners;
	private List<Cell> cellsOnLine;
	private List<Cell> cellsOnColumn;
	private List<Cell> cellsOnArea;
	
	public Cell(int x, int y, CellListener listener) {
		listeners = new ArrayList<CellListener>();
		addCellListener(listener);
		this.x = x;
		this.y = y;
		empty = true;
		immuable = false;
		value=0;
	}

	public void addCellListener(CellListener listener) {
		listeners.add(listener);
	}
	public void removeCellListener(CellListener listener) {
		listeners.remove(listener);
	}
	
	public void setValue(int value) {
		if (isImmuable()) return;

		int oldValue = this.value;
		this.value = value;
		if (isEmpty()) {
			if (value != 0) {
				setEmpty(false);
				for(CellListener listener: listeners)
					listener.cellValueInitialized(this);
			}
		} else {
			if (value != 0) {
				for(CellListener listener: listeners)
					listener.cellValueModified(this, oldValue);
			} else {
				setEmpty(true);
				for(CellListener listener: listeners)
					listener.cellValueCleared(this);
			}
		}
	}
	public int getValue() {
		return value;
	}

	private void setEmpty(boolean empty) {
		this.empty = empty;
	}
	public boolean isEmpty() {
		return empty;
	}

	private void setImmuable(boolean immuable) {
		this.immuable = immuable;
	}
	public boolean isImmuable() {
		return immuable;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public void initialValue(int value) {
		setValue(value);
		setImmuable(true);
	}
	public void play(int value) {
		setValue(value);
	}
	
	public void clear() {
		setValue(0);
	}

	public void setCellsOnLine(List<Cell> cellsOnLine) {
		this.cellsOnLine = cellsOnLine;
	}

	public List<Cell> getCellsOnLine() {
		return cellsOnLine;
	}

	public void setCellsOnColumn(List<Cell> cellsOnColumn) {
		this.cellsOnColumn = cellsOnColumn;
	}

	public List<Cell> getCellsOnColumn() {
		return cellsOnColumn;
	}

	public void setCellsOnArea(List<Cell> cellsOnArea) {
		this.cellsOnArea = cellsOnArea;
	}

	public List<Cell> getCellsOnArea() {
		return cellsOnArea;
	}
	
	public List<Integer> getPossibleValues() {
		List<Integer> possibleValues = new ArrayList<Integer>(9);
		if (isEmpty()) {
			for(int i=1; i<=9; i++) {
				possibleValues.add(new Integer(i));
			}
			List<Cell> appended = new ArrayList<Cell>(3*9);
			appended.addAll(getCellsOnArea());
			appended.addAll(getCellsOnColumn());
			appended.addAll(getCellsOnLine());
			for(Cell cell: appended) {
				if (cell != this && !cell.isEmpty()) {
					possibleValues.remove(new Integer(cell.getValue()));
				}
			}
		} else {
			possibleValues.add(new Integer(getValue()));
		}
		return possibleValues;
	}
}
