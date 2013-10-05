package base.util;

import base.applicator.object.StandardEntity;
import base.applicator.object.detail.FieldType;
import static base.applicator.object.detail.FieldType.FLOAT;
import static base.applicator.object.detail.FieldType.LONG;

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
    public static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS = new HashMap<>();
    public static char[] points = new char[]{'.', ',', '/'};

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

    public static Number convertToNumber(String val, NumberType type) {
        switch (type) {
            case INT: {
                return convertToInt(val);
            }
            case DOUBLE: {
                return convertToDouble(val);
            }
            default:
                return null;
        }
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
        Class<?> w1 = wrap(c1);
        Class<?> w2 = wrap(c2);
        return w1.isAssignableFrom(w2) || w2.isAssignableFrom(w1);
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
                if (m.getName().equals(setter) && m.getParameterTypes().length == 1) {
                    if (m.getParameterTypes()[0].isAssignableFrom(propertyType)) {
                        method = m;
                        break;
                    }
                }
            }
            if (method == null) {
                e.printStackTrace();
            }
        }
        return method;
    }

    public static String capitalize(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    public static <T extends StandardEntity> void invoke(Method method, T obj, Object val) {
        try {
            method.invoke(obj, val);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
    public static Object convert(String string, FieldType type) {
        switch(type){
            case INT:
                return convertToNumber(string, NumberType.DOUBLE);
            case DOUBLE:
                return convertToNumber(string, NumberType.DOUBLE);
            case FLOAT:
                return convertToNumber(string, NumberType.FLOAT);
            case LONG:
                return convertToNumber(string, NumberType.LONG);
            case SHORT:
                return convertToNumber(string, NumberType.SHORT);
            case STRING:
                return string;
            default:
                return string;
        }
    }

    public enum NumberType {
        INT,
        DOUBLE,
        FLOAT,
        LONG,
        SHORT,
    }
}
