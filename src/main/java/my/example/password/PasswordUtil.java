package my.example.password;

import java.security.SecureRandom;
import java.util.Random;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.apache.commons.net.util.Base64;


/**
 *
 * @author hanguyen
 */
public class PasswordUtil {

    // The higher the number of iterations the more 
    // expensive computing the hash is for us and
    // also for an attacker.
    private static final int iterations = 20 * 1000;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 128;

    private static final Random RANDOM = new SecureRandom();
    /**
     * Length of password. @see #generateRandomPassword()
     */
    public static final int PASSWORD_LENGTH = 10;

    public static void main(String args[]) {
        for (int i = 0; i < 10; i++) {
            System.out.println(" i = " + i + " ; " + PasswordUtil.generateRandomPassword());
        }
    }

    /**
     * Generate a random String suitable for use as a temporary password.
     *
     * @return String suitable for use as a temporary password
     * @since 2.4
     */
    public static String generateRandomPassword() {
        // Pick from some letters that won't be easily mistaken for each
        // other. So, for example, omit o O and 0, 1 l and L.
        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

        String pw = "";
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = (int) (RANDOM.nextDouble() * letters.length());
            pw += letters.substring(index, index + 1);
        }
        return pw;
    }

    /**
     * Computes a salted PBKDF2 hash of given plaintext password suitable for
     * storing in a database. Empty passwords are not supported.
     *
     * @param subject
     * @return
     * @throws java.lang.Exception
     */
    public static String getSaltedHash(String subject) throws Exception {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
        // store the salt with the password
        return Base64.encodeBase64String(salt) + "$" + hash(subject, salt);
    }

    /**
     * Checks whether given plaintext password corresponds to a stored salted
     * hash of the password.
     *
     * @param subject
     * @param stored
     * @return
     * @throws java.lang.Exception
     */
    public static boolean check(String subject, String stored) throws Exception {
        String[] saltAndPass = stored.split("\\$");
        if (saltAndPass.length != 2) {
            throw new IllegalStateException(
                    "The stored subject have the form 'salt$hash'");
        }
        String hashOfInput = hash(subject, Base64.decodeBase64(saltAndPass[0]));
        return hashOfInput.equals(saltAndPass[1]);
    }

    // using PBKDF2 from Sun, an alternative is https://github.com/wg/scrypt
    // cf. http://www.unlimitednovelty.com/2012/03/dont-use-bcrypt.html
    private static String hash(String subject, byte[] salt) throws Exception {
        if (subject == null || subject.length() == 0) {
            throw new IllegalArgumentException("Empty subject are not supported.");
        }
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
                subject.toCharArray(), salt, iterations, desiredKeyLen)
        );
        return Base64.encodeBase64String(key.getEncoded());
    }

}
