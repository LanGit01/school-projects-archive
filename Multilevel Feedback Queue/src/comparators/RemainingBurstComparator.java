package comparators;

import java.util.Comparator;

import process.Process;

public class RemainingBurstComparator implements Comparator<Process>{
	
	@Override
	public int compare(Process p1, Process p2){
		if(p1.getRemainingBurst() > p2.getRemainingBurst()){
			return 1;
		}else
		if(p1.getRemainingBurst() < p2.getRemainingBurst()){
			return -1;
		}
		return 0;
	}
}
