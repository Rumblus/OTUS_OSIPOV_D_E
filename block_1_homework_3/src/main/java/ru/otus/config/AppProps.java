package ru.otus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public class AppProps {
    private final String testFileName;

    private final int passingScore;

    private final Locale locale;

    public AppProps(String testFileName, int passingScore, Locale locale) {
        this.testFileName = testFileName;
        this.passingScore = passingScore;
        this.locale = locale;
    }

    public String getTestFileName() {
        return testFileName;
    }

    public int getPassingScore() {
        return passingScore;
    }

    public Locale getLocale() {
        return locale;
    }
}
