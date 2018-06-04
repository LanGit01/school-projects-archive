package scheduler;

import java.util.PriorityQueue;

import comparators.BurstTimeComparator;

import process.Process;

public class SJFScheduler extends Scheduler {

	private Process current;
	
	public SJFScheduler(){
		queue = new PriorityQueue<Process>(11, new BurstTimeComparator());
	}
	
	@Override
	public void addProcess(Process p) {
		queue.add(p);
	}

	@Override
	public void step() {
		
		lastActive = finished = preempted = null;
		
		if(current == null && !queue.isEmpty()){
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
