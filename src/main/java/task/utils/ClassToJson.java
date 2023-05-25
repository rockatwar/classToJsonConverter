package task.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public class ClassToJson {
    public static String convert(String classname, StringBuilder stringBuilder) {

        try {

        Class<?> clazz = Class.forName(classname);
        boolean firstEntry = true;

        stringBuilder.append("{");

            for (Field field : clazz.getDeclaredFields()) {
                if (field.getName().equals("this$0"))
                    continue;

                field.setAccessible(true);

                String name = field.getName();
                Class<?> type = field.getType();

                if (!firstEntry)
                    stringBuilder.append(", ");

                stringBuilder.append("\"").append(name)
                        .append("\"").append(":");

                if (Helper.notJDKClass(type)) {

                    convert(type.getTypeName(), stringBuilder);

                } else if (Map.class.isAssignableFrom(type)) {

                    Field map = clazz.getDeclaredField(name);
                    ParameterizedType parameterizedType = (ParameterizedType) map.getGenericType();

                    Type key = parameterizedType.getActualTypeArguments()[0];
                    Type value = parameterizedType.getActualTypeArguments()[1];

                    CollectionToJson.convert(key, value, stringBuilder);

                } else if (Collection.class.isAssignableFrom(type)) {

                    Field collection = clazz.getDeclaredField(name);
                    ParameterizedType parameterizedType = (ParameterizedType) collection.getGenericType();

                    Type value = parameterizedType.getActualTypeArguments()[0];

                    CollectionToJson.convert(value, stringBuilder);

                } else if (type.isArray()) {

                    ArrayToJson.convert(type, stringBuilder);

                } else {

                    stringBuilder.append("\"").append(Helper.getShortType(type)).append("\"");
                }

                firstEntry = false;
            }

            for (Class<?> innerClass : clazz.getDeclaredClasses()) {

                if (!firstEntry)
                    stringBuilder.append(", ");

                stringBuilder.append("\"").append(innerClass.getSimpleName())
                        .append("\"").append(": ");

                convert(innerClass.getName(), stringBuilder);

                firstEntry = false;
            }

        } catch (ClassNotFoundException e) {
            return "Class " + e.getMessage() + " not found";
        } catch (NoSuchFieldException e) {
            e.getStackTrace();
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }
}