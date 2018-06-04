package ui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


@SuppressWarnings("serial")
public class GanttChartDisplay extends JPanel {

	private GanttChartGUI ganttchart;
	
	public GanttChartDisplay(){
		ganttchart = new GanttChartGUI();
		init();
	}
	
	private void init(){

		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(700, 500));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		
		JScrollPane scrollpane = new JScrollPane(ganttchart);
		scrollpane.setPreferredSize(new Dimension(700, 420));
		
		HeaderPanel headerPanel = new HeaderPanel("Gantt Chart");
		
		mainPanel.add(headerPanel);
		mainPanel.add(scrollpane);
		add(mainPanel);
	}
	
	public GanttChartGUI getGanttChartGUI(){
		return ganttchart;
	}
	
}
