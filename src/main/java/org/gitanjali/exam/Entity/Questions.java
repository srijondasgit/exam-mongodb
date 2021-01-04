package org.gitanjali.exam.Entity;

import java.util.ArrayList;

public class Questions {

    private String questions;
    private int score;


    protected Questions(){
    }

    public Questions(String questions, int score) {
        this.questions = questions;
        this.score = score;
    }

    public String getQuestions() {
        return questions;
    }

    public int getScore() {
        return score;
    }


}
