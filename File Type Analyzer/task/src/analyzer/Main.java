package analyzer;

import analyzer.arguments.Arguments;
import analyzer.arguments.ArgumentsParser;
import analyzer.pattern.PatternListManager;
import analyzer.strategy.Resolver;
import analyzer.strategy.SearchStrategy;
import analyzer.taskManager.TaskManager;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final int THREAD_POOL_SIZE = 10;
    private static final String CURRENT_DIR = System.getProperty("user.dir") + File.separator;

    public static void main(String[] args) {
        System.out.println(CURRENT_DIR);
        Arguments arguments = ArgumentsParser.parse(args);

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        SearchStrategy strategy = Resolver.resolve(arguments);

        for (Path filePath: getAllFilesFromFolder(arguments)) {
            executor.submit(
                TaskManager.getFileTypeResolvingTask(
                    strategy,
                    filePath,
                    (new PatternListManager()).getPatternList(arguments)
                )
            );
        }

        try {
            executor.shutdown();
            executor.awaitTermination(60, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static List<Path> getAllFilesFromFolder(Arguments arguments) {
        try (Stream<Path> walk = Files.walk(Paths.get(CURRENT_DIR + arguments.getFilesFolder()))) {
            return walk.filter(Files::isRegularFile).sorted().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
