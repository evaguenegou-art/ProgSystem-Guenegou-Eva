import java.io.*;

public class MemoryManager {

    public static final int BLOCK_SIZE = 512;
    public static final int TOTAL_MEMORY = 1024 * 1024;
    public static final int NUM_BLOCKS =
            TOTAL_MEMORY / BLOCK_SIZE;

    public static final int SUPERBLOCK_OFFSET = 0;
    public static final int BITMAP_OFFSET = BLOCK_SIZE;
    public static final int INODE_TABLE_OFFSET =
            2 * BLOCK_SIZE;
    public static final int DATA_OFFSET =
            129 * BLOCK_SIZE;

    public static final int INODE_SIZE = 128;

    public static final int INODE_TABLE_SIZE =
            DATA_OFFSET - INODE_TABLE_OFFSET;

    public static final int MAX_INODES =
            INODE_TABLE_SIZE / INODE_SIZE;

    private byte[] memory;

    public MemoryManager() {
        this.memory = new byte[TOTAL_MEMORY];
        initializeFilesystem();
    }

    private void initializeFilesystem() {
        writeSuperblock();

        // Réserver les blocs système 0 à 128.
		for (int block = 0; block < 128; block++) {
            setBlockUsed(block, true);
        }
    }

    private void writeSuperblock() {
        // Utiliser Utils pour écrire les métadonnées.

        Utils.writeString(
                memory,
                SUPERBLOCK_OFFSET,
                "MYFS1.0",
                16);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 16,
                BLOCK_SIZE);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 20,
                TOTAL_MEMORY);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 24,
                NUM_BLOCKS);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 28,
                MAX_INODES);
    }

    public byte[] getFilesystemMemory() {
        return memory;
    }
	
	public boolean setBlockUsed(
			int blockNumber,
			boolean used) {

		if (blockNumber < 0 ||
			blockNumber >= NUM_BLOCKS) {
			return false;
		}

		int byteIndex = blockNumber / 8;
		int bitPosition = blockNumber % 8;
		int offset = BITMAP_OFFSET + byteIndex;

		if (used) {
			// Positionner le bit à 1.
			memory[offset] = (byte)(memory[offset] | (1 << bitPosition));
		} else {
			// Positionner le bit à 0.
			memory[offset] = (byte) (memory[offset] & ~(1 << bitPosition));
			
		}

		return true;
	}

	public int isBlockUsed(int blockNumber) {

		if (blockNumber < 0 ||
			blockNumber >= NUM_BLOCKS) {
			return -1;
		}

		// Calculer byteIndex.
		int byteIndex = blockNumber / 8;
		// Calculer bitPosition.
		int bitPosition = blockNumber % 8;
        int offset = BITMAP_OFFSET + byteIndex;
		// Lire le bit.
		int bit = (memory[offset] >> bitPosition) & 1;

        return bit;
	}

	public int allocateBlock() {

		// Parcourir les blocs de données :
		// 129 .. NUM_BLOCKS - 1.
		for (int block = 129; block <; ) {
			
		
		// Retourner le premier bloc libre.
		// Le marquer immédiatement comme utilisé.

		return -1;
	}

}