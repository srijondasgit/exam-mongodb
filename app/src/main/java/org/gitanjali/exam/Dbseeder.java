package org.gitanjali.exam;

import org.gitanjali.exam.entity.*;
import org.gitanjali.exam.repository.AnswersRepository;
import org.gitanjali.exam.repository.QuestionsRepository;
import org.gitanjali.exam.repository.TestRepository;
import org.gitanjali.exam.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Dbseeder implements CommandLineRunner {

    private TestRepository testRepository;
    private UserRepository userRepository;
    private QuestionsRepository questionsRepository;
    private AnswersRepository answersRepository;

    public Dbseeder(TestRepository testRepository, UserRepository userRepository, QuestionsRepository questionsRepository, AnswersRepository answersRepository) {
        this.testRepository = testRepository;
        this.userRepository = userRepository;
        this.questionsRepository = questionsRepository;
        this.answersRepository = answersRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        Test test1 = new Test(
                "test1",
                "Nes",
                "6",
                "test@test.com",
                Arrays.asList(
                        new Questions(1, "Q1 : this question", 5, "", "", "", "",""),
                        new Questions(2, "Q2 : that question", 10, "", "", "", "",""),
                        new Questions(3, "Q3 : that question", 15, "", "", "", "","")
                ),
                Arrays.asList(
                        new Submission("Sam", "sam@gmail.com", "123",
                                Arrays.asList(
                                        new Answers(1, "A1 : this answer", 5, "this is the question", 1, 1),
                                        new Answers(2, "A2 : that answer", 10, "this is the question", 1, 1)
                                ),0

                        )
                ),0

        );


        if(this.userRepository.findByEmail("test@test.com")==null){
            User user = new User("user1", "pwd", "test@test.com", "TEACHER");
            User user1 = new User("user2", "pwd", "test1@test.com", "STUDENT");

            List<User> users = Arrays.asList(user, user1);
            this.userRepository.saveAll(users);
        }


        if(this.testRepository.findAllByOwnerEquals("test@test.com")==null){
            List<Test> tests = Arrays.asList(test1);
            this.testRepository.saveAll(tests);
            }


    }
}
