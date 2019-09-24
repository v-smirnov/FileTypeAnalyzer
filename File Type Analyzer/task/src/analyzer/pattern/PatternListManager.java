package analyzer.pattern;

import analyzer.arguments.Arguments;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PatternListManager implements PatternListManagerInterface{
    private static final String CURRENT_DIR = System.getProperty("user.dir") + File.separator;

    public List<Pattern> getPatternList(Arguments arguments) {
        ArrayList<Pattern> list = new ArrayList<>();

        try {
            return
                Files.readAllLines(Paths.get(CURRENT_DIR + arguments.getPatternsPath()))
                    .stream()
                    .map((line) -> {
                            String[] items = line.split(";");

                            return new Pattern(
                                Integer.parseInt(items[0]),
                                items[1].replaceAll("\"", ""),
                                items[2].replaceAll("\"", "")
                            );
                        }
                    ).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
