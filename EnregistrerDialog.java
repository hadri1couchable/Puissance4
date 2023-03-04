

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import puisQuatre.Puis4;

/**
 * Cette classe hérite de JDialog et sert à l'utilisateur pour donner le nom du fichier pour enregistrer la partie en cours
 * 
 * @author hadri
 *
 */
public class EnregistrerDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	//Hauteur et largeur du JDialog
	final static int D_HAUTEUR = 100;
	final static int D_LARGEUR = 300;
	
	public EnregistrerDialog(Puis4 modele) {
		
		getContentPane().setLayout(new GridLayout(2, 1)); //On paramètre le layout avec un GridLayout à 2 lignes et une colonne.
		setTitle("Enregistrer Sous");
		setSize(D_LARGEUR, D_HAUTEUR);
		this.setLocation(650, 300);
		
		JPanel fichPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel fichLabel = new JLabel("Nom du fichier:");
		JTextField fichField = new JTextField(10);
		JLabel objLabel = new JLabel(".obj");
		fichPanel.add(fichLabel);
		fichPanel.add(fichField);
		fichPanel.add(objLabel);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ValiderEnregistrerListener(modele, this, fichField)); //le bouton est cablé et envoie le JTextField pour récupérer sa valeur
		buttonPanel.add(okButton);
		
		this.getContentPane().add(fichPanel);
		this.getContentPane().add(buttonPanel);
		}

}
