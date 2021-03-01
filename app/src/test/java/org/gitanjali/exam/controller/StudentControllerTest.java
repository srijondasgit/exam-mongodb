package org.gitanjali.exam.controller;

import org.gitanjali.exam.entity.Answers;
import org.gitanjali.exam.entity.Submission;
import org.gitanjali.exam.repository.TestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StudentControllerTest {

    @Mock
    TestRepository testRepository;
    @InjectMocks
    StudentController studentController;

    @Test
    public void addAnswersByEmailTest() throws ValidationException {
        when(testRepository.findByIdEquals(anyString()))
                .thenReturn(new org.gitanjali.exam.entity.Test(new ArrayList<>()));
        studentController.addAnswersByEmail("testId", "testEmail", new Answers());
        verify(testRepository, times(1)).save(any(org.gitanjali.exam.entity.Test.class));
    }

    @Test
    public void getSubmissionsByEmailTest_SubmissionNotFound() {
        List<Submission> submissionList = new ArrayList<>();
        submissionList.add(new Submission("testEmail"));
        org.gitanjali.exam.entity.Test input = new org.gitanjali.exam.entity.Test(submissionList);
        when(testRepository.findByIdEquals(anyString())).thenReturn(input);
        Submission submission = studentController.getSubmissionByEmail("testId", "dummyEmail");
        assertNotEquals(submission.getStudentEmail(), input.getSubmissions().get(0).getStudentEmail());
    }

    @Test
    public void getSubmissionsByEmailTest_SubmissionFound() {
        List<Submission> submissionList = new ArrayList<>();
        submissionList.add(new Submission("testEmail"));
        org.gitanjali.exam.entity.Test input = new org.gitanjali.exam.entity.Test(submissionList);
        when(testRepository.findByIdEquals(anyString())).thenReturn(input);
        Submission submission = studentController.getSubmissionByEmail("testId", "testEmail");
        assertEquals(submission.getStudentEmail(), input.getSubmissions().get(0).getStudentEmail());
    }

    @Test
    public void updateSubmissionByEmailTest_EmailNotFound() {
        List<Submission> submissionList = new ArrayList<>();
        submissionList.add(new Submission("testEmail"));
        org.gitanjali.exam.entity.Test input = new org.gitanjali.exam.entity.Test(submissionList);
        when(testRepository.findByIdEquals(anyString())).thenReturn(input);
        String response = studentController.updateSubmissionByEmail("testId", "dummyEmail", new Submission());
        assertEquals("Student email not found", response);
        verify(testRepository, times(0)).save(any(org.gitanjali.exam.entity.Test.class));
    }

    @Test
    public void updateSubmissionByEmailTest_EmailFound() {
        List<Submission> submissionList = new ArrayList<>();
        submissionList.add(new Submission("testEmail"));
        org.gitanjali.exam.entity.Test input = new org.gitanjali.exam.entity.Test(submissionList);
        when(testRepository.findByIdEquals(anyString())).thenReturn(input);
        String response = studentController.updateSubmissionByEmail("testId", "testEmail", new Submission());
        assertEquals("Student is updated", response);
        verify(testRepository, times(1)).save(any(org.gitanjali.exam.entity.Test.class));
    }
}
