package minvakt.controller.data;

/**
 * Created by OlavH on 11-Jan-17.
 */
public class ChangePasswordInfo {

    private String oldPassAttempt;
    private String newPassAttempt;

    public ChangePasswordInfo() {
    }

    public String getOldPassAttempt() {
        return oldPassAttempt;
    }

    public void setOldPassAttempt(String oldPassAttempt) {
        this.oldPassAttempt = oldPassAttempt;
    }

    public String getNewPassAttempt() {
        return newPassAttempt;
    }

    public void setNewPassAttempt(String newPassAttempt) {
        this.newPassAttempt = newPassAttempt;
    }
}
