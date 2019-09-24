package analyzer.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RabinKarp implements SearchStrategy {
    private final static int A = 53;
    private final static long M = 1_000_000_000 + 9;

    @Override
    public boolean contains(String text, String pattern) {
        return RabinKarpSearch(text, pattern).size() > 0;
    }

    private static List<Integer> RabinKarpSearch(String text, String pattern) {
        long patternHash = 0;
        long currSubstrHash = 0;
        long pow = 1;

        for (int i = 0; i < pattern.length(); i++) {
            patternHash += charToLong(pattern.charAt(i)) * pow;
            patternHash %= M;

            currSubstrHash += charToLong(text.charAt(text.length() - pattern.length() + i)) * pow;
            currSubstrHash %= M;

            if (i != pattern.length() - 1) {
                pow = pow * A % M;
            }
        }

        ArrayList<Integer> occurrences = new ArrayList<>();

        for (int i = text.length(); i >= pattern.length(); i--) {
            if (patternHash == currSubstrHash) {
                boolean patternIsFound = true;

                for (int j = 0; j < pattern.length(); j++) {
                    if (text.charAt(i - pattern.length() + j) != pattern.charAt(j)) {
                        patternIsFound = false;
                        break;
                    }
                }

                if (patternIsFound) {
                    occurrences.add(i - pattern.length());
                }
            }

            if (i > pattern.length()) {
                currSubstrHash = (currSubstrHash - charToLong(text.charAt(i - 1)) * pow % M + M) * A % M;
                currSubstrHash = (currSubstrHash + charToLong(text.charAt(i - pattern.length() - 1))) % M;
            }
        }

        Collections.reverse(occurrences);

        return occurrences;
    }

    private static long charToLong(char ch) {
        return (long)(ch - 'A' + 1);
    }
}
