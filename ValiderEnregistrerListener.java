import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import puisQuatre.Puis4;

public class ValiderEnregistrerListener implements ActionListener {
	
	Puis4 modele;
	EnregistrerDialog ed;
	JTextField fichField;
	
	public ValiderEnregistrerListener(Puis4 modele, EnregistrerDialog ed, JTextField fichField) {
		
		this.modele = modele;
		this.ed = ed;
		this.fichField = fichField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		modele.enregistrerSous(fichField.getText());
		ed.dispose();
	}

}
