
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import puisQuatre.Puis4;

/**
 * cette classe est listener qui écoute les boutons de la fenetre pricipale et exécute les actions demandées
 * 
 * @author hadri
 *
 */
public class ButtonListener implements ActionListener {
	
	private String text;    //Ce texte sert à paramétrer l'action du boutton
	private Puis4 modele;  
	
	public ButtonListener(String text, Puis4 modele) {
		
		this.text = text;
		this.modele = modele;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(text) {
		case "Réinitialiser":  //Réinitialiser le plateau
			modele.nouveauJeu();
			break;
		case "Nouveau Plateau":  //Changer les paramètres du plateau
			NvPltDialog dialogNvPlt = new NvPltDialog(modele);
			dialogNvPlt.setVisible(true);
			break;
		case "Enregistrer Sous":  //Enregistrer l'état de la partie dans un fichier
			EnregistrerDialog dialogEnregistrer = new EnregistrerDialog(modele);
			dialogEnregistrer.setVisible(true);
			break;
		case "Enregistrer":  //Enregistrer l'état de la partie
			modele.enregistrer();
			break;
		case "Charger":  //Charger une partie enregistrée dans un fichier
			ChargerDialog dialogCharger = new ChargerDialog(modele);
			dialogCharger.setVisible(true);
			break;
		}

	}

}
