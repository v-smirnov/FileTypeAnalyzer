package analyzer.strategy;

public class Naive implements SearchStrategy {
    @Override
    public boolean contains(String text, String pattern) {
        if (text.length() < pattern.length()) {
            return false;
        }

        for (int i = 0; i < text.length() - pattern.length() + 1; i++) {
            boolean patternIsFound = true;

            for (int j = 0; j < pattern.length(); j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    patternIsFound = false;
                    break;
                }
            }

            if (patternIsFound) {
                return true;
            }
        }

        return false;
    }
}
