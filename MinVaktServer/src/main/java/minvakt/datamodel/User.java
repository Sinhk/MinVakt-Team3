package minvakt.datamodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import minvakt.datamodel.enums.EmployeeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by OlavH on 09-Jan-17.
 */
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue
    private int userId;

    @Column(nullable = false)
    private String firstName = "Ola";

    @Column(nullable = false)
    private String lastName = "Nordmann";

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private long phone;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "user_type_id", nullable = false)
    private EmployeeType employeeType = EmployeeType.ASSISTENT;

    @Column(nullable = false)
    private int positionPercentage;

    User() {
    }

    public User(String firstName, String lastName, String email, long phone, String password, EmployeeType employeeType, int positionPercentage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public void setPositionPercentage(int positionPercentage) {
        this.positionPercentage = positionPercentage;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", password='" + password + '\'' +
                ", employeeType=" + employeeType +
                ", positionPercentage=" + positionPercentage +
                '}';
    }

    private Collection<Shift> shiftCollection = new ArrayList<>();
    @ManyToMany
    @JoinTable(name="Users_Shifts",
            joinColumns=
            @JoinColumn(name="user_id"/*, referencedColumnName="ID"*/),
            inverseJoinColumns=
            @JoinColumn(name="shift_id"/*, referencedColumnName="ID"*/)
    )
    public Collection<Shift> getShiftsForUser(){

        return shiftCollection;

    }
}
