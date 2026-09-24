public class Utils {

    public static int writeInt(byte[] memory, int offset, int value) {
        // Écrire les 4 octets de 'value' dans 'memory'
        // à partir de 'offset', en big-endian.
		byte b;
		for (int decalage = 0; decalage < 4; decalage++){
			b = (byte)(value >> (3-decalage)*8 & 0xFF);
			memory[offset + decalage] = b;
		}
		return 4;
    }

    public static int readInt(byte[] memory, int offset) {
        // Reconstituer le int sur 4 octets.
		int i = 0;
		for (int decalage = 0; decalage < 4; decalage++){
			int value = memory[offset + decalage] & 0xFF;
			i = i | (value << (3-decalage)*8);
		}
        return i;
    }

    public static int writeShort(byte[] memory, int offset, short value) {
        // Écrire les 2 octets de 'value'.
		byte b;
		for (int decalage = 0; decalage < 2; decalage++){
			b = (byte)(value >> (1-decalage)*8 & 0xFF);
			memory[offset + decalage] = b;
		}
        return 2;
    }

    public static short readShort(byte[] memory, int offset) {
        // Lire le short sur 2 octets.
		int i = 0;
		for (int decalage = 0; decalage < 2; decalage++){
			int value = memory[offset + decalage] & 0xFF;
			i = i | (value << (1-decalage)*8);
		}
        return (short)i;
    }
	
	public static int writeLong(byte[] memory, int offset, long value) {
		// Écrire les 8 octets du long en big-endian.
		byte b;
		for (int decalage = 0; decalage < 8; decalage++){
			b = (byte)(value >> (7-decalage)*8 & 0xFF);
			memory[offset + decalage] = b;
		}
		return 8;
	}

	public static long readLong(byte[] memory, int offset) {
		// Reconstituer le long.
		long i = 0L;
		for (int decalage = 0; decalage < 8; decalage++){
			long value = memory[offset + decalage] & 0xFF;
			i = i | (value << (7-decalage)*8);
		}
		return i;
	}

	public static int writeString(byte[] memory,int offset,
									String str,int maxLength) {
		// 1. Convertir la chaîne en octets.
		byte[] bytes = str.getBytes();
		// 2. Copier les octets sans dépasser maxLength.
		// 3. Nettoyer le reste de la zone avec des zéros.
		for (int decalage = 0; decalage < maxLength; decalage++) {
			if (decalage < bytes.length) {
				memory[offset + decalage] = bytes[decalage]; // Étape 2 : copie
			} else {
				memory[offset + decalage] = 0;               // Étape 3 : nettoyage
			}
		}
		return maxLength;
	}

	public static String readString(byte[] memory,int offset,
										int maxLength) {
		int decalage = 0;
		// Lire jusqu'au premier octet nul
		// ou jusqu'à maxLength.
		while (decalage < maxLength && memory[offset + decalage] != 0) {
			decalage++;
		}
		return new String(memory, offset, decalage);
	}

}

