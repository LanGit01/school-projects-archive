package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import process.Process;
import process.ProcessManager;
import process.ProcessTrace;
import scheduler.FCFSScheduler;
import scheduler.MLFQScheduler;
import scheduler.PriorityScheduler;
import scheduler.RRScheduler;
import scheduler.SJFScheduler;
import scheduler.SRTFScheduler;
import scheduler.Scheduler;
import ui.MLFQInputGUI.SchedulerType;

/**
 * Actually runs the multilevel feedback queue scheduler
 * 
 * @author ace
 *
 */
@SuppressWarnings("serial")
public class MLFQRunner extends JPanel {
	
	private InputSource source;
	private GanttChartGUI display;
	private TimingTableGUI timingTable;
	private MLFQSimulation simulation;
	private LinkedList<ProcessTrace> pTrace;
	
	private ProcessManager pm;
	private Scheduler scheduler;
	
	public MLFQRunner(InputSource inputsource, GanttChartGUI ganttChartGui, TimingTableGUI timingTable, MLFQSimulation simulation){
		this.source = inputsource;
		this.display = ganttChartGui;
		this.timingTable = timingTable;
		this.simulation = simulation;
		pm = new ProcessManager();
		pTrace = new LinkedList<ProcessTrace>();
		init();
	}
	
	public void init(){
		setPreferredSize(new Dimension(700, 500));
		
		JButton run = new JButton("RUN");
		run.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Process> processes = source.getProcessesCopy();
				ArrayList<Integer> levels = source.getLevelQuantaCopy();
				
				SchedulerType type = source.getLowestLevelScheduler();
				if(type == SchedulerType.NONE || processes == null || levels == null || processes.isEmpty() || levels.isEmpty()){
					JOptionPane.showMessageDialog(MLFQRunner.this, "Complete data on the input tab first!");
					return;
				}
				
				Process[] processList = new Process[processes.size()];
				for(int i = 0; i < processList.length; i++){
					processList[i] = processes.get(i);
					}
				
				int[] levelquanta = new int[levels.size()];
				for(int i = 0; i < levelquanta.length; i++){
					levelquanta[i] = levels.get(i);
				}
				
				pm = new ProcessManager();
				pm.addProcess(processList);
				Scheduler lowestLevelScheduler = getCorrespondingScheduler(type);
				if(lowestLevelScheduler == null) return;
				scheduler = new MLFQScheduler(lowestLevelScheduler, levelquanta);
				
				run();
				
				
				
			}
			
		});
		add(run);
	}
	
	public void run(){
		pTrace = new LinkedList<ProcessTrace>();
		int arrivalTime = pm.getLastArrivalTime();
		int time = 0;
		
		while(time < arrivalTime + 1){
			ArrayList<Process> arrived = pm.getProcessesStarted(time);
			for(Process p: arrived){
				scheduler.addProcess(p);
			}
			
			scheduler.step();
			Process last = scheduler.getLastActive();
			if(last != null){
				pTrace.add(new ProcessTrace(time, last.getID(), ((MLFQScheduler)scheduler).getLastLevel()));
			}else{
				pTrace.add(new ProcessTrace(time, ProcessTrace.NO_PROCESS, ProcessTrace.NO_PROCESS));
			}
			
			time++;
		}
		
		while(scheduler.hasProcess()){
			scheduler.step();
			Process last = scheduler.getLastActive();
			if(last != null){
				pTrace.add(new ProcessTrace(time, last.getID(), ((MLFQScheduler)scheduler).getLastLevel()));
			}else{
				pTrace.add(new ProcessTrace(time, ProcessTrace.NO_PROCESS, ProcessTrace.NO_PROCESS));
			}
			
			time++;
		}
		
		for(Process p: pm.getProcesses()){
			System.out.println(p.getID() + " " + p.getArrivalTime() + " " + p.getBurstTime() + " " + 
					p.getWaitTime() + " " + p.getTurnaroundTime() + " " + p.getEndTime());
		}
		
		display.setTrace(((MLFQScheduler)scheduler).getNumLevels(), pTrace);
		timingTable.setProcesses(pm.getProcesses());
		simulation.setAnimation(display.getNumLevels(), pTrace, display.getColorMap());
		
	}
	
	public Scheduler getCorrespondingScheduler(SchedulerType type){
		if(type == SchedulerType.FCFS){
			return new FCFSScheduler();
		}else
		if(type == SchedulerType.SJF){
			return new SJFScheduler();
		}else
		if(type == SchedulerType.SJF_PREEMPTIVE){
			return new SRTFScheduler();
		}else
		if(type == SchedulerType.PRIORITY){
			return new PriorityScheduler();
		}else
		if(type == SchedulerType.PRIORITY_PREEMPTIVE){
			return new PriorityScheduler(false);
		}else
		if(type == SchedulerType.ROUNDROBIN){
			boolean validInput = false;
			do{
				
				String message = JOptionPane.showInputDialog("Round Robin Quantum");
				try{
					if(message == null) continue;
					int quantum = Integer.parseInt(message);
					return new RRScheduler(quantum);
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(this, "Invalid input");
					validInput = false;
				}
			}while(!validInput);
		}
		return null;
	}
}
