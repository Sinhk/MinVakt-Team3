package minvakt.controller.data;

import minvakt.datamodel.Shift;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Created by klk94 on 12.01.2017.
 */
public class DateWrapper {

        private int startD;
        private int startY;
        private int startH;
        private int startMonth;
        private int startMin;

        private int endD;
        private int endY;
        private int endH;
        private int endMonth;
        private int endMin;



        public DateWrapper() {
        }

        public Shift toShift() {
            String str = startY+"-"+startMonth+"-"+startD+" "+startH+":"+startMin;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDateTime = LocalDateTime.parse(str, formatter);

            String str1 = endY+"-"+endMonth+"-"+endD+" "+endH+":"+endMin;
            LocalDateTime endDateTime = LocalDateTime.parse(str, formatter);

            Shift shift = new Shift(startDateTime,endDateTime);
            return shift;

        }

        public int getstartD() {
            return startD;
        }
        public int getstartY() {
        return startY;
    }
        public int getstartH() {
            return startH;
        }
        public int getStartMonth() {
            return startMonth;
        }
        public int getStartMin() {
        return startMin;
    }

        public int getEndD() {
        return endD;
    }
        public int getEndY() {
        return endY;
    }
        public int getEndH() {
        return endH;
    }
        public int getEndMonth() {
        return endMonth;
    }
        public int getEndMin() {
        return endMin;
    }

        public void setStartD(int startD) { this.startD =startD;}
        public void setStartY(int startY) { this.startY =startY;}
        public void setStartMonth(int startMonth) { this.startMonth =startMonth;}
        public void setStartMin(int startMin) { this.startMin =startMin;}
        public void setStartH(int startH) { this.startH =startH;}

        public void endD(int endD) { this.endD =endD;}
        public void endY(int endY) { this.endY =endY;}
        public void endMonth(int endMonth) { this.endMonth =endMonth;}
        public void endMin(int endMin) { this.endMin =endMin;}
        public void endH(int endH) { this.endH =endH;}
}
