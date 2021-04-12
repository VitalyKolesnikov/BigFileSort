package org.example.bigFileSort.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringGeneratorTest {

    @Test
    void checkStringCount() {
        List<String> list = StringGenerator.getRandomStringList(100, 9, 15);

        assertEquals(list.size(), 100);
    }

    @Test
    void checkCharacters() {
        List<String> list = StringGenerator.getRandomStringList(100, 9, 15);

        list.forEach(s -> assertTrue(s.matches("^[a-z0-9]+")));
    }

    @Test
    void checkStringLength() {
        List<String> list = StringGenerator.getRandomStringList(100, 9, 15);

        list.forEach(s -> assertTrue(s.length() >= 9 && s.length() <= 15));
    }
}
