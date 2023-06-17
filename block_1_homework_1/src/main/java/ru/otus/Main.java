package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/application-context.xml");

        TestManager testManager = context.getBean(ru.otus.TestManager.class);
        testManager.run();
    }
}