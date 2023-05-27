import test.utils.ClassToJson;

public class Main {
    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        boolean firstEntry = true;
        String result = null;

        if (args.length == 0) {
            System.out.println("Enter the class name as command line argument.");
            System.exit(0);
        }

        for (String arg : args) {
            if (!firstEntry)
                builder.append(", ");

            result = ClassToJson.convert(arg, builder);

            firstEntry = false;
        }

        System.out.println(result);
    }
}
