package org.gitanjali.exam.repository;

import org.gitanjali.exam.entity.Answers;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

@JaversSpringDataAuditable
public interface AnswersRepository extends MongoRepository<Answers, String> {

    Answers findByIdEquals(String id);


}