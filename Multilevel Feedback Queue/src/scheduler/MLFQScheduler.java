package scheduler;

import process.Process;

public class MLFQScheduler extends Scheduler {

	private Scheduler[] levels;
	private int lastLevel;
	
	private int time;
	
	public MLFQScheduler(Scheduler lowestLevelScheduler, int... quanta){
		levels = new Scheduler[quanta.length + 1];
		
		for(int i = 0; i < quanta.length; i++){
			levels[i] = new MLFQLevel(quanta[i]);
		}
		levels[quanta.length] = lowestLevelScheduler;
		
		lastLevel = 0;
		time = 0;
	}
	
	public int getNumLevels(){
		return levels.length;
	}
	
	public int getLastLevel(){
		return lastLevel;
	}

	@Override
	public void addProcess(Process p) {
		levels[0].addProcess(p);
	}
	
	@Override
	public boolean hasProcess(){
		for(Scheduler s: levels){
			if(s.hasProcess()){
				return true;
			}
		}
		return false;
	}

	@Override
	public void step() {
		
		System.out.print(time + " ");
		int level = 0;
		for(Scheduler s: levels){
			if(s.hasProcess()){
				s.step();
				
				if(lastActive != null && s.getLastActive() != null && lastActive != s.getLastActive()){
					Process p = s.getLastActive();
					if(p.getBurstTime() == p.getRemainingBurst() + 1){
						// new process
						System.out.println("NEW PROCESS");
						p.setWaitTime(time - p.getArrivalTime());
					}else{
						// preempted process
						p.setWaitTime(p.getWaitTime() + (time - p.getLastTimeActive()));
					}
					
				}
				
				lastActive = s.getLastActive();
				lastLevel = level;
				
				System.out.print("LVL:" + level + " ");
				if(lastActive != null){
					System.out.print(lastActive.getID() + " " + lastActive.getRemainingBurst() + " ");
					
					lastActive.setLastTimeActive(time + 1);
					
					if(lastActive.isFinished()){
						// Turnaroundtime = end time - arrivaltime
						lastActive.setEndTime(time);
						lastActive.setTurnaroundTime(time - lastActive.getArrivalTime() + 1);
					}
				}
				
				if(s.hasPreemptedProcess()){
					//Process preempted = s.getPreemptedProcess();
					if(level < levels.length - 1){
						levels[level+1].addProcess(s.getPreemptedProcess());
					}
					//preempted.setLastTimeActive(time);
				}
				
				break;
			}
			level++;
			
		}
		System.out.println();
		
		time++;
	}
	
}
