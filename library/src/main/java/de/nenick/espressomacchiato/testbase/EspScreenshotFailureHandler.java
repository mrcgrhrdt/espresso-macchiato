package de.nenick.espressomacchiato.testbase;

import android.content.Context;
import android.support.test.espresso.FailureHandler;
import android.support.test.espresso.base.DefaultFailureHandler;
import android.util.Log;
import android.view.View;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.tools.EspScreenshotTool;

public class EspScreenshotFailureHandler implements FailureHandler {
    private final FailureHandler delegate;

    public EspScreenshotFailureHandler(Context targetContext) {
        delegate = new DefaultFailureHandler(targetContext);
    }

    @Override
    public void handle(Throwable error, Matcher<View> viewMatcher) {
        try {
            StackTraceElement testClass = EspFindTestClassFunction.apply(Thread.currentThread().getStackTrace());
            String className = testClass.getClassName().substring(testClass.getClassName().lastIndexOf(".") + 1);
            String methodName = testClass.getMethodName();

            EspScreenshotTool.takeWithName("Failed-" + className + "." + methodName);
        } catch (Exception e) {
            Log.e("EspressoMacchiato", "Could not create an image of the current screen.", e);
        }

        delegate.handle(error, viewMatcher);
    }
}
