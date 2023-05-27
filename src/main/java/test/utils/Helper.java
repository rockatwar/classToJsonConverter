package test.utils;

public class Helper {
    public static String getShortType(Class<?> type) {

        if (type.equals(Integer.class)
                || type.getComponentType() == Integer.class
                || type.getComponentType() == int.class) {

            return "int";
        }

        if (type.equals(Character.class)
                || type.getComponentType() == Character.class
                || type.getComponentType() == char.class) {

            return "char";
        }

        if (type.isArray()) {
            return type.getSimpleName()
                    .substring(0, type.getSimpleName().indexOf("["))
                    .toLowerCase();
        } else {

            return type.getSimpleName().toLowerCase();
        }
    }

    public static boolean notJDKClass(Class<?> type) {

        return type.getClassLoader() != ClassLoader.getSystemClassLoader().getParent() && type.getClassLoader() != null;
    }
}
