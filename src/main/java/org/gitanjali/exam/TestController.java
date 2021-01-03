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

    @PostMapping("/removeQuestion/{id}")
    public void removeById(@PathVariable("id") String id, @RequestBody int qNumber){
        Test test = this.testRepository.findByIdEquals(id);

        int length = test.getQLength();
        if( qNumber <= length ){
            test.removeQuestion(qNumber);
        }

        this.testRepository.save(test);
    }

}
