package task.utils;

public class Helper {
    public static String getShortType(Class<?> type) {

        if (type.equals(Integer.class))
            return "int";

        if (type.equals(Character.class))
            return "char";

        if (type.isArray())
            return type.getSimpleName()
                    .substring(0, type.getSimpleName().indexOf("["))
                    .toLowerCase();

        else
            return type.getSimpleName().toLowerCase();

    }

    public static boolean notJDKClass(Class<?> type) {

        return !type.getPackageName().startsWith("java") && !type.getPackageName().startsWith("javax") && !type.getPackageName().startsWith("com.sun") &&
                !type.getPackageName().startsWith("jdk");

    }
}
