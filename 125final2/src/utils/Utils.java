package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.Process;

public class Utils {

	private static Color[] color = new Color[] {new Color(31,136,193), new Color(5,102,53), 
								   new Color(181,170,32),new Color(74,192,179), 
								   new Color(251,205,209), new Color(151,216,100),
								   new Color(118,32,61), new Color(28,128,128), 
								   new Color(163,210,193), new Color(255,254,147),
								   new Color(193,7,54), new Color(198,94,187),
								   new Color(215,110,79), new Color(189,195,123), 
								   new Color(93,23,205)};
									
	public static Color getColor(int i) {
		return color[i];
	}
	
	public static Font getFont(String path,float size) {
		Font f = null;
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File(path));
		} catch(FontFormatException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return f.deriveFont(size);
	}
	
	public static ArrayList<Integer> convertList(Object o) {
		o=((String)o).replaceAll("<", "").replaceAll(">", "").replaceAll("br", "").
				replaceAll("/html", "").replaceAll("div style='text-align: center;'", "").
				replaceAll("/div", "").replaceAll("html", "");
		
		if (((String)o).equals(""))
			return null;
		
		String[] token=((String)o).split(",");
		ArrayList<Integer> list=new ArrayList<Integer>();
		
		for (int i=0; i<token.length; i++) {
			list.add(Integer.parseInt(token[i]));
		}
		return list;
	}
	
	public static String convertToLabel(String s) {
		String[] tokens=s.split("]");
		s="<html><div='text align=center;'>"+tokens[0];
		for (int i=1; i<tokens.length; i++) {
			s+="<br>"+tokens[i];
		}
		return s+"</div></html>";
	}
	
	public static ArrayList<Object[]> mergeList(ArrayList<Object[]> cpu, ArrayList<Object> disk) {
		ArrayList<Object[]> obj=new ArrayList<Object[]>();
		for (int i=0; i<cpu.size(); i++) {
			System.out.println(disk.get(i));
			Object[] tmp={cpu.get(i)[0],cpu.get(i)[1],cpu.get(i)[2],cpu.get(i)[3],disk.get(i)};
			obj.add(tmp);
		}
		return obj;
	}
	
	public static int sumAll(ArrayList<Process> list) {
		int sum=0;
		for (int i=0; i<list.size(); i++) {
			sum+=list.get(i).getMax().get(0);
		}
		return sum;
	}
}