package com.david.core.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ContextUtil {

    private static Context ApplicationContext;
    private static double HeightRadio;

    public static void initialize(Application application) {
        ApplicationContext = application.getApplicationContext();
    }

    public static Context getApplicationContext() {
        return ApplicationContext;
    }

    public static void initialize(AppCompatActivity activity, int designHeight) {
        Display display = activity.getWindowManager().getDefaultDisplay();

        Point point = new Point();
        display.getRealSize(point);

        double displayHeight = point.y;
        HeightRadio = displayHeight / designHeight;
    }

    public static int getHeight(int height) {
        return (int) (height * HeightRadio);
    }

    public static String getString(int resourceId) {
        return ApplicationContext.getResources().getString(resourceId);
    }

    public static int getColor(int resourceId) {
        return ContextCompat.getColor(ApplicationContext, resourceId);
    }

    public static float getDimen(int resourceId) {
        return ApplicationContext.getResources().getDimension(resourceId);
    }
}