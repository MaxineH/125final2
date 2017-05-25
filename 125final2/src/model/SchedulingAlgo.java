package model;

import java.util.ArrayList;
import java.util.Collections;

import model.Process;

public class SchedulingAlgo {
	
	protected ArrayList<Process> readyQ;
	private float avgResponseTime=0, avgWaitingTime=0, avgTurnaroundTime=0;	
	protected int size;
	protected boolean hasReleased;
	protected Process holdProc;
	protected boolean done= false;
	protected int cs =0 ;
	protected int currProc=-1;
	
	public SchedulingAlgo(int size){
		this.size = size;
	}
	
	public void setWaitingTime(int algo){
		int turnaroundTime;
		
		for (int i=0; i<readyQ.size(); i++){
			turnaroundTime = readyQ.get(i).getCompletionTime(algo)- readyQ.get(i).getReadyTime(algo);
			readyQ.get(i).setTurnaroundTime(turnaroundTime, algo);
		}
	}
	
	public void setTurnaroundTime(int algo){
		int turnaroundTime;
		
		for (int i=0; i<readyQ.size(); i++){
			turnaroundTime = readyQ.get(i).getCompletionTime(algo)- readyQ.get(i).getReadyTime(algo);
			readyQ.get(i).setTurnaroundTime(turnaroundTime, algo);
		}
	}
	
	public void setAVGResponseTime(int algo){
		for (int i=0; i<readyQ.size(); i++){
			avgResponseTime+=readyQ.get(i).getResponseTime(algo);
		}
		avgResponseTime/=readyQ.size();
	}
	
	public void setAVGTurnaroundTime(int algo){
		for (int i=0; i<readyQ.size(); i++){
			avgTurnaroundTime+=readyQ.get(i).getTurnaroundTime(algo);
		}
		avgTurnaroundTime/=readyQ.size();
	}

	public void setAVGWaitingTime(int algo){
		for (int i=0; i<readyQ.size(); i++){
			avgWaitingTime+=readyQ.get(i).getWaitingTime(algo);
		}
		avgWaitingTime/=readyQ.size();
	}
	
	public void incCS(){
		cs++;
	}
	
	public void addNewProc(ArrayList<Process> proc){
		readyQ.addAll(new ArrayList<Process>(proc));
	}

	public void execute(int t){}
	
	public boolean hasReleased(){
		return hasReleased;
	}
	
	public float getAVGResponseTime(){
		return avgResponseTime;
	}
	
	public Process getReleased(){
		return holdProc;
	}
	
	public float getAVGWaitTime(){
		return avgWaitingTime;
	}
	
	public float getAVGTurnaroundTime(){
		return avgTurnaroundTime;
	}
	
	public int getContextSwitch(){
		return cs;
	}
	
	public boolean isDone(){
		return done;
	}
	
	public void set(){}
	
	public String getSummary() {
		return "AVG Response Time: "+getAVGResponseTime()+"<br>"+
	         "AVG Waiting Time: "+getAVGWaitTime()+"<br>"+
	         "AVG Turnaround Time: "+getAVGTurnaroundTime()+"<br>"+
	         "Context Switch: "+getContextSwitch()+"<br>";
	}
	
	public ArrayList<Object[]> getProcessSummary(int choice) {
		ArrayList<Object[]> obj=new ArrayList<Object[]>();
		Collections.sort(readyQ,Process.idComparator);
		
		for (int i=0; i<readyQ.size(); i++) {
			Object[] tmp={readyQ.get(i).getId(),readyQ.get(i).getResponseTime(choice),
					readyQ.get(i).getWaitingTime(choice),
					readyQ.get(i).getTurnaroundTime(choice)};
			System.out.println("SCHED ALGO="+readyQ.get(i).getId()+" "+tmp.length);
			obj.add(tmp);
		}
		return obj;
	}
	
	public void setCurrProc(int procID) {
		currProc = procID;
	}
	
	public int getCurrProc() {
		if (currProc>-1) {
			return readyQ.get(currProc).getId();
		}
		return currProc;
	}
}
