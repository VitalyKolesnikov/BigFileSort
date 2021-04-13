package org.example.bigFileSort.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BigFileGeneratorTest {

    public static final String BIG_FILE_PATH = "src/test/resources/big_file.txt";

    @AfterEach
    void tearDown() {
        File file = new File(BIG_FILE_PATH);
        file.delete();
    }

    @Test
    void checkFileLinesCount() throws IOException {
        BigFileGenerator.generateFile(BIG_FILE_PATH, 100_000, 9, 15);
        long lineCount;
        try (Stream<String> stream = Files.lines(Paths.get(BIG_FILE_PATH),StandardCharsets.UTF_8)) {
            lineCount = stream.count();
        }

        assertEquals(100_000, lineCount);
    }

}
