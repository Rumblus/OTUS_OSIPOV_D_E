package ru.otus.domain;

import java.util.List;

public class Question {
    private String questionText;

    private int correctAnswerIdx;

    private List<String> answers;

    public Question() {
    }

    public Question(String questionText, int correctAnswerIdx, List<String> answers) {
        this.questionText = questionText;
        this.correctAnswerIdx = correctAnswerIdx;
        this.answers = answers;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getCorrectAnswerIdx() {
        return correctAnswerIdx;
    }

    public void setCorrectAnswerIdx(int correctAnswerIdx) {
        this.correctAnswerIdx = correctAnswerIdx;
    }
}
