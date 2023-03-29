package caixin.android.com.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.entity.FriendNewsEntity;
import caixin.android.com.utils.DateUtil;
import caixin.android.com.utils.DpUtil;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.view.activity.PostImageDetailActivity;
import caixin.android.com.widget.MultiImageView;
import caixin.android.com.widget.friendcicle.ActionItem;
import caixin.android.com.widget.friendcicle.CommentListView;
import caixin.android.com.widget.friendcicle.ExpandTextView;
import caixin.android.com.widget.friendcicle.GridDividerItemDecoration;
import caixin.android.com.widget.friendcicle.PraiseListView;
import caixin.android.com.widget.friendcicle.SnsPopupWindow;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by jogger on 2019/6/11
 * 描述：
 */
public class FriendNewsAdapter extends BaseQuickAdapter<FriendNewsEntity, BaseViewHolder> {

    public FriendNewsAdapter(@Nullable List<FriendNewsEntity> data) {
        super(R.layout.rv_friend_news_item, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, FriendNewsEntity item) {
        //头像
        ImgLoader.GlideLoadCircle(helper.getView(R.id.iv_avatar), item.getUserInfo().getImg(), R.mipmap.img_user_head);
        //图片内容
        List<String> imgs = new ArrayList<>();
        if (item.getImgs() != null && !item.getImgs().isEmpty()) {
            for (int i = 0; i < item.getImgs().size(); i++) {
                imgs.add(item.getImgs().get(i));
            }
        }
        MultiImageView multiImageView = helper.getView(R.id.ll_img_container);
        multiImageView.setList(imgs);
        multiImageView.setOnItemClickListener((view, position) -> PostImageDetailActivity.navTo(mContext, imgs, position));

        //文字内容
        ExpandTextView contentTextView = helper.getView(R.id.tv_content);
        if (item.getContent() == null || item.getContent().length() <= 0) {
            contentTextView.setVisibility(View.GONE);
        } else {
            contentTextView.setVisibility(View.VISIBLE);
            contentTextView.setText(item.getContent());
        }
        //昵称
        helper.setText(R.id.tv_title, item.getUserInfo().getNikename());
        //发布时间
        helper.setText(R.id.tv_time, DateUtil.formatDate(item.getAdd_time()));
        //删除
        TextView deleteTextview = helper.getView(R.id.tv_delete);
        if (item.getUserInfo().getId() == MMKVUtil.getUserInfo().getId()) {
            deleteTextview.setVisibility(View.VISIBLE);
            deleteTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageDialog.build((AppCompatActivity) mContext)
                            .setStyle(DialogSettings.STYLE.STYLE_IOS)
                            .setTitle(mContext.getResources().getString(R.string.prompt))
                            .setMessage(mContext.getResources().getString(R.string.confirm_delete))
                            .setOkButton(mContext.getResources().getString(R.string.confirm), new OnDialogButtonClickListener() {
                                @Override
                                public boolean onClick(BaseDialog baseDialog, View v) {
                                    mMyOnClick.onDeleteFCClick(item, helper.getLayoutPosition());
                                    return false;
                                }
                            })
                            .setCancelButton(mContext.getResources().getString(R.string.cancel))
                            .show();
                }
            });
        } else {
            deleteTextview.setVisibility(View.GONE);
        }

        TextView collectTextView = helper.getView(R.id.tv_collect);
        collectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyOnClick != null)
                    mMyOnClick.onCollectClick(item);
            }
        });

        //点赞评论pop
        final SnsPopupWindow snsPopupWindow = new SnsPopupWindow(mContext);
        if (isMineLike(item)) {
            snsPopupWindow.getmActionItems().get(0).mTitle = mContext.getString(R.string.cancel);
        } else {
            snsPopupWindow.getmActionItems().get(0).mTitle = "赞";
        }
        snsPopupWindow.update();
        snsPopupWindow.setmItemClickListener(new SnsPopupWindow.OnItemClickListener() {
            private long mLasttime = 0;

            @Override
            public void onItemClick(ActionItem actionItem, int position) {
                switch (position) {
                    case 0://点赞、取消点赞
                        if (System.currentTimeMillis() - mLasttime < 700)//防止快速点击操作
                            return;
                        mLasttime = System.currentTimeMillis();
                        if (mMyOnClick != null) {
                            if ("赞".equals(actionItem.mTitle.toString())) {
                                mMyOnClick.onDigClick(false, item, helper.getLayoutPosition());
                            } else {//取消点赞
                                mMyOnClick.onDigClick(true, item, helper.getLayoutPosition());
                            }
                        }
                        break;
                    case 1://发布评论
                        mMyOnClick.onCommentClick(item, helper.getLayoutPosition());
                        break;
                    default:
                        break;
                }
            }
        });
        helper.getView(R.id.snsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出popupwindow
                snsPopupWindow.showPopupWindow(view);
            }
        });

        //点赞和评论
        boolean hasFavort = item.getThumbs() != null && !item.getThumbs().isEmpty();
        boolean hasComment = item.getComments() != null && !item.getComments().isEmpty();

        LinearLayout digCommentBody = helper.getView(R.id.digCommentBody);
        CommentListView commentListView = helper.getView(R.id.commentList);
        PraiseListView praiseListView = helper.getView(R.id.praiseListView);
        helper.setGone(R.id.lin_dig, hasFavort && hasComment);
        if (hasComment || hasFavort) {
            if (hasFavort) {//处理点赞列表
                praiseListView.setOnItemClickListener(new PraiseListView.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                      /*  String userName = favortDatas.get(position).getUser().getName();
                        String userId = favortDatas.get(position).getUser().getId();
                        Toast.makeText(MyApplication.getContext(), userName + " &id = " + userId, Toast.LENGTH_SHORT).show();*/
                    }
                });
                praiseListView.setDatas(item.getThumbs());
                praiseListView.setVisibility(View.VISIBLE);
            } else {
                praiseListView.setVisibility(View.GONE);
            }

            if (hasComment) {//处理评论列表
                commentListView.setOnItemClickListener(new CommentListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int commentPosition, int toUid) {
                        mMyOnClick.onCommentReplay(item, helper.getLayoutPosition(), toUid);
                    }

                    @Override
                    public void onDeleteClick(FriendNewsEntity.CommentsBean position) {
                        mMyOnClick.onCommentDelete(item, helper.getLayoutPosition(), position);
                    }

                    @Override
                    public void onCopyClick(FriendNewsEntity.CommentsBean position) {
                        mMyOnClick.onCommentCopy(item, helper.getLayoutPosition(), position);
                    }
                });
                commentListView.setOnItemLongClickListener(new CommentListView.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(int commentPosition) {
                      /*  //长按进行复制或者删除
                        CommentItem commentItem = commentsDatas.get(commentPosition);
                        CommentDialog dialog = new CommentDialog(context, presenter, commentItem, circlePosition);
                        dialog.show();*/
                    }
                });
                commentListView.setDatas(item.getComments());
                commentListView.setVisibility(View.VISIBLE);
            } else {
                commentListView.setVisibility(View.GONE);
            }
            digCommentBody.setVisibility(View.VISIBLE);
        } else {
            digCommentBody.setVisibility(View.GONE);
        }
        helper.addOnClickListener(R.id.iv_avatar);
    }

    private boolean isMineLike(FriendNewsEntity friendNewsEntity) {
        int myId = MMKVUtil.getUserInfo().getId();
        if (friendNewsEntity.getThumbs() != null && !friendNewsEntity.getThumbs().isEmpty()) {
            for (int i = 0; i < friendNewsEntity.getThumbs().size(); i++) {
                if (friendNewsEntity.getThumbs().get(i).getId() == myId) {
                    return true;
                }
            }
        }
        return false;
    }

    private MyOnClick mMyOnClick;

    public void setMyOnclick(MyOnClick myOnClick) {
        this.mMyOnClick = myOnClick;
    }

    public interface MyOnClick {
        void onDigClick(boolean isCancel, FriendNewsEntity item, int position);

        void onCommentClick(FriendNewsEntity item, int position);

        void onDeleteFCClick(FriendNewsEntity item, int position);

        void onCommentReplay(FriendNewsEntity item, int position, int toUid);

        void onCommentDelete(FriendNewsEntity item, int position, FriendNewsEntity.CommentsBean commentsBean);

        void onCommentCopy(FriendNewsEntity item, int position, FriendNewsEntity.CommentsBean commentId);

        void onCollectClick(FriendNewsEntity item);

    }
}
