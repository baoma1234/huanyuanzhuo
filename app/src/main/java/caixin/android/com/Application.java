package caixin.android.com;

import android.database.sqlite.SQLiteDatabase;

import androidx.cardview.widget.CardView;
import androidx.multidex.MultiDexApplication;

import caixin.android.com.dao.DaoMaster;
import caixin.android.com.dao.DaoSession;
import caixin.android.com.http.WebSocketManager;

import com.kongzue.dialog.util.DialogSettings;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mmkv.MMKV;
import com.yanzhenjie.nohttp.NoHttp;

import cn.jpush.android.api.JPushInterface;

public class Application extends MultiDexApplication {
    public static Application sInstance;
    private static boolean isHttpLogin;

    /**
     * 获得当前app运行的Application
     */
    public static Application getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("please inherit BaseApplication or call setApplication.");
        }
        return sInstance;
    }

    public static boolean isIsHttpLogin() {
        return isHttpLogin;
    }

    public static void setIsHttpLogin(boolean isHttpLogin) {
        Application.isHttpLogin = isHttpLogin;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        MMKV.initialize(this);
        initGreenDao();
        WebSocketManager.getInstance().init(this);
        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        NoHttp.initialize(this);
        DialogSettings.cancelable = true;
        initBugly();
        isHttpLogin = false;
    }

    private void initBugly() {

        Bugly.init(getApplicationContext(), "c0b1e85def", false);
//        CrashReport.initCrashReport(getApplicationContext(), "c0b1e85def", false);

    }

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    /**
     * 初始化dao
     */
    private void initGreenDao() {
        mHelper = new DaoMaster.DevOpenHelper(sInstance, "caixin.db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

}
