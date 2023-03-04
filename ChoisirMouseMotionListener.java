import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import puisQuatre.Puis4;


/**
 * Cette classe est un listener des mouvements de la souris pour récupérer ses coordonnées
 * @author hadri
 *
 */
public class ChoisirMouseMotionListener implements MouseMotionListener {

	private Puis4 modele;
	
	public ChoisirMouseMotionListener(Puis4 modele) {
		
		this.modele = modele;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		int xMouse = e.getX();
		modele.setXMouse(xMouse); //on envoie les coordonnées au modèle
	}

}
