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
public class UserEntity implements Serializable {

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
    private String password;

    @Column(nullable = false)
    private EmployeeType employeeType;

    @Column(nullable = false)
    private int positionPercentage;

    private int totalMinutes = 0; // max før overtid = 2400min = 40 timer

    UserEntity() {
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

    public UserEntity(String firstName, String lastName, String email, long phone, String password, EmployeeType employeeType, int positionPercentage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.employeeType = employeeType;
        this.positionPercentage = positionPercentage;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public long getPhone() {
        return phone;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public String toString() {
        return email.split("@")[0];
    }

}
