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
import static org.example.bigFileSort.FileSorter.MAX_LINES;
import static org.example.bigFileSort.util.StringGenerator.getRandomStringList;

public class BigFileGenerator {

    private static final Logger log = LoggerFactory.getLogger(BigFileGenerator.class);

    private static final int STR_MIN_LENGTH = 9;
    private static final int STR_MAX_LENGTH = 15;
    private static final int STR_COUNT = 30_000_000;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        File file = generateFile(BIG_FILE_PATH, STR_COUNT, STR_MIN_LENGTH, STR_MAX_LENGTH);
        long endTime = System.nanoTime();
        long totalTime = TimeUnit.NANOSECONDS.toSeconds(endTime - startTime);
        log.info("Generation complete. Total time: {} seconds. File size: {} mb",
                totalTime, file.length() / (1024 * 1024));
    }

    public static File generateFile(String filePath, int strCount, int strMinLength, int strMaxLength) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        List<String> linesBuffer;
        int linesLeft = strCount;
        log.info("Generating a file with {} lines...", String.format("%,d", strCount));
        while (linesLeft > 0) {
            int bufferSize = Math.min(MAX_LINES, linesLeft);
            linesBuffer = getRandomStringList(bufferSize, strMinLength, strMaxLength);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                for (String str : linesBuffer) {
                    bw.write(str);
                    bw.write("\r\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            linesLeft -= bufferSize;
        }
        return file;
    }

}
