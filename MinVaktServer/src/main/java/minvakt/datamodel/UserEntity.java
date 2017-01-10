package minvakt.datamodel;


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

    private EmployeeType employeeType;
    private int positionPercentage;
    private int totalMinutes = 0; // max før overtid = 2400min = 40 timer


    public UserEntity() {
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
