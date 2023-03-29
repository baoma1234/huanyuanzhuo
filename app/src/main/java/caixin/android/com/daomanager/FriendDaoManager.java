package caixin.android.com.daomanager;

import android.text.TextUtils;
import android.util.Log;

import caixin.android.com.dao.DaoSession;
import caixin.android.com.dao.FriendEntityDao;
import caixin.android.com.Application;
import caixin.android.com.entity.FriendEntity;

import java.util.List;

public class FriendDaoManager {

    private static FriendDaoManager INSTANCE;
    private FriendEntityDao friendEntityDao;

    public static synchronized FriendDaoManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FriendDaoManager();
        }
        return INSTANCE;
    }

    public FriendDaoManager() {
        DaoSession daoSession = Application.getInstance().getDaoSession();
        friendEntityDao = daoSession.getFriendEntityDao();
    }

    /**
     * 会自动判定是插入还是替换
     */
    public void insertOrReplace(FriendEntity friendEntity) {
        try {
            FriendEntity mOldResponseBean = friendEntityDao.queryBuilder().where(FriendEntityDao.Properties.Id.eq(friendEntity.getId())).build().unique();//拿到之前的记录
            if (mOldResponseBean != null) {
                friendEntity.setId(mOldResponseBean.getId());
            }
            friendEntityDao.insertOrReplace(friendEntity);
        } catch (Exception e) {
            friendEntityDao.deleteAll();
            e.printStackTrace();
        }
//        conversationReponseDao.insertOrReplace(conversationReponse);
    }

    public void removeToBlackList(int id) {
        try {
            FriendEntity mOldResponseBean = friendEntityDao.queryBuilder().where(FriendEntityDao.Properties.Id.eq(id)).build().unique();//拿到之前的记录
            if (mOldResponseBean != null) {
                mOldResponseBean.setIsBlack(1);
                friendEntityDao.insertOrReplace(mOldResponseBean);
            }
        } catch (Exception e) {
            friendEntityDao.deleteAll();
            e.printStackTrace();
        }
//        conversationReponseDao.insertOrReplace(conversationReponse);
    }


    public void deleteFriend(FriendEntity friendEntity) {
        try {
            FriendEntity mOldResponseBean = friendEntityDao.queryBuilder().where(FriendEntityDao.Properties.Id.eq(friendEntity.getId())).build().unique();//拿到之前的记录
            if (mOldResponseBean != null) {
                friendEntityDao.delete(mOldResponseBean);
            }
        } catch (Exception e) {
            friendEntityDao.deleteAll();
            e.printStackTrace();
        }
//        conversationReponseDao.insertOrReplace(conversationReponse);
    }


    public void deleteFriendById(int friendId) {
        try {
            FriendEntity mOldResponseBean = friendEntityDao.queryBuilder().where(FriendEntityDao.Properties.Id.eq(friendId)).build().unique();//拿到之前的记录
            if (mOldResponseBean != null) {
                friendEntityDao.delete(mOldResponseBean);
            }
        } catch (Exception e) {
            friendEntityDao.deleteAll();
            e.printStackTrace();
        }
//        conversationReponseDao.insertOrReplace(conversationReponse);
    }

    /**
     * 会自动判定是插入还是替换
     */
    public void insertOrReplaceAll(List<FriendEntity> friendEntities) {
        friendEntityDao.deleteAll();
        friendEntityDao.insertOrReplaceInTx(friendEntities);
    }


    private static final String TAG = "FriendDaoManager";

    /**
     * 查询所有数据
     */
    public List<FriendEntity> searchAll() {
        List<FriendEntity> searchHistories = friendEntityDao.queryBuilder().list();
        return searchHistories;
    }

    public FriendEntity searchById(int id) {
        return friendEntityDao.queryBuilder().where(FriendEntityDao.Properties.Id.eq(id)).build().unique();
    }

    //存在返回true 不存在返回false
    public FriendEntity searchByAccount(String account) {
        if (TextUtils.isEmpty(account)) {
            return null;
        }
        try {
            FriendEntity friendEntity = friendEntityDao.queryBuilder().where(FriendEntityDao.Properties.Mobile.eq(account)).build().unique();
            return friendEntity;
        } catch (Exception e) {
            return null;
        }
    }
}
