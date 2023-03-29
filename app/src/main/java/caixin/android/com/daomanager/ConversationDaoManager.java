package caixin.android.com.daomanager;

import java.util.List;

import caixin.android.com.Application;
import caixin.android.com.dao.DaoSession;
import caixin.android.com.dao.SendMessageResponseDao;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.entity.SendMessageResponse;

public class ConversationDaoManager {
    private static ConversationDaoManager INSTANCE;
    private SendMessageResponseDao conversationReponseDao;

    public static synchronized ConversationDaoManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConversationDaoManager();
        }
        return INSTANCE;
    }

    public ConversationDaoManager() {
        DaoSession daoSession = Application.getInstance().getDaoSession();
        conversationReponseDao = daoSession.getSendMessageResponseDao();
    }


    private static final String TAG = "ConversationDaoManager";

    /**
     * 会自动判定是插入还是替换
     */
    public void insertOrReplace(SendMessageResponse conversationReponse, boolean unreadNeedAdd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SendMessageResponse mOldResponseBean = null;
                    List<SendMessageResponse> searchHistories = conversationReponseDao.queryBuilder().list();
                    for (SendMessageResponse item : searchHistories) {
                        if (conversationReponse.getTotype() == SendMessageResponse.TOTYPE_GROUP && item.getTotype() == SendMessageResponse.TOTYPE_GROUP) {
                            if (item.getGroupId() == (conversationReponse.getGroupId())) {
                                mOldResponseBean = item;
                                break;
                            }
                        } else if (conversationReponse.getTotype() != SendMessageResponse.TOTYPE_GROUP && item.getTotype() != SendMessageResponse.TOTYPE_GROUP) {
                            if (conversationReponse.getGetId() == item.getGetId() && conversationReponse.getSendId() == item.getSendId()) {
                                mOldResponseBean = item;
                                break;
                            }
                            if (conversationReponse.getGetId() == item.getSendId() && conversationReponse.getSendId() == item.getGetId()) {
                                mOldResponseBean = item;
                                break;
                            }
                        }
                    }
                    if (mOldResponseBean != null) {
                        mOldResponseBean.setUpdatetime(conversationReponse.getUpdatetime());
                        mOldResponseBean.setContents(conversationReponse.getContents());
                        mOldResponseBean.setIsread(conversationReponse.getIsread());
                        mOldResponseBean.setPid(conversationReponse.getPid());
                        mOldResponseBean.setMessageId(conversationReponse.getId().intValue());
                        mOldResponseBean.setHeadImg(conversationReponse.getHeadImg());
                        mOldResponseBean.setNikeName(conversationReponse.getNikeName());
                        if (unreadNeedAdd) {
                            mOldResponseBean.setUnread(mOldResponseBean.getUnread() + 1);
                        } else {
                            mOldResponseBean.setUnread(0);
                        }
                    } else {
                        mOldResponseBean = conversationReponse;
                    }
                    conversationReponseDao.insertOrReplace(mOldResponseBean);
                } catch (Exception e) {
                    conversationReponseDao.deleteAll();
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 会自动判定是插入还是替换
     */
    public void insertOrReplaceAll(List<SendMessageResponse> conversationReponses) {
        conversationReponseDao.deleteAll();
        conversationReponseDao.insertOrReplaceInTx(conversationReponses);
    }

    public void deleteAll() {
        conversationReponseDao.deleteAll();
    }

    /**
     * 查询所有数据
     */
    public List<SendMessageResponse> searchAll() {
        List<SendMessageResponse> searchHistories = conversationReponseDao.queryBuilder().list();
        return searchHistories;
    }


    public void deleteByFriend(FriendEntity friendEntity) {
        try {
            SendMessageResponse mOldResponseBean = conversationReponseDao.queryBuilder().whereOr(SendMessageResponseDao.Properties.SendId.eq(friendEntity.getId()), SendMessageResponseDao.Properties.GetId.eq(friendEntity.getId())).build().unique();//拿到之前的记录
            if (mOldResponseBean != null) {
                conversationReponseDao.delete(mOldResponseBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteByFriendID(int friendId) {
        try {
            SendMessageResponse mOldResponseBean = conversationReponseDao.queryBuilder().whereOr(SendMessageResponseDao.Properties.SendId.eq(friendId), SendMessageResponseDao.Properties.GetId.eq(friendId)).build().unique();//拿到之前的记录
            if (mOldResponseBean != null) {
                conversationReponseDao.delete(mOldResponseBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteByGroupId(int groupId) {
        try {
            SendMessageResponse mOldResponseBean = conversationReponseDao.queryBuilder().where(SendMessageResponseDao.Properties.GroupId.eq(groupId)).build().unique();//拿到之前的记录
            if (mOldResponseBean != null) {
                conversationReponseDao.delete(mOldResponseBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SendMessageResponse getByFriendId(int friendId) {
        try {
            SendMessageResponse mOldResponseBean = conversationReponseDao.queryBuilder().whereOr(SendMessageResponseDao.Properties.SendId.eq(friendId), SendMessageResponseDao.Properties.GetId.eq(friendId)).build().unique();//拿到之前的记录
            return mOldResponseBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
