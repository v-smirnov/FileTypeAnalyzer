package analyzer.strategy;

import analyzer.arguments.Arguments;

public class Resolver {
    public static SearchStrategy resolve(Arguments arguments) {
        return new Kmp();
    }
}
