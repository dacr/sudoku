package com.sodoku;

public interface CellListener {
	public void cellValueCleared(Cell cell);
	public void cellValueInitialized(Cell cell);
	public void cellValueModified(Cell cell, int previousValue);
}
