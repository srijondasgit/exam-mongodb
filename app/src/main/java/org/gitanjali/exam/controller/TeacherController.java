package org.gitanjali.exam.controller;

import org.gitanjali.exam.entity.Answers;
import org.gitanjali.exam.entity.Questions;
import org.gitanjali.exam.entity.Submission;
import org.gitanjali.exam.entity.Test;
import org.gitanjali.exam.repository.QuestionsRepository;
import org.gitanjali.exam.repository.SubmissionRepository;
import org.gitanjali.exam.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/teacher")
public class TeacherController {

    //@Autowired
    private TestRepository testRepository;
    private QuestionsRepository questionsRepository;
    private SubmissionRepository submissionRepository;

    public TeacherController(TestRepository testRepository, QuestionsRepository questionsRepository, SubmissionRepository submissionRepository) {
        this.testRepository = testRepository;
        this.questionsRepository = questionsRepository;
        this.submissionRepository = submissionRepository;
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

    @DeleteMapping("/testId/{testId}/questionId/{questionId}")
    public String removeById(@PathVariable("testId") String testId, @PathVariable("questionId") String questionId) {
        Test test = this.testRepository.findByIdEquals(testId);
        if (test == null){
            return "Test id not found";
        }

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
    public String getSubmissionByIndex(@PathVariable("testId") String id) {
        Test test = this.testRepository.findByIdEquals(id);
        if(test == null){
            return "Test id not found";
        } else {
            List<Submission> sub = test.getSubmissions();
            if (sub.isEmpty()){
                return "Submissions not found";
            } else {
                return String.valueOf(sub);
            }

        }
    }


    @PostMapping("/testId/{testId}/emailId/{emailId}/answerIndex/{answerIndex}/updateScore")
    public void updateScore(@PathVariable("testId") String id,
                            @PathVariable("emailId") String emailId,
                            @PathVariable("answerIndex") int answerIndex,
                            @RequestBody int score ) {

        String username;
        List<String> testIds = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        System.out.println("username is : " +username);

        Test test = this.testRepository.findByIdEquals(id);
        List<Submission> submissions = test.getSubmissions();
        boolean found = false;

        for (Submission s : submissions) {
            if (s.getStudentEmail().trim().equals(emailId)) {
                List<Answers> answers = s.getAnswers();
                for( Answers a : answers){
                    if(a.getIndex()==answerIndex){
                        a.setPointScored(score);
                        this.testRepository.save(test);

                    }
                }

            }
        }


    }

}
