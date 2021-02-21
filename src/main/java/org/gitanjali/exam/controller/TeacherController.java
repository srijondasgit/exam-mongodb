package org.gitanjali.exam.controller;

import org.gitanjali.exam.entity.Answers;
import org.gitanjali.exam.entity.Questions;
import org.gitanjali.exam.entity.Submission;
import org.gitanjali.exam.entity.Test;
import org.gitanjali.exam.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


@RestController
@RequestMapping("/teacher")
public class TeacherController {

    //@Autowired
    private TestRepository testRepository;

    public TeacherController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @GetMapping("/{id}")
    public Test getById(@PathVariable("id") String id) {
        Test test = this.testRepository.findByIdEquals(id);

        return test;
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

    @PostMapping("/addTestAllFields")
    public void addTestAllFields(@RequestBody Test test) {
        this.testRepository.save(test);
    }

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

    @PostMapping("/addQuestion/{testId}")
    public void setById(@PathVariable("testId") String id, @RequestBody Questions questions) {

        Test test = this.testRepository.findByIdEquals(id);
        List<Questions> questionsList = test.getQuestions();
        boolean found = false;

        for (Questions q : questionsList) {
            if (q.getIndex() == questions.getIndex()) {
                q.setQuestionText(questions.getQuestionText());
                q.setScore(questions.getScore());
                found = true;
            }
        }

        if (found == false)
            questionsList.add(questions);

        this.testRepository.save(test);
    }

    @DeleteMapping("/removeQuestion/{testId}")
    public void removeById(@PathVariable("testId") String id, @RequestBody int index) {
        Test test = this.testRepository.findByIdEquals(id);
        List<Questions> questionsList = test.getQuestions();

        ListIterator<Questions> listIterator = questionsList.listIterator();
        while (listIterator.hasNext()) {
            Questions q = listIterator.next();
            if (q.getIndex() == index) {
                listIterator.remove();
            }
        }

        this.testRepository.save(test);
    }

    @GetMapping("/getSubmissions/test/{testId}")
    public List<Submission> getSubmissionByIndex(@PathVariable("testId") String id) {
        Test test = this.testRepository.findByIdEquals(id);
        return test.getSubmissions();

    }


    //scoring for the answer submissions
    @PostMapping("/updateScore/test/{testId}/email/{emailId}/answerIndex/{answerIndex}")
    public void updateScore(@PathVariable("testId") String id,
                            @PathVariable("emailId") String emailId,
                            @PathVariable("answerIndex") int answerIndex,
                            @RequestBody int score ) {


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
