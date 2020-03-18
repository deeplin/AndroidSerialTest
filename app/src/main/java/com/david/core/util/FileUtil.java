package com.david.core.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.arch.core.util.Function;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 * author: Ling Lin
 * created on: 2017/7/28 9:29
 * email: 10525677@qq.com
 * description:
 */

public class FileUtil {

    public static String readTextFileFromAssets(String fileName) throws IOException {
        AssetManager assetManager = ContextUtil.getApplicationContext().getAssets();
        InputStream inputStream = assetManager.open(fileName);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputReader);

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line).append("\t");
        }

        bufferedReader.close();
        inputReader.close();
        inputStream.close();

        return result.toString();
    }

    public static void readSensorData(String fileName, short[] buffer, Function<Short, Short> func) {
        try {
            AssetManager assetManager = ContextUtil.getApplicationContext().getAssets();
            InputStream inputStream = assetManager.open(fileName);

            int index = 0;
            int sourceData;

            while ((sourceData = inputStream.read()) != -1) {
                short data = func.apply((short) sourceData);
                buffer[index] = data;
                buffer[index + buffer.length / 2] = data;
                index++;
            }

            inputStream.close();
        } catch (Throwable e) {
            LoggerUtil.e(e);
        }
    }

//    public static void setLanguage(int languageIndex) {
//        if (languageIndex == LanguageMode.Chinese.getIndex()) {
//            setLocalLanguage(ContextUtil.getApplicationContext(), Locale.SIMPLIFIED_CHINESE);
//        } else if (languageIndex == LanguageMode.English.getIndex()) {
//            setLocalLanguage(ContextUtil.getApplicationContext(), Locale.ENGLISH);
//        } else if (languageIndex == LanguageMode.Turkish.getIndex()) {
//            Locale turkish = new Locale("tr", "TR");
//            setLocalLanguage(ContextUtil.getApplicationContext(), turkish);
//        } else if (languageIndex == LanguageMode.Polish.getIndex()) {
//            Locale spanish = new Locale("pl", "PL");
//            setLocalLanguage(ContextUtil.getApplicationContext(), spanish);
//        } else if (languageIndex == LanguageMode.Russia.getIndex()) {
//            Locale russia = new Locale("ru", "RU");
//            setLocalLanguage(ContextUtil.getApplicationContext(), russia);
//        }
//    }

    private static void setLocalLanguage(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = context.getResources().getConfiguration();
        configuration.locale = locale;
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public static boolean makeDirectory(String directory) {
        File file = new File(directory);
        if (!file.exists()) {
            boolean status = file.mkdirs();
            return status;
        }
        return true;
    }

    public static File[] listFile(String path) {
        File file = new File(path);
        File[] files = file.listFiles();

        return files;
    }

    public static String generateFilePath(String parentPath, String extension, String fileName) {
        File file = new File(parentPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        /** reset fileName by prefix and custom file name */
        return file.getAbsolutePath() + File.separator + fileName + "." + extension;
    }
}