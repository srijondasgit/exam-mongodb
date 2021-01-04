package org.gitanjali.exam.Entity;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "Test")
public class Test {

    @Id
    private String id;
    private String testName;
    private String schoolName;
    private String className;

    private List<Questions> questions;
    private List<Submission> submissions;
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    protected Test(){
        this.questions = new ArrayList<>();
        this.submissions = new ArrayList<>();
    }

    public Test(String testName, String schoolName, String className, List<Questions> questions, List<Submission> submissions) {
        this.testName = testName;
        this.schoolName = schoolName;
        this.className = className;
        this.questions = questions;
        this.submissions = submissions;
    }

    public String getId() {
        return id;
    }

    public String getTestName() {
        return testName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getClassName() {
        return className;
    }

    public List<Questions> getQuestions() {
        return questions;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void addQuestions(Questions questions) {
        this.questions.add(questions);
    }

    public int getQLength(){
        return this.questions.size();
    }

    public void removeQuestion(int index){
        this.questions.remove(index);
    }

    public int getSLength(){
        return this.submissions.size();
    }

}
