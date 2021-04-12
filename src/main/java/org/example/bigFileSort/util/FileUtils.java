package org.example.bigFileSort.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class FileUtils {

    public static void writeToFile(List<String> list, String filePath) {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(filePath))) {
            for (String str : list) {
                bw.write(str);
                bw.write("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readAllStringsFromFile(String filePath) {
        List<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(list::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static File newTempFile(String tempFolderPath, int fileNum, List<String> list) {
        File tempFile = new File(tempFolderPath + "/part-" + fileNum + ".txt");
        writeToFile(list, tempFile.getPath());
        return tempFile;
    }

}
