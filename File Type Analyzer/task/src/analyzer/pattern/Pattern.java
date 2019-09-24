package analyzer.pattern;

public class Pattern {
    private int priority;

    private String pattern;

    private String type;

    public Pattern(int priority, String pattern, String type) {
        this.priority = priority;
        this.pattern = pattern;
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public String getPattern() {
        return pattern;
    }

    public String getType() {
        return type;
    }
}
