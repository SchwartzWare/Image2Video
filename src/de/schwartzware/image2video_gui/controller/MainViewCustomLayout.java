package de.schwartzware.image2video_gui.controller;

import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import de.schwartzware.image2video_gui.model.Image2MovieGenerator;
import de.schwartzware.image2video_gui.model.Image2MovieGenerator.ProgressListener;
import de.schwartzware.image2video_gui.model.ImageFilter;
import de.schwartzware.image2video_gui.model.RecorderProfile;
import de.schwartzware.image2video_gui.model.RecorderProfiles;
import de.schwartzware.image2video_gui.model.Utils;

public class MainViewCustomLayout extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JPanel container;
	protected Button btnGenerateVideo;
	protected Button btnChooseImage;
	protected TextField txtImagePath;

	protected final String ACTION_BTN_CHOOSE_IMAGE = "btnChooseImage";

	public MainViewCustomLayout() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable ex) {
		}

		container = new JPanel();
		container.setLayout(null);
		container.setSize(new Dimension(500, 500));
		container.setPreferredSize(new Dimension(500, 500));

		initGUI(container);
		updateGUI(container);

		this.setContentPane(container);
		this.pack();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Image2MovieGenerator gen = new Image2MovieGenerator(new File("./scratched-and-scraped-metal-texture-2_1.jpg"),
				new File("./output.mxf"), 2, RecorderProfiles.DNXHD_DEMO);
		gen.addProgressListener(new ProgressListener() {

			@Override
			public void onProgress(double progress, int frame) {
				System.out.println("Frame: " + frame);
			}
		});
		gen.start();
	}

	protected void initGUI(Container container) {
		txtImagePath = new TextField();
		btnChooseImage = new Button();
		btnGenerateVideo = new Button();

		btnChooseImage.addActionListener(this);
	}

	protected void updateGUI(Container container) {
		container.setBackground(Color.WHITE);

		Font defaultFont = new Font("Arial", Font.PLAIN, 15);
		int defaultMargin = 15;
		txtImagePath.setLocation(defaultMargin, defaultMargin);
		txtImagePath.setSize((int) (container.getWidth() * (2. / 3.)), 30);
		txtImagePath.setFont(defaultFont.deriveFont(20f));
		txtImagePath.setBackground(Color.BLACK);
		txtImagePath.setForeground(Color.WHITE);
		btnChooseImage.setSize((int) (container.getWidth() * (1. / 3.)) - defaultMargin * 3, 30);
		btnChooseImage.setLocation((int) (container.getWidth() * (2. / 3.)) + defaultMargin * 2, txtImagePath.getY());
		btnChooseImage.setLabel("Choose image");
		btnChooseImage.setFont(defaultFont);
		btnChooseImage.setBackground(Color.BLACK);
		btnChooseImage.setForeground(Color.WHITE);
		btnChooseImage.setActionCommand(ACTION_BTN_CHOOSE_IMAGE);

		container.add(txtImagePath);
		container.add(btnChooseImage);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case ACTION_BTN_CHOOSE_IMAGE:
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new ImageFilter());
			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				txtImagePath.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
			break;
		}
	}
}
