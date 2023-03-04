import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Cette classe est un Listener qui permet d'ouvrir un JDialog pour gérer les couleurs des pions
 * 
 * @author hadri
 * @see CouleurDialog
 *
 */
public class ButtonCoulListener implements ActionListener {
	
	private String text; //Ce texte sert à choisir si on souhaite régler la couleur du joueur ou du programme
	private PlateauPanel pp; //On garde ce panel afin de modifier la couleur des pions

	public ButtonCoulListener(String text, PlateauPanel pp) {
		
		this.text = text;
		this.pp = pp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		//Instancier le JDialog et l'afficher lorque le listener est utilisé
		CouleurDialog dialogCouleurJoueur = new CouleurDialog(text, pp);
		dialogCouleurJoueur.setVisible(true);
	}



}
