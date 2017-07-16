package com.example.e610.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.example.e610.bakingapp.Activities.RecipesActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by E610 on 7/16/2017.
 *
 */

@RunWith(AndroidJUnit4.class)
public class IntentTest {

    @Rule
    public IntentsTestRule<RecipesActivity> mActivityRule = new IntentsTestRule<>(RecipesActivity.class);

    @Test
    public void triggerIntentTest() {

        onView(ViewMatchers.withId(R.id.RecipesRecyclerView))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        intended(hasComponent("com.example.e610.bakingapp.Activities.RecipeDetailedActivity"));


        onView(ViewMatchers.withId(R.id.IngredientStepsRecyclerView))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasComponent("com.example.e610.bakingapp.Activities.IngredientActivity"));

        pressBack();

        onView(ViewMatchers.withId(R.id.IngredientStepsRecyclerView))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        intended(hasComponent("com.example.e610.bakingapp.Activities.StepActivity"));
    }

}
