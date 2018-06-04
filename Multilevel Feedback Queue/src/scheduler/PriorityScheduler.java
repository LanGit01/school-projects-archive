package scheduler;

import java.util.PriorityQueue;

import comparators.PriorityComparator;
import process.Process;

public class PriorityScheduler extends Scheduler {

	private Process current;
	private boolean preemptive;
	
	public PriorityScheduler(){
		this(false);
	}
	
	public PriorityScheduler(boolean preemptive){
		this.preemptive = preemptive;
		queue = new PriorityQueue<Process>(11, new PriorityComparator());
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
		
		if(preemptive){
			if(current != queue.peek()){
				preempted = current;
				current = queue.peek();
			}
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
