package main;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import gui.Home;

public class Main {

	public static void main(String[] args) {
		BufferedImage icon=null;
		
		try {	
			URL url = Main.class.getResource("/eshilogo.png");
			icon=ImageIO.read(url);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		JFrame frame=new JFrame("PSEUDO OS");
		
		frame.setIconImage(icon);
		frame.setMinimumSize(new Dimension(1300,750));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Home());
		frame.setVisible(true);
	}
}