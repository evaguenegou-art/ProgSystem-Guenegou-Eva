public class Utils {

    public static int writeInt(byte[] memory, int offset, int value) {
        // TODO: Écrire les 4 octets de 'value' dans 'memory'
        // à partir de 'offset', en big-endian.
		byte b;
		for (int decalage = 0; decalage < 4; decalage++){
			b = (byte)(value >> (3-decalage)*8 & 0xFF);
			memory[offset + decalage] = b;
		}
		return 4;
    }

    public static int readInt(byte[] memory, int offset) {
        // TODO: Reconstituer le int sur 4 octets.
		int i = 0;
		for (int decalage = 0; decalage < 4; decalage++){
			int value = memory[offset + decalage] & 0xFF;
			i = i | (value << (3-decalage)*8);
		}
        return i;
    }

    public static int writeShort(byte[] memory, int offset, short value) {
        // TODO: Écrire les 2 octets de 'value'.
		byte b;
		for (int decalage = 0; decalage < 2; decalage++){
			b = (byte)(value >> (1-decalage)*8 & 0xFF);
			memory[offset + decalage] = b;
		}
        return 2;
    }

    public static short readShort(byte[] memory, int offset) {
        // TODO: Lire le short sur 2 octets.
		int i = 0;
		for (int decalage = 0; decalage < 2; decalage++){
			int value = memory[offset + decalage] & 0xFF;
			i = i | (value << (1-decalage)*8);
		}
        return (short)i;
    }
}