package com.dubinostech.rideshareapp;


import com.dubinostech.rideshareapp.ui.activities.SignUpActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestRegister {

    @Mock
    SignUpActivity activity = new SignUpActivity();

    final String USERNAME = "testuser";
    final String USER_EXISTS = "exists";
    final String PASSWORD = "password";
    final String PASSWORD_CONFIRM_BAD = "fakepass";
    final String SHORT_PASSWORD = "short";
    final String SHORT_PASSWORD_CONFIRM = "short";
    final String FULLNAME = "FULLNAME";
    final String INVALIDEMAIL = "EMAIL";
    final String VALIDEMAIL = "email@email.com";

    @Before
    public void setMockOutput() {
        when(activity.registerPost(anyString(), anyString(), anyString(), anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(USERNAME) && invocation.getArgument(1).equals(PASSWORD)) {
                        return true;
                    } else if (invocation.getArgument(0).equals(USER_EXISTS) && invocation.getArgument(1).equals(PASSWORD)) {
                        return false;
                    }
                    return false;
                });
    }

    @Test
    public void testRegisterSuccess(){
        boolean success = activity.registerPost(USERNAME, PASSWORD, FULLNAME, VALIDEMAIL);
        assertTrue(success);
    }

    @Test
    public void testRegisterUserAlreadyExists(){
        boolean success = activity.registerPost(USER_EXISTS, PASSWORD, FULLNAME, VALIDEMAIL);
        assertFalse(success);
    }

    @Test
    public void testRegisterPasswordsDoNotMatch(){
        boolean success = activity.checkRegister(USERNAME, PASSWORD, PASSWORD_CONFIRM_BAD, FULLNAME, VALIDEMAIL);
        assertFalse(success);
    }

    @Test
    public void testRegisterNotAllFieldsFilled(){
        boolean success = activity.checkRegister("", PASSWORD, PASSWORD_CONFIRM_BAD, FULLNAME, VALIDEMAIL);
        assertFalse(success);
    }

    @Test
    public void testRegisterPasswordTooShort(){
        boolean success = activity.checkRegister(USERNAME, SHORT_PASSWORD, SHORT_PASSWORD_CONFIRM, FULLNAME, VALIDEMAIL);
        assertFalse(success);
    }

    @Test
    public void testRegisterEmailInvalid(){
        boolean success = activity.checkRegister(USERNAME, PASSWORD, PASSWORD_CONFIRM_BAD, FULLNAME, INVALIDEMAIL);
        assertFalse(success);
    }
}