package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.util.Vector;


public class TextRedactorTest {
    private TextRedactor text;

    @BeforeEach
    void setUp(){
        text = new TextRedactor();
    }

    @Test
    @DisplayName("Чтение нескольких строк текста")
    void testReadTextWithMultipleLines() {

        String testInput = "Первая строка\nВторая строка\n\n";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testInput.getBytes());

        text.scanner = new Scanner(testInputStream);

        text.readText();

        assertEquals(2, text.vector.size());
        assertEquals("Первая строка\n", text.vector.get(0));
        assertEquals("Вторая строка\n", text.vector.get(1));
    }

    @Test
    @DisplayName("Чтение одной строки текста")
    void testReadTextWithOneLine() {

        String testInput = "Первая строка\n\n";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testInput.getBytes());

        text.scanner = new Scanner(testInputStream);

        text.readText();

        assertEquals(1, text.vector.size());
        assertEquals("Первая строка\n", text.vector.get(0));
    }

    @Test
    @DisplayName("Чтение пустого текста")
    void testReadTextWithNoLines() {

        String testInput = "\n\n";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testInput.getBytes());

        text.scanner = new Scanner(testInputStream);

        text.readText();

        assertTrue(text.vector.isEmpty());
    }

    @Test
    @DisplayName("Удаление знаков препинания")
    void testDeletePunct() {

        String testInput = "Тестовая, информация\nУдаление. знаков, препинания\n\n";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testInput.getBytes());

        text.scanner = new Scanner(testInputStream);
        text.readText();

        text.deletePunct();

        for (String line : text.vector) {
            assertFalse(line.contains("."));
            assertFalse(line.contains(","));
        }
    }

    @Test
    @DisplayName("Проверка display метода")
    void testDisplay() {
        String testInput = "Строка для вывода\n\n";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testInput.getBytes());
        text.scanner = new Scanner(testInputStream);
        text.readText();

        assertDoesNotThrow(() -> text.display());
    }

    @Test
    @DisplayName("Удаление K слов из нескольких строк")
    void testDeleteKFromMultipleLines() {
        String testInput = "строка1 слово2 слово3\nстрока4 слово5\n\n";
        String kInput = "1\n";
        String combinedInput = testInput + kInput;

        text.scanner = new Scanner(new ByteArrayInputStream(combinedInput.getBytes()));
        text.readText();

        text.deleteK();

        assertEquals(2, text.vector.size());
        assertEquals("слово2 слово3\n", text.vector.get(0)); // удалено 1 слово
        assertEquals("слово5\n", text.vector.get(1)); // удалено 1 слово
    }

    @Test
    @DisplayName("Удаление всех слов при k >= количества слов")
    void testDeleteKAllWords() {
        // Arrange
        String testInput = "один два три\n\n";
        String kInput = "5\n";
        String combinedInput = testInput + kInput;

        text.scanner = new Scanner(new ByteArrayInputStream(combinedInput.getBytes()));
        text.readText();

        text.deleteK();

        assertEquals(1, text.vector.size());
        assertEquals("\n", text.vector.get(0));
    }

    @Test
    @DisplayName("Удаление 0 слов (k=0)")
    void testDeleteKZero() {
        String testInput = "один два три\n\n";
        String kInput = "0\n";
        String combinedInput = testInput + kInput;

        text.scanner = new Scanner(new ByteArrayInputStream(combinedInput.getBytes()));
        text.readText();

        text.deleteK();

        assertEquals(1, text.vector.size());
        assertEquals("один два три\n", text.vector.get(0));
    }

    @Test
    @DisplayName("Отрицательное k")
    void testDeleteKNegative() {
        String testInput = "один два три\n\n";
        String kInput = "-5\n";
        String combinedInput = testInput + kInput;

        text.scanner = new Scanner(new ByteArrayInputStream(combinedInput.getBytes()));
        text.readText();
        Vector<String> originalVector = new Vector<>(text.vector);

        text.deleteK();

        assertEquals(originalVector, text.vector);
    }

}
