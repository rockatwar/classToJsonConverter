package task.utils;

public class ArrayToJson {
    public static void convert (Class<?> type, StringBuilder stringBuilder) {
        Class<?> element = type.getComponentType();

        if (element.isArray()) {

            stringBuilder.append("[[\"")
                    .append(Helper.getShortType(type))
                    .append("\",\"...\"],\"...\"]");
        } else {

            stringBuilder.append("[\"")
                    .append(Helper.getShortType(type))
                    .append("\",\"...\"]");
        }
    }
}
