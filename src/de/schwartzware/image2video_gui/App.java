package de.schwartzware.image2video_gui;

import java.awt.EventQueue;

import de.schwartzware.image2video_gui.controller.MainView;

public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView mainView = new MainView();
					mainView.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
