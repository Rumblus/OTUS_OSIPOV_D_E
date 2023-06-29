package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.MessageSource;
import ru.otus.config.AppProps;
import ru.otus.dao.TestReaderImpl;
import ru.otus.utils.QuestionBuilderImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Тесты класса TestManagerImpl")
public class TestManagerImplTest {
    @Mock
    private TestReaderImpl testReader;

    @Mock
    private AppProps appProps;

    @Mock
    private MessageSource messageSource;

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

        Mockito.doReturn(Locale.ENGLISH).when(appProps).getLocale();
        Mockito.doReturn("test_en.csv").when(appProps).getTestFileName();
        Mockito.doReturn(3).when(appProps).getPassingScore();

        Mockito.doReturn("Enter Your first name:").when(messageSource).getMessage("ask.first.name", null, Locale.ENGLISH);
        Mockito.doReturn("Enter Your second name:").when(messageSource).getMessage("ask.second.name", null, Locale.ENGLISH);
        Mockito.doReturn("Question: ").when(messageSource).getMessage("msg.question", null, Locale.ENGLISH);
        Mockito.doReturn("You passed the test!").when(messageSource).getMessage("msg.passed", null, Locale.ENGLISH);
        Mockito.doReturn("You failed( Try again...").when(messageSource).getMessage("msg.failed", null, Locale.ENGLISH);
    }

    @DisplayName("Тест провален")
    @Test
    void shouldRunWithNegativeResult() {
        TestManagerImpl tm = new TestManagerImpl(testReader, questionBuilder, appProps, messageSource);
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
        TestManagerImpl tm = new TestManagerImpl(testReader, questionBuilder, appProps, messageSource);
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
