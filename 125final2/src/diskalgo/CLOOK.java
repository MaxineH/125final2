package diskalgo;

import gui.Chart;

import java.util.ArrayList;
import java.util.Collections;

import model.DiskAlgo;

public class CLOOK extends DiskAlgo {

	private Chart chart;
	private boolean isRight=false;
	private int start, end;
	
	public CLOOK(int head,int max,Chart chart) {
		super(max,head);
		this.chart=chart;
	}
	
	private int getNext(int index) {
		ArrayList<Integer> tmp=list.get(index);
		if ((curr==-1 || curr!=index) && tmp.indexOf(head)!=-1) {
			tmp.add(head);
			return head;
		}
		else {
			tmp.add(head);
			Collections.sort(tmp);
		}
		
		int i=tmp.indexOf(head);
		if ((isRight && head!=end) || (!isRight && head==start)) 
			return tmp.get(i+1);
		return tmp.get(i-1);
	}
	
	public void execute(int t, int index) {
		if (index!=-1 && !list.get(index).isEmpty()) {
			if (curr!=index) {
				if (curr!=-1) {
					p.put(curr,proctotal);
					proctotal=0;
				}
				Collections.sort(list.get(index));
				start = list.get(index).get(0);
				end = list.get(index).get(list.get(index).size()-1);
				if (getDifference(head,start)<getDifference(head,end))
					isRight=false;
				else isRight=true;
			}
			
			int prev=head;
			int next=getNext(index);
			int count=getDifference(prev,next);
			
			proctotal+=count;
			total+=count;
			
			list.get(index).remove(list.get(index).indexOf(head));
			list.get(index).remove(list.get(index).indexOf(next));
			chart.drawGraph(t,next);
			
			if (next==start || next==end) {
				if (next==start) next=end;
				else next=start;
				chart.drawGraph(t, next);
				list.get(index).remove(list.get(index).indexOf(next));
			}

			head=next;
			curr=index;
		}
	}
}