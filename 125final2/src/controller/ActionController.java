package controller;

import gui.Home;
import gui.ListPanel;
import gui.MenuPanel;
import gui.SimulationPanel;
import gui.UserPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Input;
import model.Simulation;

public class ActionController implements ActionListener {

	private Home home;
	private MenuPanel menuPanel;
	private UserPanel userPanel;
	private ListPanel listPanel;
	private SimulationPanel simPanel;
	private Input input;
	private Simulation sim;
	
	public ActionController(Home home) {
		this.home=home;
		menuPanel=home.menuPanel;
		userPanel=home.userPanel;
		listPanel=home.listPanel;
		simPanel=home.simPanel;
		
		input=new Input();
		home.addBListener(this);
		userPanel.addBListener(this);
		listPanel.addBListener(this);
		simPanel.addBListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==home.button) {
			if (home.button.getText().equals("<html><U>OK</U></html>")) {	
				home.changeButton("EDIT");
				home.showNext();
				input.setSize(menuPanel.getAlgoSize());
				input.setResourceNum(menuPanel.getResourceNum());
				input.setAlgo(menuPanel.getAlgo());
				input.setAvailable(menuPanel.getAvailable());
				input.setMaxCylinder(menuPanel.getMax());
				input.setHeadCylinder(menuPanel.getHead());
				input.setQuantum(menuPanel.getQuantum());
			}
			else {
				int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset everything?",
						"Alert",JOptionPane.OK_CANCEL_OPTION);
				if (i==0) {
					home.changeButton("OK");
					home.showNext();
					home.tab.setSelectedIndex(0);
					home.tab.setEnabledAt(0, true);
					home.tab.setEnabledAt(1, true);
					listPanel.deleteRows();
					userPanel.resetAll();
					userPanel.reset();
				}
			}
		}
		else if (e.getSource()==userPanel.addButton) {
			home.tab.setEnabledAt(1, false);
			if (userPanel.isInputValid()) {
				listPanel.addRow(userPanel.getInput());
				userPanel.updateCount();
				userPanel.reset();
			}
			else {
				userPanel.showError(true);
			}
		}
		else if (e.getSource()==userPanel.resetButton) {
			userPanel.showError(false);
			userPanel.reset();
		}
		else if (e.getSource()==listPanel.resetButton) {
			home.tab.setSelectedIndex(0);
			home.tab.setEnabledAt(0, true);
			home.tab.setEnabledAt(1, true);
			listPanel.deleteRows();
			userPanel.resetAll();
			userPanel.reset();
		}
		else if (e.getSource()==listPanel.startButton) {
			if (listPanel.isInputValid()) {
				userPanel.enableButtons(false);
				input.setProcess(listPanel.getProcess());
				simPanel.init();
				simPanel.removeChart();
				simPanel.setStep(listPanel.isStep());
				sim=new Simulation(input,simPanel,listPanel.isStep());
			}
		}
		else if (e.getSource()==simPanel.step) {
			sim.pause();
			simPanel.changeName();
		}
		else if (e.getSource()==simPanel.stop) {
			sim.end();
			simPanel.stop.setEnabled(false);
		}
	}
}