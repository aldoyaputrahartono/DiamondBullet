package com.games;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main {
	public static JFrame frame;
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //ambil ukuran layar user
				frame = new JFrame("Diamond Bullet");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(new MainMenuPanel("assets/Images/Menu/bg_mainmenu1.jpg",
						"assets/Sounds/Music/main_menu_music.wav"));
				frame.setLocation(dim.width/2-800/2, dim.height/2-600/2); //set posisi frame di tengah layar
				frame.pack();
				frame.setResizable(false);
				frame.setVisible(true);
				frame.setLayout(null);
			}
		});
	}

}
