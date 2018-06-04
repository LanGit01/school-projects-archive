package scheduler;

import java.util.PriorityQueue;

import comparators.RemainingBurstComparator;

import process.Process;

public class SRTFScheduler extends Scheduler{

	private Process current;
	
	public SRTFScheduler(){
		queue = new PriorityQueue<Process>(11, new RemainingBurstComparator());
	}
	
	@Override
	public void addProcess(Process p) {
		queue.add(p);
	}

	@Override
	public void step() {
		
		lastActive = preempted = finished = null;
		
		if(current == null && !queue.isEmpty()){
			current = queue.peek();
		}
		
		// Preemption
		if(queue.size() > 1 && current != queue.peek()){
			preempted = current;
			current = queue.peek();
		}
		
		if(current != null){
			current.step();
			lastActive = current;
			
			if(current.isFinished()){
				finished = current;
				queue.remove(current);
				current = null;
				if(!queue.isEmpty()){
					current = queue.peek();
				}
			}
		}
	}

}
