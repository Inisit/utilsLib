package com.hongsou.insist.utillibrary;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.hongsou.insist.utillibrary.ftp.FtpHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author lpc
 *         <p>
 * @copyright 鸿搜网络公司 版权所有
 * <p>
 * @date 2018/7/31
 * <p>
 * @desc Log日志管理类
 */
public class LogUtil {

    private static final char VERBOSE = 'V';
    private static final char DEBUG = 'D';
    private static final char INFO = 'I';
    private static final char WARN = 'W';
    private static final char ERROR = 'E';

    //可以全局控制是否打印log日志
    private static boolean isDebug = true;
    private static int LOG_MAXLENGTH = 2000;

    public static FtpHelper ftp;

    public static String logPath = null;//log日志存放路径

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS", Locale.US);//日期格式;

    public static String fileName;


    /**
     * 初始化，须在使用之前设置，最好在Application创建时调用
     *
     * @param context
     */
    public static void init(Context context) {
        logPath = getFilePath(context) + "/Logs";//获得文件储存路径,在后面加"/Logs"建立子文件夹
    }

    /**
     * 初始化，须在使用之前设置，最好在Application创建时调用
     *
     * @param context
     */
    public static void init(Context context, String host, String user, String pass) {
        logPath = getFilePath(context) + "/Logs";//获得文件储存路径,在后面加"/Logs"建立子文件夹
        initFtp(host, 21, user, pass);
    }

    /**
     * 初始化，须在使用之前设置，最好在Application创建时调用
     *
     * @param context
     */
    public static void init(Context context, String host, int port, String user, String pass) {
        logPath = getFilePath(context) + "/Logs";//获得文件储存路径,在后面加"/Logs"建立子文件夹
        initFtp(host, port, user, pass);
    }

    /**
     * 是否打印log日志 默认true
     * @param debug
     */
    public static void setDebug(boolean debug){
        isDebug = debug;
    }

    /**
     * @desc 初始化ftp
     * @anthor lpc
     * @date: 2018/7/30
     */
    private static void initFtp(final String host, final int port, final String user, final String pass) {
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                if (ftp == null) {
                    try {
                        ftp = new FtpHelper(host, port, user, pass);
                        ftp.openConnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 获得文件存储路径
     *
     * @return
     */
    private static String getFilePath(Context context) {
        String s = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalFilesDir = context.getExternalFilesDir("");
            if (externalFilesDir != null) {
                //  /storage/emulated/0/Android/data/packname/files
                s = externalFilesDir.getAbsolutePath();
            } else {
                // /storage/emulated/0/path
                s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getPackageName();
            }
            return s;
        } else {
            // /data/user/0/packname/files
            return context.getFilesDir().getAbsolutePath();
        }
    }

    /**
     * 获得文件存储路径
     *
     * @return
     */
    private static String getFileName(String s) {
        return logPath + "/log_" + s + ".log";
    }

    public static void v(String msg) {
        v("LogUtil", msg);
    }

    public static void v(String tagName, String msg) {
        if (isDebug) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.v(tagName + i, msg.substring(start, end));
                    writeToFile(VERBOSE, tagName + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.v(tagName + i, msg.substring(start, strLength));
                    writeToFile(VERBOSE, tagName + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void d(String msg) {
        d("LogUtil", msg);
    }

    public static void d(String tagName, String msg) {
        if (isDebug) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.d(tagName + i, msg.substring(start, end));
                    writeToFile(DEBUG, tagName + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.d(tagName + i, msg.substring(start, strLength));
                    writeToFile(DEBUG, tagName + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void i(String msg) {
        i("LogUtil", msg);
    }

    public static void i(String tagName, String msg) {
        if (isDebug) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.i(tagName + i, msg.substring(start, end));
                    writeToFile(INFO, tagName + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.i(tagName + i, msg.substring(start, strLength));
                    writeToFile(INFO, tagName + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void w(String msg) {
        w("LogUtil", msg);
    }

    public static void w(String tagName, String msg) {
        if (isDebug) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.w(tagName + i, msg.substring(start, end));
                    writeToFile(WARN, tagName + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.w(tagName + i, msg.substring(start, strLength));
                    writeToFile(WARN, tagName + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void e(String msg) {
        e("LogUtil", msg);
    }

    public static void e(String tagName, String msg) {
        if (isDebug) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.e(tagName + i, msg.substring(start, end));
                    writeToFile(ERROR, tagName + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.e(tagName + i, msg.substring(start, strLength));
                    writeToFile(ERROR, tagName + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    /**
     * 将log信息写入文件中
     *
     * @param type
     * @param tag
     * @param msg
     */
    private static void writeToFile(char type, String tag, String msg) {

        if (null == logPath) {
            Log.e("LogUtil", "logPath == null ，未初始化");
            return;
        }
        //log日志名，使用时间命名，保证不重复
        fileName = getFileName(DateUtils.getStringDateShort());
        String log = dateFormat.format(new Date()) + " / " + type + " 【" + tag + "】 " + msg + "\n";//log日志内容，可以自行定制

        //如果父路径不存在
        File file = new File(logPath);
        if (!file.exists()) {
            file.mkdirs();//创建父路径
        }

        FileOutputStream fos = null;//FileOutputStream会自动调用底层的close()方法，不用关闭
        BufferedWriter bw = null;
        try {

            fos = new FileOutputStream(fileName, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(log);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();//关闭缓冲流
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param day 天数
     * @desc 删除七天之前的log日志文件
     * @anthor lpc
     * @date: 2018/11/13
     */
    public static void deleteLogFiles(String day) {
        String lastDay = getLastDay(DateUtils.getStringDateShort(), day);
        String fileName = LogUtil.getFileName(lastDay);
        Log.e("LogUtils", "init: " + fileName);
        deleteFileNames(fileName);
    }

    /**
     * 删除指定文件
     *
     * @return
     */
    private static void deleteFileNames(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return;
        }
        try {
            File filesDir = new File(dirPath);
            filesDir.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到一个时间前移几天的时间,nowdate为时间,delay为前移的天数
     */
    private static String getLastDay(String nowDate, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            Date d = formatter.parse(nowDate, pos);
            long myTime = (d.getTime() / 1000) - Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

}
