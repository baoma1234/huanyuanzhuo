package caixin.android.com.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.caixin.huanyu.R;

import caixin.android.com.utils.DpUtil;
import caixin.android.com.utils.FaceUtil;
import caixin.android.com.widget.OnFaceClickListener;

import java.util.List;


/**
 * Created by cxf on 2018/7/11.
 * 聊天表情的RecyclerView Adapter
 */

public class ImChatFaceAdapter extends RecyclerView.Adapter<ImChatFaceAdapter.Vh> {

    private List<String> mList;
    private View.OnClickListener mOnClickListener;
    private Context mContext;
    private LayoutInflater mInflater;

    public ImChatFaceAdapter(List<String> list, Context context, final OnFaceClickListener onFaceClickListener) {
        mList = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = v.getTag();
                if (tag != null && onFaceClickListener != null) {
                    String str = (String) v.getTag();
                    if (!TextUtils.isEmpty(str)) {
                        if ("<".equals(str)) {
                            onFaceClickListener.onFaceDeleteClick();
                        } else {
                            onFaceClickListener.onFaceClick(str, v.getId());
                        }
                    }
                }
            }
        };
    }

    @Override
    public Vh onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Vh(mInflater.inflate(R.layout.item_list_face_text, parent, false));
    }

    @Override
    public void onBindViewHolder(Vh vh, int position) {
        vh.setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class Vh extends RecyclerView.ViewHolder {
        TextView mImageView;
        public Vh(View itemView) {
            super(itemView);
            mImageView = (TextView) itemView;
            mImageView.setOnClickListener(mOnClickListener);
        }

        void setData(String str) {
            mImageView.setTag(str);
            if (!TextUtils.isEmpty(str)) {
                if ("<".equals(str)) {
                    String htmlFor02 = "<img src='" + R.mipmap.icon_face_delete + "'>";
                    mImageView.setText(Html.fromHtml(htmlFor02, new Html.ImageGetter() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public Drawable getDrawable(String source) {
                            int id = Integer.parseInt(source);
                            Drawable drawable = mContext.getResources().getDrawable(id, null);
                            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                            return drawable;
                        }
                    }, null));
                } else {
//                    int imgRes = FaceUtil.getFaceImageRes(str);
//                    mImageView.setId(imgRes);
                    mImageView.setText(str);
                }
            } else {
                mImageView.setClickable(false);
            }
        }
    }
}
