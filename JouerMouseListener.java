import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;

import puisQuatre.Puis4;
/**
 * Ce listener hérite de MouseAdapter afin de permettre au joueur de jouer en cliquant dans la colonne dans laquelle il souhaite placer un pion.
 * <p>Il contient la fonction <em>finpartie()</em> qui permet d'afficher un jDialog si un coup permet de finir la partie.
 * @author hadri
 * @see MouseAdapter
 *
 */
public class JouerMouseListener extends MouseAdapter {
	
	private Puis4 modele;
	private int taille_pion;
	
	public JouerMouseListener(Puis4 modele, int taille_pion) {
		
		this.modele = modele;
		this.taille_pion = taille_pion;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(!modele.estTermine()) {
			int xMouse = e.getX(); //On récupère la coordonnée en abscisse du clic
			int col = (xMouse - 90)/(taille_pion+30);  //On peut calculer le numéro de la colonne
			
			switch(modele.getNbJoueurs()) {  //Selon le nombre de joueur les actions ne sont pas les mêmes
			
			case 1:  //si il n'y a qu'un joueur
				
				if (col < modele.getNbColonnes()) {
					modele.joueurJoue(col);  //on place un pion rouge dans la colonne du clic
					if (modele.estTermine()) {finPartie();}  //on vérifie que ce coup n'a pas permit de finir la partie
					else{modele.progJoue();}  //si non le programme joue un coup
				}
				break;
			
			case 2:  //si il y a 2 joueurs
				
				switch(modele.getNbPionsJoueur()-modele.getNbPionsProg()) {  //on vérifie si les joueurs ont le même nombre de pions sur le plateau pour savoir à qui est le tour
				case 0:
					if (col < modele.getNbColonnes()) {
						modele.joueurJoue(col);
					}
					break;
				case 1:
					if (col < modele.getNbColonnes()) {
						modele.progJoue(col);
					}
					break;
				}
				
				break;
				
			}
		}
		else {finPartie();}  //on vérifie que ce coup n'a pas permit de finir la partie
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		int xMouse = e.getX();
		modele.setXMouse(xMouse); //on envoie les coordonnées au modèle
	}
	
	
	 /**
	  * Cette fonction permet d'afficher un écran de fin (JDialig) lorque la partie est terminée
	  */
	public void finPartie() {
			
		JDialog termineDialog = new JDialog();
		termineDialog.getContentPane().setLayout(new FlowLayout());
		termineDialog.setSize(200, 150);
		termineDialog.setLocation(700, 400);
		
		String message;
		if(modele.estJoueurGagne()) {message = "Les rouges ont gagné!";}
		else if(modele.estProgGagne()) {message = "Les jaunes ont gagné!";}
		else {message = "Egalité!";}
		
		JLabel messageLabel = new JLabel(message);
		termineDialog.add(messageLabel, "Center");
		termineDialog.setVisible(true);
	}
}


