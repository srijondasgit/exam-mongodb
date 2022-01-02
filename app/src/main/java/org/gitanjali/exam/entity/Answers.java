package org.gitanjali.exam.entity;


import org.springframework.data.annotation.Id;

public class Answers {
    @Id
    private String id;
    private int index;
    private String answerText;
    private int pointScored;
    private String copyQuestionText;
    private int copyScoreQuestion;
    private int copyIndexQuestion;
    private String copyQuestionId;

    public Answers() {
    }

    public String getCopyQuestionId() {
        return copyQuestionId;
    }

    public void setCopyQuestionId(String copyQuestionId) {
        this.copyQuestionId = copyQuestionId;
    }

    public Answers(int index, String answerText, int pointScored, String copyQuestionText, int copyScoreQuestion, int copyIndexQuestion, String copyQuestionId) {
        this.index = index;
        this.answerText = answerText;
        this.pointScored = pointScored;
        this.copyQuestionText = copyQuestionText;
        this.copyScoreQuestion = copyScoreQuestion;
        this.copyIndexQuestion = copyIndexQuestion;
        this.copyQuestionId = copyQuestionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Answers setAnswerText(String answerText) {
        this.answerText = answerText;
        return null;
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


}
