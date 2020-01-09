
import java.math.BigInteger;                    // Отче наш, сущий на небесах! Да святится имя Твое;
import java.util.Random;                        // да приидет Царствие Твое; да будет воля Твоя и на земле, как на небе;
import java.io.*;                               // хлеб наш насущный дай нам на сей день;
                                                // и прости нам долги наши, как и мы прощаем должникам нашим;
public class RSA {                              // и не введи нас в искушение, но избавь нас от лукавого.
                                                // Ибо Твое есть Царство и сила и слава во веки.
    private BigInteger p, q, N, phi, e, d;
    private Random r;
    private int bitlength = 1024;

    private int blocksize = 256;
    //blocksize in byte

    public RSA() {

        r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);
        N = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitlength/2, r);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0 ) {

            e.add(BigInteger.ONE);

        }
        d = e.modInverse(phi);
    }

    public RSA(BigInteger e, BigInteger d, BigInteger N) {
        this.e = e;
        this.d = d;
        this.N = N;
    }

    public static void main (String[] args) throws IOException {
        RSA rsa = new RSA();
        DataInputStream in=new DataInputStream(System.in);
        String teststring ;
        System.out.println("Enter the plain text:");
        teststring=in.readLine();
        System.out.println("Encrypting String: " + teststring);
        System.out.println("String in Bytes: " + bytesToString(teststring.getBytes()));

        // encrypt
        byte[] encrypted = rsa.encrypt(teststring.getBytes());
        System.out.println("Encrypted String in Bytes: " + bytesToString(encrypted));

        // decrypt
        byte[] decrypted = rsa.decrypt(encrypted);
        System.out.println("Decrypted String in Bytes: " +  bytesToString(decrypted));
        System.out.println("Decrypted String: " + new String(decrypted));

    }

    private static String bytesToString(byte[] encrypted) {
        String test = "";
        for (byte b : encrypted) {
            test += Byte.toString(b);
        }
        return test;
    }

    //Encrypt message

    public byte[] encrypt(byte[] message) {
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }

    // Decrypt message

    public byte[] decrypt(byte[] message) {
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }

}