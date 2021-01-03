package org.gitanjali.exam;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<Test,String> {

    Test findByIdEquals(String id);
}
