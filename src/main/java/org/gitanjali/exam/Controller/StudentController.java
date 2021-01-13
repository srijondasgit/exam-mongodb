package org.gitanjali.exam.Controller;

import java.util.List;

import org.gitanjali.exam.Entity.Answers;
import org.gitanjali.exam.Entity.Submission;
import org.gitanjali.exam.Entity.Test;
import org.gitanjali.exam.Repository.TestRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/student")
public class StudentController {

    private TestRepository testRepository;

    public StudentController(TestRepository testRepository) {
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
