package org.gitanjali.exam.Controller;

import java.util.List;

import org.gitanjali.exam.Entity.Questions;
import org.gitanjali.exam.Entity.Submission;
import org.gitanjali.exam.Entity.Test;
import org.gitanjali.exam.Repository.TestRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tests")
public class TeacherController {

    private TestRepository testRepository;

    public TeacherController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @PutMapping
    public void insert(@RequestBody Test test){
        this.testRepository.insert(test);
    }

    @PostMapping
    public void update(@RequestBody Test test){
        this.testRepository.save(test);
    }

    @GetMapping("/{id}")
    public Test getById(@PathVariable("id") String id){
        Test test = this.testRepository.findByIdEquals(id);

        return test;
    }

    @PostMapping("/addQuestion/{id}")
    public void setById(@PathVariable("id") String id, @RequestBody Questions questions){

        Test test = this.testRepository.findByIdEquals(id);
        List<Questions> questionsList = test.getQuestions();
        boolean found = false;

        for (Questions q: questionsList) {
            if(q.getIndex()==questions.getIndex()){
                q.setQuestions(questions.getQuestions());
                q.setScore(questions.getScore());
                found = true;
            }
        }

        if(found == false)
            questionsList.add(questions);

        this.testRepository.save(test);
    }

    @DeleteMapping("/removeQuestion/{id}")
    public void removeById(@PathVariable("id") String id, @RequestBody int index){
        Test test = this.testRepository.findByIdEquals(id);

        int length = test.getQLength();
        if ( index < length ){
            test.removeQuestion(index);
        }

        this.testRepository.save(test);
    }

    @GetMapping("/getSubmissionByIndex/{id}/{index}")
    public Submission getSubmissionByIndex(@PathVariable("id") String id, @PathVariable("index") int index){
        Test test = this.testRepository.findByIdEquals(id);
        Submission submission = new Submission();

        int length = test.getSLength();
        if ( index < length ){
            submission = test.getSubmissions().get(index);
        }

        return submission;
    }



    @PostMapping("/updateSubmission/{id}/{email}")
    public void updateSubmissionByEmail(@PathVariable("id") String id, @PathVariable("email") String email, @RequestBody Submission submission){

        Test test = this.testRepository.findByIdEquals(id);
        List<Submission> submissions = test.getSubmissions();
        boolean found = false;

        for (Submission s: submissions) {
            if(s.getStudentEmail().trim().equals(email.trim())){
                s.setStudentName(submission.getStudentName());
                s.setRollNo(submission.getRollNo());
                found = true;
            }
        }

        if(!found){
            submission.setStudentEmail(email.trim());
            submissions.add(submission);
        }

        this.testRepository.save(test);
    }



}
