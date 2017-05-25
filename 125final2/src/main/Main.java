package main;

import gui.Home;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		BufferedImage icon=null;
		
		try {
			icon=ImageIO.read(new File("res/eshilogo.png"));
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