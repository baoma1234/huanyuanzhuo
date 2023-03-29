package caixin.android.com.utils;

import android.text.TextUtils;

import caixin.android.com.constant.Contact;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.PicChannel;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.http.basic.config.HttpCode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mmkv.MMKV;

import java.util.List;

public class MMKVUtil {
    private static MMKV mmkv = MMKV.defaultMMKV();

    public static String getToken() {
        return mmkv.getString(Extras.TOKEN, "");
    }

    public static void setToken(String token) {
        mmkv.encode(Extras.TOKEN, token);
    }

    public static void setConnectedState(int state) {
        mmkv.encode(Extras.CONNECT_STATE, state);
    }

    public static int getConnectedState() {
        return mmkv.getInt(Extras.CONNECT_STATE, HttpCode.CONNECT_STATE_DISCONNECTED);
    }

    public static String getHomeImageAdTime() {
        return mmkv.getString(Extras.HOME_IMAGE_AD_TIME, "1");
    }

    public static void setHomeImageAdTime(String currentTime) {
        mmkv.encode(Extras.HOME_IMAGE_AD_TIME, currentTime);
    }

    public static String getLoginPhone() {
        return mmkv.getString(Extras.PHONE, "");
    }

    public static void setLoginPhone(String phone) {
        mmkv.encode(Extras.PHONE, phone);
    }

    public static boolean isRememberPass() {
        return true;
    }

    public static void setIsRememberPass(boolean isFirstInto) {
        mmkv.encode(Extras.FIRST_INTO, isFirstInto);
    }

    public static String getLoginPassword() {
        return mmkv.getString(Extras.PASSWORD, "");
    }

    public static void setLoginPassword(String phone) {
        new Exception().printStackTrace();
        mmkv.encode(Extras.PASSWORD, phone);
    }

    public static UserInfoEntity getUserInfo() {
        UserInfoEntity loginResult;
        String json = mmkv.getString(Extras.LOGIN_RESULT, "");
        if (TextUtils.isEmpty(json)) {
            loginResult = new UserInfoEntity();
        } else {
            loginResult = GsonUtil.fromJson(json, UserInfoEntity.class);
        }
        if (loginResult == null)
            loginResult = new UserInfoEntity();
        return loginResult;
    }

    public static void setUserInfo(UserInfoEntity loginResult) {
        if (loginResult == null) {
            mmkv.removeValueForKey(Extras.LOGIN_RESULT);
            return;
        }
        mmkv.encode(Extras.LOGIN_RESULT, GsonUtil.toJson(loginResult));
    }


    /**
     * 获取摇一摇的游戏号码
     */
    public static List<Integer> getShakeList() {
        return new Gson().fromJson(mmkv.decodeString(Extras.SHAKE_LIST), new TypeToken<List<Integer>>() {
        }.getType());
    }



    /**
     * 储存摇一摇的游戏号码
     */
    public static void setShakeList(List<Integer> Shake) {
        mmkv.encode(Extras.SHAKE_LIST, new Gson().toJson(Shake));
    }


    /**
     * 获取天机测算的游戏号码
     */
    public static List<Integer> getWaveColorAnimalList() {
        return new Gson().fromJson(mmkv.decodeString(Extras.WAVE_COLOR_ANIMAL_LIST), new TypeToken<List<Integer>>() {
        }.getType());
    }

    /**
     * 储存波肖转盘游戏号码
     */
    public static void setWaveColorAnimalList(List<Integer> Wave) {
        mmkv.encode(Extras.WAVE_COLOR_ANIMAL_LIST, new Gson().toJson(Wave));
    }


    /**
     * 储存恋人特码的游戏号码
     */
    public static void setLoverList(List<Integer> lover) {
        mmkv.encode(Extras.LOVER_LIST, new Gson().toJson(lover));
    }

    /**
     * 获取恋人特码的游戏号码
     */
    public static List<Integer> getLoveList() {
        return new Gson().fromJson(mmkv.decodeString(Extras.LOVER_LIST), new TypeToken<List<Integer>>() {
        }.getType());
    }

    /**
     * 获取生肖卡牌的游戏号码
     */
    public static List<Integer> getAnimalList() {
        return new Gson().fromJson(mmkv.decodeString(Extras.ANIMAL_LIST), new TypeToken<List<Integer>>() {
        }.getType());
    }

    /**
     * 储存生肖卡牌的游戏号码
     */
    public static void setAnimalList(List<Integer> Animal) {
        mmkv.encode(Extras.ANIMAL_LIST, new Gson().toJson(Animal));
    }


    public static PicChannel getPicChannel() {
        PicChannel loginResult;
        String json = mmkv.getString(Extras.PIC_CHANNEL, "");
        if (TextUtils.isEmpty(json)) {
            loginResult = new PicChannel();
        } else {
            loginResult = GsonUtil.fromJson(json, PicChannel.class);
        }
        if (loginResult == null)
            loginResult = new PicChannel();
        return loginResult;
    }

    public static void setPicChannel(PicChannel loginResult) {
        if (loginResult == null) {
            mmkv.removeValueForKey(Extras.PIC_CHANNEL);
            return;
        }
        mmkv.encode(Extras.PIC_CHANNEL, GsonUtil.toJson(loginResult));
    }

    public static int getSelectedPicChannel() {
        return mmkv.decodeInt(Extras.SELECTED_PIC_CHANNEL);
    }

    public static void setSelectedPicChannel(int selectedPicChannel) {
        mmkv.encode(Extras.SELECTED_PIC_CHANNEL, selectedPicChannel);
    }


    public static int getLoginTimes() {
        return mmkv.decodeInt(Extras.LOGIN_TIMES);
    }

    //存储期号，用于判断是否可以进行本地游戏
    public static void setLoginTimes(int time) {
        mmkv.encode(Extras.LOGIN_TIMES, time);
    }
}