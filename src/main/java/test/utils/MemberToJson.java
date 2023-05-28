package test.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class MemberToJson {
    public static void isMap (Field field) {
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();

        Type key = parameterizedType.getActualTypeArguments()[0];
        Type value = parameterizedType.getActualTypeArguments()[1];

        Builder.wrapKey(Helper.getShortType((Class<?>) key));

        if (value instanceof Class) {

            processValue((Class<?>) value);
            Builder.append("},\"...\"]");

        }

        if (value instanceof ParameterizedType) {

            Type actual = ((ParameterizedType) value).getActualTypeArguments()[0];
            processValue((Class<?>) actual);

            Builder.append("},\"...\"]");

        }
    }

    public static void isCollection (Field field) {
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        Type value = parameterizedType.getActualTypeArguments()[0];

        processValue((Class<?>) value);

    }

    public static void isArray (Class<?> type) {
        Class<?> component = type.getComponentType();

        if (component.isArray()) {

            Builder.wrapMultiArray(Helper.getShortType(type));

        } else {

            Builder.wrapSingleArray(Helper.getShortType(type));

        }
    }

    private static void processValue(Class<?> value) {

        Builder.append("[");

        if (Helper.isNotJDKClass(value)) {
            ClassToJson.convert(value.getName());

        } else {
            Builder.wrapValue(Helper.getShortType(value));

        }

        Builder.append(",\"...\"]");
    }
}
