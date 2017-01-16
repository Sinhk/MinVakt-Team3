package minvakt.controller.data;

import minvakt.datamodel.Shift;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Created by klk94 on 12.01.2017.
 */
public class DateWrapper {

        private String startD;
        private String startY;
        private String startH;
        private String startMonth;
        private String startMin;

        private String endD;
        private String endY;
        private String endH;
        private String endMonth;
        private String endMin;



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

        public String getstartD() {
            return startD;
        }
        public String getstartY() {
        return startY;
    }
        public String getstartH() {
            return startH;
        }
        public String getStartMonth() {
            return startMonth;
        }
        public String getStartMin() {
        return startMin;
    }

        public String getEndD() {
        return endD;
    }
        public String getEndY() {
        return endY;
    }
        public String getEndH() {
        return endH;
    }
        public String getEndMonth() {
        return endMonth;
    }
        public String getEndMin() {
        return endMin;
    }

        public void setStartD(String startD) { this.startD =startD;}
        public void setStartY(String startY) { this.startY =startY;}
        public void setStartMonth(String startMonth) { this.startMonth =startMonth;}
        public void setStartMin(String startMin) { this.startMin =startMin;}
        public void setStartH(String startH) { this.startH =startH;}

        public void endD(String endD) { this.endD =endD;}
        public void endY(String endY) { this.endY =endY;}
        public void endMonth(String endMonth) { this.endMonth =endMonth;}
        public void endMin(String endMin) { this.endMin =endMin;}
        public void endH(String endH) { this.endH =endH;}
}
