package org.example.bigFileSort;

import org.example.bigFileSort.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.example.bigFileSort.util.FileUtils.newTempFile;
import static org.example.bigFileSort.util.FileUtils.readAllStringsFromFile;

public class FileSorter {

    private static final Logger log = LoggerFactory.getLogger(FileSorter.class);

    public static final String BIG_FILE_PATH = "src/main/resources/big_file.txt";
    public static final String TEMP_FOLDER_PATH = "src/main/resources/temp";
    public static final int MAX_LINES = 100_000;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        sortBigFile(BIG_FILE_PATH);
        long endTime = System.nanoTime();
        long totalTime = TimeUnit.NANOSECONDS.toSeconds(endTime - startTime);
        log.info("Sorting complete. Total time: {} seconds", totalTime);
    }

    public static void sortBigFile(String filePath) {
        List<File> tempFiles = splitFile(filePath, TEMP_FOLDER_PATH, MAX_LINES);
        log.info("Sorting temp files...");
        tempFiles.forEach(FileSorter::sortFileInMemory);
        mergeFiles(tempFiles, filePath);
    }

    public static List<File> splitFile(String filePath, String tempFolderPath, int maxLines) {
        log.info("Splitting the file...");

        List<File> tempFiles = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            String str;
            int fileNum = 1;
            int currentLine = 0;
            List<String> list = new ArrayList<>();

            File dir = new File(tempFolderPath);
            dir.mkdir();

            while ((str = br.readLine()) != null) {
                list.add(str);
                currentLine++;
                if (currentLine == maxLines) {
                    tempFiles.add(newTempFile(tempFolderPath, fileNum, list));
                    currentLine = 0;
                    fileNum++;
                    list.clear();
                }
            }
            if (!list.isEmpty()) {
                tempFiles.add(newTempFile(tempFolderPath, fileNum, list));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(filePath);
        file.delete();

        log.info("Splitting complete. Total temp files: {}", tempFiles.size());
        return tempFiles;
    }

    private static void sortFileInMemory(File file) {
        List<String> list = readAllStringsFromFile(file.getPath());
        Collections.sort(list);
        FileUtils.writeToFile(list, file.getPath());
    }

    private static void mergeFiles(List<File> tempFiles, String resultFilePath) {
        log.info("Merging sorted temp files...");

        TreeMap<String, BufferedReader> map = new TreeMap<>();
        try (FileWriter fw = new FileWriter(resultFilePath, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (File file : tempFiles) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                map.put(line, br);
            }

            while (!map.isEmpty()) {
                Map.Entry<String, BufferedReader> entry = map.pollFirstEntry();
                bw.write(entry.getKey());
                bw.write("\r\n");
                String str = entry.getValue().readLine();
                if (str != null) {
                    map.put(str, entry.getValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            File dir = tempFiles.get(0).getParentFile();
            tempFiles.forEach(File::delete);
            dir.delete();
        }
    }

}
