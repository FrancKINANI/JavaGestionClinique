package identification;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

public class PasswordUtils {
    /**
     * Produit exactement le même hash que SHA2(password, 256) en MySQL
     * @param password Mot de passe en clair
     * @return Hash hexadécimal (identique à MySQL)
     */
    public static String hashPasswordMySQL(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            
            // Conversion en format hexadécimal (comme MySQL)
            BigInteger number = new BigInteger(1, hashBytes);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            
            // Padding avec des zéros si nécessaire
            while (hexString.length() < 64) {
                hexString.insert(0, '0');
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur de hachage du mot de passe", e);
        }
    }
	/**
	 * Produit un hash du mot de passe
	 * @param password Mot de passe en clair
	 * @return Hash hexadécimal
	 */
	public static String hashPassword(String password) {
		return hashPasswordMySQL(password);
	}
}