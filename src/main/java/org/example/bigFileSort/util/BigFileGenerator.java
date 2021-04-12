package org.example.bigFileSort.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.example.bigFileSort.FileSorter.BIG_FILE_PATH;

public class BigFileGenerator {

    private static final Logger log = LoggerFactory.getLogger(BigFileGenerator.class);

    private static final int STR_MIN_LENGTH = 9;
    private static final int STR_MAX_LENGTH = 15;
    private static final int STR_COUNT = 100_000;
    private static final int MULTIPLIER = 10;

    public static void main(String[] args) {
        new File(BIG_FILE_PATH).delete();

        long startTime = System.nanoTime();

        StringGenerator generator = new StringGenerator();
        List<String> list;

        log.info("Generating file with {} lines", STR_COUNT * MULTIPLIER);
        for (int i = 0; i < MULTIPLIER; i++) {
            list = generator.getRandomStringList(STR_COUNT, STR_MIN_LENGTH, STR_MAX_LENGTH);
            try (FileWriter fw = new FileWriter(BIG_FILE_PATH, true);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                for (String str : list) {
                    bw.write(str);
                    bw.write("\r\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime();
        long totalTime = TimeUnit.NANOSECONDS.toSeconds(endTime - startTime);

        log.info("Generation complete. Total time: {} seconds", totalTime);
    }

}
