package ru.otus.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.config.AppProps;
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

    private final AppProps props;

    private final MessageSource messageSource;

    private Person person;

    private List<Question> questions = new ArrayList<>();

    private List<String> answers = new ArrayList<>();

    private int minPassingScore;

    TestManagerImpl(TestReader testReader,
                    QuestionBuilder questionBuilder,
                    AppProps props,
                    MessageSource messageSource) {
        this.testReader = testReader;
        this.questionBuilder = questionBuilder;
        this.props = props;
        this.messageSource = messageSource;
        this.person = null;
        this.minPassingScore = props.getPassingScore();
    }

    public void setMinPassingScore(int minPassingScore) {
        this.minPassingScore = minPassingScore;
    }

    @Override
    public void run(String... args) {
        List<String> questionsFromFile = testReader.readQuestions();

        for(String s : questionsFromFile) {
            Question question = questionBuilder.makeQuestionFromString(s);
            questions.add(question);
        }

        this.person = new Person();

        processTest();

        boolean passedTest = checkResults();

        if(passedTest) {
            System.out.println(messageSource.getMessage("msg.passed", null, props.getLocale()));
        } else {
            System.out.println(messageSource.getMessage("msg.failed", null, props.getLocale()));
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

        System.out.println(messageSource.getMessage("ask.first.name", null, props.getLocale()));
        person.setFirstName(scanner.nextLine());

        System.out.println(messageSource.getMessage("ask.second.name", null, props.getLocale()));
        person.setLastName(scanner.nextLine());

        for(Question question : questions) {
            askQuestion(question);
            answers.add(scanner.nextLine());
        }
    }

    private void askQuestion(Question question) {
        StringBuilder str = new StringBuilder();
        int i = 1;

        str.append(messageSource.getMessage("msg.question", null, props.getLocale())
                  + question.getQuestionText() + "\n");
        for(String answer : question.getAnswers()) {
            str.append(i++ + ") " + answer + "\n");
        }

        System.out.println(str);
    }
}
