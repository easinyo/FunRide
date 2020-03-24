package com.dubinostech.rideshareapp;


import com.dubinostech.rideshareapp.ui.activities.EditUserProfileActivity;

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
public class TestUpdate {

    @Mock
    EditUserProfileActivity activity = new EditUserProfileActivity();

    final String USERNAME = "testuser";
    final String USER_EXISTS = "exists";
    final String CURRENT_PASSWORD = "curpassword";
    final String NEW_PASSWORD = "newpassword";
    final String PASSWORD_CONFIRM_BAD = "fakepass";
    final String SHORT_PASSWORD = "short";
    final String SHORT_PASSWORD_CONFIRM = "short";
    final String FULLNAME = "FULLNAME";
    final String VALIDEMAIL = "email@email.com";
    final String INVALIDEMAIL = "EMAIL";

    @Before
    public void setMockOutput() {
        when(activity.updatePost(anyString(), anyString(), anyString(), anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(VALIDEMAIL) && invocation.getArgument(2).equals(CURRENT_PASSWORD)) {
                        return true;
                    } else if (invocation.getArgument(0).equals(VALIDEMAIL) && invocation.getArgument(1).equals(NEW_PASSWORD)) {
                        return false;
                    }
                    return false;
                });
    }

    @Test
    public void testUpdateSuccess(){
        boolean success = activity.updatePost(VALIDEMAIL, FULLNAME, CURRENT_PASSWORD, NEW_PASSWORD);
        assertTrue(success);
    }

    @Test
    public void testUpdateFailure(){
        boolean success = activity.updatePost(USER_EXISTS, NEW_PASSWORD, FULLNAME, VALIDEMAIL);
        assertFalse(success);
    }

    @Test
    public void testUpdatePasswordsDoNotMatch(){
        boolean success = activity.checkUpdateUser(VALIDEMAIL, FULLNAME, CURRENT_PASSWORD, NEW_PASSWORD, PASSWORD_CONFIRM_BAD);
        assertFalse(success);
    }

    @Test
    public void testUpdateCurrentPasswordNotFilled(){
        boolean success = activity.checkUpdateUser(VALIDEMAIL, FULLNAME, "", NEW_PASSWORD, NEW_PASSWORD);
        assertFalse(success);
    }

    @Test
    public void testUpdatePasswordTooShort(){
        boolean success = activity.checkUpdateUser(VALIDEMAIL, FULLNAME, CURRENT_PASSWORD, SHORT_PASSWORD, SHORT_PASSWORD_CONFIRM);
        assertFalse(success);
    }

    @Test
    public void testUpdateInvalidEmail(){
        boolean success = activity.checkUpdateUser(INVALIDEMAIL, FULLNAME, CURRENT_PASSWORD, NEW_PASSWORD, NEW_PASSWORD);
        assertFalse(success);
    }
}