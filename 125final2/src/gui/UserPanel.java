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
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

import utils.Utils;

@SuppressWarnings("serial")
public class UserPanel extends JPanel {

	private JLabel[] label=new JLabel[2];
	private JSpinner[] spinner=new JSpinner[2];
	private JLabel error;
	private JScrollPane pane;
	private JPanel lastPanel;

	public SpinnerPanel[] sp=new SpinnerPanel[3];
	public JButton addButton, resetButton;

	private Border border;
	private int max=1;
	private int count=1;
	
	public UserPanel() {
		Font f=Utils.getFont("res\\STREET.ttf",20f);
		border=BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5,5,5,5),
				"PROCESS 1",TitledBorder.CENTER,TitledBorder.TOP,
				f.deriveFont(Font.BOLD),Color.BLACK);
		setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(10,10,10,10),border));
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(310,200));
		init(f);
	}
	
	private void init(Font f) {
		String[][] str = {{"arrival time:", "priority number:"},
				   {"Max Vector: ", "Allocated Vector: ", "Cylinders: "}};
		SpinnerNumberModel[] m = {new SpinnerNumberModel(0,0,null,1), new SpinnerNumberModel(1,1,20,1)};

		lastPanel=new JPanel();
		lastPanel.setLayout(new BorderLayout());
		lastPanel.setBackground(Color.WHITE);
		
		JPanel outerPanel=new JPanel();
		outerPanel.setOpaque(false);
		outerPanel.setLayout(new BorderLayout());
		outerPanel.setPreferredSize(new Dimension(310,400));
		
		JPanel innerPanel=new JPanel();
		innerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		innerPanel.setLayout(new GridLayout(0,1,5,0));
		innerPanel.setOpaque(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,2,5,5));
		panel.setMaximumSize(new Dimension(400,120));
		panel.setOpaque(false);
		
		for (int i=0; i<label.length; i++) {
			label[i] = new JLabel(str[0][i]);
			label[i].setOpaque(false);
			label[i].setForeground(Color.BLACK);
			label[i].setFont(f.deriveFont(15f).deriveFont(Font.BOLD));
			panel.add(label[i]);
			
			spinner[i] = new JSpinner(m[i]);
			spinner[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			JFormattedTextField txt = ((JSpinner.NumberEditor)spinner[i].getEditor()).getTextField();
			((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
			panel.add(spinner[i]);
		}
		
		outerPanel.add(panel,BorderLayout.NORTH);
		for (int i=0; i<3 ; i++) {
			sp[i] = new SpinnerPanel(str[1][i],Color.BLACK);
			innerPanel.add(sp[i]);
		}
		
		error=new JLabel("Input is invalid.");
		error.setForeground(Color.RED);
		error.setFont(f.deriveFont(15f).deriveFont(Font.BOLD));
		error.setOpaque(false);
		error.setHorizontalAlignment(JLabel.CENTER);
		error.setVisible(false);
		outerPanel.add(error,BorderLayout.SOUTH);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		buttonPanel.setBorder(null);
		buttonPanel.setOpaque(false);
		addButton = new JButton("Add");
		addButton.setFont(f.deriveFont(20f));
		addButton.setBackground(Color.WHITE);
		addButton.setFocusable(false);
		buttonPanel.add(addButton);
	
		resetButton = new JButton("Reset");
		resetButton.setFont(f.deriveFont(20f));
		resetButton.setBackground(Color.WHITE);
		resetButton.setFocusable(false);
		buttonPanel.add(resetButton);
		
		lastPanel.add(buttonPanel,BorderLayout.SOUTH);
		label[1].setVisible(false);
		spinner[1].setVisible(false);
		sp[0].setSpinner(1, 1, 20);
		updateSpinnerPanel(1);
		sp[1].disableSpinner(0);
		sp[2].setSpinner(1,0,1);
		sp[0].setValue(0, 1);
	
		outerPanel.add(innerPanel,BorderLayout.CENTER);
		lastPanel.add(outerPanel,BorderLayout.CENTER);
		pane=new JScrollPane(lastPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
							JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setBorder(null);
		pane.setOpaque(false);
		pane.getViewport().setOpaque(false);
		add(pane,BorderLayout.CENTER);
	}

	public void addListener(ChangeListener listener) {
		sp[0].addListener(listener, 0);
	}
	
	public void addBListener(ActionListener listener) {
		addButton.addActionListener(listener);
		resetButton.addActionListener(listener);
	}

	public void updateSpinnerPanel(int i) {
		sp[0].setSpinner(i, 0, 20);
		sp[1].setSpinner(i, 0, 20);
		revalidate();
		repaint();
	}
	
	public void showPrio(boolean state) {
		label[1].setVisible(state);
		spinner[1].setVisible(state);
	}
	
	public void updateMaxV() {
		sp[2].setSpinner(sp[0].getSpinnerValue(0), 0, max);
		repaint();
		revalidate();
	}
	
	public void updateMax(int max) {
		this.max=max;
		sp[2].setSpinnerMax(max);
	}

	public void updateCount() {
		if (count+1==16) {
			addButton.setEnabled(false);
			resetButton.setEnabled(false);
			return;
		}
		count++;
		((TitledBorder)border).setTitle("PROCESS "+count);
		repaint();
	}
	
	public void showError(boolean state) {
		error.setVisible(state);
	}
	
	public void reset() {
		spinner[0].setValue(0);
		spinner[1].setValue(1);
		
		for (int i=0; i<3; i++)
			sp[i].resetValue();
		sp[0].setValue(0, 1);
		pane.getVerticalScrollBar().setValue(0);
		repaint();
	}
	
	public void resetAll() {
		count=1;
		((TitledBorder)border).setTitle("PROCESS "+count);
		enableButtons(true);
		reset();
	}
	
	public void enableButtons(boolean state) {
		addButton.setEnabled(state);
		resetButton.setEnabled(state);
	}
	
	public boolean isPrioVisible() {
		return label[1].isVisible();
	}

	public boolean isInputValid() {
		for(int i=0; i<3; i++) {
			if (sp[i].getInput().equals(""))
				return false;
		}
		return true;
	}
	
	public Object[] getInput() {
		if (spinner[1].isVisible()) {
			return new Object[]{"",count,(int)spinner[0].getValue(),
				"<html><div style='text-align: center;'>"+sp[0].getInput()+"</div></html>",
				"<html><div style='text-align: center;'>"+sp[1].getInput()+"</div></html>",
				"<html><div style='text-align: center;'>"+sp[2].getInput()+"</div></html>",
				(int)spinner[1].getValue()};
		}
		return new Object[]{"",count,(int)spinner[0].getValue(),
				"<html><div style='text-align: center;'>"+sp[0].getInput()+"</div></html>",
				"<html><div style='text-align: center;'>"+sp[1].getInput()+"</div></html>",
				"<html><div style='text-align: center;'>"+sp[2].getInput()+"</div></html>"};
	}

}