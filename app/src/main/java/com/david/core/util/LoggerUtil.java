package com.david.core.util;

import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * author: Ling Lin
 * created on: 2017/7/15 17:16
 * email: 10525677@qq.com
 * description:
 */

public class LoggerUtil {

    private static final String SYSTEM_TAG = "David";
    private static final String INCUBATOR_TAG = "Incubator";
    private static final String MONITOR_TAG = "Monitor";

    static {
        FormatStrategy prettyFormatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)      //（可选）是否显示线程信息。 默认值为true
                .methodCount(3)               // （可选）要显示的方法行数。 默认2
                .methodOffset(1)               // （可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                .tag(SYSTEM_TAG)
//                .logStrategy(customLog)  //（可选）更改要打印的日志策略。 默认LogCat
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(prettyFormatStrategy));

//        if (Constant.ENABLE_LOG_TO_FILE) {
//            FormatStrategy csvFormatStrategy = CsvFormatStrategy.newBuilder()
//                    .tag(SYSTEM_TAG)
////                .logStrategy(customLog)  //（可选）更改要打印的日志策略。 默认LogCat
//                    .build();
//            Logger.addLogAdapter(new DiskLogAdapter(csvFormatStrategy));
//        }
    }

    public static void i(String message) {
        Log.i(SYSTEM_TAG, message);
    }

    public static void w(String message) {
        Log.w(SYSTEM_TAG, message);
    }

    public static void e(String message) {
        Logger.t(INCUBATOR_TAG).e(message);
    }

    public static void se(String msg) {
        Log.e(INCUBATOR_TAG, msg);
    }

    public static void e(Throwable e) {
        Logger.t(INCUBATOR_TAG).e(e, createLog(e.getMessage(), e.getStackTrace()[0]));
    }

    public static void e(String message, Throwable e) {
        Logger.t(INCUBATOR_TAG).e(e, createLog(message, e.getMessage(), e.getStackTrace()[0]));
    }

    public static void me(String msg) {
        Logger.t(MONITOR_TAG).e(msg);
    }

    public static void me(Throwable e) {
        Logger.t(MONITOR_TAG).e(e, createLog(e.getMessage(), e.getStackTrace()[0]));
    }

    private static String createLog(String log, StackTraceElement stackTraceElement) {
        return stackTraceElement.getMethodName() + "(" + stackTraceElement.getClassName() + ":"
                + stackTraceElement.getLineNumber() + "):" + log;
    }

    private static String createLog(String message, String log, StackTraceElement stackTraceElement) {
        return stackTraceElement.getMethodName() + "(" + stackTraceElement.getClassName() + ":" +
                stackTraceElement.getLineNumber() + "):" + message + ":" + log;
    }
}