package ru.otus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.dao.TestReader;
import ru.otus.domain.Person;
import ru.otus.domain.Question;
import ru.otus.utils.QuestionBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class TestManagerImpl implements TestManager {
    private final TestReader testReader;

    private final QuestionBuilder questionBuilder;

    private Person person;

    private List<Question> questions = new ArrayList<>();

    private List<String> answers = new ArrayList<>();

    @Value("${passingScore}")
    private int minPassingScore;

    TestManagerImpl(TestReader testReader, QuestionBuilder questionBuilder) {
        this.testReader = testReader;
        this.questionBuilder = questionBuilder;
        this.person = null;
    }

    @Override
    public void run() {
        List<String> questionsFromFile = testReader.readQuestions();

        for(String s : questionsFromFile) {
            Question question = questionBuilder.makeQuestionFromString(s);
            questions.add(question);
        }

        this.person = new Person();

        processTest();

        boolean passedTest = checkResults();

        if(passedTest) {
            System.out.println("You passed the test!");
        } else {
            System.out.println("You failed( Try again...");
        }
    }

    @Override
    public boolean checkResults() {
        int i = 0;
        int correctCount = 0;

        for(Question q : questions) {
            int idx = Integer.parseInt(answers.get(i++));
            if (q.getCorrectAnswerIdx() == idx) {
                ++correctCount;
            }
        }

        if (correctCount >= minPassingScore) {
            return true;
        }

        return false;
    }

    private void processTest() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Your first name:");
        person.setFirstName(scanner.nextLine());

        System.out.println("Enter Your last name:");
        person.setLastName(scanner.nextLine());

        for(Question question : questions) {
            askQuestion(question);
            answers.add(scanner.nextLine());
        }
    }

    private void askQuestion(Question question) {
        StringBuilder str = new StringBuilder();
        int i = 1;

        str.append("Question: " + question.getQuestionText() + "\n");
        for(String answer : question.getAnswers()) {
            str.append(i++ + ") " + answer + "\n");
        }

        System.out.println(str);
    }
}
