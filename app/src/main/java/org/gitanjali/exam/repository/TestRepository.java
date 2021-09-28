package org.gitanjali.exam.repository;

import org.gitanjali.exam.entity.Test;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@JaversSpringDataAuditable
public interface TestRepository extends MongoRepository<Test, String> {

    Test findByIdEquals(String id);


    List<Test> findAllByOwnerEquals(String owner);
}
