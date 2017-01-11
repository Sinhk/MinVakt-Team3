package minvakt.datamodel;


import minvakt.datamodel.enums.EmployeeType;
import minvakt.security.PBKDF2;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Created by OlavH on 09-Jan-17.
 */
public class User implements Serializable{

    private String email;
    private long tlf;

    private EmployeeType employeeType;
    private int positionPercentage;
    private int totalMinutes = 0; // max før overtid = 2400min = 40 timer

    private byte[] salt;
    private byte[] encryptedPassword;

    private PBKDF2 crypt = new PBKDF2();

    public User(){

    }
    @Deprecated
    public User(String email, long tlf, String password, int positionPercentage) {
        Objects.requireNonNull(email); Objects.requireNonNull(password);

        if (!checkPasswordRequirements(password)) throw new IllegalArgumentException("Invalid Password");

        try {
            salt = crypt.generateSalt();
            encryptedPassword = crypt.getEncryptedPassword(password, salt);

        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(); // This will never happen
        }


        this.email = email;
        this.tlf = tlf;
        this.positionPercentage = positionPercentage;
    }
    public User(String email, long tlf, String password, int positionPercentage, EmployeeType type){
        this(email,tlf,password,positionPercentage);
        this.employeeType = type;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    /**
     * @param totalMinutes Minutes to add to the total, can be both + and -
     */
    public void changeTotalMinutes(int totalMinutes) {
        this.totalMinutes += totalMinutes;
    }


    /**
     * @param password The password to attempt
     * @return Whether the password is correct with the stored password or not
     */
    public boolean authenticatePassword(String password) {

        try {
            return crypt.authenticate(password, encryptedPassword, salt);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return false;
        }
    }

    /**
     * @param oldPassword The password to be changed
     * @param newPassword The password to replace the old password
     * @return
     */
    public boolean changePassword(String oldPassword, String newPassword) {

        if (!checkPasswordRequirements(newPassword)) throw new IllegalArgumentException("Invalid password");

        if (!authenticatePassword(oldPassword)) return false;

        try {
            salt = crypt.generateSalt();
            encryptedPassword = crypt.getEncryptedPassword(newPassword, salt);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) { return false;}

        return true;
    }

    /**
     * @param attempt The password to check
     * @return if the password is a valid password or not.
     */
    private static boolean checkPasswordRequirements(String attempt) {

        Pattern uppercase = Pattern.compile(".*\\p{Upper}+.*");
        Pattern lower = Pattern.compile(".*\\p{Lower}+.*");
        int length = attempt.trim().length();
        Pattern nonWord = Pattern.compile(".*\\W{2,}.*");

        return uppercase.matcher(attempt).matches() && lower.matcher(attempt).matches() && nonWord.matcher(attempt).matches() && length >= 8;
    }

    /**
     * @param attempt The email adress to check
     * @return If the adress has a valid format or not
     */
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

    public String toString() {
        return email.split("@")[0];
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTlf(long tlf) {
        this.tlf = tlf;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public void setPositionPercentage(int positionPercentage) {
        this.positionPercentage = positionPercentage;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public void setPassword(String password){

        try {
            salt = crypt.generateSalt();
            encryptedPassword = crypt.getEncryptedPassword(password, salt);
        }catch (Exception e){}

    }

    public static void main(String[] args) {

        PBKDF2 security = new PBKDF2();

        System.out.println(checkPasswordRequirements("olavhusby--"));
        System.out.println(checkPasswordRequirements("Olavhusby--"));

        System.out.println(checkEmailRequirements("ost"));
        System.out.println(checkEmailRequirements("ost@ost.com"));
    }

}
