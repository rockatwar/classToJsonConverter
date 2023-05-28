package test.utils;

public class Builder {
    public static final StringBuilder STRING_BUILDER = new StringBuilder();

    public static void append(String str) {

        STRING_BUILDER.append(str);
    }

    public static void wrapName(String name) {

        STRING_BUILDER.append("\"").append(name).append("\"").append(":");
    }

    public static void wrapMultiArray(String array) {

        STRING_BUILDER.append("[[\"").append(array).append("\",\"...\"],\"...\"]");
    }

    public static void wrapSingleArray(String array) {

        STRING_BUILDER.append("[\"").append(array).append("\",\"...\"]");
    }

    public static void wrapKey(String key) {

        STRING_BUILDER.append("[{").append("\"").append(key).append("\"").append(":");
    }

    public static void wrapValue(String value) {

        STRING_BUILDER.append("\"").append(value).append("\"");
    }

    public static void deleteComma() {

        STRING_BUILDER.deleteCharAt(STRING_BUILDER.length() - 1);

    }

}
