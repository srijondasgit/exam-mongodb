package org.gitanjali.exam.Repository;

import org.gitanjali.exam.Entity.Test;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

@JaversSpringDataAuditable
public interface TestRepository extends MongoRepository<Test,String> {

    Test findByIdEquals(String id);
}
