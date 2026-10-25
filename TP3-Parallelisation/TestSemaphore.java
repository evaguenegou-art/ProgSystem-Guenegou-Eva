public class TestSemaphore {

    public static void main(String[] args) throws InterruptedException {
        // Scénario équilibré
        System.out.println(" Scénario équilibré ");
        ProducteurConsommateur pc1 = new ProducteurConsommateur();

        Thread[] prod1 = new Thread[2];
        Thread[] conso1 = new Thread[2];

        for (int i = 0; i < prod1.length; i++) prod1[i] = new Thread(pc1.new Producteur());
        for (int i = 0; i < conso1.length; i++) conso1[i] = new Thread(pc1.new Consommateur());

        for (Thread t : prod1) t.start();
        for (Thread t : conso1) t.start();

        for (Thread t : prod1) t.join();
        for (Thread t : conso1) t.join();

        System.out.println("Scénario équilibré terminé.");

        // Scénario déséquilibré
        System.out.println("\n Scénario déséquilibré ");
        ProducteurConsommateur pc2 = new ProducteurConsommateur();

        Thread[] prod2 = new Thread[5];
        Thread[] conso2 = new Thread[2];

        for (int i = 0; i < prod2.length; i++) prod2[i] = new Thread(pc2.new Producteur());
        for (int i = 0; i < conso2.length; i++) conso2[i] = new Thread(pc2.new Consommateur());

        for (Thread t : prod2) t.start();
        for (Thread t : conso2) t.start();

        for (Thread t : prod2) t.join();
        for (Thread t : conso2) t.join();

        System.out.println("Scénario déséquilibré terminé.");
    }
}