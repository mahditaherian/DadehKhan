package base.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mahdi
 */
public class Util {

    public static char[] NUMBERS = new char[10];

    static {
        NUMBERS[0] = '0';
        NUMBERS[1] = '1';
        NUMBERS[2] = '2';
        NUMBERS[3] = '3';
        NUMBERS[4] = '4';
        NUMBERS[5] = '5';
        NUMBERS[6] = '6';
        NUMBERS[7] = '7';
        NUMBERS[8] = '8';
        NUMBERS[9] = '9';
    }

    public static Integer convertToInt(String str) {
        String numberStr = str.replaceAll("\\D+", "");
        return Integer.parseInt(numberStr);
    }

    public static boolean isInstance(Class c1, Class c2) {
        Class superClass = c1;
        while (superClass != null) {
            if (superClass.equals(c2)) {
                return true;
            }
            superClass = superClass.getSuperclass();
        }
        superClass = c2.getSuperclass();
        while (superClass != null) {
            if (superClass.equals(c1)) {
                return true;
            }
            superClass = superClass.getSuperclass();
        }
        return false;
    }

//    public static String retainAllChars(String str, char[] chars) {
//
//    }
}
