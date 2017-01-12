package minvakt.util;

import java.util.regex.Pattern;

/**
 * Created by OlavH on 11-Jan-17.
 */
public class InfoValidator {

    /**
     * @param attempt The password to check
     * @return if the password is a valid password or not.
     */
    public static boolean checkPasswordRequirements(String attempt) {

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
    public static boolean checkEmailRequirements(String attempt) {

        return attempt.matches(".+@.+\\..+");

    }

}