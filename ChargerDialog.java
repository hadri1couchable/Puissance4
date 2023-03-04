

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import puisQuatre.Puis4;

/**
 * Cette classe hérite de JDialog et sert à l'utilisateur pour donner le nom du fichier de la partie qu'il souhaite
 * charger sur le plateau
 * 
 * @author hadri
 *
 */
public class ChargerDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	//Hauteur et largeur du JDialog
	final static int D_HAUTEUR = 100;
	final static int D_LARGEUR = 300;
	
	public ChargerDialog(Puis4 modele) {
		
		getContentPane().setLayout(new GridLayout(2, 1)); //On paramètre le layout avec un GridLayout à 2 lignes et une colonne.
		setTitle("Charger Partie");
		setSize(D_LARGEUR, D_HAUTEUR);
		this.setLocation(650, 300);
		
		JPanel fichPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel fichLabel = new JLabel("Nom du fichier:");
		JTextField fichField = new JTextField(10);  //champs de texte
		JLabel objLabel = new JLabel(".obj");
		fichPanel.add(fichLabel);
		fichPanel.add(fichField);
		fichPanel.add(objLabel);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ValiderChargerListener(modele, this, fichField));  //le bouton est cablé et envoie le JTextField pour récupérer sa valeur
		buttonPanel.add(okButton);
		
		this.getContentPane().add(fichPanel);
		this.getContentPane().add(buttonPanel);
		}

}