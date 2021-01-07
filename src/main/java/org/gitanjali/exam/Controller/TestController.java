package org.gitanjali.exam.Controller;

import org.gitanjali.exam.Entity.*;
import org.gitanjali.exam.Repository.TestRepository;
import org.gitanjali.exam.Util.JwtUtil;
import org.javers.core.Javers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tests")
public class TestController {

    private final Javers javers;
    private TestRepository testRepository;

    public TestController(Javers javers, TestRepository testRepository) {
        this.javers = javers;
        this.testRepository = testRepository;
    }

    @GetMapping("/all")
    public List<Test> getAll(){
        List<Test> tests = this.testRepository.findAll();

        return tests;
    }


    @PutMapping
    public void insert(@RequestBody Test test){
        this.testRepository.insert(test);
        javers.commit("tests", test);
    }

    @PostMapping
    public void update(@RequestBody Test test){
        this.testRepository.save(test);
        javers.commit("test", test);
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

    @GetMapping("/getSubmission/{id}/{email}")
    public Submission getSubmissionByEmail(@PathVariable("id") String id, @PathVariable("email") String email){
        Test test = this.testRepository.findByIdEquals(id);
        List<Submission> submissions = test.getSubmissions();

        for (Submission s: submissions
             ) {if(s.getStudentEmail().trim().equals(email.trim())){
                  return s;
                }
        }

        return new Submission();
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

    @PostMapping("/upsertAnswers/{id}/{email}")
    public void addAnswersByEmail(@PathVariable("id") String id, @PathVariable("email") String email, @RequestBody Answers answer){
        Test test = this.testRepository.findByIdEquals(id);
        List<Submission> submissions = test.getSubmissions();

        for (Submission s: submissions) {
            if(s.getStudentEmail().trim().equals(email.trim())){
                List<Answers> answers = s.getAnswers();
                boolean found = false;
                for ( Answers a: answers) {
                    if(a.getIndex()==answer.getIndex()){
                        a.setAnswerText(answer.getAnswerText());
                        a.setPointScored(answer.getPointScored());
                        found = true;
                    }
                }

                if(!found){
                    answers.add(answer);
                }

            }
        }

        this.testRepository.save(test);

    }



}
