package test.utils;

import test.exeption.MultipleRecurrence;
import java.lang.reflect.Field;
import java.util.*;

public class ClassToJson {
    private static final List<String> PARSED_CLASSES = new ArrayList<>();
    public static String convert(String classname) {

        try {
        Class<?> clazz = Class.forName(classname);
        Class<?>[] classes = clazz.getDeclaredClasses();

        if (PARSED_CLASSES.indexOf(classname) != PARSED_CLASSES.lastIndexOf(classname)) {
            Builder.wrapValue(clazz.getSimpleName());
            throw new MultipleRecurrence("Multiple recurrence detected at " + classname);
        }

        PARSED_CLASSES.add(classname);

        Builder.append("{");

            for (Field field : clazz.getDeclaredFields()) {
                if (field.getName().startsWith("this$"))                      // check that field is not a pointer to a parent class
                    continue;

                field.setAccessible(true);
                String name = field.getName();
                Class<?> type = field.getType();

                Builder.wrapName(name);

                if (Helper.isNotJDKClass(type)) {
                    convert(type.getTypeName());

                } else if (Map.class.isAssignableFrom(type)) {
                    Field map = clazz.getDeclaredField(name);
                    MemberToJson.isMap(map);

                } else if (Collection.class.isAssignableFrom(type)) {
                    Field collection = clazz.getDeclaredField(name);
                    MemberToJson.isCollection(collection);

                } else if (type.isArray()) {
                    MemberToJson.isArray(type);

                } else {
                    Builder.wrapValue(Helper.getShortType(type));

                }
                Builder.append(",");
            }

            Builder.deleteLastChar();

            if (classes.length != 0) {                                   // internal class processing
                Builder.append(",");
                getInnerClass(classes);
            }

        Builder.append("}");

        } catch (ClassNotFoundException e) {
            return "Class " + e.getMessage() + " not found";
        } catch (NoSuchFieldException | MultipleRecurrence e) {
            e.printStackTrace();
        }

        return Builder.STRING_BUILDER.toString();
    }

    private static void getInnerClass (Class<?>[] classes) {

        for (Class<?> innerClass : classes) {

            Builder.wrapName(innerClass.getSimpleName());

            convert(innerClass.getName());
            Builder.append(",");
        }

        Builder.deleteLastChar();
    }
}