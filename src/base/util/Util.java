package base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

    public static Double convertToDouble(String str) {
//        String numberStr = str.replaceAll("(\\D*)(\\.)(\\D*)", "");

        return Double.parseDouble(str);
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

    public static Method getSetter(Field field, Class<?> objectKind) {
        return getSetter(field.getName(), field.getType(), objectKind);
    }

    public static Method getSetter(String propertyName, Class<?> propertyType, Class<?> objectType) {
        String setter = "set" + capitalize(propertyName);
        Method method = null;
        try {
            method = objectType.getMethod(setter, propertyType);
        } catch (NoSuchMethodException e) {
            for (Method m : objectType.getMethods()) {
                if (m.getName().equals(setter) && m.getParameterTypes().length == 1)
                    if (m.getParameterTypes()[0].isAssignableFrom(propertyType)) {
                        method = m;
                        break;
                    }
            }
            if (method == null)
                e.printStackTrace();
        }
        return method;
    }

    public static String capitalize(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

//    public static String retainAllChars(String str, char[] chars) {
//
//    }
}
