package org.example.bigFileSort;

import org.assertj.core.api.Assertions;
import org.example.bigFileSort.util.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class FileSorterTest {

    public static final String BIG_FILE_PATH = "src/test/resources/big_file.txt";
    public static final String BIG_FILE_SORTED_PATH = "src/test/resources/big_file_sorted";

    @AfterEach
    void tearDown() {
        File file = new File(BIG_FILE_PATH);
        file.delete();
    }

    @Test
    void sortFile() {
        File expected = new File(BIG_FILE_SORTED_PATH);
        File actual = new File(BIG_FILE_PATH);
        List<String> list = new ArrayList<>();
        list.add("bcddd36352a");
        list.add("abcdd33562f");
        list.add("zxcgh76ert");
        list.add("pxvjk45qw");
        list.add("fgyyyz378");
        list.add("fghhhh456");
        FileUtils.writeToFile(list, BIG_FILE_PATH);

        FileSorter.sortBigFile(BIG_FILE_PATH);

        Assertions.assertThat(expected).hasSameTextualContentAs(actual);
    }

}
