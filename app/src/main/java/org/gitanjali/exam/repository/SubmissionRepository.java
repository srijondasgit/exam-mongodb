package org.gitanjali.exam.repository;

import org.gitanjali.exam.entity.Questions;
import org.gitanjali.exam.entity.Submission;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


@JaversSpringDataAuditable
public interface SubmissionRepository extends MongoRepository<Submission, String> {

    Submission findByIdEquals(String id);


}