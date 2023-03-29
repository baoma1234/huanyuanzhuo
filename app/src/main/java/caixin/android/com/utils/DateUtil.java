package caixin.android.com.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cxf on 2018/7/14.
 */

public class DateUtil {

    private static final long INTERVAL = 60;

    private static final SimpleDateFormat SDF1;
    private static final SimpleDateFormat SDF2;
    private static final SimpleDateFormat SDF3;
    private static final SimpleDateFormat SDF4;
    private static final SimpleDateFormat SDF5;

    static {
        SDF1 = new SimpleDateFormat("aa hh:mm");
        SDF2 = new SimpleDateFormat("昨天aa hh:mm");
        SDF3 = new SimpleDateFormat("M月d日aa hh:mm");
        SDF4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SDF5 = new SimpleDateFormat("yyyy-MM-dd");
    }

    public static boolean isItToday(long time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(time);
            Date old = sdf.parse(sdf.format(date));
            Date now = sdf.parse(sdf.format(new Date()));
            long oldTime = old.getTime();
            long nowTime = now.getTime();
            long day = (nowTime - oldTime) / (24 * 60 * 60 * 1000);
            if (day == 0) {  //今天
                return true;
            } else {     //昨天
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static long StringToLong(String s) {
        Date date = new Date();
        try {
            date = SDF4.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String formatDate(String s) {
        long currentTime = System.currentTimeMillis() / 1000;
        long time = StringToLong(s) / 1000;
        if (isCloseEnough(time, currentTime)) {
            return "刚刚";
        } else if (Math.abs(time - currentTime) < 60 * 60) {
            return Math.abs(currentTime - time) / 60 + "分钟前";
        } else if (Math.abs(time - currentTime) < 24 * 60 * 60) {
            return Math.abs(currentTime - time) / (60 * 60) + "小时前";
        } else if (Math.abs(time - currentTime) < 24 * 60 * 60 * 7) {
            return Math.abs(currentTime - time) / (60 * 60 * 24) + "天前";
        } else {
            return s;
        }
    }

    public static long DataToLong(Date date) {
        long lSysTime1 = date.getTime();   //得到秒数，Date类型的getTime()返回毫秒数
        return lSysTime1;
    }

    public static String LongToString(long time) {
        Date dt = new Date(time);
        String sDateTime = SDF5.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
        return sDateTime;
    }

    public static String getTimestampString(long time) {
        if (isSameDay(time)) {
            return SDF1.format(new Date(time));
        } else if (isYesterday(time)) {
            return SDF2.format(new Date(time));
        } else {
            return SDF3.format(new Date(time));
        }
    }

    private static boolean isSameDay(long inputTime) {
        TimeInfo tStartAndEndTime = getTodayStartAndEndTime();
        if (inputTime > tStartAndEndTime.getStartTime() && inputTime < tStartAndEndTime.getEndTime())
            return true;
        return false;
    }


    private static boolean isYesterday(long inputTime) {
        TimeInfo yStartAndEndTime = getYesterdayStartAndEndTime();
        if (inputTime > yStartAndEndTime.getStartTime() && inputTime < yStartAndEndTime.getEndTime())
            return true;
        return false;
    }


    /**
     * 获取今天00:00:00~23:59:59 开始和结束的时间戳
     */
    private static TimeInfo getTodayStartAndEndTime() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar1.getTime();
        long startTime = startDate.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        calendar2.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar2.getTime();
        long endTime = endDate.getTime();
        TimeInfo info = new TimeInfo();
        info.setStartTime(startTime);
        info.setEndTime(endTime);
        return info;
    }

    /**
     * 获取昨天00:00:00~23:59:59 开始和结束的时间戳
     */
    private static TimeInfo getYesterdayStartAndEndTime() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, -1);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);

        Date startDate = calendar1.getTime();
        long startTime = startDate.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE, -1);
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        calendar2.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar2.getTime();
        long endTime = endDate.getTime();
        TimeInfo info = new TimeInfo();
        info.setStartTime(startTime);
        info.setEndTime(endTime);
        return info;
    }

    /**
     * 判断两个时间戳是否小于 INTERVAL
     */
    public static boolean isCloseEnough(long time1, long time2) {
        return Math.abs(time2 - time1) < INTERVAL;
    }


    public static class TimeInfo {
        private long startTime;
        private long endTime;

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }
    }

    public static String getTimestampString(Date messageDate) {
        String format = null;
        String language = Locale.getDefault().getLanguage();
        boolean isZh = language.startsWith("zh");
        long messageTime = messageDate.getTime();
        if (isSameDay(messageTime)) {
            if(isZh) {
                format = "aa hh:mm";
            } else {
                format = "hh:mm aa";
            }
        } else if (isYesterday(messageTime)) {
            if(isZh){
                format = "昨天aa hh:mm";
            }else{
                return "Yesterday " + new SimpleDateFormat("hh:mm aa", Locale.ENGLISH).format(messageDate);
            }
        } else {
            if(isZh){
                format = "M月d日aa hh:mm";
            }
            else{
                format = "MMM dd hh:mm aa";
            }
        }
        if(isZh){
            return new SimpleDateFormat(format,Locale.CHINESE).format(messageDate);
        }else{
            return new SimpleDateFormat(format,Locale.ENGLISH).format(messageDate);
        }
    }
}
