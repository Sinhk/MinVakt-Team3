package main.java.datamodel;


import main.java.security.PBKDF2;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Created by OlavH on 09-Jan-17.
 */
public class User {

    private String email;
    private long tlf;

    private EmployeeType employeeType;

    private byte[] salt;
    private byte[] encryptedPassword;

    public User(String email, long tlf, String password) {

        Objects.requireNonNull(email);
        Objects.requireNonNull(password);
        if (checkPasswordRequirements(password)) throw new IllegalArgumentException("Invalid Password");

        PBKDF2 crypt = new PBKDF2();

        try {
            salt = crypt.generateSalt();
            encryptedPassword = crypt.getEncryptedPassword(password, salt);

        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(); // This will never happen
        }


        this.email = email;
        this.tlf = tlf;
    }

    public boolean authenticatePassword(String password) {

        PBKDF2 crypt = new PBKDF2();

        try {
            return crypt.authenticate(password, encryptedPassword, salt);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return false;
        }
    }

    public boolean changePassword(String oldPassword, String newPassword) {

        PBKDF2 crypt = new PBKDF2();

        if (!authenticatePassword(oldPassword)) return false;

        try {
            salt = crypt.generateSalt();
            encryptedPassword = crypt.getEncryptedPassword(newPassword, salt);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) { return false;}

        return true;
    }

    private static boolean checkPasswordRequirements(String attempt) {

        Pattern uppercase = Pattern.compile(".*\\p{Upper}+.*");
        Pattern lower = Pattern.compile(".*\\p{Lower}+.*");
        int length = attempt.trim().length();
        Pattern nonWord = Pattern.compile(".*\\W{2,}.*");

        return uppercase.matcher(attempt).matches() && lower.matcher(attempt).matches() && nonWord.matcher(attempt).matches() && length >= 8;
    }

    private static boolean checkEmailRequirements(String attempt) {

        return attempt.matches(".+@.+\\..+");

    }

    public String getEmail() {
        return email;
    }

    public long getTlf() {
        return tlf;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public static void main(String[] args) {

        PBKDF2 security = new PBKDF2();

        System.out.println(checkPasswordRequirements("olavhusby--"));
        System.out.println(checkPasswordRequirements("Olavhusby--"));

        System.out.println(checkEmailRequirements("ost"));
        System.out.println(checkEmailRequirements("ost@ost.com"));
    }

}
