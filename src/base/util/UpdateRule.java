package base.util;

import java.util.*;

/**
 * @author Mahdi
 */
public class UpdateRule {
    public TimeType timeType = TimeType.SECOND;
    public int update = 10;
    public List<TimeRange> updateRanges;
    private GregorianCalendar cal;

    public UpdateRule() {
        updateRanges = new ArrayList<TimeRange>();
        cal = new GregorianCalendar();
    }

    public boolean isOnTime() {

        for (TimeRange range : updateRanges) {
            if (range.isInRange(cal.get(Calendar.HOUR_OF_DAY))) {
                return true;
            }
        }
        return false;
    }
}
