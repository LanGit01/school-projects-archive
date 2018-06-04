package comparators;

import java.util.Comparator;

import process.Process;

public class PriorityComparator implements Comparator<Process>{

	@Override
	public int compare(Process p1, Process p2) {
		
		if(p1.getPriority() > p2.getPriority()){
			return 1;
		}else
		if(p1.getPriority() < p2.getPriority()){
			return -1;
		}
		
		return 1;
	}

}
