package org.gitanjali.exam;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Test")
public class Test {

    @Id
    private String id;
    private String testName;
    private String schoolName;
    private String className;
    private List<String> questions;
    private List<Submission> submissions;

    protected Test(){
        this.questions = new ArrayList<>();
        this.submissions = new ArrayList<>();
    }


    public Test(String testName, String schoolName, String className, List<String> questions, List<Submission> submissions) {
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

    public List<String> getQuestions() {
        return questions;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

}
