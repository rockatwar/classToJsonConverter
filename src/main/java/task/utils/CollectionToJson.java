package task.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class CollectionToJson {
    public static void convert (Type key, Type value, StringBuilder stringBuilder) {

        stringBuilder.append("[{");
        stringBuilder.append("\"").append(Helper.getShortType((Class<?>) key))
                .append("\"").append(": ");

        if (value instanceof Class) {

            stringBuilder.append("[");

            if (Helper.notJDKClass((Class<?>) value)) {
                ClassToJson.convert(((Class<?>) value).getName(), stringBuilder);
            } else {
                stringBuilder.append("\"")
                        .append(Helper.getShortType((Class<?>) value)).append("\"");
            }

            stringBuilder.append(",\"...\"]")
                    .append("},\"...\"]");

        }

        if (value instanceof ParameterizedType) {

            Type actual = ((ParameterizedType) value).getActualTypeArguments()[0];

            stringBuilder.append("[");

            if (Helper.notJDKClass((Class<?>) actual)) {
                ClassToJson.convert(((Class<?>) actual).getName(), stringBuilder);

            } else {

                stringBuilder.append("\"")
                        .append(Helper.getShortType((Class<?>) actual)).append("\"");
            }

            stringBuilder.append(",\"...\"]")
                    .append("},\"...\"]");

        }
    }

    public static void convert (Type value, StringBuilder stringBuilder) {

        stringBuilder.append("[");

        if (Helper.notJDKClass((Class<?>) value)) {

            ClassToJson.convert(((Class<?>) value).getName(), stringBuilder);

        } else {

            stringBuilder.append("\"")
                    .append(Helper.getShortType((Class<?>) value)).append("\"");
        }

        stringBuilder.append(",\"...\"]");

    }
}
