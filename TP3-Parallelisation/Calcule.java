public class Calcule extends Thread {
    // Déclaration des attributs nécessaires
	private int valeur;
	private int multiplicateur;
	private int index;
	private long resultat = 0;
	
	public Calcule(int valeur, int multiplicateur, int index) {
		this.valeur = valeur;
		this.multiplicateur = multiplicateur;
		this.index = index;
	}
	
	@Override
	public void run() {
		// Calcul intensif
		for (int i = 0; i < this.multiplicateur; i++) {
			this.resultat += this.valeur * this.valeur + this.valeur;
							// on pourrais aussi mettre un sleep
		}
	}
	
	public long getResultat() {
		return resultat;
	}
	
	

}