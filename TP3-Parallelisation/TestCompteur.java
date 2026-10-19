public class TestCompteur {
    private static final int NB_TESTS = 50;
    private static final int NB_THREADS = 9;
    private static final int INCREMENTS_PAR_THREAD = 1111111;
    private static final int ATTENDU = NB_THREADS * INCREMENTS_PAR_THREAD;

    public static void main(String[] args) throws InterruptedException {
        int succes = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

		// boucle pour observer la variabilité
        for (int t = 0; t < NB_TESTS; t++) {
			// réinitialise le compteur
            CompteurDangereux.resetCompteur();
            Thread[] threads = new Thread[NB_THREADS];
			
			// créée les threads 
            for (int i = 0; i < NB_THREADS; i++) {
                threads[i] = new Thread(new CompteurDangereux.Incrementeur("Thread-" + i, INCREMENTS_PAR_THREAD));
            }

            for (Thread thread : threads) thread.start();
            for (Thread thread : threads) thread.join();

			// affiche le résultat et compare avec la valeur attendue
            int resultat = CompteurDangereux.getCompteur();
            if (resultat == ATTENDU) succes++;
            if (resultat < min) min = resultat;
            if (resultat > max) max = resultat;
        }

        System.out.println("\nStatistiques : ");
		System.out.println("Configuration : " + NB_THREADS + " threads x " + INCREMENTS_PAR_THREAD + " incréments");
        System.out.println("Succès : " + succes + "/" + NB_TESTS + " (" + (succes * 100.0 / NB_TESTS) + "%)");
        System.out.println("Valeur minimale obtenue : " + min);
        System.out.println("Valeur maximale obtenue : " + max);
        System.out.println("Valeur attendue : " + ATTENDU);
    }
}