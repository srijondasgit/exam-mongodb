package org.gitanjali.exam.entity;

import java.util.ArrayList;
import java.util.List;

public class Submission {

    private String studentName;
    private String studentEmail;
    //@Indexed(unique = true)
    private String rollNo;
    private List<Answers> answers;

    public Submission(String studentName, String studentEmail, String rollNo, List<Answers> answers) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.rollNo = rollNo;
        this.answers = answers;

    }

    public Submission() {
        this.answers = new ArrayList<>();
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getRollNo() {
        return rollNo;
    }

    public List<Answers> getAnswers() {
        return answers;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }
}
