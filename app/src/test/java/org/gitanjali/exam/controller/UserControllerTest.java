package org.gitanjali.exam.controller;

import org.gitanjali.exam.entity.Submission;
import org.gitanjali.exam.repository.TestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    TestRepository testRepository;
    @Mock
    AuthenticationManager authenticationManager;
    @InjectMocks
    UserController userController;

    @Test
    public void getAllTest_NoTestFound() {
        when(testRepository.findAll()).thenReturn(new ArrayList<>());

        assertEquals(0, userController.getAll().size());
        verify(testRepository, times(1)).findAll();
    }

    @Test
    public void getAllTest_TestFound() {
        List<Submission> submissions = new ArrayList<>();
        submissions.add(new Submission("a@b.com"));
        List<org.gitanjali.exam.entity.Test> tests = new ArrayList<>();
        tests.add(new org.gitanjali.exam.entity.Test(submissions));
        when(testRepository.findAll()).thenReturn(tests);

        List<org.gitanjali.exam.entity.Test> testResult = userController.getAll();

        assertEquals(1, testResult.size());
        assertEquals("Data is hidden", testResult.get(0).getSubmissions().get(0).getStudentEmail());
        verify(testRepository, times(1)).findAll();
    }

    @Test(expected = NullPointerException.class)
    public void getById_NoTestFound() {
        when(testRepository.findByIdEquals(anyString())).thenReturn(null);

        userController.getById(anyString());
        verify(testRepository, times(1)).findByIdEquals(anyString());
    }

    @Test
    public void getById_TestFound() {
        List<Submission> submissions = new ArrayList<>();
        submissions.add(new Submission("a@b.com"));
        org.gitanjali.exam.entity.Test test = new org.gitanjali.exam.entity.Test(submissions);
        when(testRepository.findByIdEquals(anyString())).thenReturn(test);

        org.gitanjali.exam.entity.Test testResult = userController.getById(anyString());

        assertEquals("Data is hidden", testResult.getSubmissions().get(0).getStudentEmail());
        verify(testRepository, times(1)).findByIdEquals(anyString());
    }

    @Test
    public void getAllTestId_NoTestFound() {
        when(testRepository.findAll()).thenReturn(new ArrayList<>());

        List<String> testIds = userController.getAllTestId();

        assertEquals(0, testIds.size());
        verify(testRepository, times(1)).findAll();
    }

    @Test
    public void getAllTestId_TestFound() {
        List<Submission> submissions = new ArrayList<>();
        submissions.add(new Submission());
        List<org.gitanjali.exam.entity.Test> tests = new ArrayList<>();
        tests.add(new org.gitanjali.exam.entity.Test(submissions).setId("A"));
        tests.add(new org.gitanjali.exam.entity.Test(submissions).setId("B"));
        when(testRepository.findAll()).thenReturn(tests);

        List<String> result = userController.getAllTestId();

        assertEquals(2, result.size());
        verify(testRepository, times(1)).findAll();
    }
}
