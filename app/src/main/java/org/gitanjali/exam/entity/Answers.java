package org.gitanjali.exam.entity;


public class Answers {
    private int index;
    private String answerText;
    private int pointScored;
    private String copyQuestionText;
    private int copyScoreQuestion;
    private int copyIndexQuestion;

    public Answers() {
    }

    public Answers(int index, String answerText, int pointScored, String copyQuestionText, int copyScoreQuestion, int copyIndexQuestion) {
        this.index = index;
        this.answerText = answerText;
        this.pointScored = pointScored;
        this.copyQuestionText = copyQuestionText;
        this.copyScoreQuestion = copyScoreQuestion;
        this.copyIndexQuestion = copyIndexQuestion;
    }



    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getPointScored() {
        return pointScored;
    }

    public void setPointScored(int pointScored) {
        this.pointScored = pointScored;
    }

    public String getCopyQuestionText() {
        return copyQuestionText;
    }

    public void setCopyQuestionText(String copyQuestionText) {
        this.copyQuestionText = copyQuestionText;
    }

    public int getCopyScoreQuestion() {
        return copyScoreQuestion;
    }

    public void setCopyScoreQuestion(int copyScoreQuestion) {
        this.copyScoreQuestion = copyScoreQuestion;
    }

    public int getCopyIndexQuestion() {
        return copyIndexQuestion;
    }

    public void setCopyIndexQuestion(int copyIndexQuestion) {
        this.copyIndexQuestion = copyIndexQuestion;
    }
//    public int getIndex() {
//        return index;
//    }
//
//    public String getAnswerText() {
//        return answerText;
//    }
//
//    public int getPointScored() {
//        return pointScored;
//    }
//
//    public void setIndex(int index) {
//        this.index = index;
//    }
//
//    public void setAnswerText(String answerText) {
//        this.answerText = answerText;
//    }
//
//    public void setPointScored(int pointScored) {
//        this.pointScored = pointScored;
//    }


}
