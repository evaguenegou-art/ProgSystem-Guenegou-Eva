public class CompteurSecurise {
    private static int compteurGlobal = 0;  // Variable partagée MODIFIABLE
	
	// objet dédié comme verrou
	private static final Object verrou = new Object();

    static class Incrementeur implements Runnable {
        private final String nom;
        private final int nombreIncrements;

        public Incrementeur(String nom, int nombreIncrements) {
            this.nom = nom;
            this.nombreIncrements = nombreIncrements;
        }

        @Override
        public void run() {
            for (int i = 0; i < nombreIncrements; i++) {
                // protection de la section critique de la méthode run() 
				synchronized(verrou) {
					// opération critique
					compteurGlobal++;
				}
            }
            System.out.println(nom + " terminé. Compteur vu : " + compteurGlobal);
        }
    }

	public static int getCompteur() {
		// protection de la méthode getCompteur()
		synchronized(verrou) {
			return compteurGlobal;
		}
	}

	public static void resetCompteur() {
		// protection de la méthode resetCompteur()
		synchronized(verrou) {
			compteurGlobal = 0;
		}
	}
}
