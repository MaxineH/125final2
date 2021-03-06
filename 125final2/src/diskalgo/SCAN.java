package diskalgo;

import gui.Chart;

import java.util.ArrayList;
import java.util.Collections;

import model.DiskAlgo;

public class SCAN extends DiskAlgo {

	private Chart chart;
	private boolean isRight=false;
	
	public SCAN(int head,int max,Chart chart) {
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
		if ((isRight && head!=max) || (!isRight && head==tmp.get(0))) {
			return tmp.get(i+1);
		}
		return tmp.get(i-1);
	}
	
	public void execute(int t, int index) {
		//index is process id
		if (index!=-1) {
			if (curr!=index) {
				if (curr!=-1) {
					p.put(curr, proctotal);
					proctotal=0;
				}
				if (getDifference(head,0)<getDifference(head,max))
					isRight=false;
				else isRight=true;
				list.get(index).add(0,0);
				list.get(index).add(max);
			}
			
			int prev=head;
			int next=getNext(index);
			int count=getDifference(prev,next);
			
			proctotal+=count;
			total+=count;
			
			list.get(index).remove(list.get(index).indexOf(head));
			list.get(index).remove(list.get(index).indexOf(next));
			
			if (next==0 || next==max) {
				chart.drawGraph(t-0.5,next);
				head=next;
				next=getNext(index);
				count=getDifference(head,next);
				proctotal+=count;
				total+=count;
				chart.drawGraph(t, next);
				list.get(index).remove(list.get(index).indexOf(head));
				list.get(index).remove(list.get(index).indexOf(next));
			}
			else {
				chart.drawGraph(t,next);
			}
			
			head=next;
			curr=index;
		}
	}
}