package analyzer.arguments;

public class ArgumentsParser {
    public static Arguments parse(String[] args) {
        if (args.length < 2) {
            throw new RuntimeException("Please provide all required arguments");
        }

        Arguments arguments = new Arguments();
        arguments.setFilesFolder(args[0]);
        arguments.setPatternsPath(args[1]);

        return arguments;
    }
}
