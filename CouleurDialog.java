import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;

/**
 * cette classe hérite de JDialog et sert à l'utilisateur pour choisir une couleur pour les pions
 * @author hadri
 *
 */
@SuppressWarnings("serial")
public class CouleurDialog extends JDialog {
	
	final static int LARGEUR = 400;
	final static int HAUTEUR = 300;
	int r_c;
	int g_c;
	int b_c;
	
	public CouleurDialog(String progOuJoueur, PlateauPanel pp) {
	
	setTitle("Sélecteur de couleur");
	setSize(LARGEUR, HAUTEUR);
	
	
	ColorPanel cp = new ColorPanel(25, 140, 80);
	
	
//----------------------------------------------------------------------------------------------------------------------//
	
	JPanel panelRouge = new JPanel(new FlowLayout());
	JLabel labelRouge = new JLabel("Rouge: ");
	JLabel numberRouge = new JLabel(String.valueOf(r_c));
	JSlider sliderRouge = new JSlider(0, 255, 25);
	
	sliderRouge.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			
			numberRouge.setText(String.valueOf(sliderRouge.getValue()));
			cp.set_r(sliderRouge.getValue());
			cp.repaint();
		}
	});
	
	panelRouge.add(labelRouge);
	panelRouge.add(numberRouge);
	panelRouge.add(sliderRouge);
	
//----------------------------------------------------------------------------------------------------------------------//
	
	JPanel panelVert = new JPanel(new FlowLayout());
	JLabel labelVert = new JLabel("Vert:");
	JLabel numberVert = new JLabel(String.valueOf(g_c));
	JSlider sliderVert = new JSlider(0, 255, 140);

	sliderVert.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			
			numberVert.setText(String.valueOf(sliderVert.getValue()));
			cp.set_g(sliderVert.getValue());
			cp.repaint();
		}
	});
	
	panelVert.add(labelVert);
	panelVert.add(numberVert);
	panelVert.add(sliderVert);
	
//----------------------------------------------------------------------------------------------------------------------//
	
	JPanel panelBleu = new JPanel(new FlowLayout());
	JLabel labelBleu = new JLabel("Bleu:");
	JLabel numberBleu = new JLabel(String.valueOf(b_c));
	JSlider sliderBleu = new JSlider(0, 255, 80);

	sliderBleu.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			
			numberBleu.setText(String.valueOf(sliderBleu.getValue()));
			cp.set_b(sliderBleu.getValue());
			cp.repaint();
		}
	});
	
	panelBleu.add(labelBleu);
	panelBleu.add(numberBleu);
	panelBleu.add(sliderBleu);
	
//----------------------------------------------------------------------------------------------------------------------//
	
	cp.addMouseListener(new MouseInputAdapter() {
		public void mouseClicked(MouseEvent e) {
			
			int r_ = new Random().nextInt(256);
			cp.set_r(r_);
			numberRouge.setText(String.valueOf(r_));
			sliderRouge.setValue(r_);
			int g_ = new Random().nextInt(256);
			cp.set_g(g_);
			numberVert.setText(String.valueOf(g_));
			sliderVert.setValue(g_);
			int b_ = new Random().nextInt(256);
			cp.set_b(b_);
			numberBleu.setText(String.valueOf(b_));
			sliderBleu.setValue(b_);
		}
	});
	
//----------------------------------------------------------------------------------------------------------------------//
	
	JPanel buttonPanel = new JPanel(new FlowLayout());
	JButton okButton = new JButton("OK");
	okButton.addActionListener(new ValiderCoulListener(sliderRouge, sliderVert, sliderBleu, this, pp, progOuJoueur));
	buttonPanel.add(okButton);
	
//----------------------------------------------------------------------------------------------------------------------//
	
	JPanel cursorPanel = new JPanel(new GridLayout(3, 1));
	cursorPanel.add(panelRouge);
	cursorPanel.add(panelVert);
	cursorPanel.add(panelBleu);
	
	this.getContentPane().add(cursorPanel, "North");
	this.getContentPane().add(cp, "Center");
	this.getContentPane().add(buttonPanel, "South");
	}
	
//----------------------------------------------------------------------------------------------------------------------//
	
	public void setRougeLabel(int r_c) {
		this.r_c = r_c;
	}
	
	public void setVertLabel(int g_c) {
		this.g_c = g_c;
	}
	
	public void setBleuLabel(int b_c) {
		this.b_c = b_c;
	}
		
}
