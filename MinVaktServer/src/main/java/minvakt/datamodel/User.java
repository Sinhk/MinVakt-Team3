package minvakt.datamodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import minvakt.datamodel.enums.EmployeeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by OlavH on 09-Jan-17.
 */
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue
    private int userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private long phone;

    @Column(nullable = false)

    @JsonIgnore
    private byte[] salt;

    @JsonIgnore
    private byte[] encryptedPassword;

    @Column(nullable = false)
    private EmployeeType employeeType;

    @Column(nullable = false)
    private int positionPercentage;

    User() {
    }

    public User(String firstName, String lastName, String email, long phone, byte[] salt, byte[] encryptedPassword, EmployeeType employeeType, int positionPercentage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.salt = salt;
        this.encryptedPassword = encryptedPassword;
        this.employeeType = employeeType;
        this.positionPercentage = positionPercentage;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getPositionPercentage() {
        return positionPercentage;
    }

    public long getPhone() {
        return phone;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }


    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public void setPositionPercentage(int positionPercentage) {
        this.positionPercentage = positionPercentage;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", salt='" + salt+ '\'' +
                ", encryptedPassword='" + encryptedPassword+ '\'' +
                ", employeeType=" + employeeType +
                ", positionPercentage=" + positionPercentage +
                '}';
    }
}
