import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import puisQuatre.Puis4;
/**
 * Cette classe est un listener qui écoute le bouton pour valider les paramètres du jeu
 * 
 * @author hadri
 * @see NvPltDialog
 *
 */
public class NvPltValiderListener implements ActionListener {
	
	private JDialog jd;
	private JComboBox<Integer> nbLigCB;
	private JComboBox<Integer> nbColCB;
	private JComboBox<Integer> niveauCB;
	private JComboBox<Integer> nbJoueursCB;
	private Puis4 modele;
	
	/**
	 * On récupère le JDialog, tout les comboBox dans leur état au moment ou le bouton est utilisé et le modèle.
	 * @param jd
	 * @param nbLigCB
	 * @param nbColCB
	 * @param niveauCB
	 * @param nbJoueursCB
	 * @param modele
	 */
	public NvPltValiderListener(JDialog jd, JComboBox<Integer> nbLigCB, JComboBox<Integer> nbColCB, JComboBox<Integer> niveauCB, JComboBox<Integer> nbJoueursCB, Puis4 modele) {
		
		this.jd = jd;
		this.nbLigCB = nbLigCB;
		this.nbColCB = nbColCB;
		this.niveauCB = niveauCB;
		this.nbJoueursCB = nbJoueursCB;
		this.modele = modele;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		modele.setNbJoueurs((int)nbJoueursCB.getSelectedItem());// changer le nombre de joueurs
		modele.nouveauJeu((int)nbColCB.getSelectedItem(), (int)nbLigCB.getSelectedItem(), (int)niveauCB.getSelectedItem()); //changer le nb de lignes, colonnes et le niveau du jeu
		jd.dispose();
	}

}
