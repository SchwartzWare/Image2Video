package de.schwartzware.image2video_gui.controller;

import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import de.schwartzware.image2video_gui.model.Image2MovieGenerator;
import de.schwartzware.image2video_gui.model.Image2MovieGenerator.ProgressListener;
import de.schwartzware.image2video_gui.model.ImageFilter;
import de.schwartzware.image2video_gui.model.RecorderProfile;
import de.schwartzware.image2video_gui.model.RecorderProfiles;
import de.schwartzware.image2video_gui.model.Utils;
import de.schwartzware.image2video_gui.view.ImageViewer;
import de.schwartzware.image2video_gui.view.ProgressButton;

public class MainView extends JFrame implements ActionListener, ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JTextField txtImagePath;
	protected JSpinner spnrDuration;
	protected JButton btnChooseImage;
	protected ProgressButton btnGenerate;
	protected ImageViewer pnlImage;
	protected JComboBox<String> cboPresets;
	protected JCheckBox ckbUseImageResolution;
	protected JLabel lblProfile;
	protected RecorderProfile[] profiles = { RecorderProfiles.H264_DEMO, RecorderProfiles.DNXHD_DEMO };

	protected final String ACTION_BTN_CHOOSE_IMAGE = "btnChooseImage";

	private File imgFile;

	public MainView() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}

		JPanel container = new JPanel();
		this.setContentPane(container);
		this.pack();
		this.setSize(500, 500);

		initGUI(container);
		initEvents(container);
		updateGUI(container);
	}

	private void initEvents(JPanel container) {
		btnChooseImage.addActionListener(this);
	}

	private void initGUI(JPanel container) {
		int gridx = 0;
		int gridy = 0;

		container.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// GUI elements

		// First row
		JPanel pnlFirstRow = new JPanel();
		// pnlFirstRow.setBackground(Color.GREEN);
		pnlFirstRow.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = gridy;
		c.gridwidth = 2;
		container.add(pnlFirstRow, c);

		c.insets = new Insets(10, 10, 10, 10); // top padding

		txtImagePath = new JTextField();
		txtImagePath.setEditable(false);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;

		pnlFirstRow.add(txtImagePath, c);

		btnChooseImage = new JButton("Choose image...");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 40;
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 0;

		pnlFirstRow.add(btnChooseImage, c);

		// Second row
		gridy += 1;

		pnlImage = new ImageViewer();
		pnlImage.setBackground(Color.WHITE);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = gridy;
		c.weighty = 1;
		c.gridwidth = 2;

		container.add(pnlImage, c);

		// 3rd row
		gridy += 1;

		JLabel lblDuration = new JLabel("Duration");
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0; // reset to default
		c.gridwidth = 1;
		c.weightx = 0;
		c.weighty = 0; // request any extra vertical space
		c.gridx = 0; // aligned with button 2
		c.gridy = gridy; // third row
		container.add(lblDuration, c);

		spnrDuration = new JSpinner();
		spnrDuration.setValue(2);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0; // reset to default
		c.weightx = 2;
		c.gridx = 1; // aligned with button 2
		c.gridy = gridy; // third row
		container.add(spnrDuration, c);

		// 4th row
		gridy += 1;

		lblProfile = new JLabel("Profile");
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0; // reset to default
		c.gridwidth = 1;
		c.weightx = 0;
		c.weighty = 0; // request any extra vertical space
		c.gridx = 0; // aligned with button 2
		c.gridy = gridy; // third row
		container.add(lblProfile, c);

		cboPresets = new JComboBox<String>();
		for (int i = 0; i < profiles.length; i += 1) {
			cboPresets.addItem(profiles[i].name);
		}
		cboPresets.addItemListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0; // reset to default
		c.weightx = 2;
		c.gridx = 1; // aligned with button 2
		c.gridy = gridy; // third row
		container.add(cboPresets, c);

		// 5th row
		gridy += 1;

		lblProfile = new JLabel("Use image size?");
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0; // reset to default
		c.gridwidth = 1;
		c.weightx = 0;
		c.weighty = 0; // request any extra vertical space
		c.gridx = 0; // aligned with button 2
		c.gridy = gridy; // third row
		container.add(lblProfile, c);

		ckbUseImageResolution = new JCheckBox();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0; // reset to default
		c.weightx = 2;
		c.gridx = 1; // aligned with button 2
		c.gridy = gridy; // third row
		container.add(ckbUseImageResolution, c);

		// Last row
		gridy += 1;

		btnGenerate = new ProgressButton("Generate");
		btnGenerate.addActionListener(this);
		btnGenerate.setProgress(0.5f);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0; // reset to default
		c.weightx = 0;
		c.gridx = 0; // aligned with button 2
		c.gridy = gridy; // third row
		c.gridwidth = 2;
		container.add(btnGenerate, c);

	}

	private void updateGUI(JPanel container) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnChooseImage) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new ImageFilter());
			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				imgFile = fileChooser.getSelectedFile();
				txtImagePath.setText(imgFile.getAbsolutePath());
				pnlImage.setImageFile(imgFile);
			}
		} else if (e.getSource() == btnGenerate) {
			int duration = (int) spnrDuration.getValue();
			RecorderProfile profile = profiles[cboPresets.getSelectedIndex()];

			Image2MovieGenerator gen = new Image2MovieGenerator(imgFile, new File("./output." + profile.format),
					duration, profile);
			ProgressListener l = new ProgressListener() {

				@Override
				public void onProgress(double progress, int frame) {
					System.out.println("Frame: " + frame);
					System.out.println("Progress: " + progress);
					btnGenerate.setProgress((float) progress);
					if (progress == 1.0) {
						btnGenerate.setProgress(0);
						gen.removeProgressListener(this);
					}
				}
			};
			gen.addProgressListener(l);
			gen.setUseImageSizeAsResolution(ckbUseImageResolution.isSelected());
			new Thread(() -> {
				gen.start();
			}).start();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == cboPresets) {
			if (!profiles[cboPresets.getSelectedIndex()].allowCustomResolution) {
				ckbUseImageResolution.setEnabled(false);
				ckbUseImageResolution.setSelected(false);
			} else {
				ckbUseImageResolution.setEnabled(true);
			}
		}
	}
}
