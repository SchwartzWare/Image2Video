package de.schwartzware.image2video_gui.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class ProgressButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Color progressBarColor = Color.RED;
	protected float progressValue = 0.0f;

	public ProgressButton(String label) {
		super(label);
	}

	public void setProgress(float f) {
		this.progressValue = f;
		this.repaint();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(progressBarColor);
		g.fillRect(0, 0, (int) (getWidth() * progressValue), getHeight());
	}

}
