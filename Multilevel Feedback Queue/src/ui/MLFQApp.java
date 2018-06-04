package ui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class MLFQApp extends JPanel {
	
	public static void main(String[] args){
		JFrame window = new JFrame("Multilevel Feedback Queue Scheduling");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(new MLFQApp());
		window.pack();
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	public MLFQApp(){
		init();
	}
	
	private void init(){
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel introPanel = new JPanel();
		introPanel.setPreferredSize(new Dimension(700, 500));
		
		MLFQInputGUI mlfqInputGui = new MLFQInputGUI();
		GanttChartDisplay ganttChartDisplay = new GanttChartDisplay();
		TimingTableGUI timingTable = new TimingTableGUI();
		MLFQSimulation simulation = new MLFQSimulation();
		
		tabbedPane.addTab("Intro", introPanel);
		tabbedPane.addTab("Input", mlfqInputGui);
		tabbedPane.addTab("Run", new MLFQRunner(mlfqInputGui, ganttChartDisplay.getGanttChartGUI(), timingTable, simulation));
		tabbedPane.addTab("Time Table", timingTable);
		tabbedPane.addTab("Gantt Chart", ganttChartDisplay);
		tabbedPane.addTab("Simulation", simulation);
	
		
		add(tabbedPane);
		
	}
}
