package ru.otus;

import java.util.ArrayList;
import java.util.List;

public class TestManager {
    private TestReader testReader;

    private List<Question> questions = new ArrayList<>();

    TestManager(TestReader testReader) {
        this.testReader = testReader;
    }

    public void run() {
        questions = testReader.readQuestionsFromFile();

        for(Question question : questions) {
            askQuestion(question);
        }
    }

    private void askQuestion(Question question) {
        StringBuilder str = new StringBuilder();
        int i = 1;

        str.append("Question: " + question.getQuestionText() + "\n");
        for(String answer : question.getAnswers()) {
            str.append(i++ + ") " + answer + "\n");
        }
        str.append("\n");

        System.out.println(str.toString());
    }
}
