package org.gitanjali.exam.repository;


import org.gitanjali.exam.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUserName(String username);

    User findByEmail(String username);

}
