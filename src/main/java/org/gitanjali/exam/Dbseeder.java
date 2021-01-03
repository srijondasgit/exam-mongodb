package org.gitanjali.exam;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
                        new Questions("Q1 : this question", 5),
                        new Questions ("Q2 : that question", 10)
                ),
                Arrays.asList(
                        new Submission("Sam","sam@gmail.com", "123",
                                Arrays.asList(
                                        new String("A1 : this answer"),
                                        new String ("A2 : that answer")
                                )

                        )
                )

        );

        this.testRepository.deleteAll();

        List<Test> tests = Arrays.asList(test1);
        this.testRepository.saveAll(tests);


    }
}
