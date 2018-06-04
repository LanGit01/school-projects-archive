package comparators;

import java.util.Comparator;

import process.Process;

public class BurstTimeComparator implements Comparator<Process>{

	// increasing (p1 is gt/lt p2)
	@Override
	public int compare(Process p1, Process p2){
		if(p1.getBurstTime() > p2.getBurstTime()){
			return 1;
		}else
		if(p1.getBurstTime() < p2.getBurstTime()){
			return -1;
		}
		return -1;
	}

}
