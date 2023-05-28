package test.utils;

import java.util.HashMap;
import java.util.Map;

public class Helper {

    private static final Map<Class<?>, String> SHORT_TYPE_MAP = new HashMap<>();

    static {
        SHORT_TYPE_MAP.put(Integer.class, "int");
        SHORT_TYPE_MAP.put(int.class, "int");
        SHORT_TYPE_MAP.put(Character.class, "char");
        SHORT_TYPE_MAP.put(char.class, "char");
    }

    public static String getShortType(Class<?> type) {

        if (type.isArray()) {
            return SHORT_TYPE_MAP.getOrDefault(type.getComponentType(), type.getSimpleName().substring(0, type.getSimpleName().indexOf("[")).toLowerCase());

        } else {
            return SHORT_TYPE_MAP.getOrDefault(type, type.getSimpleName().toLowerCase());

        }
    }

    public static boolean isNotJDKClass(Class<?> type) {

        return type.getClassLoader() != ClassLoader.getSystemClassLoader().getParent() && type.getClassLoader() != null;
    }
}
