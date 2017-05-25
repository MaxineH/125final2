package cpuscheduling;

import gui.Chart;

import java.awt.Color;
import java.util.ArrayList;

import model.SchedulingAlgo;
import model.Process;

public class RR extends SchedulingAlgo{
	
	int choice;
	int curr = 0;
	int readyQSize=0;
	int q=0;
	int counter=0;
	int prev=-1;
	int quantum;
	boolean addedNewProc=false;
	private Chart chart;
	
	public RR(int choice, Chart chart, int size, int quantum){
		super(size);
		this.chart = chart;
		this.choice= choice;
		readyQ = new ArrayList<Process>();
		this.quantum = quantum;
	}

	public void set(){
		setTurnaroundTime(choice);
		setAVGResponseTime(choice);
		setAVGWaitingTime(choice);
		setAVGTurnaroundTime(choice);
	}
	
	public void execute(int t){
		int burstTime, remTime, responseTime;
		
		for (int i=readyQSize; i<readyQ.size(); i++){
			chart.addReadyQueue(readyQ.get(i).getColor(), t);
			burstTime = readyQ.get(i).getBurstTime();
			readyQ.get(i).setRemainingTime(burstTime,choice);
			readyQ.get(i).setResponseTime(-1, choice);
			readyQ.get(i).setReadyTime(t,choice);
		}
		
		readyQSize = readyQ.size();

		if (curr>=readyQSize || curr==-1) {
			curr=getProcess();
		
			if (curr==-1){
				chart.addBox(t, Color.LIGHT_GRAY,true); //addBox(Color.WHITE);
				setCurrProc(-1);
			}
		}
		
		if (curr<readyQSize && curr!=-1){ 
			if (readyQ.get(curr).getResponseTime(choice)==-1){ 	//response time
				responseTime = t-readyQ.get(curr).getReadyTime(choice);
				readyQ.get(curr).setResponseTime(responseTime, choice);
				readyQ.get(curr).setWaitingTime(responseTime, choice);
			}

			if (prev!=readyQ.get(curr).getId()) {
				//insert context switch
				try {
					if (readyQ.get(prev).getRemainingTime(choice)>0) {
						incCS();
					}
				} catch (IndexOutOfBoundsException e) {
					if (readyQ.get(readyQ.size()-1).getRemainingTime(choice)>0) {
						incCS();
					}
				}
				prev = readyQ.get(curr).getId();		
			}
			
			if (currProc!=curr)
				chart.addBox(t, readyQ.get(curr).getColor(), true);
			else
				chart.addBox(t, readyQ.get(curr).getColor(),false);
			setCurrProc(curr);
			q++;
			
			remTime = readyQ.get(curr).getRemainingTime(choice);
			readyQ.get(curr).setRemainingTime(--remTime,choice);
			
			if (readyQ.get(curr).getRemainingTime(choice)==0){
				readyQ.get(curr).setCompletionTime(t+1,choice);
				setReleased(readyQ.get(curr));
				curr++;
				counter++;
				q=0;
			}
			else if ( q == quantum) {
				curr++;
				q=0;
			}
		}
		if (counter==size)	done=true;
	}

	public int getProcess(){
		for (int i=0; i<readyQ.size(); i++){
			if (readyQ.get(i).getRemainingTime(choice) != 0){
				return i;
			}
		}
		return -1;
	}
	public Process getReleased(){
		hasReleased = false;
		return holdProc;
	}
	
	private void setReleased(Process p){
		hasReleased = true;
		holdProc= p;
	}
	
	public int getContextSwitch(){
		return cs-1;
	}
}
