package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;


import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import process.Process;

@SuppressWarnings("serial")
public class TimingTableGUI extends JPanel{

	//private ArrayList<Process> processes;
	private JTable pTable;
	private ProcessTimingTableModel ptModel;
	
	public TimingTableGUI(){
		init();
	}
	
	public void init(){
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(700, 500));
		//mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setLayout(new BorderLayout());
		
		HeaderPanel headerPanel = new HeaderPanel("Time Table");
		
		ptModel = new ProcessTimingTableModel();
		pTable = new JTable(ptModel);
		JScrollPane scrollpane = new JScrollPane(pTable);
		//scrollpane.setBackground(Color.CYAN);
		//scrollpane.setPreferredSize(new Dimension(660, 200));
		
		
		
		mainPanel.add(headerPanel, BorderLayout.PAGE_START);
		mainPanel.add(scrollpane, BorderLayout.CENTER);
		add(mainPanel);
	}
	
	public void setProcesses(ArrayList<Process> processes){
		ptModel.setData(processes);
	}
	
	private class ProcessTimingTableModel extends AbstractTableModel{

		private String[] columnHeaders;
		private ArrayList<Object[]> data;
		
		public ProcessTimingTableModel(){
			columnHeaders = new String[6];
			data = new ArrayList<Object[]>();
			
			columnHeaders[0] = "PID";
			columnHeaders[1] = "Arrival Time";
			columnHeaders[2] = "Burst Time";
			columnHeaders[3] = "Wait Time";
			columnHeaders[4] = "Turnaround Time";
			columnHeaders[5] = "End Time";
		}
		
		public void addData(Process p){
			Object[] newData = new Object[6];
			
			newData[0] = p.getID();
			newData[1] = p.getArrivalTime();
			newData[2] = p.getBurstTime();
			newData[3] = p.getWaitTime();
			newData[4] = p.getTurnaroundTime();
			newData[5] = p.getEndTime();
			
			data.add(newData);
			
			fireTableRowsInserted(data.size() - 1, data.size() - 1);
		}
		
		public void setData(ArrayList<Process> processes){
			data.clear();
			for(Process p: processes){
				addData(p);
			}
		}
		
		@Override
		public String getColumnName(int col){
			return columnHeaders[col];
		}
		
		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public int getColumnCount() {
			return columnHeaders.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data.get(rowIndex)[columnIndex];
		}
		
	}
}
