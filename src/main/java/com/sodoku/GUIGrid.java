package com.sodoku;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolox.nodes.PClip;


class PCell extends PClip {
	static final long serialVersionUID=1;
	private PValue pValue=null;
	private PPath shape;
	private final Cell cell;
	private PChoice[] pChoices= new PChoice[9];
	
	public PCell(float width, float height, Cell cell) {
		this.cell = cell;
		shape = new PPath();
		shape.moveTo(0f, 0f);
		shape.lineTo(0f, width-1);
		shape.lineTo(width-1, height-1);
		shape.lineTo(height-1, 0f);
		shape.closePath();
        shape.setPaint(Color.YELLOW);
        shape.setStrokePaint(Color.RED);
        
        addChild(shape);

        setPathToRectangle(0f,0f, width, height);
        setStrokePaint(null);


        shape.addInputEventListener(new PBasicInputEventHandler() {
        	public void mouseEntered(PInputEvent event) {
        		shape.setPaint(Color.GREEN);		        		
        	};
        	public void mouseExited(PInputEvent event) {
        		shape.setPaint(Color.YELLOW);
        	};
        	public void mouseClicked(PInputEvent event) {
        		if (event.getButton()==3 && !getCell().isImmuable()) {
        			getCell().clear();
        			//setPValue(null);
        		}
        	};
        });
	}
	
	public void refresh() {
		for(int n=0; n<9; n++) {
			if (pChoices[n] != null) {
				shape.removeChild(pChoices[n]);
				pChoices[n]=null;
			}
		}
		if (cell.isEmpty()) {
			for(Integer n : cell.getPossibleValues()) {
        		pChoices[n-1]=new PChoice(this, n);
        		shape.addChild(pChoices[n-1]);
	        }
			setPValue(null);
		} else {
			if (!cell.isImmuable()) {
				setPValue(new PValue(this, cell.getValue(), false));
			} else {
				setPValue(new PValue(this, cell.getValue(), true));
			}			
		}
        		
	}

	public void setPValue(PValue valueNode) {
		if (this.pValue != null) {
			shape.removeChild(this.pValue);
		}
		this.pValue = valueNode;
		if (valueNode != null) {
			// played
			//cell.play(valueNode.getValue());
			shape.addChild(0, this.pValue);
		} else {
			// cleared
			//cell.play(0);
		}
	}

	public PValue getPValue() {
		return pValue;
	}
	
	public Cell getCell() {
		return cell;
	}
}







class PChoice extends PText {
	static final long serialVersionUID=1;
	static final Color colorNormal=Color.GRAY;
	static final Color colorHighlighted=Color.BLACK;
	private final PCell pCell;
	private final int value;

	public PChoice(PCell pCell, int value) {
		this.value = value;
		this.pCell = pCell;
		
    	double w = pCell.getWidth()/3.0;
    	double h = pCell.getHeight()/3.0;
		
		setText(Integer.toString(value));
    	Font f = getFont();
    	f = f.deriveFont(Font.BOLD, (float)h);
    	setFont(f);
    	setTextPaint(colorNormal);
    	setTransparency(0.7f);

    	int n = value-1;
    	
    	
    	double x = w*(n%3);
    	double y = h*(n/3);
    	
    	double tx=(w-getWidth())/2.0;
    	double ty=(h-getHeight())/2.0;

    	translate(x+tx, y+ty);

    	addInputEventListener(new PBasicInputEventHandler() {
        	public void mouseEntered(PInputEvent arg0) {
        		setTextPaint(colorHighlighted);		        		
        	};
        	public void mouseExited(PInputEvent arg0) {
        		setTextPaint(colorNormal);
        	};
        	public void mouseClicked(PInputEvent arg0) {
        		//getPCell().setPValue(new PValue(getPCell(), getValue(), false));
        		getPCell().getCell().play(getValue());
        	};
    	});
	}
	public PCell getPCell() {
		return pCell;
	}
	public int getValue() {
		return value;
	}
}



class PValue extends PText {
	static final long serialVersionUID=1;
	static final Color colorNormal=Color.RED;
	static final Color colorImmuable= Color.BLUE;
	private final PCell pCell;
	private final int value;

	public PValue(PCell cell, int value, boolean immuable) {
		this.value = value;
		this.pCell = cell;
		setText(Integer.toString(value));
		Font bigFont = getFont().deriveFont(Font.BOLD, (float)(getPCell().getHeight()));
		setFont(bigFont);
		setTextPaint(immuable?colorImmuable:colorNormal);
		translate( (cell.getWidth()-getWidth())/2.0, (cell.getHeight()-getHeight())/2.0);
	}
	public PCell getPCell() {
		return pCell;
	}
	public int getValue() {
		return value;
	}
}





public class GUIGrid extends PCanvas implements CellListener {
	static final long serialVersionUID=1;
	static final float size=72f;
	private Map<Cell, PCell> pCells = new HashMap<Cell, PCell>(9*9);
	private Game game;

	public GUIGrid(Game game) {
		this.game = game;

        //removeInputEventListener(getPanEventHandler());
        //removeInputEventListener(getZoomEventHandler());
        setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);

		PLayer layer = getLayer();        
		int hgap=0, vgap;
		for(int x=0; x<9; x++) {
			hgap += (x%3==0 && x>0)?8:0;
			vgap=0;
			for(int y=0; y<9; y++) {
				vgap += (y%3==0 && y>0)?8:0;
				Cell cell = game.getCell(x,y); 
				PCell pCell = new PCell(size, size, cell);
				cell.addCellListener(this);
				pCells.put(cell, pCell);
				pCell.refresh();
				pCell.translate(pCell.getWidth()*x+hgap, pCell.getHeight()*y+vgap);
		        layer.addChild(pCell);
			}
		}
		PBounds bounds = layer.getFullBounds();
		setPreferredSize(new Dimension((int)bounds.getWidth(), (int)bounds.getHeight()));				
	}
	public GUIGrid() {
		this(new Game());
	}

	public void cellValueCleared(Cell cell) {
		for(PCell pCell : pCells.values()) {
			pCell.refresh();
		}
	}
	public void cellValueInitialized(Cell cell) {
		for(PCell pCell : pCells.values()) {
			pCell.refresh();
		}
	}
	public void cellValueModified(Cell cell, int previousValue) {
		for(PCell pCell : pCells.values()) {
			pCell.refresh();
		}
	}
	
	public Game getGame() {
		return game;
	}
	
	public void solve() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//getGame().solve();
			}
		});
	}
}
