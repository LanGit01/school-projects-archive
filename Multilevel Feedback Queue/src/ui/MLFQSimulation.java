package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import process.ProcessTrace;
import ui.simulation.DrawingCanvas;

@SuppressWarnings("serial")
public class MLFQSimulation extends JPanel {
	
	private int levels;
	private List<ProcessTrace> pTrace;
	private HashMap<Integer, Color> colorMap;
	
	private DrawingCanvas canvas;
	
	public MLFQSimulation(){
		init();
	}
	
	private void init(){
		levels = 0;
		pTrace = null;
		colorMap = null;
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		HeaderPanel headerPanel = new HeaderPanel("Real-Time Simulation");
		mainPanel.add(headerPanel);
		
		canvas = new DrawingCanvas();
		//mainPanel.add(canvas);
		
		JPanel buttonPanel = new JPanel();
		JButton startAnimationButton = new JButton("START ANIMATION");
		startAnimationButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(levels == 0 || pTrace == null || colorMap == null){
					JOptionPane.showMessageDialog(MLFQSimulation.this, "Complete data on input tab first!");
					return;
				}
				
				int timeStarted = 0;
				int successive = 0;
				int time = 0;
				for(time = 0; time < pTrace.size(); time++){
					ProcessTrace current = pTrace.get(time);
					
					if(current.getID() != ProcessTrace.NO_PROCESS){
						ProcessTrace started = current;
						timeStarted = time;
						successive = 0;
						
						while(time < pTrace.size() && current.getID() == started.getID() && current.getLevel() == started.getLevel()){
							if(time + 1 < pTrace.size()){
								current = pTrace.get(time + 1);
							}
							successive++;
							time++;
						}
						time--;
						
						//create
						System.out.println("PTRACE: " + started.getID() + " " + started.getLevel() + " " + timeStarted + " " + successive);
						canvas.addProcessTimeBox(started.getLevel(), timeStarted, successive, colorMap.get(started.getID()));
					}
				}
				
			
				canvas.setNumLevels(levels);
				canvas.setMaxTime(pTrace.size());
				
				JScrollPane scrollpane = new JScrollPane(canvas);
				scrollpane.setPreferredSize(new Dimension(600, 400));
				
				JOptionPane animationPane = new JOptionPane(scrollpane, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
				
				JDialog dialog = animationPane.createDialog(MLFQSimulation.this, "Animation");
				dialog.addWindowListener(new WindowAdapter(){
					
					public void windowClosing(WindowEvent we){
						canvas.stopAnimation();
					}
				});
				dialog.pack();
				dialog.setVisible(true);
				
				//canvas.startAnimation();
			}
			
		});
		buttonPanel.add(startAnimationButton);
		mainPanel.add(buttonPanel);
		
		add(mainPanel);
	}
	
	public void setAnimation(int levels, List<ProcessTrace> pTrace, HashMap<Integer, Color> colorMap){
		this.levels = levels;
		this.pTrace = pTrace;
		this.colorMap = colorMap;
	}
	
	
}
