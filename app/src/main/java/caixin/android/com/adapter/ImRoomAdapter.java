package caixin.android.com.adapter;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caixin.huanyu.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import caixin.android.com.Application;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.entity.PicChannel;
import caixin.android.com.entity.SendMessageResponse;
import caixin.android.com.utils.ClickUtil;
import caixin.android.com.utils.DateUtil;
import caixin.android.com.utils.DimensUtils;
import caixin.android.com.utils.DpUtil;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.utils.TextRender;
import caixin.android.com.view.activity.ChatRoomActivity;
import caixin.android.com.view.activity.PostImageDetailActivity;
import caixin.android.com.view.activity.SimplePlayer;
import caixin.android.com.widget.BubbleLayout;
import caixin.android.com.widget.MyImageView;
import caixin.android.com.widget.PopupList;
import caixin.android.com.widget.TextDialog;


/**
 * Created by cxf on 2018/10/25.
 */

public class ImRoomAdapter extends RecyclerView.Adapter {

    private static final int TYPE_TEXT_LEFT = 1;
    private static final int TYPE_TEXT_RIGHT = 2;
    private static final int TYPE_IMAGE_LEFT = 3;
    private static final int TYPE_IMAGE_RIGHT = 4;
    private static final int TYPE_RED_PACK_LEFT = 5;
    private static final int TYPE_RED_PACK_RIGHT = 6;
    private static final int TYPE_VIDEO_LEFT = 7;
    private static final int TYPE_VIDEO_RIGHT = 8;
    private static final int TYPE_FILE_LEFT = 9;
    private static final int TYPE_FILE_RIGHT = 10;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private List<SendMessageResponse> mList;
    private LayoutInflater mInflater;
    private ActionListener mActionListener;
    private View.OnClickListener mOnImageClickListener;
    private View.OnClickListener mOnVideoClickListener;
    private View.OnClickListener mOnRedPackClickListener;
    private View.OnLongClickListener mOnAvatarLongClickListener;
    private View.OnClickListener mOnAvatarClickListener;
    private int[] mLocation;
    private ValueAnimator mAnimator;

    private Context mContext;

    public ImRoomAdapter(Activity context) {
        mList = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        mLocation = new int[2];
        mOnImageClickListener = v -> {
            if (!ClickUtil.canClick()) {
                return;
            }
            MyImageView imageView = (MyImageView) v;
            imageView.getLocationOnScreen(mLocation);
            final int position = imageView.getMsgId();
            SendMessageResponse bean = mList.get(position);
            if (mActionListener != null) {
                mActionListener.onImageClick(imageView, mLocation[0], mLocation[1], bean);
            }
        };


        mOnVideoClickListener = v -> {
            if (!ClickUtil.canClick()) {
                return;
            }
            MyImageView imageView = (MyImageView) v;
            final int position = imageView.getMsgId();
            SendMessageResponse bean = mList.get(position);
            if (mActionListener != null) {
                mActionListener.onVideoClick(bean);
            }
            return;
        };

        mOnRedPackClickListener = v -> {
            Object tag = v.getTag();
            if (tag == null) {
                return;
            }
            if (mActionListener != null) {
                mActionListener.onRedPackClick((SendMessageResponse) tag, v);
            }
            return;
        };
        mOnAvatarLongClickListener = v -> {
            Object tag = v.getTag();
            if (tag == null) {
                return false;
            }
            final int position = (int) tag;
            SendMessageResponse bean = mList.get(position);
            if (mActionListener != null) {
                mActionListener.onAvatarLongClick(bean);
                return true;
            }
            return false;
        };

        mOnAvatarClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = v.getTag();
                if (tag == null) {
                    return;
                }
                final int position = (int) tag;
                SendMessageResponse bean = mList.get(position);
                if (mActionListener != null) {
                    mActionListener.onAvatarClick(bean);
                }
            }
        };
        mAnimator = ValueAnimator.ofFloat(0, 900);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setDuration(700);
        mAnimator.setRepeatCount(-1);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
              /*  if (mChatVoiceLayout != null) {
                    mChatVoiceLayout.animate((int) (v / 300));
                }*/
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        SendMessageResponse msg = mList.get(position);
        switch (msg.getType()) {
            case SendMessageResponse.TYPE_TEXT:
                if (msg.isFromSelf()) {
                    return TYPE_TEXT_RIGHT;
                } else {
                    return TYPE_TEXT_LEFT;
                }
            case SendMessageResponse.TYPE_IMAGE:
                if (msg.isFromSelf()) {
                    return TYPE_IMAGE_RIGHT;
                } else {
                    return TYPE_IMAGE_LEFT;
                }
            case SendMessageResponse.TYPE_RED_PACK:
                if (msg.isFromSelf()) {
                    return TYPE_RED_PACK_RIGHT;
                } else {
                    return TYPE_RED_PACK_LEFT;
                }
            case SendMessageResponse.TYPE_VIDEO:
                if (msg.isFromSelf()) {
                    return TYPE_VIDEO_RIGHT;
                } else {
                    return TYPE_VIDEO_LEFT;
                }
            case SendMessageResponse.TYPE_FILE:
                if (msg.isFromSelf()) {
                    return TYPE_FILE_RIGHT;
                } else {
                    return TYPE_FILE_LEFT;
                }
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TEXT_LEFT:
                return new TextVh(mInflater.inflate(R.layout.item_chat_text_left, parent, false));
            case TYPE_TEXT_RIGHT:
                return new SelfTextVh(mInflater.inflate(R.layout.item_chat_text_right, parent, false));
            case TYPE_IMAGE_LEFT:
                return new ImageVh(mInflater.inflate(R.layout.item_chat_image_left, parent, false));
            case TYPE_IMAGE_RIGHT:
                return new SelfImageVh(mInflater.inflate(R.layout.item_chat_image_right, parent, false));
            case TYPE_RED_PACK_LEFT:
                return new RedPackVh(mInflater.inflate(R.layout.item_chat_red_pack_left, parent, false));
            case TYPE_RED_PACK_RIGHT:
                return new RedPackVh(mInflater.inflate(R.layout.item_chat_red_pack_right, parent, false));
            case TYPE_VIDEO_LEFT:
                return new VideoVh(mInflater.inflate(R.layout.item_chat_video_left, parent, false));
            case TYPE_VIDEO_RIGHT:
                return new SelfVideoVh(mInflater.inflate(R.layout.item_chat_video_right, parent, false));
            case TYPE_FILE_LEFT:
                return new FileVh(mInflater.inflate(R.layout.item_chat_file_left, parent, false));
            case TYPE_FILE_RIGHT:
                return new SelfFileVh(mInflater.inflate(R.layout.item_chat_file_right, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position, List payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        ((Vh) vh).setData(mList.get(position), position, payload);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    }

    private static final String TAG = "ImRoomAdapter";

    public int insertItem(SendMessageResponse bean) {
        if (mList != null && bean != null) {
            mList.add(bean);
            int size = mList.size();
            notifyItemChanged(size);
            notifyItemInserted(size);
            int lastItemPosition = mLayoutManager.findLastVisibleItemPosition();
            Log.e(TAG, "insertItem: " + size + ",lastItemPosition = " + lastItemPosition);
            if (lastItemPosition >= size - 3) {
                scrollToBottom();
            } else {
                if (bean.isFromSelf()) {
                    scrollToBottom();
                }
            }
            return size;
        }
        return -1;
    }

    public void setList(List<SendMessageResponse> list) {
        if (mList != null && list != null && list.size() > 0) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
            scrollToBottom();
        }
    }

    public void addList(List<SendMessageResponse> list) {
        if (mList != null && list != null && list.size() > 0) {
            mList.addAll(0, list);
            notifyDataSetChanged();
            scrollToWhen();
        }
    }

    public void clearList() {
        if (mList != null)
            mList.clear();
    }

    public void scrollToBottom() {
        if (mList.size() > 0 && mLayoutManager != null) {
            mLayoutManager.scrollToPositionWithOffset(mList.size() - 1, -DpUtil.dp2px(20));
        }
        if (mContext instanceof ChatRoomActivity) {
            ((ChatRoomActivity) mContext).GoneScrollToBottomView();
        }
    }

    public void scrollToWhen() {
        if (mList.size() > 0 && mLayoutManager != null) {
            mLayoutManager.scrollToPositionWithOffset(15, -DpUtil.dp2px(20));
        }
    }

    public void notifyItem(SendMessageResponse bean) {
        int position = 0;
        for (int i = 0; i < mList.size(); i++) {
            if (bean.getId() == mList.get(i).getId()) {
                position = i;
                break;
            }
        }
        mList.set(position, bean);
        notifyItemChanged(position);
    }

    public void deleteByMessageId(int id) {
        SendMessageResponse messageResponse = null;
        if (mList != null && mList.size() > 0) {
            for (SendMessageResponse item : mList) {
                if (item.getId() == id) {
                    messageResponse = item;
                    break;
                }
            }
        }
        if (messageResponse != null) {
            mList.remove(messageResponse);
            notifyDataSetChanged();
        }
    }

    public SendMessageResponse getLastMessage() {
        if (mList == null || mList.size() == 0) {
            return null;
        }
        return mList.get(mList.size() - 1);
    }


    public SendMessageResponse getOldestMessage() {
        if (mList == null || mList.size() == 0) {
            return null;
        }
        return mList.get(0);
    }

    public List<SendMessageResponse> getList() {
        return mList;
    }

    class Vh extends RecyclerView.ViewHolder {
        ImageView mAvatar;
        FrameLayout mAvatarContainer;
        TextView mTime;
        TextView nickName;
        SendMessageResponse mImMessageBean;

        public Vh(View itemView) {
            super(itemView);
            mAvatarContainer = itemView.findViewById(R.id.avatar_container);
            mAvatar = (ImageView) itemView.findViewById(R.id.avatar);
            mTime = (TextView) itemView.findViewById(R.id.time);
            nickName = itemView.findViewById(R.id.text_nickName);
            mAvatarContainer.setOnLongClickListener(mOnAvatarLongClickListener);
            mAvatarContainer.setOnClickListener(mOnAvatarClickListener);
        }

        void setData(SendMessageResponse bean, int position, Object payload) {
            mAvatarContainer.setTag(position);
            mImMessageBean = bean;
            if (bean.getManager() == MemberEntity.MANAGER_BOSS) {
                nickName.setTextColor(mContext.getResources().getColor(R.color.darkviolet));
            } else if (bean.getManager() == MemberEntity.MANAGER_MANAGER) {
                nickName.setTextColor(mContext.getResources().getColor(R.color.red));
            } else {
                nickName.setTextColor(mContext.getResources().getColor(R.color.nickName));
            }
            if (bean.isFromSelf()) {
                ImgLoader.loadGif(mAvatar, bean.getHeadImg());
                nickName.setText(Application.getInstance().getString(R.string.me));
            } else {
                ImgLoader.loadGif(mAvatar, bean.getHeadImg());
                nickName.setText(bean.getNikeName());
                if (!TextUtils.isEmpty(bean.getTag())) {
                    nickName.setText(bean.getTag());
                }
            }
            mTime.setText(DateUtil.formatDate(bean.getAddtime()));
        }
    }

    class TextVh extends Vh {

        TextView mText;
        TextView tv_reply_content;
        ImageView iv_reply;
        RelativeLayout rl_reply;
        private List<String> popupMenuItemList = new ArrayList<>();

        public TextVh(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.text);
            rl_reply = itemView.findViewById(R.id.rl_reply);
            tv_reply_content = itemView.findViewById(R.id.tv_reply_content);
            iv_reply = itemView.findViewById(R.id.iv_reply);
        }

        @Override
        public void setData(SendMessageResponse bean, int position, Object payload) {
            super.setData(bean, position, payload);
            if (bean.getTotype() != 0) {
                popupMenuItemList.clear();
                popupMenuItemList.add(Application.getInstance().getString(R.string.message_text_copy));
                popupMenuItemList.add(Application.getInstance().getString(R.string.message_text_delete));
                popupMenuItemList.add(Application.getInstance().getString(R.string.collect));
                popupMenuItemList.add("回复");
                PopupList normalViewPopupList = new PopupList(mContext);
                normalViewPopupList.bind(mText, popupMenuItemList, new PopupList.PopupListListener() {
                    @Override
                    public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                        return true;
                    }

                    @Override
                    public void onPopupListClick(View contextView, int contextPosition, int position) {
                        Object tag = contextView.getTag();
                        if (tag == null) {
                            return;
                        }
                        int itemPosition = (int) tag;
                        SendMessageResponse bean = mList.get(itemPosition);
                        if (mActionListener != null) {
                            if (position == 1) {
                                mActionListener.onMessageDelete(bean);
                            } else if (position == 0) {
                                mActionListener.onMessageCopy(bean);
                            } else if (position == 2) {
                                mActionListener.onMessageCollect(bean);
                            } else if (position == 3) {
                                mActionListener.onReplyClick(bean);
                            }
                        }
                    }
                });
            }
            mText.setTag(position);
            if (payload == null) {
                String text = bean.getContents();
                text = Html.fromHtml(text).toString();
//                Html.fromHtml(TextRender.renderChatMessage(text).toString());
                mText.setText(TextRender.renderChatMessage(text));
            }

            if (bean.getReplystatus() == 1) {
                rl_reply.setVisibility(View.VISIBLE);
                if (bean.getReplay_pid() == 1) {
                    tv_reply_content.setText(bean.getReply_data().getContents());
                    tv_reply_content.setVisibility(View.VISIBLE);
                    iv_reply.setVisibility(View.GONE);
                    rl_reply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!ClickUtil.canClick())
                                return;
                            TextDialog textDialog = new TextDialog(bean.getReply_data().getContents());
                            textDialog.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "TextDialog");
                        }
                    });
                } else if (bean.getReplay_pid() == 3) {
                    ImgLoader.GlideLoad(iv_reply, bean.getReply_data().getImgurl(), R.mipmap.web_default);
                    iv_reply.setVisibility(View.VISIBLE);
                    tv_reply_content.setText("【图片】");
                    rl_reply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            List<String> gTzimglist = new ArrayList<>();
                            int position = 0;
                            gTzimglist.add(bean.getReply_data().getImgurl());
                            Intent intent = new Intent(mContext, PostImageDetailActivity.class);
                            intent.putExtra(Extras.IMAGES, (Serializable) gTzimglist);
                            intent.putExtra(Extras.POSITION, position);
                            mContext.startActivity(intent);
                        }
                    });
                } else if (bean.getReplay_pid() == 5) {
                    iv_reply.setVisibility(View.GONE);
                    tv_reply_content.setText("【文件】");
                } else if (bean.getReplay_pid() == 4) {
                    ImgLoader.GlideLoad(iv_reply, bean.getReply_data().getThumbImgUrl(), R.mipmap.web_default);
                    iv_reply.setVisibility(View.VISIBLE);
                    tv_reply_content.setText("【视频】");
                    rl_reply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putString("source", bean.getReply_data().getImgurl());
                            bundle.putString("img", bean.getReply_data().getThumbImgUrl());
                            Intent intent = new Intent(mContext, SimplePlayer.class);
                            if (bundle != null) {
                                intent.putExtras(bundle);
                            }
                            mContext.startActivity(intent);
                        }
                    });
                }
            } else {
                rl_reply.setVisibility(View.GONE);
            }

        }
    }

    class SelfTextVh extends TextVh {

        View mFailIcon;
        View mLoading;
        TextView mText;
        private List<String> popupMenuItemList = new ArrayList<>();

        public SelfTextVh(View itemView) {
            super(itemView);
            mFailIcon = itemView.findViewById(R.id.icon_fail);
            mLoading = itemView.findViewById(R.id.loading);
            mText = itemView.findViewById(R.id.text);

        }

        @Override
        public void setData(SendMessageResponse bean, int position, Object payload) {
            super.setData(bean, position, payload);
            if (bean.getTotype() != 0) {
                popupMenuItemList.clear();
                popupMenuItemList.add(Application.getInstance().getString(R.string.message_text_copy));
                popupMenuItemList.add(Application.getInstance().getString(R.string.message_text_delete));
                popupMenuItemList.add(Application.getInstance().getString(R.string.collect));
                popupMenuItemList.add("回复");
                PopupList normalViewPopupList = new PopupList(mContext);
                normalViewPopupList.bind(mText, popupMenuItemList, new PopupList.PopupListListener() {
                    @Override
                    public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                        return true;
                    }

                    @Override
                    public void onPopupListClick(View contextView, int contextPosition, int position) {
                        Object tag = contextView.getTag();
                        if (tag == null) {
                            return;
                        }
                        int itemPosition = (int) tag;
                        SendMessageResponse bean = mList.get(itemPosition);
                        if (mActionListener != null) {
                            if (position == 1) {
                                mActionListener.onMessageDelete(bean);
                            } else if (position == 0) {
                                mActionListener.onMessageCopy(bean);
                            } else if (position == 2) {
                                mActionListener.onMessageCollect(bean);
                            } else if (position == 3) {
                                mActionListener.onReplyClick(bean);
                            }
                        }
                    }
                });
            }
            mText.setTag(position);
            if (payload == null) {
                String text = bean.getContents();
                mText.setText(TextRender.renderChatMessage(text));
            }
            if (mLoading.getVisibility() == View.VISIBLE) {
                mLoading.setVisibility(View.INVISIBLE);
            }
            if (mFailIcon.getVisibility() == View.VISIBLE) {
                mFailIcon.setVisibility(View.INVISIBLE);
            }
        }
    }


    class FileVh extends Vh {

        TextView mText;
        private List<String> popupMenuItemList = new ArrayList<>();

        public FileVh(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.text);
        }

        @Override
        public void setData(SendMessageResponse bean, int position, Object payload) {
            super.setData(bean, position, payload);
            if (bean.getTotype() != 0) {
                popupMenuItemList.clear();
                popupMenuItemList.add(Application.getInstance().getString(R.string.message_text_delete));
                popupMenuItemList.add(Application.getInstance().getString(R.string.collect));
                popupMenuItemList.add("回复");
                PopupList normalViewPopupList = new PopupList(mContext);
                normalViewPopupList.bind(mText, popupMenuItemList, new PopupList.PopupListListener() {
                    @Override
                    public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                        return true;
                    }

                    @Override
                    public void onPopupListClick(View contextView, int contextPosition, int position) {
                        Object tag = contextView.getTag();
                        if (tag == null) {
                            return;
                        }
                        int itemPosition = (int) tag;
                        SendMessageResponse bean = mList.get(itemPosition);
                        if (mActionListener != null) {
                            if (position == 0) {
                                mActionListener.onMessageDelete(bean);
                            } else if (position == 1) {
                                mActionListener.onMessageCollect(bean);
                            } else if (position == 2) {
                                mActionListener.onReplyClick(bean);
                            }
                        }
                    }
                });
            }
            mText.setTag(position);
            if (payload == null) {
                String text = bean.getContents();
                text = Html.fromHtml(text).toString();
//                Html.fromHtml(TextRender.renderChatMessage(text).toString());
                mText.setText(TextRender.renderChatMessage(text));
            }
        }
    }


    class SelfFileVh extends FileVh {

        View mFailIcon;
        View mLoading;
        TextView mText;
        private List<String> popupMenuItemList = new ArrayList<>();

        public SelfFileVh(View itemView) {
            super(itemView);
            mFailIcon = itemView.findViewById(R.id.icon_fail);
            mLoading = itemView.findViewById(R.id.loading);
            mText = itemView.findViewById(R.id.text);

        }

        @Override
        public void setData(SendMessageResponse bean, int position, Object payload) {
            super.setData(bean, position, payload);
            if (bean.getTotype() != 0) {
                popupMenuItemList.clear();
                popupMenuItemList.add(Application.getInstance().getString(R.string.message_text_delete));
                popupMenuItemList.add(Application.getInstance().getString(R.string.collect));
                popupMenuItemList.add("回复");
                PopupList normalViewPopupList = new PopupList(mContext);
                normalViewPopupList.bind(mText, popupMenuItemList, new PopupList.PopupListListener() {
                    @Override
                    public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                        return true;
                    }

                    @Override
                    public void onPopupListClick(View contextView, int contextPosition, int position) {
                        Object tag = contextView.getTag();
                        if (tag == null) {
                            return;
                        }
                        int itemPosition = (int) tag;
                        SendMessageResponse bean = mList.get(itemPosition);
                        if (mActionListener != null) {
                            if (position == 0) {
                                mActionListener.onMessageDelete(bean);
                            } else if (position == 1) {
                                mActionListener.onMessageCollect(bean);
                            } else if (position == 2) {
                                mActionListener.onReplyClick(bean);
                            }
                        }
                    }
                });
            }
            mText.setTag(position);
            if (payload == null) {
                String text = bean.getContents();
                mText.setText(TextRender.renderChatMessage(text));
            }
            if (mLoading.getVisibility() == View.VISIBLE) {
                mLoading.setVisibility(View.INVISIBLE);
            }
            if (mFailIcon.getVisibility() == View.VISIBLE) {
                mFailIcon.setVisibility(View.INVISIBLE);
            }
        }
    }


    class ImageVh extends Vh {
        MyImageView mImg;
        public List<String> popupMenuItemList = new ArrayList<>();

        public ImageVh(View itemView) {
            super(itemView);
            mImg = (MyImageView) itemView.findViewById(R.id.img);
            mImg.setOnClickListener(mOnImageClickListener);
        }

        @Override
        public void setData(SendMessageResponse bean, int position, Object payload) {
            super.setData(bean, position, payload);
            if (bean.getIs_zl() == 1) {
                itemView.findViewById(R.id.iv_is_ziliao).setVisibility(View.VISIBLE);
            } else {
                itemView.findViewById(R.id.iv_is_ziliao).setVisibility(View.GONE);
            }
            if ((bean.getImg() != null))
                if (bean.getImg().getHeight() > 120 || bean.getImg().getWidth() > 120) {
                    ViewGroup.LayoutParams imageLayoutParams = mImg.getLayoutParams();
                    int width = (DimensUtils.getScreenWidth() - DimensUtils.dp2Px(mContext, 10)) / 2;
                    double height;
                    if (bean.getImg().getWidth() == 0 || bean.getImg().getHeight() == 0) {
                        height = 500;
                    } else {
                        height = (double) width / (double) bean.getImg().getWidth() * bean.getImg().getHeight();
                    }
                    imageLayoutParams.width = width;
                    imageLayoutParams.height = (int) height;
                    mImg.setLayoutParams(imageLayoutParams);
                }
            if (bean.getTotype() != 0) {
                popupMenuItemList.clear();
                popupMenuItemList.add(Application.getInstance().getString(R.string.message_text_delete));
                popupMenuItemList.add(Application.getInstance().getString(R.string.collect));
                popupMenuItemList.add("回复");
                PopupList normalViewPopupList = new PopupList(mContext);
                normalViewPopupList.bind(mImg, popupMenuItemList, new PopupList.PopupListListener() {
                    @Override
                    public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                        return true;
                    }

                    @Override
                    public void onPopupListClick(View contextView, int contextPosition, int position) {
                        MyImageView imageView = (MyImageView) contextView;
                        final int myPosition = imageView.getMsgId();
                        SendMessageResponse bean = mList.get(myPosition);
                        if (mActionListener != null) {
                            if (position == 0) {
                                mActionListener.onMessageDelete(bean);
                            } else if (position == 1) {
                                mActionListener.onMessageCollect(bean);
                            } else if (position == 2) {
                                mActionListener.onReplyClick(bean);
                            }
                        }
                    }
                });
            }
            if (payload == null) {
                mImg.setMsgId(position);
                if (selectedPicChannel > 0) {
                    String url = bean.getImg().getImgurl();
                    url = url.replaceAll(picChannel.getData().get(0), picChannel.getData().get(selectedPicChannel));
                    ImgLoader.ChatRoomRoundCorners(url, mImg, R.mipmap.web_default);
                } else {
                    ImgLoader.ChatRoomRoundCorners(bean.getImg().getImgurl(), mImg, R.mipmap.web_default);
                }
            }
        }
    }

    class VideoVh extends Vh {
        MyImageView mImg;
        public List<String> popupMenuItemList = new ArrayList<>();

        public VideoVh(View itemView) {
            super(itemView);
            mImg = (MyImageView) itemView.findViewById(R.id.img);
            mImg.setOnClickListener(mOnVideoClickListener);
        }

        @Override
        public void setData(SendMessageResponse bean, int position, Object payload) {
            super.setData(bean, position, payload);
            if ((bean.getImg() != null))
                if (bean.getImg().getHeight() > 120 || bean.getImg().getWidth() > 120) {
                    ViewGroup.LayoutParams imageLayoutParams = mImg.getLayoutParams();
                    int width = (DimensUtils.getScreenWidth() - DimensUtils.dp2Px(mContext, 10)) / 2;
                    double height;
                    if (bean.getImg().getWidth() == 0 || bean.getImg().getHeight() == 0) {
                        height = 500;
                    } else {
                        height = (double) width / (double) bean.getImg().getWidth() * bean.getImg().getHeight();
                    }
                    imageLayoutParams.width = width;
                    imageLayoutParams.height = (int) height;
                    mImg.setLayoutParams(imageLayoutParams);
                }
            if (bean.getTotype() != 0) {
                popupMenuItemList.clear();
                popupMenuItemList.add(Application.getInstance().getString(R.string.message_text_delete));
                popupMenuItemList.add(Application.getInstance().getString(R.string.collect));
                popupMenuItemList.add("回复");
                PopupList normalViewPopupList = new PopupList(mContext);
                normalViewPopupList.bind(mImg, popupMenuItemList, new PopupList.PopupListListener() {
                    @Override
                    public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                        return true;
                    }

                    @Override
                    public void onPopupListClick(View contextView, int contextPosition, int position) {
                        MyImageView imageView = (MyImageView) contextView;
                        final int myPosition = imageView.getMsgId();
                        SendMessageResponse bean = mList.get(myPosition);
                        if (mActionListener != null) {
                            if (position == 0) {
                                mActionListener.onMessageDelete(bean);
                            } else if (position == 1) {
                                mActionListener.onMessageCollect(bean);
                            } else if (position == 2) {
                                mActionListener.onReplyClick(bean);
                            }
                        }
                    }
                });
            }
            if (payload == null) {
                mImg.setMsgId(position);
                if (selectedPicChannel > 0) {
                    String url = bean.getImg().getThumb();
                    url = url.replaceAll(picChannel.getData().get(0), picChannel.getData().get(selectedPicChannel));
                    ImgLoader.ChatRoomRoundCorners(url, mImg, R.mipmap.web_default);
                } else {
                    ImgLoader.ChatRoomRoundCorners(bean.getImg().getThumb(), mImg, R.mipmap.web_default);
                }
            }
        }
    }

    private int selectedPicChannel;

    public void setSelectedPicChannel(int selectedPicChannel) {
        this.selectedPicChannel = selectedPicChannel;
    }

    private PicChannel picChannel;

    public void setPicChannel(PicChannel picChannel) {
        this.picChannel = picChannel;
    }

    class SelfImageVh extends ImageVh {
        View mFailIcon;
        View mLoading;

        public SelfImageVh(View itemView) {
            super(itemView);
            mFailIcon = itemView.findViewById(R.id.icon_fail);
            mLoading = itemView.findViewById(R.id.loading);
        }

        @Override
        public void setData(SendMessageResponse bean, int position, Object payload) {
            super.setData(bean, position, payload);
            if (bean.getTotype() == SendMessageResponse.TOTYPE_FRIEND) {
                popupMenuItemList.clear();
                popupMenuItemList.add(Application.getInstance().getString(R.string.message_text_delete));
                popupMenuItemList.add(Application.getInstance().getString(R.string.collect));
                popupMenuItemList.add("回复");
                PopupList normalViewPopupList = new PopupList(mContext);
                normalViewPopupList.bind(mImg, popupMenuItemList, new PopupList.PopupListListener() {
                    @Override
                    public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                        return true;
                    }

                    @Override
                    public void onPopupListClick(View contextView, int contextPosition, int position) {
                        MyImageView imageView = (MyImageView) contextView;
                        final int myPosition = imageView.getMsgId();
                        SendMessageResponse bean = mList.get(myPosition);
                        if (mActionListener != null) {
                            if (position == 0) {
                                mActionListener.onMessageDelete(bean);
                            } else if (position == 1) {
                                mActionListener.onMessageCollect(bean);
                            } else if (position == 2) {
                                mActionListener.onReplyClick(bean);
                            }
                        }
                    }
                });
            }
        }
    }


    class SelfVideoVh extends VideoVh {
        View mFailIcon;
        View mLoading;

        public SelfVideoVh(View itemView) {
            super(itemView);
            mFailIcon = itemView.findViewById(R.id.icon_fail);
            mLoading = itemView.findViewById(R.id.loading);
        }

        @Override
        public void setData(SendMessageResponse bean, int position, Object payload) {
            super.setData(bean, position, payload);
            if (bean.getTotype() == SendMessageResponse.TOTYPE_FRIEND) {
                popupMenuItemList.clear();
                popupMenuItemList.add(Application.getInstance().getString(R.string.message_text_delete));
                popupMenuItemList.add(Application.getInstance().getString(R.string.collect));
                popupMenuItemList.add("回复");
                PopupList normalViewPopupList = new PopupList(mContext);
                normalViewPopupList.bind(mImg, popupMenuItemList, new PopupList.PopupListListener() {
                    @Override
                    public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                        return true;
                    }

                    @Override
                    public void onPopupListClick(View contextView, int contextPosition, int position) {
                        MyImageView imageView = (MyImageView) contextView;
                        final int myPosition = imageView.getMsgId();
                        SendMessageResponse bean = mList.get(myPosition);
                        if (mActionListener != null) {
                            if (position == 0) {
                                mActionListener.onMessageDelete(bean);
                            } else if (position == 1) {
                                mActionListener.onMessageCollect(bean);
                            } else if (position == 2) {
                                mActionListener.onReplyClick(bean);
                            }
                        }
                    }
                });
            }
        }
    }

    class RedPackVh extends Vh {
        public RedPackVh(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.bl_red_pack).setOnClickListener(mOnRedPackClickListener);
        }

        @Override
        public void setData(SendMessageResponse bean, int position, Object payload) {
            super.setData(bean, position, payload);
            itemView.findViewById(R.id.bl_red_pack).setTag(bean);
            BubbleLayout bubbleLayout = itemView.findViewById(R.id.bl_red_pack);
            ImageView imageView = itemView.findViewById(R.id.iv_red_pack);
            TextView textView = itemView.findViewById(R.id.text);
            if (bean.getRed_status()) {
                bubbleLayout.setmBubbleBgColor(R.color.pk_red_chat);
                imageView.setImageResource(R.mipmap.packet_item1);
            } else {
                bubbleLayout.setmBubbleBgColor(R.color.pk_red_chat_gray);
                imageView.setImageResource(R.mipmap.packet_item2);
            }
            if (TextUtils.isEmpty(bean.getContents())) {
                textView.setText(mContext.getResources().getString(R.string.default_greetings));
            } else {
                textView.setText(bean.getContents());
            }
        }
    }

    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }

    public interface ActionListener {
        void onImageClick(MyImageView imageView, int x, int y, SendMessageResponse bean);

        void onAvatarLongClick(SendMessageResponse bean);

        void onMessageDelete(SendMessageResponse bean);

        void onMessageCopy(SendMessageResponse bean);

        void onMessageCollect(SendMessageResponse bean);

        void onRedPackClick(SendMessageResponse bean, View view);

        void onAvatarClick(SendMessageResponse bean);

        void onVideoClick(SendMessageResponse bean);

        void onReplyClick(SendMessageResponse bean);

    }

    public void release() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
        mActionListener = null;
        mOnImageClickListener = null;
    }

}
