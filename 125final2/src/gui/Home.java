package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

import controller.ActionController;
import controller.ChangeController;
import controller.ItemController;
import utils.CustomTab;
import utils.Utils;

@SuppressWarnings("serial")
public class Home extends JPanel {

	public MenuPanel menuPanel;
	public Preset preset;
	public UserPanel userPanel;
	public RandPanel randPanel;
	public ListPanel listPanel;
	public SimulationPanel simPanel;
	
	public CustomTab tab;
	public JButton button;
	private JPanel card,inner;
	
	public Home() {
		setLayout(new BorderLayout());
		setBackground(new Color(38,45,52));
		
		initLeft();
		initRight();
		new ItemController(this);
		new ChangeController(this);
		new ActionController(this);
	}
	
	private void initLeft() {
		JPanel panel=new JPanel();
		panel.setPreferredSize(new Dimension(400,700));
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		
		button=new JButton("<html><U>OK</U></html>");
		button.setForeground(Color.WHITE);
		button.setFont(Utils.getFont("res\\STREET.ttf", 15f));
		button.setBackground(new Color(33,38,43));
		button.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					button.doClick();
				}
			}
		});
		
		preset=new Preset();
		menuPanel=new MenuPanel(preset);
		
		inner=new JPanel();
		inner.setLayout(new BoxLayout(inner,BoxLayout.Y_AXIS));
		inner.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		inner.setBackground(new Color(/*38,45,52*/33,38,43));
		inner.setPreferredSize(new Dimension(400,700));
		
		userPanel=new UserPanel();
		randPanel=new RandPanel();
		listPanel=new ListPanel();
		
		tab=new CustomTab();
		tab.add(userPanel,"User-Defined");
		tab.add(randPanel,"Randomize");
		tab.setMinimumSize(new Dimension(400,200));
		
		inner.add(tab);
		inner.add(Box.createRigidArea(new Dimension(5,5)));
		inner.add(listPanel);
		
		card=new JPanel();
		card.setOpaque(true);
		card.setLayout(new CardLayout());
		card.add(menuPanel,"Menu");
		card.add(inner,"Input");
		
		panel.add(button,BorderLayout.SOUTH);
		panel.add(card,BorderLayout.CENTER);
		
		add(panel,BorderLayout.WEST);
	}
	
	private void initRight() {
		simPanel=new SimulationPanel();
		add(simPanel,BorderLayout.CENTER);
	}

	public void addBListener(ActionListener listener) {
		button.addActionListener(listener);
	}
	
	public void addListener(ChangeListener listener) {
		tab.addChangeListener(listener);
	}

	public void changeButton(String txt) {
		button.setText("<html><U>"+txt+"</U></html>");
		button.setFont((button.getFont()).deriveFont(Font.BOLD));
	}	
	
	public void showNext() {
		CardLayout c=(CardLayout)card.getLayout();
		c.next(card);
	}
}