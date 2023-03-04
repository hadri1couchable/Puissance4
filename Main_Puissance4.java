import puisQuatre.Puis4;

/**
 * Cette classe permet d'instancier les classes Puis4, FenetrePuissance4.
 * <p>Elle rend le JDialog visible pour que l'utilisateur puisse paramétrer le jeu.</p>
 * 
 * Remarque : Pour jouer, le joueur doit cliquer dans la colonne dans laquelle il souhaite placer un pion
 * 
 * @see Puis4
 * @see FenetrePuissance4
 * @author hadri
 */
public class Main_Puissance4 {

	public static void main(String[] args) {
		
		Puis4 modele = new Puis4();
		FenetrePuissance4 fenetre = new FenetrePuissance4(modele);
		
		fenetre.setVisible(true);
		
	}

}
