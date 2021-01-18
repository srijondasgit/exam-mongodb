package org.gitanjali.exam;

import org.gitanjali.exam.Entity.*;
import org.gitanjali.exam.Repository.TestRepository;
import org.gitanjali.exam.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Dbseeder implements CommandLineRunner{

    private TestRepository testRepository;
    private UserRepository userRepository;

    public Dbseeder(TestRepository testRepository, UserRepository userRepository) {
        this.testRepository = testRepository;
        this.userRepository = userRepository;
    }

    //public Dbseeder(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }


    @Override
    public void run(String... args) throws Exception {

        Test test1 = new Test(
                "test1",
                "Nes",
                "6",
                "test@test.com",
                Arrays.asList(
                        new Questions(1,"Q1 : this question", 5),
                        new Questions (2, "Q2 : that question", 10)
                ),
                Arrays.asList(
                        new Submission("Sam","sam@gmail.com", "123",
                                Arrays.asList(
                                        new Answers(1,"A1 : this answer",5),
                                        new Answers (2, "A2 : that answer", 10)
                                )

                        )
                )

        );

        this.testRepository.deleteAll();

        List<Test> tests = Arrays.asList(test1);
        this.testRepository.saveAll(tests);

        this.userRepository.deleteAll();
        User user = new User ("user1", "pwd", "test@test.com", "TEACHER");
        User user1 = new User ("user2", "pwd", "test1@test.com", "STUDENT");


        List<User> users = Arrays.asList(user, user1);
        this.userRepository.saveAll(users);


    }
}
