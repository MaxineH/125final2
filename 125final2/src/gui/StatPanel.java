package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import utils.Utils;

@SuppressWarnings("serial")
public class StatPanel extends JPanel {
	
	private DefaultTableModel model;
	private JLabel label;
	private Font font;
	
	public StatPanel() {
		font=Utils.getFont("res\\STREET.ttf",25f);

		setPreferredSize(new Dimension(450,400));
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setOpaque(false);
		setBorder(new CompoundBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(5,5,5,5),"Statistics",
				TitledBorder.CENTER,TitledBorder.TOP,font,Color.BLACK),
				BorderFactory.createEmptyBorder(5,5,5,5)));
	}
	
	private void initTop() {
		String[] colNames={"<html><div styke='text-align:center;'>Process<br>Number</div></html>",
				"<html><div styke='text-align:center;'>Response<br>Time</div></html>",
				"<html><div styke='text-align:center;'>Waiting<br>Time</div></html>",
				"<html><div styke='text-align:center;'>Turnaround<br>Time</div></html>",
				"<html><div styke='text-align:center;'>Total<br>Cylinders</div></html>"};
	
		model=new DefaultTableModel() {
			public boolean isCellEditable(int row, int col){return false;}};
		model.setColumnIdentifiers(colNames);
		model.setColumnCount(5);
		
		DefaultTableCellRenderer r=new DefaultTableCellRenderer();
		r.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		
		JTable table=new JTable(model);
		table.setFont(font);
		table.setRowHeight(22);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setFont(font);
		table.getTableHeader().setBackground(new Color(29,45,78));
		table.getTableHeader().setForeground(Color.WHITE);
		
		for(int i=0; i<5; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(r);
		}
		
		JScrollPane pane=new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setPreferredSize(new Dimension(430,400));
		pane.setBorder(BorderFactory.createEmptyBorder());
		pane.setOpaque(false);
		pane.getViewport().setOpaque(false);
		pane.setMaximumSize(new Dimension(900,150));
		pane.setMinimumSize(new Dimension(450,150));
		add(pane);
		add(Box.createRigidArea(new Dimension(10,10)));
	}

	private void setTable(ArrayList<Object[]> obj) {
		for (int i=0; i<obj.size(); i++) {
			model.addRow(obj.get(i));
		}
	}
	
	public void init(ArrayList<Object[]> obj, String str) {
		label=new JLabel(str);
		label.setFont(font);
		label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		initTop();
		setTable(obj);
		add(label);
	}
}