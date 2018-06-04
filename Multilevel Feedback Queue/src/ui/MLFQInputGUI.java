package ui;

import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import process.Process;

/**
 * User interface that handles user operations such as:<br>
 * <ul>
 * 		<li>Adding Process</li>
 * 		<li>Adding Levels</li>
 * </ul>
 * 
 * @author ace
 *
 */
@SuppressWarnings("serial")
public class MLFQInputGUI extends JPanel implements InputSource {
	
	public final String[] schedulers = new String[]{"FCFS", "SJF", "SJF preemptive", "Priority", "Priority preemptive", "Round Robin"};
	public enum SchedulerType { NONE, FCFS, SJF, SJF_PREEMPTIVE, PRIORITY, PRIORITY_PREEMPTIVE, ROUNDROBIN};
	
	private ArrayList<Process> inputProcesses;
	private ArrayList<Integer> levelQuanta;
	private SchedulerType lowestLevel;
	
	
	public MLFQInputGUI(){
		inputProcesses = new ArrayList<Process>();
		levelQuanta = new ArrayList<Integer>();
		lowestLevel = SchedulerType.FCFS;
		init();
	}
	
	private void init(){
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JPanel setup = new JPanel(new GridLayout(1, 2));

		setup.add(new AddProcessPanel());
		setup.add(new AddLevelPanel());
		mainPanel.add(setup, BorderLayout.PAGE_START);
		
		JPanel run = new JPanel();
		JButton runButton = new JButton("Run");
		runButton.setPreferredSize(new Dimension(100, 40));
		run.add(runButton);
		
		add(mainPanel);
		
		
	}
	
	public Process addProcess(int at, int bt, int pt){
		Process p = new Process(inputProcesses.size(), at, bt, pt);
		inputProcesses.add(p);
		return p;
	}
	
	private void normalizeProcesses(){
		ArrayList<Process> newList = new ArrayList<Process>(inputProcesses.size());
		
		int i = 0;
		for(Process p: inputProcesses){
			newList.add(new Process(i, p.getArrivalTime(), p.getBurstTime(), p.getPriority()));
			i++;
		}
		
		inputProcesses = newList;
	}
	
	public ArrayList<Process> getProcessesCopy(){
		normalizeProcesses();
		
		ArrayList<Process> copy = new ArrayList<Process>(inputProcesses.size());
		
		for(Process p: inputProcesses){
			copy.add(new Process(p.getID(), p.getArrivalTime(), p.getBurstTime(), p.getPriority()));
		}
		
		return copy;
	}
	
	public void addLevelQuantum(int quantum){
		levelQuanta.add(quantum);
	}
	
	public ArrayList<Integer> getLevelQuantaCopy(){
		ArrayList<Integer> copy = new ArrayList<Integer>();
		
		for(Integer i: levelQuanta){
			copy.add(new Integer(i));
		}
		
		return copy;
	}
	
	public SchedulerType getLowestLevelScheduler(){
		return lowestLevel;
	}
	
	
	/**
	 * GUI Panel for adding process via user input
	 * 
	 * @author ace
	 *
	 */
	private class AddProcessPanel extends JPanel{
		
		private ProcessTableModel ptModel;
		
		public AddProcessPanel(){
			init();
		}
		
		private void init(){
			setPreferredSize(new Dimension(350, 500));
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			
			
			// Process table
			ptModel = new ProcessTableModel();
			JTable processTable = new JTable(ptModel);
			JScrollPane scrollpane = new JScrollPane(processTable);
			
			add(scrollpane, BorderLayout.CENTER);
			
			// Buttons panel
			JPanel buttonPanel = new JPanel();
			buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
			JButton addProcessButton = new JButton("Add Process");
			addProcessButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					getInput();
				}
				
			});
			buttonPanel.add(addProcessButton);
			
			
			
			add(buttonPanel, BorderLayout.PAGE_END);
		}
		
		private void getInput(){
			JPanel dialogPanel = new JPanel(new BorderLayout());
			
			JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
			labels.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
			labels.add(new JLabel("Arrival Time"));
			labels.add(new JLabel("Burst Time"));
			labels.add(new JLabel("Priority"));
			dialogPanel.add(labels, BorderLayout.WEST);
			
			JPanel inputs = new JPanel(new GridLayout(0, 1, 2, 2));
			JTextField at = new JTextField();
			inputs.add(at);
			JTextField bt = new JTextField();
			inputs.add(bt);
			JTextField pt = new JTextField();
			inputs.add(pt);
			dialogPanel.add(inputs, BorderLayout.CENTER);
			
			
			int option = JOptionPane.showConfirmDialog(MLFQInputGUI.this, dialogPanel, "New Process", JOptionPane.OK_CANCEL_OPTION);
			if(option == JOptionPane.OK_OPTION){
				try{
					int pat = Integer.parseInt(at.getText());
					int pbt = Integer.parseInt(bt.getText());
					int ppt = 0;
					if(pt.getText().equals("")){
						ppt = 0;
					}else{
						ppt = Integer.parseInt(pt.getText());
					}
					
					MLFQInputGUI inputgui = MLFQInputGUI.this;
					
					Process proc = inputgui.addProcess(pat, pbt, ppt);
					ptModel.addData(proc);
					
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(MLFQInputGUI.this, "Invalid Input", "Input", JOptionPane.OK_OPTION);
					
				}
			}
		}
	}
	
	private class ProcessTableModel extends AbstractTableModel{
		
		private String[] columnNames;
		private ArrayList<Object[]> data;
		
		public ProcessTableModel(){
			columnNames = new String[4];
			data = new ArrayList<Object[]>();
			
			columnNames[0] = "PID";
			columnNames[1] = "Arrival Time";
			columnNames[2] = "Burst Time";
			columnNames[3] = "Priority";
		}
		
		public void addData(Process p){
			Object[] newData = new Object[columnNames.length];
			newData[0] = p.getID();
			newData[1] = p.getArrivalTime();
			newData[2] = p.getBurstTime();
			newData[3] = p.getPriority();
			data.add(newData);
		
			fireTableRowsInserted(data.size()-1, data.size()-1);
			
		}
		
		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public String getColumnName(int col){
			if(col > columnNames.length - 1) return null;
			return columnNames[col];
		}
		
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			
			return data.get(rowIndex)[columnIndex];
		}
	}
	
	/**
	 * GUI panel for adding levels
	 * 
	 * @author ace
	 *
	 */
	private class AddLevelPanel extends JPanel {
		
		
		private LevelTableModel levelmodel;
		
		public AddLevelPanel(){
			init();
		}
		
		public void init(){
			setPreferredSize(new Dimension(350, 500));
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			
			// Combobox
			JPanel comboPanel = new JPanel();
			comboPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 50, 10));
			JComboBox<String> schedulerChooser = new JComboBox<String>(schedulers);
			schedulerChooser.addItemListener(new ItemListener(){
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED){
						System.out.println(e.getItem());
						String type = (String)e.getItem();
						
						if(type.equals("FCFS")){
							lowestLevel = SchedulerType.FCFS;
						}else
						if(type.equals("SJF")){
							lowestLevel = SchedulerType.SJF;
						}else
						if(type.equals("SJF preemptive")){
							lowestLevel = SchedulerType.SJF_PREEMPTIVE;
						}else
						if(type.equals("Priority")){
							lowestLevel = SchedulerType.PRIORITY;
						}else
						if(type.equals("Priority preemptive")){
							lowestLevel = SchedulerType.PRIORITY_PREEMPTIVE;
						}else
						if(type.equals("Round Robin")){
							lowestLevel = SchedulerType.ROUNDROBIN;
						}
					
						
						
					}
					
				}
				
			});
			comboPanel.add(new JLabel("Select Lowest-level Scheduler"));
			comboPanel.add(schedulerChooser);
			
			//p.setPreferredSize(new Dimension(300, 100));
			add(comboPanel, BorderLayout.PAGE_START);
			
			// Level table
			levelmodel = new LevelTableModel();
			JTable levelTable = new JTable(levelmodel);
			JScrollPane scrollpane = new JScrollPane(levelTable);
			
			add(scrollpane, BorderLayout.CENTER);
			
			
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
			JButton addLevelButton = new JButton("Add Level");
			addLevelButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					getInput();
				}
				
			});
			buttonPanel.add(addLevelButton);
			
			add(buttonPanel, BorderLayout.PAGE_END);
			
		}
		
		public void getInput(){
			JPanel dialogPanel = new JPanel(new BorderLayout());
			
			JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
			labels.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
			labels.add(new JLabel("Quantum"));
			dialogPanel.add(labels, BorderLayout.WEST);
			
			JPanel inputs = new JPanel(new GridLayout(0, 1, 2, 2));
			JTextField quantumField = new JTextField();
			inputs.add(quantumField);
			dialogPanel.add(inputs, BorderLayout.CENTER);
			
			int option = JOptionPane.showConfirmDialog(MLFQInputGUI.this, dialogPanel, "New Process", JOptionPane.OK_CANCEL_OPTION);
			if(option == JOptionPane.OK_OPTION){
				try{
					int quantum = Integer.parseInt(quantumField.getText());
					
					MLFQInputGUI inputgui = MLFQInputGUI.this;
					
					inputgui.addLevelQuantum(quantum);
					levelmodel.addLevel(quantum);
					
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(MLFQInputGUI.this, "Invalid Input", "Input", JOptionPane.OK_OPTION);
				}
				
					

			}
		}
		
	}
	
	private class LevelTableModel extends AbstractTableModel{

		private String[] columnNames;
		private ArrayList<Object[]> data;
		
		public LevelTableModel(){
			columnNames = new String[2];
			data = new ArrayList<Object[]>();
			
			columnNames[0] = "Level";
			columnNames[1] = "Feedback Quantum";
		}
		
		
		public void addLevel(int quantum){
			Object[] newData = new Object[columnNames.length];
			
			newData[0] = data.size();
			newData[1] = quantum;
			
			data.add(newData);
			
			fireTableRowsInserted(data.size()-1, data.size()-1);
			
		}
		
		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public String getColumnName(int col){
			if(col > columnNames.length - 1) return null;
			return columnNames[col];
		}
		
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			
			return data.get(rowIndex)[columnIndex];
		}
		
	}

}
