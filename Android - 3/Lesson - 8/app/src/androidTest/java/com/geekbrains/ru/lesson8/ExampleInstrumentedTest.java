package com.geekbrains.ru.lesson8;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ProgressBar;

import com.geekbrains.ru.lesson8.data.models.RepsModel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.geekbrains.ru.lesson8", appContext.getPackageName());
    }

    @Test
    public void ensureProgressViewIsShowing() {
        MainActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.loadingView);
        assertThat(viewById, notNullValue());
        assertThat(viewById, instanceOf(ProgressBar.class));
        activity.showLoading();
        assertEquals(viewById.getVisibility(), View.VISIBLE);
    }

    @Test
    public void ensureContentIsShowing() {
        MainActivity activity = rule.getActivity();
        View content = activity.findViewById(R.id.contentView);
        assertThat(content, notNullValue());
        activity.showRepoList(new ArrayList<>());
        assertEquals(content.getVisibility(), View.VISIBLE);
    }

    @Test
    public void ensureEmptyStateIsShowing() {
        MainActivity activity = rule.getActivity();
        View empty = activity.findViewById(R.id.emptyView);
        assertThat(empty, notNullValue());
        activity.showRepoList(new ArrayList<>());
        assertEquals(empty.getVisibility(), View.VISIBLE);
    }

    @Test
    public void progressIsHidden() {
        MainActivity activity = rule.getActivity();
        View progress = activity.findViewById(R.id.loadingView);
        assertThat(progress, notNullValue());
        assertThat(progress, instanceOf(ProgressBar.class));
        activity.hideLoading();
        assertEquals(progress.getVisibility(), View.GONE);

    }
}
