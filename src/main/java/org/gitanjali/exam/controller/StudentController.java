package org.gitanjali.exam.controller;

import org.gitanjali.exam.entity.Answers;
import org.gitanjali.exam.entity.Submission;
import org.gitanjali.exam.entity.Test;
import org.gitanjali.exam.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/student")
public class StudentController {

    //@Autowired
    private TestRepository testRepository;

    public StudentController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @PostMapping("/upsertAnswers/test/{testId}/email/{email}")
    public void addAnswersByEmail(@PathVariable("testId") String id, @PathVariable("email") String email, @RequestBody Answers answer) {
        Test test = this.testRepository.findByIdEquals(id);
        List<Submission> submissions = test.getSubmissions();

        for (Submission s : submissions) {
            if (s.getStudentEmail().trim().equals(email.trim())) {
                List<Answers> answers = s.getAnswers();
                boolean found = false;
                for (Answers a : answers) {
                    if (a.getIndex() == answer.getIndex()) {
                        a.setAnswerText(answer.getAnswerText());
                        found = true;
                    }
                }

                if (!found) {
                    answers.add(answer);
                }
            }
        }

        this.testRepository.save(test);

    }

    @GetMapping("/getSubmission/test/{testId}/email/{email}")
    public Submission getSubmissionByEmail(@PathVariable("id") String id, @PathVariable("email") String email) {

        Test test = this.testRepository.findByIdEquals(id);
        List<Submission> submissions = test.getSubmissions();

        for (Submission s : submissions
                ) {
            if (s.getStudentEmail().trim().equals(email.trim())) {
                return s;
            }
        }

        return new Submission();
    }

    @PostMapping("/updateSubmissionProfile/{id}/{email}")
    public String updateSubmissionByEmail(@PathVariable("id") String id, @PathVariable("email") String email, @RequestBody Submission submission) {

        Test test = this.testRepository.findByIdEquals(id);
        List<Submission> submissions = test.getSubmissions();
        boolean found = false;

        for (Submission s : submissions) {
            if (s.getStudentEmail().trim().equals(email.trim())) {
                s.setStudentName(submission.getStudentName());
                s.setRollNo(submission.getRollNo());
                this.testRepository.save(test);
                found = true;
            }
        }

        if (!found) {
            return ("Student email not found");
        } else {
            return ("Student is updated");
        }

    }

}
