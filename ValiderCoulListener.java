import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSlider;

public class ValiderCoulListener implements ActionListener {
	
	private CouleurDialog jd;
	private JSlider sliderRouge;
	private JSlider sliderVert;
	private JSlider sliderBleu;
	private PlateauPanel pp;
	String text;
	
	public ValiderCoulListener(JSlider sliderRouge, JSlider sliderVert, JSlider sliderBleu, CouleurDialog jd, PlateauPanel pp, String progOuJoueur) {
		
		this.jd = jd;
		this.sliderRouge = sliderRouge;
		this.sliderVert = sliderVert;
		this.sliderBleu = sliderBleu;
		this.pp = pp;
		this.text = progOuJoueur;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(text) {
		case "Couleur Joueur":
			pp.setCouleurJoueur(sliderRouge.getValue(), sliderVert.getValue(), sliderBleu.getValue());
			break;
		case "Couleur Programme":
			pp.setCouleurProg(sliderRouge.getValue(), sliderVert.getValue(), sliderBleu.getValue());
			break;
		}
		jd.dispose();
	}

}
