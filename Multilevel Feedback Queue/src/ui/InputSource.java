package ui;

import java.util.ArrayList;

import process.Process;
import ui.MLFQInputGUI.SchedulerType;

public interface InputSource {
	public ArrayList<Process> getProcessesCopy();
	public ArrayList<Integer> getLevelQuantaCopy();
	public SchedulerType getLowestLevelScheduler();
}
