package org.gitanjali.exam.Controller;

import lombok.AllArgsConstructor;
import org.gitanjali.exam.Entity.Answers;
import org.gitanjali.exam.Entity.Submission;
import org.gitanjali.exam.Entity.Test;
import org.gitanjali.exam.Repository.TestRepository;
import org.javers.core.Javers;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController("/student")
//@RequestMapping("/student")
public class StudentController {

    private final Javers javers;
    private TestRepository testRepository;

    public StudentController(Javers javers, TestRepository testRepository) {
        this.javers = javers;
        this.testRepository = testRepository;
    }

    @PostMapping("/upsertAnswers/{id}/{email}")
    public void addAnswersByEmail(@PathVariable("id") String id, @PathVariable("email") String email, @RequestBody Answers answer){
        Test test = this.testRepository.findByIdEquals(id);
        List<Submission> submissions = test.getSubmissions();

        for (Submission s: submissions) {
            if(s.getStudentEmail().trim().equals(email.trim())){
                List<Answers> answers = s.getAnswers();
                boolean found = false;
                for ( Answers a: answers) {
                    if(a.getIndex()==answer.getIndex()){
                        a.setAnswerText(answer.getAnswerText());
                        a.setPointScored(answer.getPointScored());
                        found = true;
                    }
                }

                if(!found){
                    answers.add(answer);
                }

            }
        }

        this.testRepository.save(test);

    }



}
