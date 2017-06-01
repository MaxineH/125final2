package cpuscheduling;

import gui.Chart;

import java.awt.Color;
import java.util.ArrayList;

import model.SchedulingAlgo;
import model.Process;

public class SRTF extends SchedulingAlgo{

	int choice;
	int curr = 0;
	int counter = 0;
	int readyQSize=0;
	int prev=-1;
	boolean addedNewProc=false;
	private Chart chart;
	
	public SRTF(int choice, Chart chart, int size){
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
		curr = getShortestTime();
		
		if (curr<readyQSize && curr!=-1){ 
			if (readyQ.get(curr).getResponseTime(choice)==-1){ 	//response time
				responseTime = t-readyQ.get(curr).getReadyTime(choice);
				readyQ.get(curr).setResponseTime(responseTime, choice);
				readyQ.get(curr).setWaitingTime(responseTime, choice);
			}
		

			if ( prev != readyQ.get(curr).getId() ) {
				//insert context switch
				try {
					if ( readyQ.get(prev).getRemainingTime(choice) > 0 ) {
						incCS();
					}
				} catch ( IndexOutOfBoundsException e ) {
					if ( readyQ.get(readyQ.size()-1).getRemainingTime(choice) > 0 ) {
						incCS();
					}
				}
				prev = readyQ.get(curr).getId();		
			}
			
			chart.addBox(t,readyQ.get(curr).getColor());
			setCurrProc(curr);
			
			remTime = readyQ.get(curr).getRemainingTime(choice);
			readyQ.get(curr).setRemainingTime(--remTime,choice);

			if (readyQ.get(curr).getRemainingTime(choice)==0){
				readyQ.get(curr).setCompletionTime(t+1,choice);
				setReleased(readyQ.get(curr));
				counter++;
			}
		} else{ //idle time
			chart.addBox(t,Color.LIGHT_GRAY);
			setCurrProc(-1);
		}
		if (counter==size)	done=true;
	}
	
	private int getShortestTime () {
		int i = 0;
		sort();
		
		for( i = 0 ; i < readyQ.size() ; i++ ) {
			if ( readyQ.get(i).getRemainingTime(choice) > 0) {
				return i;
			}
		}
		return -1;
	}
	
	private void sort(){
		Process temp;
		for (int i=0; i<readyQ.size(); i++){
			for (int j=1; j<(readyQ.size()-i); j++){
				if (readyQ.get(j-1).getRemainingTime(choice) > 
						readyQ.get(j).getRemainingTime(choice)){
					temp = readyQ.get(j-1);
					readyQ.set(j-1, readyQ.get(j));
					readyQ.set(j, temp);
				}
			}
		}
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
