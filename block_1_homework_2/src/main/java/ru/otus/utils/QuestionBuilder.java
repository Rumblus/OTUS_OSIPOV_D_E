package ru.otus.utils;

import ru.otus.domain.Question;

public interface QuestionBuilder {
    Question makeQuestionFromString(String line);
}
