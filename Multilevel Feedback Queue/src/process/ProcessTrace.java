package process;

public class ProcessTrace {
	
	public static final int NO_PROCESS = -1;
	
	private final int time;
	private final int id;
	private final int level;
	
	public ProcessTrace(int time, int id, int level){
		this.time = time;
		this.id = id;
		this.level = level;
	}
	
	public int getTime(){
		return time;
	}
	
	public int getID(){
		return id;
	}
	
	public int getLevel(){
		return level;
	}
}
