package org.gitanjali.exam.Controller;


import org.gitanjali.exam.Entity.AuthRequest;
import org.gitanjali.exam.Entity.Test;
import org.gitanjali.exam.Repository.TestRepository;
import org.gitanjali.exam.Util.JwtUtil;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.json.JsonConverter;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/test")
    public String getTestChanges() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Test.class)
                .withNewObjectChanges();

        Changes changes = javers.findChanges(jqlQuery.build());

        return "<pre>" + changes.prettyPrint() + "</pre>";
    }

    @RequestMapping("/test/{login}/snapshots")
    public String getTestSnapshots(@PathVariable String login) {
        QueryBuilder jqlQuery = QueryBuilder.byInstanceId(login, Test.class);

        List<CdoSnapshot> changes = javers.findSnapshots(jqlQuery.build());

        changes.sort((o1, o2) -> -1 * o1.getCommitMetadata().getCommitDate().compareTo(o2.getCommitMetadata().getCommitDate()));

        JsonConverter jsonConverter = javers.getJsonConverter();

        return jsonConverter.toJson(changes);
    }


}
