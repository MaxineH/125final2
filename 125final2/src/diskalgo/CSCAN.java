package diskalgo;

import java.util.ArrayList;
import java.util.Collections;

import gui.Chart;
import model.DiskAlgo;
import model.Process;

public class CSCAN extends DiskAlgo {

	private Chart chart;
	private boolean isRight=false;
	
	public CSCAN(int head,int max,Chart chart) {
		super(max,head);
		this.chart=chart;
	}

	private int getNext(int index) {
		ArrayList<Integer> tmp=list.get(index);
		if ((curr==-1 || curr!=index) && tmp.indexOf(head)!=-1)
			return head;
		else {
			tmp.add(head);
			Collections.sort(tmp);
		}
		
		int i=tmp.indexOf(head);
		if ((isRight && head!=max) || (!isRight && head==tmp.get(0))) 
			return tmp.get(i+1);
		return tmp.get(i-1);
	}
//	
//	private void setDirection(int index) {
//		ArrayList<Integer> tmp=list.get(index);
//		if ((curr==-1 || curr!=index) && tmp.indexOf(head)==-1) {
//			tmp.add(head);
//			Collections.sort(tmp);
//		}
//		
//		int i=tmp.indexOf(head);
//		int right,left;
//		
//		if (i!=0) left=tmp.get(i-1);
//		else left=tmp.get(tmp.size()-1);
//		
//		if (i!=tmp.size()-1) right=tmp.get(i+1);
//		else right=tmp.get(0);
//		
//		tmp.remove(i);
//		if (getDifference(head,left)>getDifference(right,head))
//			isRight=false;
//		isRight=true;
//	}
	
	public void execute(int t, int index) {
		//index is process id
		if (index!=-1) {
			if (curr!=index && curr!=-1) {
				p.put(curr, proctotal);
				proctotal=0;
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
			
			if (next==0) {
				chart.drawGraph(t-0.5, next);
				next=max;
				chart.drawGraph(t-0.5, next);
				list.get(index).remove(list.get(index).indexOf(max));
			}
			else if (next==max) {
				chart.drawGraph(t-0.5, next);
				next=0;
				chart.drawGraph(t-0.5, next);
				list.get(index).remove(list.get(index).indexOf(0));
			}
			else {
				chart.drawGraph(t,next);
			}
			head=next;
			curr=index;
		}
	}
}