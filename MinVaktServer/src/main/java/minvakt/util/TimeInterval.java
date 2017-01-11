package minvakt.util;

import minvakt.datamodel.Shift;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;

public class TimeInterval implements Serializable{

    private LocalDateTime start, end;
    private DayOfWeek[] days = new DayOfWeek[0];

    // Special case for PredeterminedIntervals.java enum
    private LocalTime timeStart, timeEnd;

    public TimeInterval(LocalDateTime start, LocalDateTime end){

        this.start = start;
        this.end = end;
    }

    // Special case for PredeterminedIntervals.java enum
    public TimeInterval(LocalTime start, LocalTime end){

        timeStart = start;
        timeEnd = end;
    }

    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }
    public void setDays(DayOfWeek ... days){ this.days = days; }
    public long getMinutes(){ return ChronoUnit.MINUTES.between(start, end); }
    public Optional<DayOfWeek[]> getDays(){

        if(days.length == 0) return Optional.empty();
        else return Optional.of(days);
    }
    public void addDay(DayOfWeek day){
        DayOfWeek[] newDays = Arrays.copyOf(days, days.length+1);
        newDays[newDays.length-1] = day;
        days = newDays;
    }

    public boolean overlaps(TimeInterval interval){

        LocalDateTime iStart = interval.getStart();
        LocalDateTime iEnd = interval.getEnd();

        return !start.isAfter(iEnd) && !iStart.isAfter(end); // For not overlapWith at same min: return start.isBefore(iEnd) && iStart.isBefore(end);

        /*if( start.equals(iStart) || end.equals(iEnd) ) return true;
        if( ((start.isAfter(iStart)) && (end.isBefore(iEnd))) || ((start.isBefore(iStart)) && end.isAfter(iEnd)) ) return true;
        if( (start.isBefore(iStart)) && (end.isAfter(iEnd)) ) return true;
        if( (start.isBefore(iStart) && (end.isBefore(iEnd))) ) return true;
        return false;*/
    }
    public boolean daysMatch(TimeInterval interval){

        if( !this.getDays().isPresent()  || !interval.getDays().isPresent() ) return false;

        for(DayOfWeek day : days)
            for(DayOfWeek iDay : interval.getDays().get()) // Checked above...
                if (day.equals(iDay)) return true;

        return false;
    }
    public Optional<TimeInterval> overlapWith(TimeInterval interval){

        if(!this.overlaps(interval)) return Optional.empty();

        LocalDateTime iStart = interval.getStart();
        LocalDateTime iEnd = interval.getEnd();

        LocalDateTime s;

        if(start.equals(iStart)){
            s = start;
        }
        else {
            s = start.isBefore(iStart) ? iStart : start;
        }

        LocalDateTime e;

        if(end.equals(iEnd)){
            e = end;
        }
        else {
            e = end.isBefore(iEnd) ? end : iEnd;

        }
        return Optional.of(new TimeInterval(s, e));

    }
    public boolean hasDays(){
        return false;
    }
    public static TimeInterval of(Shift event){
        return new TimeInterval(event.getEndDateTime(), event.getEndDateTime());
    }

    public String toString() {
        return start+" -> "+end;
    }

    public static void main(String[] args) {

        TimeInterval i0 = new TimeInterval(LocalDateTime.of(2017, 1, 10, 10,0), LocalDateTime.of(2017, 1, 10, 14, 0));
        TimeInterval i1 = new TimeInterval(LocalDateTime.of(2017, 1, 10, 6, 0), LocalDateTime.of(2017, 1, 10, 18, 0));

        System.out.println(i0.overlaps(i1));

        i0.overlapWith(i1).ifPresent( (timeInterval)-> {System.out.println(timeInterval+"");});


        i0.getDays().ifPresent(dayOfWeeks->{System.out.println(dayOfWeeks);});
        i0.addDay(DayOfWeek.MONDAY);
        i0.getDays().ifPresent(consumer->{System.out.println(Arrays.toString(consumer));});

    }
}
