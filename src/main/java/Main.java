import test.utils.Builder;
import test.utils.ClassToJson;

public class Main {
    public static void main(String[] args) {
        boolean firstEntry = true;
        String result = null;

        if (args.length == 0) {
            System.out.println("Enter the class name as command line argument.");
            System.exit(0);
        }

        for (String arg : args) {
            if (!firstEntry)
                Builder.append(",");

            result = ClassToJson.convert(arg);

            firstEntry = false;
        }

        System.out.println(result);
    }
}
