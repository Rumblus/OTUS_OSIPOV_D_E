package ru.otus.dao;

import org.springframework.stereotype.Component;
import ru.otus.config.AppProps;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestReaderImpl implements TestReader {
    private final String fileName;

    private final AppProps props;

    TestReaderImpl(AppProps props) {
        this.props = props;
        this.fileName = props.getTestFileName();
    }

    @Override
    public List<String> readQuestions() {
        InputStream inputStream = TestReaderImpl.class.getClassLoader().getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        List<String> strings = new ArrayList<>();

        try {
            while ((line = reader.readLine()) != null) {
                strings.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strings;
    }
}
