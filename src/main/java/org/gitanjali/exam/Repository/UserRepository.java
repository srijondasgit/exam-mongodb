package org.gitanjali.exam.Repository;


import org.gitanjali.exam.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User findByUserName(String username);
}
