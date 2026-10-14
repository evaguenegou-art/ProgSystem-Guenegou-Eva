public class CalculParallele {
    private static final int[] DONNEES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int MULTIPLICATEUR = 1000;

    public static void main(String[] args) {
        System.out.println("=== Calcul Parallèle ===");

        long debut = System.nanoTime();
        long sommeTotal = 0;
		
		Calcule[] threads = new Calcule[DONNEES.length];

        // Création des instances de Calcule dans une boucle
        for (int i = 0; i < DONNEES.length; i++) {
            threads[i] = new Calcule(DONNEES[i], MULTIPLICATEUR, i);
		}
		
		for (int i = 0; i < threads.length; i++) {
            threads[i].start();
		}
        
		// Récupère tous les résultats
		try {
            for (int i = 0; i < threads.length; i++) {
                threads[i].join();
                sommeTotal += threads[i].getResultat();
            }
        } catch (InterruptedException e) {
            System.err.println("Erreur : intérruption");
            Thread.currentThread().interrupt();
        }

        long duree = (System.nanoTime() - debut) / 1_000_000;
        System.out.println("Résultat total : " + sommeTotal);
        System.out.println("Durée : " + duree + " ms");
    }
}
