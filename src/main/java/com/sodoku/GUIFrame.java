package com.sodoku;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIFrame extends JFrame {
	static final long serialVersionUID=1;
	
	private Game game;

	public GUIFrame(Game game) {
		super("SudoKu Grid");
		setGame(game);
		
		JPanel buttons = new JPanel(new FlowLayout());
		buttons.add(new JButton(new GUIFrameSolveAction(this)));
		buttons.add(new JButton(new GUIFrameResetAction(this)));

		GUIGrid grid = new GUIGrid(game);
		add(grid, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x= 0 +(screen.width-getWidth())/2;
        int y= 0 +(screen.height-getHeight())/2;
        setLocation(x, y);
		setVisible(true);
	}
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
}


abstract class GUIFrameAction extends JButtonAction {
    private final GUIFrame  guiFrame;
    GUIFrameAction(GUIFrame guiFrame, String text) {
        super(text);
        this.guiFrame = guiFrame;
    }
    GUIFrame getGUIFrame() {
        return guiFrame;
    }
    Game getGame() {
    	return guiFrame.getGame();
    }
}


class GUIFrameSolveAction extends GUIFrameAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4565692153803186434L;
	public GUIFrameSolveAction(GUIFrame guiFrame) {
        super(guiFrame, "S_olve");
        putValue(Action.SHORT_DESCRIPTION, "Solves the current game");
        //putValue(Action.SMALL_ICON, Resources.createIconRotateRight());
    }
    public void actionPerformed(ActionEvent e) {
    	new Thread(new Runnable() {
			public void run() {
		        getGame().solve();
    		}    		
    	}).start();
    }
}


class GUIFrameResetAction extends GUIFrameAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8501932094016296532L;
	public GUIFrameResetAction(GUIFrame guiFrame) {
        super(guiFrame, "R_eset");
        putValue(Action.SHORT_DESCRIPTION, "Reset the game");
        //putValue(Action.SMALL_ICON, Resources.createIconRotateRight());
    }
    public void actionPerformed(ActionEvent e) {
    	new Thread(new Runnable() {
			public void run() {
		        getGame().reset();
    		}    		
    	}).start();
    }
}
