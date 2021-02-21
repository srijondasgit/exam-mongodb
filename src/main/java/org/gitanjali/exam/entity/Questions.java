package org.gitanjali.exam.entity;

public class Questions {

    private int index;
    private String questionText;
    private int score;

    protected Questions() {
    }

    public Questions(int index, String questionText, int score) {
        this.index = index;
        this.questionText = questionText;
        this.score = score;
    }

    public int getIndex() {
        return index;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getScore() {
        return score;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
