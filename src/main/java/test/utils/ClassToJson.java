package test.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class ClassToJson {
    public static String convert(String classname, StringBuilder builder) {

        try {
        Class<?> clazz = Class.forName(classname);
        Class<?>[] classes = clazz.getDeclaredClasses();
        boolean firstEntry = true;

        builder.append("{");

            for (Field field : clazz.getDeclaredFields()) {
                if (field.getName().startsWith("this$"))                      // check that field is not a pointer to a parent class
                    continue;

                field.setAccessible(true);
                String name = field.getName();
                Class<?> type = field.getType();

                if (!firstEntry)                                         // check that is not a first entry into cycle
                    builder.append(", ");                                // append comma if so

                builder.append("\"").append(name)
                        .append("\"").append(":");

                if (Helper.notJDKClass(type)) {
                    convert(type.getTypeName(), builder);

                } else if (Map.class.isAssignableFrom(type)) {
                    Field map = clazz.getDeclaredField(name);
                    MemberToJson.isMap(map, builder);

                } else if (Collection.class.isAssignableFrom(type)) {
                    Field collection = clazz.getDeclaredField(name);
                    MemberToJson.isCollection(collection, builder);

                } else if (type.isArray()) {
                    MemberToJson.isArray(type, builder);

                } else {
                    builder.append("\"")
                            .append(Helper.getShortType(type))
                            .append("\"");
                }

                firstEntry = false;
            }

            if (classes.length != 0) {                             // internal class processing
                builder.append(", ");
                getInnerClass(classes, builder);
            }

        } catch (ClassNotFoundException e) {
            return "Class " + e.getMessage() + " not found";
        } catch (NoSuchFieldException e) {
            e.getStackTrace();
        }

        builder.append("}");

        return builder.toString();
    }

    private static void getInnerClass (Class<?>[] classes, StringBuilder builder) {
        boolean firstEntry = true;

        for (Class<?> innerClass : classes) {
            if (!firstEntry) builder.append(", ");

            builder.append("\"").append(innerClass.getSimpleName())
                    .append("\"").append(": ");

            convert(innerClass.getName(), builder);

            firstEntry = false;
        }
    }
}