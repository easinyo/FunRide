package com.dubinostech.rideshareapp

package com.example.android.testing.blueprint.ui.espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.android.testing.blueprint.HelloTestingBlueprintActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * A test class that is only run against flavor2.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

  // Literal string instead of R.id.app_name because we want this test to only pass with flavor2.
  public static final String APP_NAME= "RideShareApp";

    @Rule
    public ActivityTestRule<HelloTestingBlueprintActivity> mActivityRule =
            new ActivityTestRule<>(HelloTestingBlueprintActivity.class);

    @Test
    public void findViewPerformActionAndCheckAssertion() {
        // Find Button and Click on it
      onView(withText(APP_NAME_FLAVOR2)).check(matches(isDisplayed()));
    }
}