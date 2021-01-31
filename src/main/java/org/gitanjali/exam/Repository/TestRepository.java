package org.gitanjali.exam.Repository;

import org.gitanjali.exam.Entity.Test;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@JaversSpringDataAuditable
public interface TestRepository extends MongoRepository<Test,String> {

    Test findByIdEquals(String id);
    List<Test> findAllByOwnerEquals(String owner);
}
