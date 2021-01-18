package org.gitanjali.exam.Controller;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gitanjali.exam.Entity.AuthRequest;
import org.gitanjali.exam.Entity.Test;
import org.gitanjali.exam.Repository.TestRepository;
import org.gitanjali.exam.Util.JwtUtil;
import org.javers.core.Javers;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Test> getAll(){
        List<Test> tests = this.testRepository.findAll();

        return tests;
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("invalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }

    @GetMapping("/audit/test")
    public String getTestChanges(@RequestParam(required = false) String user) {
    	QueryBuilder query = QueryBuilder.byClass(Test.class);
    	if(StringUtils.isNotBlank(user)) {
    		query.byAuthor(user);
    	}
    	query.limit(10);
    	query.withChildValueObjects();
    	
        List<Shadow<Test>> shadows = javers.findShadows(query.build());
        return javers.getJsonConverter().toJson(shadows);
    }

}
