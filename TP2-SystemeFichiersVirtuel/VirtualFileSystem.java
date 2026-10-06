import java.util.*;

public class VirtualFileSystem {

    private MemoryManager memoryManager;

    public VirtualFileSystem() {
        this.memoryManager =
                new MemoryManager();
    }

    private int allocateInode() {

        byte[] memory =
                memoryManager.getFilesystemMemory();

        // Parcourir les inodes de 0 à MAX_INODES - 1.
		for (int i = 0; i < MemoryManager.MAX_INODES; i++) {
            Inode inode = new Inode(memoryManager, i);
        // Identifier le premier inode libre.
		if (inode.getFileType() == 0) {
			// Retourner son numéro.
			return i;
            }
        }

        return -1;
    }

    public boolean createFile(
            String directory,
            String filename) {

        int inodeNum = allocateInode();

        if (inodeNum == -1) {
            return false;
        }

        // Construire l'inode.
		Inode inode = new Inode(memoryManager, inodeNum);
		
		long now = System.currentTimeMillis();
        int[] emptyPointers = new int[Inode.DIRECT_POINTERS];
		
        // L'initialiser comme fichier vide.
		inode.writeToMemory(1, 0, now, now, emptyPointers, 0, (short) 0644, 1);

        return true;
    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }
	
	public boolean writeFile(
			int inodeNum,
			byte[] data) {

		int blocksNeeded =
				(data.length
				+ MemoryManager.BLOCK_SIZE - 1)
				/ MemoryManager.BLOCK_SIZE;

		if (blocksNeeded > Inode.DIRECT_POINTERS) {
			return false;
		}

		int[] blockPointers =
				new int[Inode.DIRECT_POINTERS];

		// Allouer blocksNeeded blocs.
		for (int i = 0; i < blocksNeeded; i++) {
            int numBloc = memoryManager.allocateBlock();
            if (numBloc == -1) {
                return false; // Plus de bloc disponible
            }
            blockPointers[i] = numBloc;
        }

		byte[] memory =
				memoryManager.getFilesystemMemory();

		int bytesRemaining =
				data.length;

		int dataSrcOffset = 0;

		// Pour chaque bloc :
		// - calculer la quantité à copier ;
		// - récupérer le numéro du bloc ;
		// - calculer son offset physique ;
		// - copier les données.
		for (int i = 0; i < blocksNeeded; i++) {
            int aCopier = Math.min(bytesRemaining, MemoryManager.BLOCK_SIZE);
            int offsetBloc = blockPointers[i] * MemoryManager.BLOCK_SIZE;

            System.arraycopy(data, dataSrcOffset, memory, offsetBloc, aCopier);

            dataSrcOffset += aCopier;
            bytesRemaining -= aCopier;
        }

		// Mettre à jour l'inode.
		Inode inode = new Inode(memoryManager, inodeNum);
        long now = System.currentTimeMillis();

        inode.writeToMemory(1, data.length, now, now, blockPointers, 0, (short) 0644, 1);

		return true;
	}
	
	public byte[] readFile(int inodeNum) {

		Inode inode =
				new Inode(memoryManager, inodeNum);

		int fileSize =
				inode.getFileSize();

		if (fileSize == 0) {
			return new byte[0];
		}

		byte[] fileData =
				new byte[fileSize];

		byte[] memory =
				memoryManager.getFilesystemMemory();

		int[] blockPointers =
				inode.getDirectPointers();

		int bytesRemaining = fileSize;
        int destOffset = 0;
		// Parcourir les blocs utilisés.
		// Copier chaque fragment vers fileData.
		for (int i = 0; i < blockPointers.length && bytesRemaining > 0; i++) {
            int numBloc = blockPointers[i];
            if (numBloc == 0) break;

            int aCopier = Math.min(bytesRemaining, MemoryManager.BLOCK_SIZE);
            int offsetBloc = numBloc * MemoryManager.BLOCK_SIZE;

            System.arraycopy(memory, offsetBloc, fileData, destOffset, aCopier);

            destOffset += aCopier;
            bytesRemaining -= aCopier;
        }

		return fileData;
	}
}
