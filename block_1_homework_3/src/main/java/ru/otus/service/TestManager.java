package ru.otus.service;

import org.springframework.boot.CommandLineRunner;

public interface TestManager extends CommandLineRunner {
    void run(String... args);

    boolean checkResults();
}
