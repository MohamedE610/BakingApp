package com.example.e610.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.e610.bakingapp.Activities.RecipesActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by E610 on 7/13/2017.
 */
@RunWith(AndroidJUnit4.class)
public class StepActivityTest {




    @Rule
    public ActivityTestRule<RecipesActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipesActivity.class);

    private IdlingResource mIdlingResource;


    // Registers any resource that needs to be synchronized with Espresso before the test is run.
    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void stepActivityTest() {

        int CurrentIndex=0;

        onView(ViewMatchers.withId(R.id.recipes_recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(CurrentIndex, click()));

        onView(ViewMatchers.withId(R.id.ingredient_steps_recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(CurrentIndex+1, click()));
        //CurrentIndex+1 as index 0 is "ingredients"

        onView(withId(R.id.step_description)).perform(scrollTo()).check(matches(isDisplayed()));

        onView(withId(R.id.next_btn)).perform(scrollTo()).perform(click());
        CurrentIndex++;

        onView(withId(R.id.step_id)).check(matches(withText("Step "+CurrentIndex)));

        onView(withId(R.id.previous_btn)).perform(scrollTo()).perform(click());
        CurrentIndex--;

        onView(withId(R.id.step_id)).check(matches(withText("Step "+CurrentIndex)));

    }

    // Remember to unregister resources when not needed to avoid malfunction.
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}
