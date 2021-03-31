package org.gitanjali.exam.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.gitanjali.exam.entity.Answers;
import org.gitanjali.exam.entity.Submission;
import org.gitanjali.exam.entity.Test;
import org.gitanjali.exam.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/student")
public class StudentController {

    //@Autowired
    private TestRepository testRepository;

    public StudentController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @PostMapping("/upsertAnswers/test/{testId}")
    public ResponseEntity<String> addAnswersByEmail(@PathVariable("testId") String id, @RequestBody Answers answer) {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }


        Test test = this.testRepository.findByIdEquals(id);
        List<Submission> submissions = test.getSubmissions();

        for (Submission s : submissions) {
            if (s.getStudentEmail().trim().equals(username.trim())) {
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

        return new ResponseEntity<>("Success", HttpStatus.OK) ;
    }

    @GetMapping("/getSubmission/test/{testId}")
    public Submission getSubmissionByEmail(@PathVariable("testId") String id) {

        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Test test = this.testRepository.findByIdEquals(id);
        List<Submission> submissions = test.getSubmissions();

        for (Submission s : submissions
                ) {
            if (s.getStudentEmail().trim().equals(username.trim())) {
                return s;
            }
        }

        return new Submission();
    }


    @PostMapping("/insertUpdateSubmissionHeader/{testId}")
    public String insertSubmissionHeader(@PathVariable("testId") String id, @RequestBody Submission submission) {

        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Test test = this.testRepository.findByIdEquals(id);
        List<Submission> submissions = test.getSubmissions();
        boolean found = false;

        if (CollectionUtils.isEmpty(submissions)){
            Submission s1 = new Submission( submission.getStudentName(), username , submission.getRollNo(),new ArrayList<>() );
            test.setSubmissions(new ArrayList<>());
            test.getSubmissions().add(s1);
            this.testRepository.save(test);

        } else {
            System.out.println("username : "+username);
            for (Submission s : submissions) {
                if (username.trim().equals(s.getStudentEmail().trim())) {
                    s.setStudentName(submission.getStudentName());
                    s.setRollNo(submission.getRollNo());
                    this.testRepository.save(test);
                }
            }

        }

        return "updated successfully";

    }


    @PostMapping("/updateSubmissionProfile/{testId}/{email}")
    public String updateSubmissionByEmail(@PathVariable("testId") String id, @PathVariable("email") String email, @RequestBody Submission submission) {

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
