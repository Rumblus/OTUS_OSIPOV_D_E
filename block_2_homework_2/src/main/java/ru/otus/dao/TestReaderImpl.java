package ru.otus.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component("testReader")
public class TestReaderImpl implements TestReader {
    private String fileName;

    TestReaderImpl(@Value("${testFile.name}") String fileName) {
        this.fileName = fileName;
    }

    public String getFilename() {
        return fileName;
    }

    public void setFilename(String filename) {
        this.fileName = filename;
    }

    public List<String> readQuestions() {
        InputStream inputStream = TestReaderImpl.class.getClassLoader().getResourceAsStream("test.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        List<String> questions = new ArrayList<>();

        try {
            while ((line = reader.readLine()) != null) {
                questions.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }
}
