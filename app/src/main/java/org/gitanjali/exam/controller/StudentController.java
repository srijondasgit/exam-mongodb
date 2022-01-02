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

    @PostMapping("/testId/{testId}/questionId/{questionId}/upsertAnswersUpdated")
    public ResponseEntity<String> upsertAnswersUpdated(@PathVariable("testId") String testId, @PathVariable("questionId") String questionId, @RequestBody Answers answer) {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }


        Test test = this.testRepository.findByIdEquals(testId);
        if(test == null) return new ResponseEntity<>("Test id not found", HttpStatus.OK) ;
        List<Submission> submissions = test.getSubmissions();

        boolean submissionFound = false;
        for (Submission s : submissions) {
            if (s.getStudentEmail().trim().equals(username.trim())) {
                List<Answers> answers = s.getAnswers();
                submissionFound = true;
                boolean found = false;

                for (Answers a : answers) {
                    // code to update
                    if (questionId.equals(answer.getCopyQuestionId())) {
                        a.setAnswerText(answer.getAnswerText());
                        found = true;
                    }
                }

                // code to insert
                if (!found) {
                    answer.setCopyQuestionId(questionId);
                    this.answersRepository.save(answer);
                    answers.add(answer);
                    answer.setId(answer.getId());
                    this.testRepository.save(test);
                }
            }
        }
        if(!submissionFound) return new ResponseEntity<>("No submission header found for user", HttpStatus.OK) ;

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

        sub.setStudentEmail("No submission found");
        return sub;
    }


    @PostMapping("/testId/{testId}/insertUpdateSubmissionHeader")
    public ResponseEntity<String> insertSubmissionHeader(@PathVariable("testId") String id, @RequestBody Submission submission) {

        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Test test = this.testRepository.findByIdEquals(id);
        if(test == null) return new ResponseEntity<>("No data found", HttpStatus.NOT_FOUND);

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

        //return updatedId;
        if(updatedId.equals("Something went wrong here")) return new ResponseEntity<>(updatedId, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(updatedId, HttpStatus.OK);

    }

    @GetMapping("/testId/{testId}/questionId/{questionId}/getAnswer")
    public Answers getAnswer(@PathVariable("testId") String testId, @PathVariable("questionId") String questionId) {

        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Submission sub = new Submission();
        Answers ans = new Answers();
        //sub.setStudentEmail("Test id not found");

        Test test = this.testRepository.findByIdEquals(testId);
        if(test == null) return ans;
        List<Submission> submissions = test.getSubmissions();

        for (Submission s : submissions) {
            if (s.getStudentEmail().trim().equals(username.trim())){
                List<Answers> answers = s.getAnswers();
                for ( Answers a : answers){
                    if(a.getCopyQuestionId().equals(questionId)){
                        return a;
                    }
                }
            }
        }

        ans.setAnswerText("No Answer found");
        return ans;
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
