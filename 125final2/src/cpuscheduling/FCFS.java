package cpuscheduling;

import gui.Chart;

import java.awt.Color;
import java.util.ArrayList;

import model.SchedulingAlgo;
import model.Process;

public class FCFS extends SchedulingAlgo{

	int choice;
	int curr = 0;
	int readyQSize=0;
	boolean addedNewProc=false;
	private Chart chart;
	
	public FCFS(int choice, Chart chart, int size) {
		super(size);
		this.chart = chart;
		this.choice= choice;
		readyQ = new ArrayList<Process>();
	}

	public void execute(int t){
		int burstTime, remTime, responseTime;
		
		if (curr < size) {
			for (int i=readyQSize; i<readyQ.size(); i++){
				chart.addReadyQueue(readyQ.get(i).getColor(), t);
				burstTime = readyQ.get(i).getBurstTime();
				readyQ.get(i).setRemainingTime(burstTime,choice);
				readyQ.get(i).setResponseTime(-1, choice);
				readyQ.get(i).setReadyTime(t,choice);
			}
			
			readyQSize = readyQ.size();
			
			if (curr<readyQSize){ 
				if (readyQ.get(curr).getResponseTime(choice)==-1){ 	//response time
					responseTime = t-readyQ.get(curr).getReadyTime(choice);
					readyQ.get(curr).setResponseTime(responseTime, choice);
					readyQ.get(curr).setWaitingTime(responseTime, choice);
				}
			
				remTime = readyQ.get(curr).getRemainingTime(choice);
				readyQ.get(curr).setRemainingTime(--remTime,choice);

				if (readyQ.get(curr).getRemainingTime(choice)==0){
					readyQ.get(curr).setCompletionTime(t+1,choice);
					setReleased(readyQ.get(curr));
					chart.addBox(t+1, readyQ.get(curr).getColor(), true);
					setCurrProc(curr);	//curr is process index in queue
					curr++;
				}
				else {
					chart.addBox(t, readyQ.get(curr).getColor(), false);
					setCurrProc(curr);	//curr is process index in queue
				}
			}
			
			else{ //idle time
				chart.addBox(t, Color.LIGHT_GRAY,true);
				setCurrProc(-1);
			}
		}
		else{
			done=true;
		}
	}
	
	public void set(){
		setTurnaroundTime(choice);
		setAVGResponseTime(choice);
		setAVGWaitingTime(choice);
		setAVGTurnaroundTime(choice);
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
