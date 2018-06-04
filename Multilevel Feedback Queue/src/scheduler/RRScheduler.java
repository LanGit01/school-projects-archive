package scheduler;

import java.util.LinkedList;

import process.Process;

public class RRScheduler extends Scheduler {

	private int index;
	private int quantum;
	private int quantumCount;
	
	public RRScheduler(int quantum){
		queue = new LinkedList<Process>();
		this.quantum = quantum;
		quantumCount = 0;
		index = -1;
	}
	
	@Override
	public void addProcess(Process p) {
		queue.add(p);
	}

	@Override
	public void step() {
		
		LinkedList<Process> list = (LinkedList<Process>)queue;
		
		if(list.size() > 1 && preempted != null){
			index++;
			if(index > list.size() - 1){
				index = 0;
			}
		}
		
		
		lastActive = finished = preempted = null;
		
		
		if(index == -1 && !list.isEmpty()){
			index = 0;
		}
		
		
		if(!list.isEmpty()){
			Process p = list.get(index);
			p.step();
			quantumCount++;
			lastActive = p;
			
			if(p.isFinished()){
				finished = p;
				queue.remove(p);
				quantumCount = 0;
				if(index > list.size() - 1){
					index = 0;
				}
				/*
				index++;
				if(index > list.size() - 1){
					index = 0;
				}
				*/
			}else
			if(quantum == quantumCount){
				preempted = p;
				quantumCount = 0;
				/*index++;
				if(index > list.size() - 1){
					index = 0;
				}*/
			}
		}
	}

}
