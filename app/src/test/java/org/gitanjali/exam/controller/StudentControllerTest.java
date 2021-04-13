package org.gitanjali.exam.controller;

import org.gitanjali.exam.entity.Answers;
import org.gitanjali.exam.entity.Submission;
import org.gitanjali.exam.repository.TestRepository;
import org.junit.Test;
import org.junit.Before;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.Collections;
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
    @Mock
    SecurityContext securityContext;

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("Test123", "Password123");

    //private final Token token =  new Token(Collections.singletonList(new SimpleGrantedAuthority("dummy_permission")), 123L, 345L,                       "dummy-domain", "dummy-db", "abc-123-prq", 1, 1L);
    @Before public void init(){
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void addAnswersByEmailTest() throws ValidationException {
        //securityContextHo = new SecurityContextHolder();
        //UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("Test123", "Password123");
        when(securityContext.getAuthentication()).thenReturn(token);
        when(testRepository.findByIdEquals(anyString()))
                .thenReturn(new org.gitanjali.exam.entity.Test(new ArrayList<>()));
        studentController.addAnswersByEmail("testId",new Answers());
        verify(testRepository, times(1)).save(any(org.gitanjali.exam.entity.Test.class));
    }

    @Test
    public void getSubmissionsByEmailTest_SubmissionNotFound() {
        List<Submission> submissionList = new ArrayList<>();
        submissionList.add(new Submission("testEmail"));
        org.gitanjali.exam.entity.Test input = new org.gitanjali.exam.entity.Test(submissionList);
        when(testRepository.findByIdEquals(anyString())).thenReturn(input);
        Submission submission = studentController.getSubmissionByEmail("testId");
        assertNotEquals(submission.getStudentEmail(), input.getSubmissions().get(0).getStudentEmail());
    }

    @Test
    public void getSubmissionsByEmailTest_SubmissionFound() {
        when(securityContext.getAuthentication()).thenReturn(token);

        List<Submission> submissionList = new ArrayList<>();
        submissionList.add(new Submission("testEmail"));
        org.gitanjali.exam.entity.Test input = new org.gitanjali.exam.entity.Test(submissionList);
        when(testRepository.findByIdEquals(anyString())).thenReturn(input);
        Submission submission = studentController.getSubmissionByEmail("testId");
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
