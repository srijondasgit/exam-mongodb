package org.gitanjali.exam.controller;


import org.apache.commons.lang3.StringUtils;
import org.gitanjali.exam.entity.AuthRequest;
import org.gitanjali.exam.entity.Submission;
import org.gitanjali.exam.entity.Test;
import org.gitanjali.exam.repository.TestRepository;
import org.gitanjali.exam.util.JwtUtil;
import org.javers.core.Javers;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    private TestRepository testRepository;

    @Autowired
    private Javers javers;

    public UserController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @GetMapping("/allTests")
    public List<Test> getAll() {
        List<Test> tests = this.testRepository.findAll();
        List<Test> testsClean = new LinkedList<Test>();
        Submission s = new Submission("Data is hidden");
        //test.setSubmissions(Arrays.asList(s));
        for (Test t : tests) {
            t.setSubmissions(Arrays.asList(s));
            testsClean.add(t);
        }

        return testsClean;
    }

    @GetMapping("/testId/{testId}/getTest")
    public Test getById(@PathVariable("testId") String id) {
        Test test = this.testRepository.findByIdEquals(id);
        Submission s = new Submission("Data is hidden");
        test.setSubmissions(Arrays.asList(s));
        return test;
    }

    @GetMapping("/allTestsIds")
    public List<String> getAllTestId() {
        List<String> testIds = new ArrayList<>();
        List<Test> tests = this.testRepository.findAll();

        for (Test t : tests) {
            testIds.add(t.getId());
        }

        return testIds;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())

            );
            return new ResponseEntity<>(jwtUtil.generateToken(authRequest.getUserName()), HttpStatus.OK) ;

        } catch (Exception ex) {
            //throw new Exception("invalid username/password");
            return new ResponseEntity<>("Invalid username/password", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/audit/test")
    public String getTestChanges(@RequestParam(required = false) String user) {
        QueryBuilder query = QueryBuilder.byClass(Test.class);
        if (StringUtils.isNotBlank(user)) {
            query.byAuthor(user);
        }
        query.limit(10);
        query.withChildValueObjects();

        List<Shadow<Test>> shadows = javers.findShadows(query.build());
        return javers.getJsonConverter().toJson(shadows);
    }

}
