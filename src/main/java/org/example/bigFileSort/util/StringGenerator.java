package org.example.bigFileSort.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringGenerator {

    private static final int LEFT_LIMIT = 97; // 'a'
    private static final int RIGHT_LIMIT = 122; // 'z'

    public static List<String> getRandomStringList(int count, int minLength, int maxLength) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(generateString(minLength, maxLength));
        }
        return list;
    }

    private static String generateString(int minLength, int maxLength) {
        int length = getRandomNumber(minLength, maxLength);
        Random random = new Random();
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int addNumDecider = random.nextInt(10);
            if (addNumDecider > 5) {
                builder.append(random.nextInt(10));
            } else {
                int randomLimitedInt = LEFT_LIMIT + (int) (random.nextFloat() * (RIGHT_LIMIT - LEFT_LIMIT + 1));
                builder.append((char) randomLimitedInt);
            }
        }
        return builder.toString();
    }

    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
