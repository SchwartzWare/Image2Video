package de.schwartzware.image2video_gui.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JPanel;

import de.schwartzware.image2video_gui.model.Utils;

public class ImageViewer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected File imgFile;
	protected BufferedImage img;

	public ImageViewer() {

	}

	public ImageViewer(File imgFile) {
		this.setImageFile(imgFile);
	}

	public void setImageFile(File file) {
		this.imgFile = file;
		this.img = Utils.loadImage(file);
		this.repaint();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = this.getX();
		int y = this.getY();
		int width = this.getWidth();
		int height = this.getHeight();

		if (img != null) {
			int imgWidth = img.getWidth();
			int imgHeight = img.getHeight();

			float aspectRatio = (float) imgWidth / (float) imgHeight;

			int newImgWidth = 0;
			int newImgHeight = 0;

			int widthWithoutBorder = width - 2;
			int heightWithoutBorder = height - 2;
			if (width > height) {
				newImgHeight = heightWithoutBorder;
				newImgWidth = (int) (heightWithoutBorder * aspectRatio);
			} else {
				newImgWidth = widthWithoutBorder;
				newImgHeight = (int) (widthWithoutBorder / aspectRatio);
			}

			g.drawImage(img, 1 + widthWithoutBorder / 2 - newImgWidth / 2,
					1 + heightWithoutBorder / 2 - newImgHeight / 2, newImgWidth, newImgHeight, this);
		}

		g.drawRect(0, 0, width - 1, height - 1);

	}
}
