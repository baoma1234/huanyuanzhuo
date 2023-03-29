package caixin.android.com.utils.notifier;

/*
 *  * EaseMob CONFIDENTIAL
 * __________________
 * Copyright (C) 2017 EaseMob Technologies. All rights reserved.
 *
 * NOTICE: All information contained herein is, and remains
 * the property of EaseMob Technologies.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from EaseMob Technologies.
 */

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.os.Build;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * various util class
 *
 */
public class NotifierUtil {
    public final static String TAG = "EasyUtils";

    private static Hashtable<String, String> resourceTable = new Hashtable<String, String>();

    public static boolean isAppRunningForeground(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = activityManager.getRunningAppProcesses();
            if (runningProcesses == null) {
                return false;
            }
            final String packageName = ctx.getPackageName();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && processInfo.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        } else {
            try {
                List<RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
                if (tasks == null || tasks.size() < 1) {
                    return false;
                }
                boolean b = ctx.getPackageName().equalsIgnoreCase(tasks.get(0).baseActivity.getPackageName());
                return b;
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String getTopActivityName(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        try {
            List<RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
            if (tasks == null || tasks.size() < 1) {
                return "";
            }
            return tasks.get(0).topActivity.getClassName();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isSingleActivity(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = null;
        try {
            tasks = activityManager.getRunningTasks(1);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (tasks == null || tasks.size() < 1) {
            return false;
        }
        return tasks.get(0).numRunning == 1;
    }

    public static List<String> getRunningApps(Context ctx) {
        List<String> list = new ArrayList<String>();
        try {
            ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningAppProcessInfo> infos = activityManager.getRunningAppProcesses();
            if (infos == null) {
                return list;
            }
            for(RunningAppProcessInfo info : infos){
                String appName = info.processName;
                if(appName.contains(":"))
                    appName = appName.substring(0,appName.indexOf(":"));
                if(!list.contains(appName))
                    list.add(appName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTimeStamp () {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyyMMddHHmmss");
        return dateFormat.format(date);
    }

    public static boolean writeToZipFile(byte[] data, String filePath) {
        OutputStream os = null;
        GZIPOutputStream zos = null;
        try {
            os = new FileOutputStream(filePath);
            zos = new GZIPOutputStream(new BufferedOutputStream(os));
            zos.write(data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * get the string from app context by using the string name
     * because of the performance issue, we will keep to string in hashmap to avoid
     * the resource lookup
     * @param stringName
     * @return
     */
    public static String getAppResourceString(Context context, String stringName) {
        String str = resourceTable.get(stringName);
        if (str != null) {
            return str;
        }

        int strId = context.getResources().getIdentifier(
                stringName, "string", context.getPackageName());
        str = context.getString(strId);
        if (str != null) {
            resourceTable.put(stringName, str);
        }
        return str;
    }

    public static String convertByteArrayToString(byte[] input) {
        StringBuilder sb = new StringBuilder();
        for (byte value : input) {
            sb.append(String.format("0x%02X", value));
        }
        return sb.toString();
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isSDCardExist() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    public static boolean copyFile(String oldPath, String newPath) {
        boolean isok = true;

        InputStream inStream = null;
        FileOutputStream fs = null;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                inStream = new FileInputStream(oldPath); //读入原文件
                fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    //System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
            }
            else
            {
                isok = false;
            }
        }
        catch (Exception e) {
            // System.out.println("复制单个文件操作出错");
            // e.printStackTrace();
            isok = false;
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (Exception e) {

                }
            }
            if (fs != null) {
                try {
                    fs.close();
                } catch (Exception e) {

                }
            }
        }
        return isok;

    }

    public static boolean copyFolder(String oldPath, String newPath) {
        boolean isok = true;
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a=new File(oldPath);
            String[] fileList=a.list();
            String filePath = null;
            File temp = null;
            for (String aFile : fileList) {
                if (oldPath.endsWith(File.separator)) {
                    filePath = oldPath + aFile;
                } else {
                    filePath = oldPath + File.separator + aFile;
                }

                temp = new File(filePath);

                if (temp.isFile()) {
                    try {
                        input = new FileInputStream(temp);
                        output = new FileOutputStream(newPath + "/" + temp.getName());
                        byte[] b = new byte[1024 * 5];
                        int len;
                        while ((len = input.read(b)) != -1) {
                            output.write(b, 0, len);
                        }
                        output.flush();
                    } catch (Exception e) {
                        isok = false;
                    } finally {
                        if (input != null) {
                            try {
                                input.close();
                            } catch (Exception e) {

                            }
                        }
                        if (output != null) {
                            try {
                                output.close();
                            } catch (Exception e) {

                            }
                        }
                    }
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + aFile, newPath + "/" + aFile);
                }
            }
        }
        catch (Exception e) {
            isok = false;
        }
        return isok;
    }

    public static String useridFromJid(String memberName){
        String username = "";
        if (memberName.contains("_") && memberName.contains("@easemob.com")) {
            username = memberName.substring(memberName.indexOf("_") + 1, memberName.indexOf("@"));
        } else if (memberName.contains("_")) { // 在音视频会议新接口中传入的uid为appKey_username
            username = memberName.substring(memberName.indexOf("_") + 1);
        } else {
            username = memberName;
        }
        return username;
    }

    /**
     * 生成音视频访问需要的uid，格式：appKey_username
     *
     * @param appKey
     * @param username
     * @return
     */
    public static String getMediaRequestUid(String appKey, String username) {
        return appKey + "_" + username;
    }
}
