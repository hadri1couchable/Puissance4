

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ColorPanel extends JPanel {
	
	int r_c;
	int g_c;
	int b_c;
	
	public ColorPanel(int r_c, int g_c, int b_c) {
		
		this.r_c = r_c;
		this.g_c = g_c;
		this.b_c = b_c;
		this.repaint();
	}
	
	protected void paintComponent(Graphics g) {
		
		g.setColor(new Color(r_c, g_c, b_c));
		g.fillRect(0, 0, 1200, 800);
		
	}
	
	public void set_r(int r) {
		r_c = r;
	}
	
	public void set_g(int g) {
		g_c = g;
	}
	
	public void set_b(int b) {
		b_c = b;
	}
	
	

}
