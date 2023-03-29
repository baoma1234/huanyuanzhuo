package caixin.android.com.daomanager;

import java.util.List;

import caixin.android.com.dao.DaoSession;
import caixin.android.com.dao.FriendEntityDao;
import caixin.android.com.dao.GroupEntityDao;
import caixin.android.com.Application;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.entity.GroupEntity;

public class GroupDaoManager {

    private static GroupDaoManager INSTANCE;
    private GroupEntityDao groupEntityDao;

    public static synchronized GroupDaoManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GroupDaoManager();
        }
        return INSTANCE;
    }

    public GroupDaoManager() {
        DaoSession daoSession = Application.getInstance().getDaoSession();
        groupEntityDao = daoSession.getGroupEntityDao();
    }

    /**
     * 会自动判定是插入还是替换
     */
    public void insertOrReplace(GroupEntity groupEntity) {
        try {
            GroupEntity mOldResponseBean = groupEntityDao.queryBuilder().where(GroupEntityDao.Properties.Id.eq(groupEntity.getId())).build().unique();//拿到之前的记录
            if (mOldResponseBean != null) {
                groupEntity.setId(mOldResponseBean.getId());
            }
            groupEntityDao.insertOrReplace(groupEntity);
        } catch (Exception e) {
            groupEntityDao.deleteAll();
            e.printStackTrace();
        }
    }

    public void deleteByGroupId(int id) {
        try {
            GroupEntity mOldResponseBean = groupEntityDao.queryBuilder().where(GroupEntityDao.Properties.Id.eq(id)).build().unique();//拿到之前的记录
            if (mOldResponseBean != null) {
                groupEntityDao.delete(mOldResponseBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 会自动判定是插入还是替换
     */
    public void insertOrReplaceAll(List<GroupEntity> friendEntities) {
        groupEntityDao.deleteAll();
        if (friendEntities == null)
            return;
        groupEntityDao.insertOrReplaceInTx(friendEntities);
    }

    /**
     * 查询所有数据
     */
    public List<GroupEntity> searchAll() {
        List<GroupEntity> searchHistories = groupEntityDao.queryBuilder().list();
        return searchHistories;
    }
}
