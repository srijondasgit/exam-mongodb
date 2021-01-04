package org.gitanjali.exam.Entity;

import java.util.ArrayList;

public class Questions {

    private int index;
    private String questions;
    private int score;

    protected Questions(){
    }

    public Questions(int index, String questions, int score) {
        this.index = index;
        this.questions = questions;
        this.score = score;
    }

    public int getIndex() {
        return index;
    }

    public String getQuestions() {
        return questions;
    }

    public int getScore() {
        return score;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
