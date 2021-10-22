package org.gitanjali.exam.controller;

import org.gitanjali.exam.config.EmailConfig;
import org.gitanjali.exam.entity.*;
import org.gitanjali.exam.model.RegisterEmail;
import org.gitanjali.exam.repository.QuestionsRepository;
import org.gitanjali.exam.repository.SubmissionRepository;
import org.gitanjali.exam.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.*;


@RestController
@RequestMapping("/teacher")
public class TeacherController {

    //@Autowired
    private TestRepository testRepository;
    private QuestionsRepository questionsRepository;
    private SubmissionRepository submissionRepository;
    private EmailConfig emailConfig;

    public TeacherController(TestRepository testRepository, QuestionsRepository questionsRepository, SubmissionRepository submissionRepository, EmailConfig emailConfig) {
        this.testRepository = testRepository;
        this.questionsRepository = questionsRepository;
        this.submissionRepository = submissionRepository;
        this.emailConfig = emailConfig;
    }


    @PostMapping("/addTestHeader")
    public Test addTestHeader(@RequestBody Test test) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Test test1 = new Test(test.getTestName(), test.getSchoolName(),
                test.getClassName(), username, Arrays.asList(), Arrays.asList());

        List<Test> tests = Arrays.asList(test1);
        this.testRepository.saveAll(tests);

        return test1;
    }

   // @PostMapping("/addTestAllFields")
   // public void addTestAllFields(@RequestBody Test test) {
   //     this.testRepository.save(test);
   // }

    @GetMapping("/getOwnersTestsList")
    public List<String> getTestsList() {
        String username;
        List<String> testIds = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        List<Test> tests = this.testRepository.findAllByOwnerEquals(username);

        for (Test t : tests
                ) {
            testIds.add(t.getId());

        }

        return testIds;
    }

    @PostMapping("/testId/{testId}/addQuestion")
    public String setById(@PathVariable("testId") String testId, @RequestBody Questions questions) {

        String username;
        List<String> testIds = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }


        Test test = this.testRepository.findByIdEquals(testId);

        if(!username.equals(test.getOwner()))
            return "Teacher does not own this document";

        List<Questions> questionsList = test.getQuestions();
        boolean found = false;

        this.questionsRepository.save(questions);
        questions.setId(questions.getId());
        questionsList.add(questions);
        this.testRepository.save(test);
        return "Document successfully added";

    }

    @GetMapping("/testId/{testId}/getTest")
    public String getById(@PathVariable("testId") String id) {
        Test test = this.testRepository.findByIdEquals(id);
        if(test == null) return "Test id not found";
        return String.valueOf(test);
    }

    @DeleteMapping("/testId/{testId}/questionId/{questionId}")
    public String removeById(@PathVariable("testId") String testId, @PathVariable("questionId") String questionId) {
        Test test = this.testRepository.findByIdEquals(testId);
        if (test == null) return "Test id not found";

        List<Questions> questionsList = test.getQuestions();
        List<Questions> questionsListCopy = new ArrayList<Questions>();
        for( Questions q : questionsList){
            questionsListCopy.add(q);
        }

        int counter = 0;
        boolean found = false;

        ListIterator<Questions> listIterator = questionsList.listIterator();
        while (listIterator.hasNext()) {
            Questions q = listIterator.next();
            if (q.getId().equals(questionId)) {
                questionsListCopy.remove(counter);
                found = true;
            }
            counter++;
        }

        if(found == false){
            return "Question id not found";
        }

        test.setQuestions(questionsListCopy);
        this.testRepository.save(test);
        return "Question deleted successfully";
    }

    @GetMapping("/testId/{testId}/getSubmissions")
    public List<Submission> getSubmissionByIndex(@PathVariable("testId") String id) {
        Test test = this.testRepository.findByIdEquals(id);
        Submission err = new Submission();
        List errList = new ArrayList();
        errList.add(err);

        if(test == null){
            err.setStudentEmail("Test id not found");
            return errList;
        } else {
            List<Submission> sub = test.getSubmissions();
            if (sub.isEmpty()){
                err.setStudentEmail("Submissions not found");
                return errList;
            } else {
                return sub;
            }

        }
    }


    @GetMapping("/testId/{testId}/submissionId/{submissionId}/getSubmissionDetails")
    public Submission getSubmissionDetails(@PathVariable("testId") String testId,
                              @PathVariable("submissionId") String submissionId) {

        String username;
        Submission retSubmission = new Submission();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        System.out.println("username is : " +username);

        Test test = this.testRepository.findByIdEquals(testId);

        if (test == null) {
            retSubmission.setStudentEmail("Test id not found");
            return retSubmission;
        }
        List<Submission> submissions = test.getSubmissions();
        if(submissions.isEmpty()){
            retSubmission.setStudentEmail("No submissions found");
            return retSubmission;
        }

        for (Submission s : submissions) {
            if (s.getId().equals(submissionId)) {
                return s;

            }
        }

        retSubmission.setStudentEmail("Something went wrong. No matching submission found");
        return retSubmission;
    }



    @PostMapping("/testId/{testId}/submissionId/{submissionId}/answerId/{answerId}/updatePointsScored")
    public String updateScore(@PathVariable("testId") String testId,
                            @PathVariable("submissionId") String submissionId,
                            @PathVariable("answerId") String answerId,
                            @RequestBody Answers answer ) {

        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        System.out.println("username is : " +username);

        Test test = this.testRepository.findByIdEquals(testId);
        if (test == null) return "Test id not found";
        List<Submission> submissions = test.getSubmissions();
        if(submissions.isEmpty()) return "No submissions found";

        for (Submission s : submissions) {
            if (s.getId().equals(submissionId)) {
                List<Answers> answers = s.getAnswers();
                for( Answers a : answers){
                    if(a.getId().equals(answerId)){
                        a.setPointScored(answer.getPointScored());
                        this.testRepository.save(test);
                        return "Updated score successfully";

                    }
                }

            }
        }

        return "Score not updated";
    }

    @PostMapping("/testId/{testId}/submissionId/{submissionId}/updateTotalAndEmail")
    public String updateTotalAndEmail(@PathVariable("testId") String testId,
                                @PathVariable("submissionId") String submissionId,
                                @RequestBody RegisterEmail registerEmail,
                                BindingResult bindingResult)
            throws ValidationException {

        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        System.out.println("username is : " +username);



        if (bindingResult.hasErrors()) {
            throw new ValidationException("Email error");
        }

        //Create a mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfig.getHost());
        mailSender.setPort(this.emailConfig.getPort());
        mailSender.setUsername(this.emailConfig.getUsername());
        mailSender.setPassword(this.emailConfig.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");


        //Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("admin@gitanjali.org");

        mailMessage.setSubject("Gitanjali.org - Answer verfication email");

        Test test = this.testRepository.findByIdEquals(testId);
        if (test == null) return "No testId found";
        List<Submission> submissions = test.getSubmissions();
        if(submissions.isEmpty()) return "No submissionId found";

        int totalScore = 0;
        int maxScore = 0;
        boolean found = false;
        String studentEmail = "";

        for(Questions q : test.getQuestions()){
            maxScore = maxScore + q.getScore();
        }

        for (Submission s : submissions) {
            if (s.getId().equals(submissionId)) {
                List<Answers> answers = s.getAnswers();
                for( Answers a : answers){
                    totalScore = totalScore + a.getPointScored();
                }

                found = true;
                s.setTotalScoreObtained(totalScore);
                studentEmail = s.getStudentEmail();
            }
        }

        if(found == false) return "Not found submissionId";

        this.testRepository.save(test);
        mailMessage.setTo(studentEmail);
        mailMessage.setText("Teacher - "+ username + " has completed answer verification. Student score - "+totalScore+" , Total score of questions - "+maxScore);

        mailSender.send(mailMessage);

        System.out.println("Email sent to user : " +studentEmail);
        return "Successfully updated total score and emailed the student";

    }


}
