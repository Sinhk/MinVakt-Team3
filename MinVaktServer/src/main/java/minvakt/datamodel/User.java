package minvakt.datamodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import minvakt.datamodel.enums.EmployeeType;
import minvakt.util.TimeUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by OlavH on 09-Jan-17.
 */
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
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
    private String password; // Spring Security will hash and salt

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


    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public void setPositionPercentage(int positionPercentage) {
        this.positionPercentage = positionPercentage;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @OneToMany(mappedBy = "user", targetEntity = UserShiftInfo.class)
    private Collection<Shift> shiftCollection = new ArrayList<>();

    public Collection<Shift> getShiftsForUser(){

        /*userInfoCollection.forEach(userShiftInfo -> {
            if (!shiftCollection.contains(userShiftInfo.getShift())){
                shiftCollection.add(userShiftInfo.getShift());

            }
        });*/

        return shiftCollection;

    }
    /*@OneToMany
    @JoinTable(name = "Users_Shifts",
            joinColumns = { @JoinColumn(name = "user_id"), @JoinColumn(name = "shift_id")})
    private Collection<UserShiftInfo> userInfoCollection = new ArrayList<>();
*/

    public boolean addShiftToUser(Shift shift){

        return shiftCollection.add(shift);

    }
    public boolean removeShift(Shift shift){

        return shiftCollection.remove(shift);
    }
    public boolean authenticatePassword(String attempt){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(attempt, password);

    }

    public boolean changePassword(String oldPass, String newPass){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (authenticatePassword(oldPass)){

            setPassword(encoder.encode(newPass));
            return true;
        }

        return false;
    }

    public Collection<Shift> getShiftsInRange(LocalDate start, LocalDate end){

        Collection<Shift> shiftsForUser = getShiftsForUser();

        List<Shift> collect = shiftsForUser
                .stream()
                .filter(shift -> TimeUtil.isInDateInterval(start, end, shift.getStartDateTime().toLocalDate()))
                .collect(Collectors.toList());

        return collect;

    }





}
