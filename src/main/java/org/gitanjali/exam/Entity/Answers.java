package org.gitanjali.exam.Entity;


public class Answers {
    private int index;
    private String answer;
    private int pointScored;

    public Answers() {
    }

    public Answers(int index, String answer, int pointScored) {
        this.index = index;
        this.answer = answer;
        this.pointScored = pointScored;
    }

    public int getIndex() {
        return index;
    }

    public String getAnswer() {
        return answer;
    }

    public int getPointScored() {
        return pointScored;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setPointScored(int pointScored) {
        this.pointScored = pointScored;
    }



}
