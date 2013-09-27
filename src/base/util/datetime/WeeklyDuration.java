/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.util.datetime;

/**
 *
 * @author mrl
 */
public class WeeklyDuration {

    private int startSecondOfDay = 0;
    private int endSecondOfDay = 86399;
    private DayOfWeek dayOfWeek = DayOfWeek.SATURDAY;

    public WeeklyDuration() {
    }

    public boolean isInDuration(int second) {
        return second >= startSecondOfDay && second <= endSecondOfDay;
    }

    public int getStartSecondOfDay() {
        return startSecondOfDay;
    }

    public void setStartSecondOfDay(int startSecondOfDay) {
        this.startSecondOfDay = startSecondOfDay;
    }

    public int getEndSecondOfDay() {
        return endSecondOfDay;
    }

    public void setEndSecondOfDay(int endSecondOfDay) {
        this.endSecondOfDay = endSecondOfDay;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
