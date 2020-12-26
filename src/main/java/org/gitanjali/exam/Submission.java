package org.gitanjali.exam;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Submission")
public class Submission {

    private String studentName;
    private String studentEmail;
    private String rollNo;
    private List<String> answers;


    public Submission(String studentName, String studentEmail, String rollNo, List<String> answers) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.rollNo = rollNo;
        this.answers = answers;
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

    public List<String> getAnswers() {
        return answers;
    }

}
