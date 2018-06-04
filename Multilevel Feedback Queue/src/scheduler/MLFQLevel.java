package scheduler;

import java.util.LinkedList;

import process.Process;

public class MLFQLevel extends Scheduler {

	private int quantum;
	private int quantumCount;
	
	public MLFQLevel(int quantum){
		this.quantum = quantum;
		quantumCount = 0;
		queue = new LinkedList<Process>();
	}
	
	@Override
	public void addProcess(Process p) {
		queue.add(p);
	}

	@Override
	public void step() {
		
		lastActive = finished = preempted = null;
		
		if(!queue.isEmpty()){
			Process p = queue.peek();
			p.step();
			quantumCount++;
			lastActive = p;
			
			if(p.isFinished()){
				finished = p;
				queue.poll();
				quantumCount = 0;
			}else
			if(quantumCount == quantum){
				preempted = p;
				queue.poll();
				quantumCount = 0;
			}
		}
	}

}
