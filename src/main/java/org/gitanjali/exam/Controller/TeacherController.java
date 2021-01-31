package org.gitanjali.exam.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gitanjali.exam.Entity.Questions;
import org.gitanjali.exam.Entity.Submission;
import org.gitanjali.exam.Entity.Test;
import org.gitanjali.exam.Repository.TestRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/teacher")
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

    @PostMapping("/addTest")
    public Test addTest(@RequestBody  Test test){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        Test test1 = new Test(test.getTestName(),test.getSchoolName(),
                test.getClassName(), username, Arrays.asList(),Arrays.asList());

        List<Test> tests = Arrays.asList(test1);
        this.testRepository.saveAll(tests);

        return test1;
    }

    @GetMapping("/getOwnersTestsList")
    public List<String> getTestsList(){
        String username;
        List<String> testIds = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        List<Test> tests = this.testRepository.findAllByOwnerEquals(username);

        for (Test t: tests
             ) {testIds.add(t.getId());

        }

        return testIds;
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
