package ru.otus;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestReader {
    private String filename;

    TestReader(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<Question> readQuestionsFromFile() {
        InputStream inputStream = TestReader.class.getClassLoader().getResourceAsStream("test.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        List<Question> questions = new ArrayList<>();

        try {
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(";");
                questions.add(makeQuestion(row));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }

    private Question makeQuestion(String[] strings) {
        List<String> answers = new ArrayList<>();
        answers = Arrays.stream(strings).toList().subList(2, strings.length);

        return new Question(strings[0], Integer.valueOf(strings[1]), answers);
    }
}
