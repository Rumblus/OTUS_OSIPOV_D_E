package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты класса Question")
public class QuestionTest {

    @DisplayName("Корректно создается конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        Question question = new Question("1 + 1 = ?", 1, Arrays.asList("2", "3", "4"));

        assertEquals("1 + 1 = ?", question.getQuestionText());
        assertEquals(1, question.getCorrectAnswerIdx());
        assertEquals(Arrays.asList("2", "3", "4"), question.getAnswers());
    }

    @DisplayName("Корректно работают сеттеры и геттеры")
    @Test
    void shouldSettersGettersWork() {
        Question question = new Question();

        question.setQuestionText("1 + 1 = ?");
        question.setCorrectAnswerIdx(1);
        question.setAnswers(Arrays.asList("2", "3", "4"));

        assertEquals("1 + 1 = ?", question.getQuestionText());
        assertEquals(1, question.getCorrectAnswerIdx());
        assertEquals(Arrays.asList("2", "3", "4"), question.getAnswers());
    }
}
