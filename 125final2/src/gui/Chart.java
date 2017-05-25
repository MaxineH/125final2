package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

import utils.CustomField;
import utils.Utils;

@SuppressWarnings("serial")
public class Chart extends JPanel {
	
	private JPanel avail,queue,center,innerPanel;
	private DiskPanel bottom;
	private JLabel[] label;
	
	private Font f;
	private Color prevColor=Color.WHITE, rColor=Color.WHITE;
	
	public Chart(int id,int head,int max,int time) {
		f=Utils.getFont("res\\STREET.ttf",18f);
		innerPanel=new JPanel();
		
		Border b=BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5,5,5,5),
				"Simulation "+id,TitledBorder.CENTER,TitledBorder.TOP,
				f.deriveFont(30f).deriveFont(Font.BOLD),Color.BLACK);
		innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.Y_AXIS));
		innerPanel.setPreferredSize(new Dimension(450,600));
		innerPanel.setBackground(Color.WHITE);
		innerPanel.setBorder(b);
		initTop();
		initCenter();
		bottom=new DiskPanel(max,head,time);
		innerPanel.add(bottom);
		
		setLayout(new BorderLayout());
		JScrollPane pane=new JScrollPane(innerPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setBorder(null);
		pane.setOpaque(false);
		pane.getViewport().setOpaque(false);
		add(pane,BorderLayout.CENTER);
	}
	
	private void initTop() {
		avail=new JPanel();
		avail.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(5,5,5,5),"Current Available",
				TitledBorder.CENTER,TitledBorder.TOP,f,Color.BLACK),
				BorderFactory.createEmptyBorder(5,5,5,5)));
		avail.setOpaque(false);
		avail.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
		innerPanel.add(avail);
		innerPanel.add(Box.createRigidArea(new Dimension(5,5)));
		
		queue=new JPanel();
		queue.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(5,5,5,5),"Ready Queue",
				TitledBorder.CENTER,TitledBorder.TOP,f,Color.BLACK),
				BorderFactory.createEmptyBorder(5,5,5,5)));
		queue.setMaximumSize(new Dimension(900,50));
		queue.setLayout(new GridLayout(1,15,0,0));
		queue.setOpaque(false);
		innerPanel.add(queue);
		innerPanel.add(Box.createRigidArea(new Dimension(5,5)));
	}

	private void initCenter() {
		center=new JPanel();
		center.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		center.setOpaque(false);
		
		JScrollPane pane=new JScrollPane(center,JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setBorder(null);
		pane.setOpaque(false);
		pane.getViewport().setBorder(null);
		pane.getViewport().setOpaque(false);
		pane.setMinimumSize(new Dimension(450,150));
		innerPanel.add(pane);
		innerPanel.add(Box.createRigidArea(new Dimension(10,20)));
	}
	
	@SuppressWarnings("deprecation")
	public void addReadyQueue(Color color, int t) {
		if (queue.countComponents()==0 && t!=0) {
			queue.add(new CustomField(Color.LIGHT_GRAY,rColor,"0"));
			rColor=Color.LIGHT_GRAY;
		}
		queue.add(new CustomField(color,rColor,Integer.toString(t)));
		rColor=color;
		queue.repaint();
	}

	public void initAvailable(ArrayList<Integer> set) {
		label=new JLabel[set.size()];
		for (int i=1; i<set.size(); i++) {
			label[i]=new JLabel(Integer.toString(set.get(i)));
			label[i].setFont(f);
			avail.add(label[i]);
		}
	}

	public void showAvailable(int i, String s) {
		label[i].setText(s);
		avail.repaint();
		repaint();
		revalidate();
	}

	public void addBox(int t, Color color, boolean hasLabel) {
		if (hasLabel)
			center.add(new CustomField(color,prevColor,Integer.toString(t)));
		else
			center.add(new CustomField(color,prevColor,""));
		prevColor=color;
		
		center.repaint();
		repaint();
		revalidate();
	}

	public void drawGraph(int n, int t) {
		bottom.updateChart(n, t);
	}

	public void showStat(ArrayList<Object[]> obj,String str) {
		innerPanel.add(new StatPanel(obj,str));
	}
}