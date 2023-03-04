import java.awt.Color;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;

import puisQuatre.Puis4;
/**
 * Cette classe définit le JPanel qui permet de dessiner le plateau de jeu.
 * @author hadri
 * @see JouerMouseListener
 */
public class PlateauPanel extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	
	private Puis4 modele;
	/**Nombre de lignes du plateau */
	private int nbLig;
	/**Nombre de colonnes du plateau */
    private int nbCol;
    
    Color couleurJoueur;
	Color couleurProg;
	
	private int taille_pion;
	
    /**
     * Création du Panel qui contient le plateau de jeu
     * @param modele : classe Puis4
     */
	public PlateauPanel(Puis4 modele) {
		
		this.setBackground(new Color(80, 71, 245));  //La couleur de l'arriere plan
		
		this.modele = modele;
		
		couleurJoueur = new Color(224, 53, 43);
		couleurProg = new Color(255, 224, 61);
		
		taille_pion = 65;
		
		modele.addPropertyChangeListener(this);   //On ajoute un PropertyChangeListener pour pouvoir redessiner le plateau à chaque changement du modèle
		this.addMouseListener(new JouerMouseListener(modele, taille_pion));//On ajoute un MouseListener pour détecter les clics de souris de l'utilisateur
		this.addMouseMotionListener(new ChoisirMouseMotionListener(modele));//On ajoute un MouseMotionListener pour détecter les déplacements de souris de l'utilisateur
	}
	
	/**
	 * Cette fonctionner permet de redessiner le plateau à chaque changement dans le modèle
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		
		this.repaint();
	}
	
	/**
	 * cette fonction est celle qui est chargée de dessiner le plateau de jeu
	 */
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		//on définit ces valeurs ici pour pouvoir les récupérer en cas de changement des paramètres par l'utilisateur
		this.nbLig = modele.getNbLignes();
		this.nbCol = modele.getNbColonnes();
		
		//une double boucle pour parcourir toutes les lignes et colones du plateau
		for (int l=0; l<nbLig; l++){
            for (int c=0; c<nbCol; c++){
            	switch(modele.getPion(c, l)) {  //On associe une couleur au pion en fonction de sa valeur
            	case Puis4.VIDE:
            		g.setColor(new Color(240, 197, 173));
            		break;
            	case Puis4.JOUEUR:
            		g.setColor(couleurJoueur);
            		break;
            	case Puis4.PROG:
            		g.setColor(couleurProg);
            	}
                
                g.fillOval(110+c*(taille_pion+30), 560-l*(taille_pion+10), taille_pion, taille_pion);  //On dessine le pion avec la bonne couleur
                g.setColor(Color.BLACK);
                g.drawOval(110+c*(taille_pion+30), 560-l*(taille_pion+10), taille_pion, taille_pion);
                
            }
        }
		//dessiner le pion qui suit la souris
		Color couleur_choix = modele.getNbPionsJoueur() == modele.getNbPionsProg() ? couleurJoueur:couleurProg; //la couleur en fonction du tour
		g.setColor(couleur_choix);
		g.fillOval(modele.getXMouse()-taille_pion/2, 560-nbLig*(taille_pion+10), taille_pion, taille_pion);
	}
	
	/**
	 * Cette fonction sert à changer la couleur du pion du joueur.
	 * 
	 * @param r : rouge
	 * @param g : vert
	 * @param b : bleu
	 */
	public void setCouleurJoueur(int r, int g, int b) {
		
		couleurJoueur = new Color(r, g, b);
	}
	
	/**
	 * Cette fonction sert à changer la couleur du pion du programme.
	 * 
	 * @param r : rouge
	 * @param g : vert
	 * @param b : bleu
	 */
	public void setCouleurProg(int r, int g, int b) {
		
		couleurProg = new Color(r, g, b);
	}

}
