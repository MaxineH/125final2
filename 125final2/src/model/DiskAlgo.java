package model;

import java.util.ArrayList;
import java.util.HashMap;

import model.Process;

public class DiskAlgo {

	protected HashMap<Integer,Integer> p=new HashMap<Integer,Integer>();
	protected HashMap<Integer,ArrayList<Integer>> list=new HashMap<Integer,ArrayList<Integer>>();
	protected int total=0;
	protected int proctotal=0;
	protected int max,head;
	
	public DiskAlgo(int max,int head) {
		this.max=max;
		this.head=head;
	}
	
	protected int getDifference(int prev,int next) {
		if (next>prev) return next-prev;
		return prev-next;
	}
	
	public void addList(ArrayList<Process> p) {
		for (Process i: p) {
			list.put(i.getId(), new ArrayList<Integer>(i.getCylinder()));
		}
	}
	
	public void execute(int t, int index) {}
	
	public String getTotal() {
		return "Total Cylinders: "+total;
	}
	
	public ArrayList<Object> getProcessSummary() {
		ArrayList<Object> obj=new ArrayList<Object>();
		for (int i=0; i<=p.size(); i++) {
			System.out.println("DISK ALGO="+p.get(i+1));
			obj.add(p.get(i+1));
		}
		return obj;
	}
}