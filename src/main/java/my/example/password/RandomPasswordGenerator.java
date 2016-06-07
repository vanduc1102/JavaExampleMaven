package my.example.password;

import java.security.SecureRandom;
import java.util.Random;

public class RandomPasswordGenerator {

    private static final String ALPHA_CAPS = "ABCDEFGHIJKMNPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijkmnpqrstuvwxyz";
    private static final String NUM = "23456789";
    private static final String SPL_CHARS = "!@#$%^&*=+-";

    public static char[] generatePswd(int minLen, int maxLen, int noOfCAPSAlpha,
            int noOfDigits, int noOfSplChars) {
        if (minLen > maxLen) {
            throw new IllegalArgumentException("Min. Length > Max. Length!");
        }
        if ((noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen) {
            throw new IllegalArgumentException("Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
        }
        Random rnd = new SecureRandom();
        int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
        char[] pswd = new char[len];
        int index = 0;
        for (int i = 0; i < noOfCAPSAlpha; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
        }
        for (int i = 0; i < noOfDigits; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
        }
        for (int i = 0; i < noOfSplChars; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length()));
        }
        for (int i = 0; i < len; i++) {
            if (pswd[i] == 0) {
                pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
            }
        }
        return pswd;
    }

    private static int getNextIndex(Random rnd, int len, char[] pswd) {
        int index = rnd.nextInt(len);
        while (pswd[index = rnd.nextInt(len)] != 0);
        return index;
    }

    public static void main(String[] args) {
        int noOfCAPSAlpha = 3;
        int noOfDigits = 2;
        int noOfSplChars = 2;
        int minLen = 8;
        int maxLen = 12;

        for (int i = 0; i < 10; i++) {
            char[] pswd = RandomPasswordGenerator.generatePswd(minLen, maxLen,
                    noOfCAPSAlpha, noOfDigits, noOfSplChars);
            System.out.println("Len = " + pswd.length + ", " + new String(pswd));
        }
    }
}
