package org.gitanjali.exam.Controller;

import org.gitanjali.exam.Entity.Test;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/audit")
public class AuditController {

    private final Javers javers;

    @Autowired
    public AuditController(Javers javers) {
        this.javers = javers;
    }

    @RequestMapping("/test")
    public String getPersonChanges() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Test.class);

        Changes tests = javers.findChanges(jqlQuery.build());

        return javers.getJsonConverter().toJson(tests);
    }
}
