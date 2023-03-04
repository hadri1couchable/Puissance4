import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import puisQuatre.Puis4;
/**
 * Cette classe définit le JDialog qui permet à l'utilisateur de paramétrer le jeu.
 * @author hadri
 * @see NvPltValiderListener
 *
 */
public class NvPltDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;

	//Hauteur et largeur du JDialog
	final static int D_HAUTEUR = 310;
	final static int D_LARGEUR = 250;
	
	/**
	 * Création du JDialog
	 * @param modele : classe Puis4
	 * @param fenetre : classe FenetrePuissance4
	 */
	public NvPltDialog(Puis4 modele) {
	
		getContentPane().setLayout(new GridLayout(5, 1)); //On paramètre le layout avec un GridLayout à 5 lignes et une colonne.
		setTitle("Créer un nouveau plateau");
		setSize(D_LARGEUR, D_HAUTEUR);
		this.setLocation(650, 300);
		
		//le premier Panel qui contient un ComboBox pour choisir le nombre de lignes du plateau
		JPanel nbLigPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel nbLigLabel = new JLabel("Nombre de lignes:");
		JComboBox<Integer> nbLigCB = new JComboBox<Integer>();
		DefaultComboBoxModel<Integer> nbLigCBModel = new DefaultComboBoxModel<Integer>();
		nbLigCBModel.addElement(4);
		nbLigCBModel.addElement(5);
		nbLigCBModel.addElement(6);
		nbLigCBModel.addElement(7);
		nbLigCBModel.addElement(8);
		nbLigCBModel.setSelectedItem(6);
		nbLigCB.setModel(nbLigCBModel);
		nbLigPanel.add(nbLigLabel);
		nbLigPanel.add(nbLigCB);
		
		//le premier Panel qui contient un ComboBox pour choisir le nombre de colonnes du plateau
		JPanel nbColPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel nbColLabel = new JLabel("Nombre de colones:");
		JComboBox<Integer> nbColCB = new JComboBox<Integer>();
		DefaultComboBoxModel<Integer> nbColCBModel = new DefaultComboBoxModel<Integer>();
		nbColCBModel.addElement(4);
		nbColCBModel.addElement(5);
		nbColCBModel.addElement(6);
		nbColCBModel.addElement(7);
		nbColCBModel.addElement(8);
		nbColCBModel.setSelectedItem(7);
		nbColCB.setModel(nbColCBModel);
		nbColPanel.add(nbColLabel);
		nbColPanel.add(nbColCB);
		
		//le premier Panel qui contient un ComboBox pour choisir le niveau du programme si le joueur joue tout seul
		JPanel niveauPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel niveauLabel = new JLabel("Niveau du programme:");
		JComboBox<Integer> niveauCB = new JComboBox<Integer>();
		DefaultComboBoxModel<Integer> niveauCBModel = new DefaultComboBoxModel<Integer>();
		niveauCBModel.addElement(1);
		niveauCBModel.addElement(2);
		niveauCBModel.addElement(3);
		niveauCBModel.setSelectedItem(2);
		niveauCB.setModel(niveauCBModel);
		niveauPanel.add(niveauLabel);
		niveauPanel.add(niveauCB);
		
		//le premier Panel qui contient un ComboBox pour choisir le nombre de joueur souhaité
		JPanel nbJoueursPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel nbJoueurLabel = new JLabel("Nombre de Joueurs:");
		JComboBox<Integer> nbJoueursCB = new JComboBox<Integer>();
		DefaultComboBoxModel<Integer> nbJoueurCBModel = new DefaultComboBoxModel<Integer>();
		nbJoueurCBModel.addElement(1);
		nbJoueurCBModel.addElement(2);
		nbJoueursCB.setModel(nbJoueurCBModel);
		nbJoueursPanel.add(nbJoueurLabel);
		nbJoueursPanel.add(nbJoueursCB);
		
		//le bouton valider est cablé à un listener qui prend en paramètres tous les ComboBox, le modèle et la fenêtre du jeu
		JPanel validerPanel = new JPanel(new FlowLayout());
		JButton pltValider = new JButton("Valider");
		pltValider.addActionListener(new NvPltValiderListener(this, nbLigCB, nbColCB, niveauCB, nbJoueursCB, modele));
		validerPanel.add(pltValider);
		
		//on ajoute tous les panels au JDialog
		add(nbLigPanel);
		add(nbColPanel);
		add(niveauPanel);
		add(nbJoueursPanel);
		add(validerPanel);
	}
	

}
