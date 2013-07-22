package base.util;

import base.applicator.object.StandardEntity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahdi
 */
public class Util {

    public static char[] NUMBERS = new char[10];
    public static Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS = new HashMap<Class<?>, Class<?>>();

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


        PRIMITIVES_TO_WRAPPERS.put(boolean.class, Boolean.class);
        PRIMITIVES_TO_WRAPPERS.put(byte.class, Byte.class);
        PRIMITIVES_TO_WRAPPERS.put(char.class, Character.class);
        PRIMITIVES_TO_WRAPPERS.put(double.class, Double.class);
        PRIMITIVES_TO_WRAPPERS.put(float.class, Float.class);
        PRIMITIVES_TO_WRAPPERS.put(int.class, Integer.class);
        PRIMITIVES_TO_WRAPPERS.put(long.class, Long.class);
        PRIMITIVES_TO_WRAPPERS.put(short.class, Short.class);
        PRIMITIVES_TO_WRAPPERS.put(void.class, Void.class);
    }

    // safe because both Long.class and long.class are of type Class<Long>
    @SuppressWarnings("unchecked")
    public static <T> Class<T> wrap(Class<T> c) {
        return c.isPrimitive() ? (Class<T>) PRIMITIVES_TO_WRAPPERS.get(c) : c;
    }

    public static Integer convertToInt(String str) {
        String numberStr = str.replaceAll("\\D+", "");
        return Integer.parseInt(numberStr);
    }

    public static Double convertToDouble(String str) {
//        String numberStr = str.replaceAll("(\\D*)(\\.)(\\D*)", "");

        return Double.parseDouble(str);
    }

    public static boolean isInstance(Class<?> c1, Class<?> c2) {

//        while (superClass != null) {
//            if (superClass.equals(c2)) {
//                return true;
//            }
//            superClass = superClass.getSuperclass();
//        }
        Class<?> w1 = wrap(c1);
        Class<?> w2 = wrap(c2);
        return w1.isAssignableFrom(w2) || w2.isAssignableFrom(w1);
        //        Class<?> superClass = w1;
//        superClass = c2.getSuperclass();
//        while (superClass != null) {
//            if (superClass.equals(c1)) {
//                return true;
//            }
//            superClass = superClass.getSuperclass();
//        }
//        return false;
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

    public static <T extends StandardEntity> void invoke(Method method, T obj, Object val) {
        try {
            method.invoke(obj, val);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

//    public static String retainAllChars(String str, char[] chars) {
//
//    }
}
