package minvakt.controller.data;

import minvakt.datamodel.User;

/**
 * Created by OlavH on 11-Jan-17.
 */
public class ChangePasswordInfo {

    private User user;
    private String oldPassAttempt;
    private String newPassAttempt;

    public ChangePasswordInfo() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
