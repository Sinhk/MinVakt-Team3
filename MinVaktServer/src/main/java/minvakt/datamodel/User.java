package minvakt.datamodel;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by OlavH on 09-Jan-17.
 */
@Entity
@Table(name = "employee")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", updatable = false, nullable = false)
    private int userId;

    @Column(nullable = false)
    private String firstName = "";

    @Column(nullable = false)
    private String lastName = "";

    @Column(nullable = false)
    private int phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int positionPercentage;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private EmployeeCategory category = new EmployeeCategory(){{setCategoryId(1);}};

    @OneToMany(mappedBy = "user")
    private List<ShiftAssignment> shiftAssignments;

    public User() {
    }

    public User(String firstName, String lastName, String email, int phone, int positionPercentage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.positionPercentage = positionPercentage;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPositionPercentage() {
        return positionPercentage;
    }

    public void setPositionPercentage(int positionPercentage) {
        this.positionPercentage = positionPercentage;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", positionPercentage=" + positionPercentage +
                '}';
    }

    public EmployeeCategory getCategory() {
        return category;
    }

    public void setCategory(EmployeeCategory category) {
        this.category = category;
    }

    public List<ShiftAssignment> getShiftAssignments() {
        return shiftAssignments;
    }

    public void setShiftAssignments(List<ShiftAssignment> shiftAssignments) {
        this.shiftAssignments = shiftAssignments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (phone != user.phone) return false;
        if (positionPercentage != user.positionPercentage) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (int) (phone ^ (phone >>> 32));
        result = 31 * result + email.hashCode();
        result = 31 * result + positionPercentage;
        return result;
    }


    /*public Collection<Shift> shiftsInRange(LocalDate start, LocalDate end) {

        Collection<Shift> shiftsForUser = getShiftAssignments();

        List<Shift> collect = shiftsForUser
                .stream()
                .filter(shift -> TimeUtil.isInDateInterval(start, end, shift.getStartDateTime().toLocalDate()))
                .collect(Collectors.toList());

        return collect;

    }*/
}
