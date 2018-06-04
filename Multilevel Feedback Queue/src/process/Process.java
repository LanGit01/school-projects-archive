package process;

public class Process {

	private int id;
	private int arrivalTime;
	private int burstTime;
	private int waitTime;
	private int turnaroundTime;
	private int endTime;
	
	private int priority;
	
	private int remainingBurstTime;
	private int lastTimeActive;
	
	public Process(int id, int arrivalTime, int burstTime){
		this(id, arrivalTime, burstTime, 0);
	}
	
	public Process(int id, int arrivalTime, int burstTime, int priority){
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.remainingBurstTime = burstTime;
		this.priority = priority;
		waitTime = turnaroundTime = endTime = lastTimeActive = 0;
	}
	
	public void setArrivalTime(int at){ this.arrivalTime = at;	}
	public void setBurstTime(int bt){ this.burstTime = bt; }
	public void setWaitTime(int wt){ this.waitTime = wt; }
	public void setTurnaroundTime(int tt){ this.turnaroundTime = tt; }
	public void setEndTime(int et){ this.endTime = et; }
	public void setPriority(int priority){ this.priority = priority; }
	
	
	public int getID(){ return id; }
	public int getArrivalTime(){ return arrivalTime; }
	public int getBurstTime(){ return burstTime; }
	public int getWaitTime(){ return waitTime; }
	public int getTurnaroundTime(){ return turnaroundTime;}
	public int getEndTime(){return endTime; }
	public int getPriority(){ return priority; }
	
	public boolean isFinished(){
		return (remainingBurstTime == 0);
	}
	
	public int getRemainingBurst(){ 
		return remainingBurstTime; 
	}
	
	public void resetRemainingBurst(){
		remainingBurstTime = burstTime;
	}
	
	public void setLastTimeActive(int time){
		lastTimeActive = time;
	}
	
	public int getLastTimeActive(){
		return lastTimeActive;
	}
	
	public void step(){
		if(remainingBurstTime == 0) return;
		remainingBurstTime--;
	}
	
}
