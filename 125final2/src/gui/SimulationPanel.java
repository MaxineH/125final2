package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import utils.Utils;

@SuppressWarnings("serial")
public class SimulationPanel extends JPanel {

	private JPanel right;
	public JButton step, stop;
	
	private Font f;
	
	public SimulationPanel() {
		f=Utils.getFont("res\\STREET.ttf", 20f).deriveFont(Font.BOLD);
		setLayout(new BorderLayout());
		setOpaque(false);
		initTop();
		initRight();
		revalidate();
		repaint();
	}
	
	private void initTop() {
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
		panel.setBackground(new Color(33,39,44));
		panel.setPreferredSize(new Dimension(900,50));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		step=new JButton("PLAY");
		step.setFont(f);
		step.setForeground(Color.WHITE);
		step.setBackground(new Color(33,38,43));
		step.setFocusable(false);
		step.setVisible(false);
		
		stop=new JButton("STOP");
		stop.setFont(f);
		stop.setForeground(Color.WHITE);
		stop.setBackground(new Color(33,38,43));
		stop.setFocusable(false);
		stop.setVisible(false);
		
		panel.add(step);
		panel.add(stop);
		add(panel,BorderLayout.NORTH);
	}
	
	private void initRight() {
		right=new JPanel();
		right.setBackground(new Color(38,45,52));
		right.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		right.setLayout(new GridLayout(1,2,10,0));
		add(right,BorderLayout.CENTER);
	}
	
	public void init() {
		step.setVisible(true);
		stop.setVisible(true);
		stop.setEnabled(true);
	}
	
	public void addBListener(ActionListener listener) {
		step.addActionListener(listener);
		stop.addActionListener(listener);
	}

	public void setStep(boolean isStep) {
		step.setEnabled(isStep);
	}

	public void changeName() {
		if (!step.isEnabled())
			return;
		if (step.getText().equals("PLAY"))
			step.setText("PAUSE");
		else
			step.setText("PLAY");
	}
	
	public void addChart(Chart chart) {
		right.add(chart);
		repaint();
	}
	
	@SuppressWarnings("deprecation")
	public void removeChart() {
		if (right.countComponents()==0)
			return;
		right.removeAll();
		right.repaint();
		right.revalidate();
	}
}