package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.Process;
import utils.Utils;

@SuppressWarnings("serial")
public class ListPanel extends JPanel {

	private JScrollPane pane;
	private DefaultTableModel model;
	private DefaultTableCellRenderer r,cell;
	private JTable table;
	private JRadioButton[] m = new JRadioButton[2];
	private ButtonGroup group;
	private JLabel label;
	private ArrayList<Process> process = new ArrayList<Process>();
	
	public JButton startButton, resetButton;
	
	public ListPanel() {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setBackground(new Color(38,45,52));
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		setMinimumSize(new Dimension(400,400));
		init();
	}
	
	private void init() {
		Font f = Utils.getFont("res\\STREET.ttf",15f);
		String[] colNames = {"","<html><div style='text-align: center;'>Proc<br>No</div></html>",
							 "<html><div style='text-align: center;'>Arrival<br>Time</div></html>",
							 "<html><div style='text-align: center;'>Max<br>Vector</div></html>",
							 "<html><div style='text-align: center;'>Allocated<br>Vector</div></html>",
							 "Cylinders"};
		model = new DefaultTableModel(0,5) {
			public boolean isCellEditable(int row,int col) {return false;}};
		model.setColumnIdentifiers(colNames);
		model.setColumnCount(6);
		
		cell=new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,Object value,
                    boolean isSelected,boolean hasFocus,int row, int column) {
				JComponent component=(JComponent) super.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);
				if (column==0) {
					component.setBackground(Utils.getColor(row));
				}
				return this;
			}};
		
		r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		
		table = new JTable(model);
		table.setFont(f);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setFont(f.deriveFont(13f));
		table.getTableHeader().setBackground(new Color(40,216,178));
		table.getTableHeader().setForeground(Color.WHITE);
		
		for(int i=0; i<6; i++) {
			if (i>0) {
				table.getColumnModel().getColumn(i).setCellRenderer(r);
				table.getColumnModel().getColumn(i).setMaxWidth(65);
				table.getColumnModel().getColumn(i).setMinWidth(65);
			}
			else {
				table.getColumnModel().getColumn(i).setMaxWidth(30);
				table.getColumnModel().getColumn(i).setCellRenderer(cell);
			}
		}
		
		pane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setBorder(BorderFactory.createEmptyBorder());
		pane.setOpaque(false);
		pane.getViewport().setOpaque(false);
		add(pane);
		add(Box.createRigidArea(new Dimension(10,50)));
		
		JPanel modes = new JPanel();
		modes.setOpaque(false);
		modes.setLayout(new GridLayout(1,3,10,10));
		modes.setMaximumSize(new Dimension(350,70));
		
		label = new JLabel("mode:");
		label.setFont(f.deriveFont(18f));
		label.setForeground(Color.WHITE);
		modes.add(label);
		
		String[] s = {"automatic", "step"};
		group = new ButtonGroup();
		for (int i=0; i<2; i++) {
			m[i] = new JRadioButton(s[i]);
			m[i].setFont(f.deriveFont(18f));
			m[i].setOpaque(false);
			m[i].setForeground(Color.WHITE);
			modes.add(m[i]);
			group.add(m[i]);
		}
		m[0].setSelected(true);
		add(modes);
		
		JPanel button = new JPanel();
		button.setOpaque(false);
		button.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		startButton = new JButton("Start");
		startButton.setFont(f.deriveFont(18f));
		startButton.setBackground(new Color(38,45,52));
		startButton.setForeground(Color.WHITE);
		startButton.setFocusable(false);
		button.add(startButton);
		
		resetButton = new JButton("Reset");
		resetButton.setFont(f.deriveFont(18f));
		resetButton.setBackground(new Color(38,45,52));
		resetButton.setForeground(Color.WHITE);
		resetButton.setFocusable(false);
		button.add(resetButton);
		add(Box.createRigidArea(new Dimension(10,20)));
		add(button);
	}
	
	private void updateRowHeights() {
	    for (int row=0; row<table.getRowCount(); row++) {
	        int rowHeight = table.getRowHeight();

	        for (int column=0; column<table.getColumnCount(); column++) {
	            Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
	            rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
	        }
	        table.setRowHeight(row, rowHeight);
	    }
	}	
	
	public void addBListener(ActionListener listener) {
		startButton.addActionListener(listener);
		resetButton.addActionListener(listener);
	}
	
	public void showPrio(int n) {
		if (n==7) {
			model.addColumn("<html><div style='text-align: center;'>Priority<br>No</div></html>");
		}
		model.setColumnCount(n);
		table.getColumnModel().getColumn(0).setCellRenderer(cell);
	}

	public void addRow(Object[] o) {
		model.addRow(o);
		updateRowHeights();
		if (o.length==7) {
			process.add(new Process((int)o[1],(int)o[2],(int)o[6],Utils.convertList(o[3]),
					Utils.convertList(o[4]),Utils.convertList(o[5])));
		}
		else {
			process.add(new Process((int)o[1],(int)o[2],Utils.convertList(o[3]),
					Utils.convertList(o[4]),Utils.convertList(o[5])));
		}
		int index=process.size()-1;
		process.get(index).setColor(Utils.getColor(((int)o[1])-1));
	}
	
	public void deleteRows() {
		model.setRowCount(0);
		process.clear();
	}
	
	public boolean isInputValid() {
		if ((m[0].isSelected() || m[1].isSelected()) && table.isValid())
			return true;
		return false;
	}

	public boolean isStep() {
		if (m[1].isSelected())
			return true;
		return false;
	}
	
	public ArrayList<Process> getProcess() {
		return process;
	}
}