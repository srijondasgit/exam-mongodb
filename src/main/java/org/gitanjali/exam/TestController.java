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

}
