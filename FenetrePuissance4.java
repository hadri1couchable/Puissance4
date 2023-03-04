import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import puisQuatre.Puis4;

/**
 * Cette classe permet de définir le JFrame qui contiendra tous les éléments du jeu.
 * <p>
 * @author hadri
 * @see PlateauPanel
 * @see ButtonListener
 */
public class FenetrePuissance4 extends JFrame {

	private static final long serialVersionUID = 1L;
    
    /**
     * Création de la fenêtre du jeu
     * @param modele : classe Puis4
     */
	public FenetrePuissance4(Puis4 modele) {
		
		Image icon = Toolkit.getDefaultToolkit().getImage("logo_puissance4.jpg"); //icone de l'application
		
		this.setSize(1080, 740);
		this.setTitle("PUISSANCE 4");
		this.setIconImage(icon);
	
		JPanel buttonPanel = new JPanel(new FlowLayout()); //Panel qui contient les boutons de l'écran de jeu
		JButton buttonRein = new JButton("Réinitialiser");  //Le bouton sert à "retirer" tous les pions du plateau
		buttonRein.addActionListener(new ButtonListener("Réinitialiser", modele)); 
		buttonPanel.add(buttonRein);
		
		//Créer un panel du plateau
		PlateauPanel plateauPanel = new PlateauPanel(modele);
		
		//La barre de menu afin d'enregistrer une partie ou de modifier les paramètres
		//Les menus et sous menus pour enregistrer et charger une partie
		JMenuItem menuEnregistrerSous = new JMenuItem("Enregistrer Sous");
		menuEnregistrerSous.addActionListener(new ButtonListener("Enregistrer Sous", modele));
		JMenuItem menuEnregistrer = new JMenuItem("Enregistrer");
		menuEnregistrerSous.addActionListener(new ButtonListener("Enregistrer", modele));
		JMenuItem menuCharger = new JMenuItem("Charger");
		menuCharger.addActionListener(new ButtonListener("Charger", modele));
		
		JMenu menuFich = new JMenu("Fichier");  
		menuFich.add(menuEnregistrerSous);
		menuFich.add(menuEnregistrer);
		menuFich.add(menuCharger);
		
		//Les menus pour gérer les réglages de la partie et les couleurs
		JMenuItem menuRegl = new JMenuItem("Réglages Plateau");
		menuRegl.addActionListener(new ButtonListener("Nouveau Plateau", modele));
		JMenu menuCoul = new JMenu("Couleurs");
		JMenuItem coulJoueur = new JMenuItem("Joueur");
		coulJoueur.addActionListener(new ButtonCoulListener("Couleur Joueur", plateauPanel));
		menuCoul.add(coulJoueur);
		JMenuItem coulProg = new JMenuItem("Programme");
		coulProg.addActionListener(new ButtonCoulListener("Couleur Programme", plateauPanel));
		menuCoul.add(coulProg);
		
		JMenu menuPara = new JMenu("Paramètres");
		menuPara.add(menuRegl);
		menuPara.add(menuCoul);
		
		JMenuBar menuBar = new JMenuBar();  //On ajoute les menus à la barre de menu
		menuBar.add(menuFich);
		menuBar.add(menuPara);
		
		
		this.setJMenuBar(menuBar); //ajouter la barre de menu
		this.getContentPane().add(plateauPanel, "Center");  //ajouter le panel du plateau
		this.getContentPane().add(buttonPanel, "South");   //ajouter le panel du bouton
	}
}
