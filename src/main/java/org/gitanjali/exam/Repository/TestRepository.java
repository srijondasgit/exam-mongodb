package org.gitanjali.exam.Repository;

import org.gitanjali.exam.Entity.Test;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<Test,String> {

    Test findByIdEquals(String id);

}
