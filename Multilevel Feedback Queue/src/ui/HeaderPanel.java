package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HeaderPanel extends JPanel {
	private static final String mainHeader = "Multilevel Feedback Queue";
	
	private String subHeader;
	
	private Font mainHeaderFont;
	private Font subHeaderFont;
	
	public HeaderPanel(String subHeader){
		this.subHeader = subHeader;
		mainHeaderFont = new Font("Georgia", Font.BOLD, 18);
		subHeaderFont = new Font("Georgia", Font.ITALIC, 14);
		init();
	}
	
	private void init(){
		setPreferredSize(new Dimension(700, 80));
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		
		FontMetrics metrics = null;
		
		
		// Draw Headers
		Dimension d = getSize();
		int height_last = 0;
		metrics = g2d.getFontMetrics(mainHeaderFont);
		g2d.setFont(mainHeaderFont);
		g2d.drawString(mainHeader, (d.width - metrics.stringWidth(mainHeader)) / 2, metrics.getHeight() + 20);
		height_last = metrics.getHeight() + 20;
		
		metrics = g2d.getFontMetrics(subHeaderFont);
		g2d.setFont(subHeaderFont);
		g2d.drawString(subHeader, (d.width - metrics.stringWidth(subHeader))/2,  height_last + metrics.getHeight());
		
	
	}
}
