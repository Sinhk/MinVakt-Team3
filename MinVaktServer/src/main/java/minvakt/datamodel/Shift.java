package minvakt.datamodel;

import minvakt.datamodel.enums.PredeterminedIntervals;
import minvakt.datamodel.enums.ShiftType;
import minvakt.util.TimeInterval;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Shift {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private int shiftId;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Transient
    private PredeterminedIntervals interval;

    private ShiftType shiftType = ShiftType.AVAILABLE;

    private String comment;

    public Shift() {
    }

    public Shift(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Objects.requireNonNull(startDateTime); Objects.requireNonNull(endDateTime);

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
    public Shift(LocalDateTime startDateTime, PredeterminedIntervals interval){
        Objects.requireNonNull(startDateTime); Objects.requireNonNull(interval);

        this.startDateTime = startDateTime;
        this.interval = interval;
    }

    public Shift(LocalDateTime date, PredeterminedIntervals intervals, ShiftType type) {
        this(date, intervals);
        this.shiftType = type;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
    public PredeterminedIntervals getPredeterminedInterval() {return interval;}
    public ShiftType getShiftType() { return shiftType; }
    public TimeInterval getTimeInterval(){return new TimeInterval(startDateTime, endDateTime);}

    public void setShiftType(ShiftType shiftType) { this.shiftType = shiftType; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String toString() {
        return startDateTime.toString()+": "+ endDateTime.toString()+" -> "+endDateTime.toString();
    }


    @OneToMany(mappedBy = "shift", targetEntity = UserShiftInfo.class)
    private Collection<User> userCollection = new ArrayList<>();

    public Collection<User> getUsers(){

        /*userInfoCollection.forEach(userShiftInfo -> {

            if (!userCollection.contains(userShiftInfo.getUser())) {
                userCollection.add(userShiftInfo.getUser());
            }
        });*/

        return userCollection;
    }

    /*@OneToMany
    @JoinTable(name = "Users_Shifts",
            joinColumns = { @JoinColumn(name = "shift_id"), @JoinColumn(name = "user_id")})
    private Collection<UserShiftInfo> userInfoCollection = new ArrayList<>();
*/
    public boolean changeShiftFromUserToUser(User from, User to){

        boolean remove = userCollection.remove(from);
        boolean add = userCollection.add(to);

        return remove && add;
    }

    /*public User getResponsibleForShift(){

        Collection<User> users = getUsers();

    }*/

}