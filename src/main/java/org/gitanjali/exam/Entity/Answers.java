package org.gitanjali.exam.Entity;


public class Answers {
    private int index;
    private String answerText;
    private int pointScored;

    public Answers() {
    }

    public Answers(int index, String answerText, int pointScored) {
        this.index = index;
        this.answerText = answerText;
        this.pointScored = pointScored;
    }

    public int getIndex() {
        return index;
    }

    public String getAnswerText() {
        return answerText;
    }

    public int getPointScored() {
        return pointScored;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public void setPointScored(int pointScored) {
        this.pointScored = pointScored;
    }



}
