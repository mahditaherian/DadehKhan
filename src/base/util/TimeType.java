package base.util;

/**
 * @author Mahdi
 */
public enum TimeType {
    SECOND(1),
    MINUTE(60 * TimeType.SECOND.seconds),
    HOUR(60 * TimeType.MINUTE.seconds),
    DAY(24 * TimeType.HOUR.seconds),
    WEEK(7 * TimeType.DAY.seconds),
//    MONTH(30 * TimeType.DAY.seconds),
//    YEAR(12 * TimeType.MONTH.seconds)
    ;

    int seconds;

    TimeType(int seconds) {
        this.seconds = seconds;
    }
}
