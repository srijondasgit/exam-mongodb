package org.gitanjali.exam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/tests")
public class TestController {

    private TestRepository testRepository;

    public TestController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @GetMapping("/all")
    public List<Test> getAll(){
        List<Test> tests = this.testRepository.findAll();

        return tests;
    }

}
