import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JTextField;

import puisQuatre.Puis4;

public class ValiderChargerListener implements ActionListener {
	
	Puis4 modele;
	ChargerDialog cd;
	JTextField fichField;

	public ValiderChargerListener(Puis4 modele, ChargerDialog chargerDialog, JTextField fichField) {
		this.modele = modele;
		this.cd = chargerDialog;
		this.fichField = fichField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String nomFichier = fichField.getText();
		File fichier = new File(nomFichier);
		if (fichier.exists()) {
			modele.chargerFichier(fichier);
		}
		
		cd.dispose();
	}

}
