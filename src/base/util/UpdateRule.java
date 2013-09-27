package base.util;

import base.applicator.Property;
import base.applicator.object.StandardEntity;
import base.grabber.PropertyType;
import base.util.datetime.DayOfWeek;
import base.util.datetime.WeeklyDuration;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Duration;

/**
 * @author Mahdi
 */
public class UpdateRule extends StandardEntity {

    Map<WeeklyDuration, Integer> durations;
//    List<Pair<WeeklyDuration, Integer>> durations;
    boolean allowUpdate;
    private int lastUpdateSecond = -1;

    {
        addParameter(new Property("durations", durations, PropertyType.DURATIONS));
    }

    public UpdateRule() {
        durations = new HashMap<>();
        allowUpdate = true;
//        dateTime.
    }

    public void setDurations(Map<WeeklyDuration, Integer> dur) {
        durations = dur;
    }

    public void addDuration(WeeklyDuration duration, int interval) {
        durations.put(duration, interval);
    }

    public boolean allowUpdate() {
        DateTime now = new DateTime();
        final int year = now.get(DateTimeFieldType.year());
        final int month = now.get(DateTimeFieldType.monthOfYear());
        final int day = now.get(DateTimeFieldType.dayOfMonth());
        DateTime midnight = new DateTime(year, month, day, 0, 0, 0, 0);
        Duration duration = new Duration(midnight.withDayOfMonth(day), now);

        int seconds = duration.toStandardSeconds().getSeconds();
        DayOfWeek dayOfWeek = DayOfWeek.get(Calendar.DAY_OF_WEEK);
        final WeeklyDuration weeklyDuration = getDuration(dayOfWeek, seconds);
        if (allowUpdate && weeklyDuration != null) {
            int interval = durations.get(weeklyDuration);
            if (lastUpdateSecond + interval < weeklyDuration.getEndSecondOfDay()) {
                return true;
            }
        }
        return allowUpdate;
    }

    public WeeklyDuration getDuration(DayOfWeek dayOfWeek, int secondOfDay) {
        for (WeeklyDuration weeklyDuration : durations.keySet()) {
            if ((weeklyDuration.getDayOfWeek().equals(DayOfWeek.EVERY_DAY) || weeklyDuration.getDayOfWeek().equals(dayOfWeek))
                    && weeklyDuration.isInDuration(secondOfDay)) {

                return weeklyDuration;
            }
        }
        return null;
    }

    public int getLastUpdateSecond() {
        return lastUpdateSecond;
    }

    public void setLastUpdateSecond(int lastUpdateSecond) {
        this.lastUpdateSecond = lastUpdateSecond;
    }
}
