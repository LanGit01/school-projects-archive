package process;

import java.util.ArrayList;

public class ProcessManager {

	private ArrayList<Process> processes;
	
	public ProcessManager(){
		processes = new ArrayList<Process>();
	}
	
	public void addProcess(Process... procs){
		for(Process p: procs){
			processes.add(p);
		}
	}
	
	public ArrayList<Process> getProcesses(){
		return processes;
	}
	
	public int getLastArrivalTime(){
		int at = 0;
		for(Process p: processes){
			if(p.getArrivalTime() > at){
				at = p.getArrivalTime();
			}
		}
		
		return at;
	}
	
	public ArrayList<Process> getProcessesStarted(int time){
		ArrayList<Process> startedProcesses = new ArrayList<Process>();;
		
		for(Process p: processes){
			if(p.getArrivalTime() == time){
				startedProcesses.add(p);
			}
		}
		
		return startedProcesses;
	}
}
