package org.gitanjali.exam;

import org.gitanjali.exam.Entity.Answers;
import org.gitanjali.exam.Entity.Questions;
import org.gitanjali.exam.Entity.Submission;
import org.gitanjali.exam.Entity.Test;
import org.gitanjali.exam.Repository.TestRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Dbseeder implements CommandLineRunner{

    private TestRepository testRepository;

    public Dbseeder(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Test test1 = new Test(
                "test1",
                "Nes",
                "6",
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


    }
}
