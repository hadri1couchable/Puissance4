package puisQuatre;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Cette classe permet de jouer à "puissance 4". 
 * <p>Elle comporte une methode <em>progJoue()</em> permettant 
 * de placer un pion du programme (PROG) d'une manière "intelligente".</p>
 * 
 * Remarques : <ul><li>La classe permet d'enregistrer des PropertyChangeListener. <br>
 * Cela signifie que la mise en place d'un "pion" (du programme ou du joueur)
 * dans le plateau (un tableau d'entiers) entraîne la notification du changement à
 * tous les "observateurs" (les PropertyChangeListeners qui se sont enregistrés).</li> 
 * <li>La classe implemente l'interface Serializable, nécessaire si on
 * désire sauvegarder l'état du jeu.</li>
 * <li>L'ensemble de cellules (pions et vides) du damier, le plateau, peut être visualisé
 * comme un tableau de deux dimensions. Chaque cellule représente une position 
 * dans le plateau accessible à partir du numéro de la colonne et du numéro de 
 * la ligne. Ainsi par exemple la cellule de colonne=1 et ligne=0 correspond
 * au deuxième emplacement à partir de la gauche (colonne 1) dans la ligne
 * inférieure -la plus base- (ligne 0). Graphiquement c'est l'emplacement "X"&nbsp;:
 * <pre>
 * . . . . . .
 * . . . . . .
 * . . . . . .
 * . . . . . .
 * . X . . . .</pre>
 * 
 * Le plateau peut être obtenu avec getPlateau(). 
 * Il peut aussi être modifié avec setPlateau(int[][]). <br>
 * Ces deux méthodes peuvent rendre service si vous désirez sauvegarder l'état
 * du jeu à un moment donné.</li></ul>
 * @author Miguel Tomasena. <br>
 * @see Plateau
 * @see java.beans.PropertyChangeListener
 */
public class Puis4 implements Serializable {
    private static final long serialVersionUID = 3L;
    // gestion des PropertyChangeListener
    private transient PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private Plateau plateau;
    /** Plateau du jeu. C'est à dire l'ensemble de cellules (pions) qui consituent le damier */	
    private int nbColonnes;
    /** Nombre de pions maximum par colonne. */
    private int nbLignes;
    /** La constante Puis4.VIDE represente un emplacement vide dans le plateau. */
    public static final int VIDE = 0;
    /** La constante Puis4.PROG represente un pion du programme. */
    public static final int PROG = 1;
    /** La constante Puis4.JOUEUR represente un pion du joueur. */
    public static final int JOUEUR = -1;
    /** Nombre de pions du programme placés dans le plateau. */
    private int nbPionsProg;
    /** Nombre de pions du joueur placés dans le plateau. */
    private int nbPionsJoueur;
    private static final int INFINI = 1000;
    private int niveauJeu; // profondeur dans l'arbre du jeu
    private int meilleurChoix;
    private int nbJoueurs;
    private String nomFichier;
    private int xMouse;
    
    /**
     * Création et initialisation d'un jeu avec valeurs par défaut.
     * 
     * Par défaut : le nombre maximum de pions (lignes) par colonne est 6, 
     * le nombre de colonnes est 7,
     * et le niveau de jeu est 2
     */
    public Puis4() {
        this(7, 6, 2); // dimensions et niveau par défaut
    }

    /**
     * Création et initialisation d'un jeu.
     * @param nbCol : nombre de colonnes
     * @param nbLig : nombre maximum de lignes (pions) par colonne, 
     * @param niveauJeu : niveau de jeu.
     * La qualité du jeu augmente avec le niveau du jeu. Ce niveau
     * correspond à la profondeur maximale d'analyse dans l'arbre de jeu.
     * Attention à l'explosion combinatoire. Par exemple niveauJeu = 3 est déjà assez important.
     */

    public Puis4(int nbCol, int nbLig, int niveauJeu) { // initalisation jeu
        setNbColonnes(nbCol);
        setNbLignes(nbLig);
        setNbJoueurs(1);
        setNiveauJeu(niveauJeu);
        this.setNbPionsJoueur(0);
        this.setNbPionsProg(0);
        plateau = new Plateau(nbCol,nbLig);
        this.nomFichier = "Default_File";
    }
    
    public void setXMouse(int x) {
    	int old = xMouse;
    	xMouse = x;
    	pcs.firePropertyChange("xMouse", old, x);
    }
    
    public int getXMouse() {
    	return xMouse;
    }
    
    /**
     * Nombre de lignes du jeu
     * @return entier
     */
    public int getNbLignes() {
        return nbLignes;
    }

    /**
     * Nombre de colonnes du jeu.
     * @return entier. Nombre de maximum de pions par ligne.
     */
    public int getNbColonnes() {
        return nbColonnes;
    }
    
    public int getNbJoueurs() {
    	return nbJoueurs;
    }

    /**
     * Fixe le nombre de lignes.
     * @param i
     */
    private void setNbLignes(int i) {
        int old = nbLignes;
        nbLignes = i;
        pcs.firePropertyChange("nbLignes", old, i);       
    }
    
    public void setNbJoueurs(int nb) {
    	nbJoueurs = nb;
    }

    /**
     * Fixe le nombre de colonnes.
     * @param i
     */
    private void setNbColonnes(int i) {
        int old = nbColonnes;
        nbColonnes = i;
        pcs.firePropertyChange("nbColonnes", old, i);	    
    }
    private void setNbPionsProg(int nbPionsProg) {
        int old = this.nbPionsProg;
        this.nbPionsProg = nbPionsProg;
        pcs.firePropertyChange("nbPionsProg", old, nbPionsProg);
    }

    private void setNbPionsJoueur(int nbPionsJoueur) {
        int old = this.nbPionsJoueur;
        this.nbPionsJoueur = nbPionsJoueur;
        pcs.firePropertyChange("nbPionsJoueur", old, nbPionsJoueur);
    }
    /**
     * Nombre de pions du programme placés dans le plateau.
     * @return entier représentant le nombre.
     */
    public int getNbPionsProg() {
        return nbPionsProg;
    }
    /**
     * Nombre de pions du joueur placés dans le plateau.
     * @return entier représentant le nombre.
     */
    public int getNbPionsJoueur() {
        return nbPionsJoueur;
    }

    /**
     * Profondeur maximale de l'analyse.
     * @return entier représentant le niveau de jeu.
     * C'est à dire le nombre de coups en "avant"
     * lors de l'analyse.
     */
    public int getNiveauJeu() {
        return niveauJeu;
    }

    /**
     * La méthode retourne le contenu d'un emplacement du plateau.
     * @return une constante indiquant le contenu du plateau dans l'emplacement.<br>
     * Trois possibles valeurs : Puis4.VIDE, Puis4.PROG ou Puis4.JOUEUR
     * @param colonne du plateau, 
     * @param ligne du plateau.
     * Les colonnes sont numérotées à partir de 0. La dernière colonne est : getColonnes()-1<br>
     * Les lignes sont numérotées à partir de 0. La dernière ligne est : getLignes()-1<br>
     */
    public int getPion(int colonne,int ligne) {
        return plateau.getT()[colonne][ligne];
    }

    /**
     * Fixe le niveau de jeu. C'est à dire
     * nombre de coups en avant lors de l'analyse. <br>
     * Attention à la progression exponentiel du nombre de positions analysées. 
     * Par exemple le niveau n signifie (nbColonnes puissance n) positions à étudier.
     * @param i un int représentant le nivau du jeu.
     */
    public void setNiveauJeu(int i) {
        int old = niveauJeu;
        niveauJeu = i;
        pcs.firePropertyChange("niveauJeu", old, i);
    }

    /**
     * La méthode initialise le plateau de jeu à vide.
     * Les dimensions et la profondeur du jeu restent
     * les mêmes.
     *
     */
    public void nouveauJeu() {
        this.setNbPionsJoueur(0);
        this.setNbPionsProg(0);
        plateau = new Plateau(nbColonnes, nbLignes); // plateau vide
        pcs.firePropertyChange("plateau", null, null);
    }

    /**
     * La méthode initialise le plateau de jeu à vide.
     * Les dimensions et la profondeur du jeu sont modifiées.
     * @param nbLig nombre de lignes du plateau
     * @param nbCol nombre de colonnes du plateau
     * @param niveauJeu représente le nivau du jeu.
     */
    public void nouveauJeu(int nbCol, int nbLig, int niveauJeu) {
        this.setNbPionsJoueur(0);
        this.setNbPionsProg(0);
        setNbColonnes(nbCol);
        setNbLignes(nbLig);
        setNiveauJeu(niveauJeu);
        plateau = new Plateau(nbCol, nbLig);
        pcs.firePropertyChange("plateau", null, null);
    }		

    /**
     * Le joueur joue, il place un pion, dans la colonne col.
     * S'il n'était pas possible de placer un pion dans la
     * colonne, alors le plateau reste inchangé.
     * Les colonnes sont numérotées à partir de 0.<br>
     * Le pion du joueur (Puis4.JOUEUR) sera placé dans la ligne
     * de valeur numérique la plus faible possible pour simuler
     * la "gravité". <br> 
     * Par exemple si la colonne est vide le premier pion ira
     * dans la ligne 0.
     * 
     * @param col la colonne où joue le joueur
     */
    public void joueurJoue(int col) {
        plateau.jouer(col, JOUEUR);
        setNbPionsJoueur(getNbPionsJoueur()+1);
    }
    /**
     * Cette méthode fait appel à l'analyse du jeu (algorithme alfa-beta)
     * puis elle place un pion du programme (PROG) dans le "meilleur" choix
     * d'après l'analyse.	 * 
     * C'est LA METHODE permettant de jouer au programme.
     * @return int correspondant à la colonne choisie par la programme pour placer un pion.
     */
    public int progJoue() {
        // le programme choisi la ligne pour jouer 
        alfaBeta(plateau, -INFINI - 1, INFINI + 1, 0);
        plateau.jouer(this.getMeilleurChoix(), PROG);
        setNbPionsProg(getNbPionsProg()+1);
        return meilleurChoix;
    }
    /**
     * On utilise cette méthode seulement quand on souhaite 
     * faire jouer deux joueurs (personnes) l'un contre l'autre
     * en utilisant l'interface.
     * Dans cette situation PROG represente un fait un pion 
     * d'un des joueurs. 
     * Cette méthode place un pion de PROG, dans la colonne col.
     * S'il n'était pas possible de placer un pion dans la
     * colonne, alors le plateau reste inchangé.
     * 
     * @param col la colonne où joue le 2nd joueur
     */
    public void progJoue(int col) {
        // En fait le 'programme' est un autre joueur 
        plateau.jouer(col, PROG);
        setNbPionsProg(getNbPionsProg()+1);
    }
    /**
     * Méthode d'analyse du jeu. Algorithme alfa-beta
     * 
     * @param plateau
     * @param alfa
     * @param beta
     * @param prof
     * @return
     */
    private int alfaBeta(Plateau plateau, int alfa, int beta, int prof) {
        int ligne = 0; // no de la ligne pour jouer
        if (prof == this.getNiveauJeu() || plateau.estTermine()) {
            return eval(plateau);
        } else {
            if (prof % 2 == 1) { // c'est un noeud min
                while (ligne < plateau.getNbLig() && alfa < beta) {
                    if (plateau.estPossibleJouer(ligne)) {
                        plateau.jouer(ligne, Puis4.JOUEUR);
                        beta =
                                Math.min(
                                        beta,
                                        alfaBeta(plateau, -INFINI, beta, prof + 1));
                        // retirer le pion de ligne	
                        plateau.dejouer(ligne);
                    }
                    ligne++;
                }
                return beta;
            } else { // noeud max
                while (ligne < plateau.getNbLig() && alfa < beta) {
                    if (plateau.estPossibleJouer(ligne)) {
                        plateau.jouer(ligne, Puis4.PROG);
                        int a = alfaBeta(plateau, alfa, INFINI, prof + 1);
                        if (a > alfa) {
                            alfa = a;
                            if (prof == 0) {
                                this.setMeilleurChoix(ligne);
                                // actualisation choix
                            }
                        }
                        // retirer le pion de ligne	
                        plateau.dejouer(ligne);
                    }
                    ligne++;
                }
                return alfa;
            }
        }
    }
    /**
     * Evaluation du plateau de jeu
     * @param plateau
     * @return
     */
    private int eval(Plateau plateau) {
        if (plateau.estGagne(JOUEUR)) {
            return -INFINI;
        } else if (plateau.estGagne(PROG)) {
            return INFINI;
        } else {
            int points = 0; // nb de point de l'évaluation accumulés
            int l; // ligne
            int c; // colonne
            int liberte; // dégré liberté, ie nb de extrémités non bloquées
            int nb; // nb de pions d'un même joueur alignés
            int qui; // valeur du dernier pion : PROG ou JOUEUR 
            int[][] t = plateau.getT();
            // Vertical
            for (l = 0; l < plateau.getNbLig(); l++) {
                c = 0;
                liberte = 0;
                nb = 1;
                qui = t[l][c];
                if (qui == Puis4.VIDE)
                    continue; // colonne vide
                c++;
                while (c < plateau.getNbCol() && t[l][c] != Puis4.VIDE) {
                    if (qui == t[l][c]) {
                        nb++;
                    } else {
                        qui = t[l][c];
                        nb = 1;
                    }
                    c++;
                }
                if (c < plateau.getNbCol()) {
                    points = points + sequence(nb, qui, 1);
                }
            }

            /*******************************/
            // Horizontale
            for (c = 0; c < plateau.getNbCol(); c++) {
                l = 0;
                ;
                liberte = 0;
                nb = 0;
                qui = Puis4.VIDE;
                while (l < plateau.getNbLig()) {
                    if (qui != Puis4.VIDE
                            && t[l][c] == Puis4.VIDE) { // on comptabilise la sequence
                        points = points + sequence(nb, qui, liberte + 1);
                        qui = t[l][c];
                    } else if (
                            qui == Puis4.VIDE
                            && t[l][c] != Puis4.VIDE) { // on commence une seq
                        nb = 1;
                        if (l > 0)
                            liberte = 1;
                        qui = t[l][c];
                    } else if (
                            qui != Puis4.VIDE
                            && t[l][c] != Puis4.VIDE
                            && qui == t[l][c]) {
                        nb++; // on incremente la séquence
                    } else if (
                            qui != Puis4.VIDE
                            && t[l][c] != Puis4.VIDE
                            && qui != t[l][c]) {
                        // changement de séquence, on comptabilise
                        points = points + sequence(nb, qui, liberte);
                        liberte = 0;
                        qui = t[l][c];
                        nb = 0;
                    }
                    l++;
                }

            }

            /*******************************/
            // Diagonale 1

            //  on commence par la diag "intéressante" la plus àgauche
            int ligne = 0;
            int colonne = plateau.getNbCol() - 4;
            while (ligne < plateau.getNbLig() - 3) {
                l = ligne;
                c = colonne;
                liberte = 0;
                nb = 0;
                qui = Puis4.VIDE;
                while (l < plateau.getNbLig() && c < plateau.getNbCol()) {
                    if (qui != Puis4.VIDE
                            && t[l][c] == Puis4.VIDE) { // on comptabilise la sequence
                        points = points + sequence(nb, qui, liberte + 1);
                        qui = t[l][c];
                    } else if (
                            qui == Puis4.VIDE
                            && t[l][c] != Puis4.VIDE) { // on commence une seq
                        nb = 1;
                        if (l > 0)
                            liberte = 1;
                        qui = t[l][c];
                    } else if (
                            qui != Puis4.VIDE
                            && t[l][c] != Puis4.VIDE
                            && qui == t[l][c]) {
                        nb++; // on incremente la séquence
                    } else if (
                            qui != Puis4.VIDE
                            && t[l][c] != Puis4.VIDE
                            && qui != t[l][c]) {
                        // changement de séquence, on comptabilise
                        points = points + sequence(nb, qui, liberte);
                        liberte = 0;
                        qui = t[l][c];
                        nb = 0;
                    }
                    l++;
                    c++;
                }
                colonne--;
                if (colonne < 0) {
                    colonne = 0;
                    ligne++;
                }
            }

            /*******************************/
            // Diagonale 2

            //  on commence par la diag "intéressante" la plus à gauche
            ligne = 3;
            colonne = 0;
            while (colonne < plateau.getNbCol() - 3) {
                l = ligne;
                c = colonne;
                liberte = 0;
                nb = 0;
                qui = Puis4.VIDE;
                while (l >= 0 && c < plateau.getNbCol()) {
                    if (qui != Puis4.VIDE
                            && t[l][c] == Puis4.VIDE) { // on comptabilise la sequence
                        points = points + sequence(nb, qui, liberte + 1);
                        qui = t[l][c];
                    } else if (
                            qui == Puis4.VIDE
                            && t[l][c] != Puis4.VIDE) { // on commence une seq
                        nb = 1;
                        if (l > 0)
                            liberte = 1;
                        qui = t[l][c];
                    } else if (
                            qui != Puis4.VIDE
                            && t[l][c] != Puis4.VIDE
                            && qui == t[l][c]) {
                        nb++; // on incremente la séquence
                    } else if (
                            qui != Puis4.VIDE
                            && t[l][c] != Puis4.VIDE
                            && qui != t[l][c]) {
                        // changement de séquence, on comptabilise
                        points = points + sequence(nb, qui, liberte);
                        liberte = 0;
                        qui = t[l][c];
                        nb = 0;
                    }
                    l--;
                    c++;
                }
                ligne++;
                if (ligne >= plateau.getNbLig()) {
                    colonne++;
                    ligne = plateau.getNbLig() - 1;
                }
            }
            return points;
        }
    }
    /**
     * Pour l'évaluation, on donne un poids à une séquence de pions.
     * @param nb
     * @param qui
     * @param liberte
     * @return
     */
    private int sequence(int nb, int qui, int liberte) {
        // evalue une sequence de pions;
        return (nb * nb * nb * nb * liberte) * qui;
    }
    /**
     * Indique si le joueur a gagné la partie.
     * @return true si c'est le cas.
     */
    public boolean estJoueurGagne() {
        return plateau.estGagne(JOUEUR);
    }
    /**
     * Indique si le programme a gagné la partie.
     * @return true si c'est le cas.
     */
    public boolean estProgGagne() {
        return plateau.estGagne(PROG);
    }
    /**
     * Pour vérifier si le jeu est terminé.
     * C'est à dire un des joueur a gagné ou le plateau est plein.
     * @return true si le jeu est terminé.
     */
    public boolean estTermine() {
        return plateau.estPlein()
                || this.estJoueurGagne()
                || this.estProgGagne();
    }
    /**
     * Pour vérifier s'il est possible de jouer dans une certaine colonne
     * @return true s'il est possible. 
     * @param c : numero de la colonne
     */
    public boolean estPossibleJouer(int c) {
        return plateau.estPossibleJouer(c);
    }
    /**
     * Converti le plateau sous forme d'une chaine
     * @return la chaîne de caracteres représentant le plateau.
     */
    public String toString() {
        return plateau.toString();
    }

    /**
     * Résultat de l'analyse alfa-beta.
     * Numéro de la colonne du jeu où le programme a placé le dernier pion.
     * @return int  "meilleur choix" fait par le programme
     */
    public int getMeilleurChoix() {
        return meilleurChoix;
    }

    /**
     * @param i
     */
    private void setMeilleurChoix(int i) {
        meilleurChoix = i;
    }

    /**
     * Pour obtenir le plateau courant du jeu. C'est à dire l'ensemble de pions du jeu (le plateau).<br>
     * @return int[][] tableau d'entiers représentant les cellules du jeu.
     */
    public int[][] getPlateau() {
        return plateau.getT();
    }

    /**
     * Pour modifier le plateau de jeu. <br>
     * L'ensemble de pions du plateau et eventuellement sa taille 
     * sont remplacés.<br>
     * Le niveau du jeu reste inchangé.<br>
     * @param t tableau représentant le nouveau plateau.
     */
    public void setPlateau(int[][] t) {
        int nbLig = t[0].length;
        int nbCol = t.length;
        this.setNbLignes(nbLig);
        this.setNbColonnes(nbCol);	
        plateau = new Plateau(nbCol,nbLig);		
        plateau.setT(t);
        int nJ = 0; // nb pions JOUEUR
        int nP = 0; // nb pions PROG		
        for (int l=0; l<nbLig; l++){
            for (int c=0; c<nbCol; c++){
                if (this.getPion(c, l) == Puis4.PROG)
                    nP++;
                else if (this.getPion(c, l) == Puis4.JOUEUR)
                    nJ++;
            }
        }
        this.setNbPionsJoueur(nJ);
        this.setNbPionsProg(nP);

        pcs.firePropertyChange("plateau", null, null);
    }
    
    public void enregistrerSous(String nomFichier) {
    	this.nomFichier = nomFichier;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomFichier) );
			oos.writeObject(plateau.getT());
			oos.close();
		} catch (IOException e) {
			System.err.println("Exception enregistrerSous");
		}		
	}
    
    public void enregistrer() {
    	enregistrerSous(nomFichier);
    }
    
    public void chargerFichier(File fichier) {		
		try {
			ObjectInputStream ois = 
				new ObjectInputStream(
						new FileInputStream(fichier));
			try {
				int[][] plt_tab = (int[][]) ois.readObject();
				plateau = new Plateau(plt_tab.length-1, plt_tab[0].length-1);
				plateau.setT(plt_tab);
				this.setNbLignes(plt_tab.length-1);
				this.setNbColonnes(plt_tab[0].length+1);
			} catch (ClassNotFoundException e) {
				System.err.println("ClassNotFoundException Puis4");
				e.printStackTrace();
			}
			ois.close();
		} catch (FileNotFoundException e) {
			System.err.println("Exception FileNotFound Puis");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException Puis4");
			e.printStackTrace();
		}	
		pcs.firePropertyChange("plateau", null, null);
	}	

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return pcs.getPropertyChangeListeners();
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName, listener);
    }

    public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
        return pcs.getPropertyChangeListeners(propertyName);
    }
}
