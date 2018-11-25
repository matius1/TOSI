package Vernam;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import static java.lang.String.valueOf;
import static java.math.BigInteger.probablePrime;

public class Main {

    private static String msg = "Litwo! Ojczyzno moja! Ty jesteś jak zdrowie. " +
            "Nazywał się zatrudniał i bagnami skradał się o książki nowe dziwo w pół godziny tak i tuż za stołem. " +
            "Z wieku może Białopiotrowiczowi samemu odmówił! Bo nie wiedział, czy na drugim końcu dzieje tego " +
            "nigdy na wzgórek z jednej dwórórki. Wyczha! poszli, a brano z wieczerzą powynosić z chleba gałeczki " +
            "trzy stogi użątku, co o mniej zgorszenia. Ach, ja w Pańskim pisano zakonie i liczba żołnierza i dobra," +
            " które wylotem kontusz otarł prędko, jak on zmienił się biedak zając. Puszczano wtenczas za domem " +
            "urządzał wieczerzę. on zająca pochwycił. Asesor zaś Gotem. Dość, że.";

    private static int bitLength = 512;

    public static void main(String[] args) {
        System.out.println("Original message: " + msg);

        BigInteger[] cipher = generateCipher();

        String encrypted = calculate(msg.toCharArray(), cipher);
        String decrypted = calculate(encrypted.toCharArray(), cipher);

        System.out.println("Encrypted String: " + encrypted);
        System.out.println("Decrypted String: " + decrypted);
    }

    private static BigInteger[] generateCipher() {
        BigInteger n = generateN(bitLength, new SecureRandom());
        BigInteger s = generateS(n);

        BigInteger[] x = new BigInteger[msg.length()];
        BigInteger[] cipher = new BigInteger[msg.length()];

        x[0] = s.pow(2).mod(n);
        cipher[0] = x[0].mod(n);

        for (int i = 1; i < cipher.length; i++) {
            x[i] = x[i - 1].pow(2).mod(n);
            cipher[i] = x[i].mod(n);
        }

        return cipher;
    }

    public static BigInteger generateN(int bitLength, Random rand) {
        BigInteger p = probablePrime(bitLength / 2, rand);
        BigInteger g = probablePrime(bitLength / 2, rand);

        while (p.equals(g)) {
            g = probablePrime(bitLength / 2, rand);
        }

        return p.multiply(g);
    }

    private static BigInteger generateS(BigInteger n) {
        byte[] nBytes = n.toByteArray();
        int random = new SecureRandom().nextInt(nBytes.length - 1);

        return new BigInteger(valueOf(random)).abs();
    }

    private static String calculate(char[] msg, BigInteger[] cipher) {
        int length = msg.length;
        char[] result = new char[length];

        for (int i = 0; i < length; i++) {
            result[i] = (char) ((msg[i]) ^ cipher[i].intValue());
        }

        return valueOf(result);
    }


}
