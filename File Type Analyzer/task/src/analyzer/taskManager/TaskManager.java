package analyzer.taskManager;

import analyzer.pattern.Pattern;
import analyzer.strategy.SearchStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class TaskManager {
    public static Runnable getFileTypeResolvingTask(SearchStrategy strategy, Path path, List<Pattern> patterns) {
        return
            () -> {
                try {
                    String text = new String(Files.readAllBytes(path));

                    patterns.sort(Comparator.comparingInt(Pattern::getPriority).reversed());

                    boolean fileTypeDefined = false;
                    for (Pattern pattern: patterns) {
                        if (strategy.contains(text, pattern.getPattern())) {
                            fileTypeDefined = true;

                            System.out.println(path.getFileName().toString() + ": " + pattern.getType());
                            break;
                        }
                    }

                    if (!fileTypeDefined) {
                        System.out.println(path.getFileName().toString() + ": " + "Unknown file type");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            };
    }
}
