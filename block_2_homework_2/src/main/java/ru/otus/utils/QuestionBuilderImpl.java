package ru.otus.utils;

import org.springframework.stereotype.Component;
import ru.otus.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("questionBuilder")
public class QuestionBuilderImpl implements QuestionBuilder {
    public Question makeQuestionFromString(String line) {
        String[] row = line.split(";");
        Question result = makeQuestion(row);
        return result;
    }

    private Question makeQuestion(String[] strings) {
        List<String> answers = new ArrayList<>();
        answers = Arrays.stream(strings).toList().subList(2, strings.length);
        return new Question(strings[0], Integer.valueOf(strings[1]), answers);
    }
}
