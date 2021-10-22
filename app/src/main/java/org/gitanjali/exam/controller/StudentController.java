package org.gitanjali.exam.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.gitanjali.exam.entity.Answers;
import org.gitanjali.exam.entity.Submission;
import org.gitanjali.exam.entity.Test;
import org.gitanjali.exam.repository.AnswersRepository;
import org.gitanjali.exam.repository.SubmissionRepository;
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
    private SubmissionRepository submissionRepository;
    private AnswersRepository answersRepository;

    public StudentController(TestRepository testRepository, SubmissionRepository submissionRepository, AnswersRepository answersRepository) {
        this.testRepository = testRepository;
        this.submissionRepository = submissionRepository;
        this.answersRepository = answersRepository;
    }

    @PostMapping("/testId/{testId}/upsertAnswers")
    public ResponseEntity<String> addAnswersByEmail(@PathVariable("testId") String id, @RequestBody Answers answer) {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }


        Test test = this.testRepository.findByIdEquals(id);
        if(test == null) return new ResponseEntity<>("Test id not found", HttpStatus.OK) ;
        List<Submission> submissions = test.getSubmissions();

        for (Submission s : submissions) {
            if (s.getStudentEmail().trim().equals(username.trim())) {
                List<Answers> answers = s.getAnswers();
                boolean found = false;

                for (Answers a : answers) {
                    if (a.getId().equals(answer.getId())) {
                        a.setAnswerText(answer.getAnswerText());
                        found = true;
                    }
                }

                if (!found) {

                    this.answersRepository.save(answer);
                    answers.add(answer);
                    answer.setId(answer.getId());
                    this.testRepository.save(test);
                }
            }
        }

        this.testRepository.save(test);

        return new ResponseEntity<>("Success", HttpStatus.OK) ;
    }

    @GetMapping("/testId/{testId}/getSubmission")
    public Submission getSubmissionByEmail(@PathVariable("testId") String id) {

        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Submission sub = new Submission();
        sub.setStudentEmail("Test id not found");

        Test test = this.testRepository.findByIdEquals(id);
        if(test == null) return sub;
        List<Submission> submissions = test.getSubmissions();

        for (Submission s : submissions
                ) {
            if (s.getStudentEmail().trim().equals(username.trim())) {
                return s;
            }
        }

        sub.setStudentEmail("Error");
        return sub;
    }


    @PostMapping("/testId/{testId}/insertUpdateSubmissionHeader")
    public String insertSubmissionHeader(@PathVariable("testId") String id, @RequestBody Submission submission) {

        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Test test = this.testRepository.findByIdEquals(id);
        if(test == null) return "Test id not found";

        List<Submission> submissions = test.getSubmissions();
        boolean found = false;
        String updatedId = "Something went wrong here";
        if (CollectionUtils.isEmpty(submissions)){
            Submission s1 = new Submission( submission.getStudentName(), username , submission.getRollNo(),new ArrayList<>(), 0);
            test.setSubmissions(new ArrayList<>());

            this.submissionRepository.save(s1);
            s1.setId(s1.getId());
            test.getSubmissions().add(s1);
            updatedId = s1.getId();
            this.testRepository.save(test);


        } else {
            System.out.println("username : "+username);
            for (Submission s : submissions) {
                if (username.trim().equals(s.getStudentEmail().trim())) {
                    s.setStudentName(submission.getStudentName());
                    s.setRollNo(submission.getRollNo());
                    this.testRepository.save(test);
                    updatedId = s.getId();
                }
            }

        }

        return updatedId;

    }


//    @PostMapping("/testId/{testId}/updateSubmissionProfile")
//    public String updateSubmissionByEmail(@PathVariable("testId") String id, @RequestBody Submission submission) {
//        String username;
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            username = ((UserDetails) principal).getUsername();
//        } else {
//            username = principal.toString();
//        }
//
//        System.out.println("username is : " +username);
//
//        String email = username;
//
//        Test test = this.testRepository.findByIdEquals(id);
//        List<Submission> submissions = test.getSubmissions();
//        boolean found = false;
//
//        for (Submission s : submissions) {
//            if (s.getStudentEmail().trim().equals(email.trim())) {
//                s.setStudentName(submission.getStudentName());
//                s.setRollNo(submission.getRollNo());
//                this.testRepository.save(test);
//                found = true;
//            }
//        }
//
//        if (!found) {
//            return ("Student email not found");
//        } else {
//            return ("Student is updated");
//        }
//
//    }
//
}
