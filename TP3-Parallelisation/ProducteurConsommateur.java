import java.util.concurrent.Semaphore;
import java.util.LinkedList;
import java.util.Queue;

public class ProducteurConsommateur {
	private static final int TAILLE_BUFFER = 5;
	private final Integer[] buffer = new Integer[TAILLE_BUFFER];

	private int in = 0;  // écriture
	private int out = 0; // lecture

	// Déclaration des 3 sémaphores avec les bonnes valeurs initiales
	private final Semaphore placesLibres = new Semaphore(TAILLE_BUFFER); // buffer vide -> 5 places libres
	private final Semaphore elementsDisponibles = new Semaphore(0); // aucun élément à consommer
	private final Semaphore mutexBuffer = new Semaphore(1); // erreur -> si ordre des acqisitions mal faites
															// un seul thread peut manipuler le buffer à la fois

	private int genererProduit() {
        return (int) (Math.random() * 100);
    }
	
	class Producteur implements Runnable {
		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					int produit = genererProduit();

					// Attend une place libre
					placesLibres.acquire();
					
					// Acquéri l'accès exclusif au buffer 
					mutexBuffer.acquire();	
					
					// Ajoute l'élément au buffer
					buffer[in] = produit;
                    in = (in + 1) % TAILLE_BUFFER;
					
					// Libère l'accès au buffer
					mutexBuffer.release();
					
					// Signale qu'un élément est disponible
					elementsDisponibles.release();
				}
			} catch (InterruptedException e) {
				System.err.println("Thread interrompu : " + e.getMessage());
			}
		}
	}

	class Consommateur implements Runnable {
		public void run() {
			try {
				for (int i = 0; i < 7; i++) {
					
					// Attend qu'un élément soit disponible
					elementsDisponibles.acquire();
					
					// Acquéri l'accès exclusif au buffer
					mutexBuffer.acquire();
					
					// Retire un élément du buffer 
					Integer produit = buffer[out];
                    out = (out + 1) % TAILLE_BUFFER;
					
					// Libère l'accès au buffer
					mutexBuffer.release();
					
					// Signale qu'une place est libre
					placesLibres.release();
				}
			} catch (InterruptedException e) {
				System.err.println("Thread interrompu : " + e.getMessage());
			}
		}
	}
}