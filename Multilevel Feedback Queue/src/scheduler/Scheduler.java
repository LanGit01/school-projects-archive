package scheduler;

import java.util.Queue;

import process.Process;

public abstract class Scheduler {
	
	protected Queue<Process> queue;
	protected Process lastActive;
	protected Process finished;
	protected Process preempted;
	
	public boolean hasProcess(){
		return !queue.isEmpty();
	}
	
	public boolean hasFinishedProcess(){
		return !(finished == null);
	}
	
	public boolean hasPreemptedProcess(){
		return !(preempted == null);
	}
	
	public Process getFinishedProcess(){
		return finished;
	}
	
	public Process getPreemptedProcess(){
		return preempted;
	}
	
	public Process getLastActive(){
		return lastActive;
	}
	
	public abstract void addProcess(Process p);
	public abstract void step();
}
