package org.gitanjali.exam.Repository;

import org.gitanjali.exam.Entity.Test;
import org.gitanjali.exam.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TestRepository extends MongoRepository<Test,String> {

    Test findByIdEquals(String id);
}
