package caixin.android.com.widget.friendcicle;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.caixin.huanyu.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.entity.FriendNewsEntity;
import caixin.android.com.utils.MMKVUtil;

/**
 * Created by yiwei on 16/7/9.
 */
public class CommentListView extends LinearLayout {
    private int itemColor;
    private int itemSelectorColor;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private List<FriendNewsEntity.CommentsBean> mDatas;
    private LayoutInflater layoutInflater;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setDatas(List<FriendNewsEntity.CommentsBean> datas) {
        if (datas == null) {
            datas = new ArrayList<FriendNewsEntity.CommentsBean>();
        }
        mDatas = datas;
        notifyDataSetChanged();
    }

    public List<FriendNewsEntity.CommentsBean> getDatas() {
        return mDatas;
    }

    public CommentListView(Context context) {
        super(context);
    }

    public CommentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public CommentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PraiseListView, 0, 0);
        try {
            //textview的默认颜色
            itemColor = typedArray.getColor(R.styleable.PraiseListView_item_color, getResources().getColor(R.color.praise_item_default));
            itemSelectorColor = typedArray.getColor(R.styleable.PraiseListView_item_selector_color, getResources().getColor(R.color.praise_item_selector_default));

        } finally {
            typedArray.recycle();
        }
    }

    public void notifyDataSetChanged() {

        removeAllViews();
        if (mDatas == null || mDatas.size() == 0) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mDatas.size(); i++) {
            final int index = i;
            View view = getView(index);
            if (view == null) {
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }

            addView(view, index, layoutParams);
        }

    }

    private View getView(final int position) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(getContext());
        }
        View convertView = layoutInflater.inflate(R.layout.item_friend_circle_comment, null, false);
        TextView commentTv = (TextView) convertView.findViewById(R.id.commentTv);
        final CircleMovementMethod circleMovementMethod = new CircleMovementMethod(itemSelectorColor, itemSelectorColor);
        final FriendNewsEntity.CommentsBean bean = mDatas.get(position);
        String name = bean.getNikename();
        String toReplyName = "";
        if (bean.getReply() != 0) {
            toReplyName = bean.getUnikename();
        }
        final XPopup.Builder xPopBuilder = new XPopup.Builder(getContext())
                .hasShadowBg(false)
                .watchView(commentTv);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(setClickableSpan(name, bean.getIdX()));

        if (!TextUtils.isEmpty(toReplyName)) {
            builder.append(" 回复 ");
            builder.append(setClickableSpan(toReplyName, bean.getUidX()));
        }
        builder.append(": ");
        //转换表情字符
        String contentBodyStr = bean.getContentX();
        builder.append(contentBodyStr);
        commentTv.setText(builder);
        commentTv.setMovementMethod(circleMovementMethod);
        commentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    if (bean.getUidX() == MMKVUtil.getUserInfo().getId()) {
                        xPopBuilder
                                .asAttachList(new String[]{"复制", "删除"}, null,
                                        new OnSelectListener() {
                                            @Override
                                            public void onSelect(int position, String text) {
                                                if (position == 0) {
                                                    if (onItemClickListener != null) {
                                                        onItemClickListener.onCopyClick(bean);
                                                    }
                                                } else {
                                                    if (onItemClickListener != null) {
                                                        onItemClickListener.onDeleteClick(bean);
                                                    }
                                                }
                                            }
                                        })
                                .show();
                    } else {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(position, bean.getIdX());
                        }
                    }
                }
            }
        });
        commentTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    if (onItemLongClickListener != null) {
                        onItemLongClickListener.onItemLongClick(position);
                    }
                    return true;
                }
                return false;
            }
        });

        return convertView;
    }

    @NonNull
    private SpannableString setClickableSpan(final String textStr, final int id) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new SpannableClickable(itemColor) {
                                    @Override
                                    public void onClick(View widget) {
//                                        Toast.makeText(MyApplication.getContext(), textStr + " &id = " + id, Toast.LENGTH_SHORT).show();
                                    }
                                }, 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

    public static interface OnItemClickListener {
        public void onItemClick(int position, int toUid);

        public void onDeleteClick(FriendNewsEntity.CommentsBean bean);

        public void onCopyClick(FriendNewsEntity.CommentsBean bean);
    }

    public static interface OnItemLongClickListener {
        public void onItemLongClick(int position);
    }
}
