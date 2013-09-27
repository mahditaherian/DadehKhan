/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.util.datetime;

import java.util.Calendar;
import java.util.NoSuchElementException;

/**
 *
 * @author mrl
 */
public enum DayOfWeek {
    EVERY_DAY(0),
    SATURDAY(Calendar.SATURDAY),
    SUNDAY(Calendar.SUNDAY),
    MONDAY(Calendar.MONDAY),
    TUESDAY(Calendar.TUESDAY),
    WEDNESDAY(Calendar.WEDNESDAY),
    THURSDAY(Calendar.THURSDAY),
    FRIDAY(Calendar.FRIDAY);
    
    private int value;

    private DayOfWeek(int value) {
        this.value = value;
    }
    
    public static DayOfWeek get(int val){
        for(DayOfWeek dayOfWeek: DayOfWeek.values()){
            if(val == dayOfWeek.value){
                return dayOfWeek;
            }
        }
        throw new NoSuchElementException(val + " is not supported day of week value!");
    }
    
    
}
