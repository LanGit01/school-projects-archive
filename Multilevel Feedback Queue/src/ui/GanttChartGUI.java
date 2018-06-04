package ui;

import java.util.List;
import java.util.HashMap;
import java.util.Iterator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import process.ProcessTrace;

@SuppressWarnings("serial")
public class GanttChartGUI extends JPanel {

	private Dimension minDimension;
	
	private int levelHeight;
	private int horizontalBoxSpacing;
	private Dimension headerArea;
	private Dimension pbox;
	private Dimension timingArea;
	private Dimension levelLabelArea;
	
	private Font labelFont;
	private Font basicFont;
	
	private int levels;
	private List<ProcessTrace> pTrace;
	private HashMap<Integer, Color> colorMap;
	
	public GanttChartGUI(){
		colorMap = new HashMap<Integer, Color>();
		init();
	}
	
	public void setTrace(int levels, List<ProcessTrace> pTrace){
		this.levels = levels;
		this.pTrace = pTrace;
		
		// Generate Colors
		Iterator<ProcessTrace> ptItr = pTrace.iterator();
		while(ptItr.hasNext()){
			int id = ptItr.next().getID();
			if(!colorMap.containsKey(id)){
				generateRandomColor(id);
			}
		}
		
		Insets insets = getBorder().getBorderInsets(this);
		
		int height = insets.top + insets.bottom + headerArea.height + timingArea.height + (levels * levelHeight);
		int width = insets.left + insets.right + + levelLabelArea.width + (pTrace.size() * (pbox.width + horizontalBoxSpacing));
		setPreferredSize(new Dimension(width, height));
		
		if(height < getMinimumSize().height){
			Dimension min = getMinimumSize();
			headerArea = new Dimension(headerArea.width, ((min.height - height) / 2));
		}
		
		revalidate();
		repaint();
	}
	
	public int getNumLevels(){
		return levels;
	}
	
	public HashMap<Integer, Color> getColorMap(){
		return colorMap;
	}
	
	private void init(){
		labelFont = new Font("Verdana", Font.BOLD, 12);
		basicFont = new Font("Verdana", Font.PLAIN, 12);
		
		minDimension = new Dimension(650, 450);
		setMinimumSize(minDimension);
		
		levelHeight = 70;
		horizontalBoxSpacing = 0;
		headerArea = new Dimension(minDimension.width, 40);
		pbox = new Dimension(50, 50);
		timingArea = new Dimension(50, 40);
		levelLabelArea = new Dimension(100, 70);
		
		
		// Set Margin
		//setBorder(BorderFactory.createLineBorder(Color.BLACK, 20));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		Insets insets = getBorder().getBorderInsets(this);
		
		if(pTrace == null) return;
		
		FontMetrics metrics = null;
		
		// Draw level labels
		metrics = g2d.getFontMetrics(labelFont);
		g2d.setFont(labelFont);
		for(int i = 0; i < levels; i++){
			g2d.drawString("Level " + (i + 1), insets.left, insets.top + headerArea.height + (i * levelHeight) + ((levelHeight / 2 + metrics.getHeight())/ 2));
		}
		
		// Draw Processes
		int time = 0;
		Iterator<ProcessTrace> ptItr = pTrace.iterator();
		while(ptItr.hasNext()){
			ProcessTrace pt = ptItr.next();
			
			if(pt.getID() != ProcessTrace.NO_PROCESS){
				drawProcessBox(g2d, pt.getID(), pt.getLevel(), time, insets);
			}
			
			// Draw time
			int timingY = insets.top + headerArea.height + (levels * levelHeight);
			g2d.drawString(Integer.toString(time), insets.left + levelLabelArea.width + (time * (pbox.width + horizontalBoxSpacing)) + (pbox.width/2 - 3), timingY);
			
			time++;
		}
		
	}
	
	private void drawProcessBox(Graphics2D g, int pid, int level, int time, Insets insets){
		int x  = (insets == null ? 0 : insets.left) + levelLabelArea.width + (time * pbox.width);
		int y = (insets == null ? 0 : insets.top) + headerArea.height + (level * levelHeight);
		
		int colorx = x;
		int colory = y + (pbox.height / 2);
		
		Color prev = g.getColor();
		
		// Draw Process ID
		Font prevFont = g.getFont();
		
		g.setFont(basicFont);
		FontMetrics metrics = g.getFontMetrics(basicFont);
		
		String idString = "P" + Integer.toString(pid);
		g.drawString(idString, x + ((pbox.width - metrics.stringWidth(idString)) / 2) , y + ((pbox.height / 2 + metrics.getHeight()) / 2));
		
		// Draw Color
		g.setColor(colorMap.get(pid));
		g.fillRect(colorx, colory, pbox.width, pbox.height / 2);
		
		// Draw border
		g.setColor(Color.BLACK);
		g.drawRect(x, y, pbox.width, pbox.height);
		
		
		g.setColor(prev);
		g.setFont(prevFont);
	}
	
	
	private Color generateRandomColor(int pid){
		Color generated;
		
		do{
			int red = (int)(Math.random() * 127 + 127);
			int green = (int)(Math.random() * 127 + 127);
			int blue = (int)(Math.random() * 127 + 127);
			
			generated = new Color(red, green, blue);
			
		}while(colorMap.values().contains(generated));
		
			
		colorMap.put(pid, generated);
		return generated;
	}
	
}
