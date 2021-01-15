package org.gitanjali.exam.Controller;


import java.util.ArrayList;
import java.util.List;

import org.gitanjali.exam.Entity.Test;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.json.JsonConverter;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String getTestChanges() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Test.class)
                .withNewObjectChanges();

        Changes changes = javers.findChanges(jqlQuery.build());

        return "<pre>" + changes.prettyPrint() + "</pre>";
    }

    @RequestMapping("/test/{id}")
    public String getTestChanges(@PathVariable Integer id) {
        QueryBuilder jqlQuery = QueryBuilder.byInstanceId(id, Test.class)
                .withNewObjectChanges();

        Changes changes = javers.findChanges(jqlQuery.build());

        return "<pre>" + changes.prettyPrint() + "</pre>";
    }

    @RequestMapping("/test/snapshots")
    public String getTestSnapshots() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Test.class);

        List<CdoSnapshot> changes = new ArrayList(javers.findSnapshots(jqlQuery.build()));

        changes.sort((o1, o2) -> -1 * o1.getCommitMetadata().getCommitDate().compareTo(o2.getCommitMetadata().getCommitDate()));

        JsonConverter jsonConverter = javers.getJsonConverter();

        return jsonConverter.toJson(changes);
    }

    @RequestMapping("/person/{login}/snapshots")
    public String getTestSnapshots(@PathVariable String login) {
        QueryBuilder jqlQuery = QueryBuilder.byInstanceId(login, Test.class);

        List<CdoSnapshot> changes = javers.findSnapshots(jqlQuery.build());

        changes.sort((o1, o2) -> -1 * o1.getCommitMetadata().getCommitDate().compareTo(o2.getCommitMetadata().getCommitDate()));

        JsonConverter jsonConverter = javers.getJsonConverter();

        return jsonConverter.toJson(changes);
    }

}
