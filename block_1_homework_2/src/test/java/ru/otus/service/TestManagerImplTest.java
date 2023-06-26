package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.dao.TestReaderImpl;
import ru.otus.utils.QuestionBuilderImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты класса TestManagerImpl")
public class TestManagerImplTest {
    @Mock
    private TestReaderImpl testReader;

    private QuestionBuilderImpl questionBuilder = new QuestionBuilderImpl();

    @BeforeEach
    void initTestReader() {
        List<String> stringsToReturn = new ArrayList<>();
        stringsToReturn.add("1 + 1 = ?;1;2;4;5;");
        stringsToReturn.add("3 + 4 = ?;3;5;6;7;");
        stringsToReturn.add("5 - 2 = ?;1;3;2;1;");
        stringsToReturn.add("6 - 8 = ?;3;2;8;-2;");
        stringsToReturn.add("4 - 1 = ?;2;1;3;9;");

        Mockito.doReturn(stringsToReturn).when(testReader).readQuestions();
    }

    @DisplayName("Тест провален")
    @Test
    void shouldRunWithNegativeResult() {
        TestManagerImpl tm = new TestManagerImpl(testReader, questionBuilder);
        tm.setMinPassingScore(3);

        String testAnswers = "Ivan\nIvanov\n1\n1\n1\n1\n1\n";

        InputStream systemInput = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream(testAnswers.getBytes());
        System.setIn(in);
        PrintStream systemOutput = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        System.setOut(out);

        tm.run();

        String[] lines = baos.toString().split(System.lineSeparator());
        String resultString = lines[lines.length - 1];

        System.setIn(systemInput);
        System.setOut(systemOutput);

        assertEquals("You failed( Try again...", resultString);
    }

    @DisplayName("Тест пройден")
    @Test
    void shouldRunWithPositiveResult() {
        TestManagerImpl tm = new TestManagerImpl(testReader, questionBuilder);
        tm.setMinPassingScore(3);

        String testAnswers = "Ivan\nIvanov\n1\n3\n1\n3\n2\n";

        InputStream systemInput = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream(testAnswers.getBytes());
        System.setIn(in);
        PrintStream systemOutput = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        System.setOut(out);

        tm.run();

        String[] lines = baos.toString().split(System.lineSeparator());
        String resultString = lines[lines.length - 1];

        System.setIn(systemInput);
        System.setOut(systemOutput);

        assertEquals("You passed the test!", resultString);
    }

}
