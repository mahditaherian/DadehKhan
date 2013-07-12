package base.util;

/**
 * @author Mahdi
 */
public class TimeRange {
    public int start;
    public int end;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public boolean isInRange(int hourOfDay) {
        return hourOfDay >= start && hourOfDay < end;
    }
}
