package org.gitanjali.exam;

import org.javers.core.Javers;
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
        javers.commit("test", test);

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

        test.addQuestions(questions);

        this.testRepository.save(test);
    }

    @DeleteMapping("/removeQuestion/{id}")
    public void removeById(@PathVariable("id") String id, @RequestBody int index){
        Test test = this.testRepository.findByIdEquals(id);

        int length = test.getQLength();
        if( index < length ){
            test.removeQuestion(index);
        }

        this.testRepository.save(test);
    }

    @GetMapping("/getSubmission/{id}/{index}")
    public Submission getSubmissionByIndex(@PathVariable("id") String id, @PathVariable("index") int index){
        Test test = this.testRepository.findByIdEquals(id);
        Submission submission = new Submission();

        int length = test.getSLength();
        if( index < length ){
            submission = test.getSubmissions().get(index);
        }

        return submission;
    }

    @GetMapping("/getSubmissionByEmail/{id}/{email}")
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
}
