package GEDCOM;

import java.util.Date;

public class Marriage {
    private String id;
    private Date start;
    private Date end;
    
    public Marriage(String id, Date start, Date end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public String getId() {
        return this.id;
    }

    public Date getStart() {
        return this.start;
    }

    public Date getEnd() {
         return this.end;
    }

    // Checks if a date occurs during the marriage
    public Boolean during(Date date) {
        if (date.before(start)) {
            return false;
        }
        if (date.after(start) && this.end == null) {
            return true;
        }
        if ((date.after(start) || date.equals(start)) && ((date.before(end) || date.equals(end)))) {
            return true;
        }
        return false;
    }
}