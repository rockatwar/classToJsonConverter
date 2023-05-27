package test.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class MemberToJson {
    public static void isMap (Field field, StringBuilder builder) {
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();

        Type key = parameterizedType.getActualTypeArguments()[0];
        Type value = parameterizedType.getActualTypeArguments()[1];

        builder.append("[{");
        builder.append("\"").append(Helper.getShortType((Class<?>) key))
                .append("\"").append(": ");

        if (value instanceof Class) {

            builder.append("[");

            if (Helper.notJDKClass((Class<?>) value)) {
                ClassToJson.convert(((Class<?>) value).getName(), builder);

            } else {

                builder.append("\"")
                        .append(Helper.getShortType((Class<?>) value)).append("\"");
            }

            builder.append(",\"...\"]")
                    .append("},\"...\"]");

        }

        if (value instanceof ParameterizedType) {

            Type actual = ((ParameterizedType) value).getActualTypeArguments()[0];

            builder.append("[");

            if (Helper.notJDKClass((Class<?>) actual)) {
                ClassToJson.convert(((Class<?>) actual).getName(), builder);

            } else {

                builder.append("\"")
                        .append(Helper.getShortType((Class<?>) actual)).append("\"");
            }

            builder.append(",\"...\"]")
                    .append("},\"...\"]");

        }
    }

    public static void isCollection (Field field, StringBuilder builder) {
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        Type value = parameterizedType.getActualTypeArguments()[0];

        builder.append("[");

        if (Helper.notJDKClass((Class<?>) value)) {

            ClassToJson.convert(((Class<?>) value).getName(), builder);

        } else {

            builder.append("\"")
                    .append(Helper.getShortType((Class<?>) value)).append("\"");
        }

        builder.append(",\"...\"]");

    }

    public static void isArray (Class<?> type, StringBuilder builder) {
        Class<?> element = type.getComponentType();

        if (element.isArray()) {

            builder.append("[[\"")
                    .append(Helper.getShortType(type))
                    .append("\",\"...\"],\"...\"]");
        } else {

            builder.append("[\"")
                    .append(Helper.getShortType(type))
                    .append("\",\"...\"]");
        }
    }
}
