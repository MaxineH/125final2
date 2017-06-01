package cpuscheduling;

import gui.Chart;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import model.SchedulingAlgo;
import model.Process;

public class SJF extends SchedulingAlgo{

	int choice;
	int curr = 0;
	int counter=0;
	int readyQSize=0;
	boolean addedNewProc=false;
	boolean hasReleased=false;
	Process holdProc;
	private Chart chart;
	
	public SJF(int choice, Chart chart, int size){
		super(size);
		this.chart = chart;
		this.choice= choice;
		readyQ = new ArrayList<Process>();
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
		if (curr==-1) curr = getShortestTime();
		
		if (curr<readyQSize && curr!=-1){ 
			if (readyQ.get(curr).getResponseTime(choice)==-1){ 	//response time
				responseTime = t-readyQ.get(curr).getReadyTime(choice);
				readyQ.get(curr).setResponseTime(responseTime, choice);
				readyQ.get(curr).setWaitingTime(responseTime, choice);
			}
		
			chart.addBox(t,readyQ.get(curr).getColor());
			setCurrProc(curr);
			
			remTime = readyQ.get(curr).getRemainingTime(choice);
			readyQ.get(curr).setRemainingTime(--remTime,choice);

			if (readyQ.get(curr).getRemainingTime(choice)==0){
				readyQ.get(curr).setCompletionTime(t+1,choice);
				setReleased(readyQ.get(curr));
				curr = -1;
				counter++;
			}
		}
		
		else{ //idle time
			chart.addBox(t,Color.LIGHT_GRAY);
			curr=-1;
			setCurrProc(-1);
		}
		if (counter==size)	done=true;
	}
	
	private int getShortestTime () {
		Collections.sort(readyQ, Process.burstTimeComparator);
		for ( int i = 0 ; i < readyQ.size() ; i++ ) {
			if ( readyQ.get(i).getRemainingTime(choice) != 0)
				return i;
		}
		return -1;
	}
	
	public boolean hasReleased(){
		return hasReleased;
	}
	
	public Process getReleased(){
		hasReleased = false;
		return holdProc;
	}
	
	private void setReleased(Process p){
		hasReleased = true;
		holdProc= p;
	}
}
