package scheduler;

import java.util.LinkedList;

import process.Process;

public class FCFSScheduler extends Scheduler {

	public FCFSScheduler(){
		queue = new LinkedList<Process>();
		preempted = null;
	}
	
	@Override
	public void addProcess(Process p) {
		queue.add(p);
	}

	@Override
	public void step() {
		
		lastActive = finished = null;
		
		if(!queue.isEmpty()){
			Process p = queue.peek();
			p.step();
			lastActive = p;
			
			if(p.isFinished()){
				finished = p;
				queue.remove();
			}
		}
	}

}
